package ServerClient;

import ai.djl.*;
import ai.djl.basicdataset.tabular.*;
import ai.djl.basicdataset.utils.*;
import ai.djl.engine.*;
import ai.djl.inference.*;
import ai.djl.metric.*;
import ai.djl.modality.*;
import ai.djl.modality.nlp.*;
import ai.djl.modality.nlp.bert.*;
import ai.djl.ndarray.*;
import ai.djl.ndarray.types.*;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.nn.norm.*;
import ai.djl.repository.zoo.*;
import ai.djl.training.*;
import ai.djl.training.dataset.*;
import ai.djl.training.evaluator.*;
import ai.djl.training.listener.*;
import ai.djl.training.loss.*;
import ai.djl.training.util.*;
import ai.djl.translate.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.commons.csv.*;

/**
 * A BERT Model Version using the Deep Java Library BERT Wrapper.
 * Adapted from DLJ Examples
 * 
 * @author kamran hussain
 *
 */
public class DLJBertModel {

	public DLJBertModel() {
		System.out.println("You are using: " + Engine.getInstance().getEngineName() + " Engine");
	}

	private final class BertFeaturizer implements CsvDataset.Featurizer {

		private final BertFullTokenizer tokenizer;
		private final int maxLength; // the cut-off length

		public BertFeaturizer(BertFullTokenizer tokenizer, int maxLength) {
			this.tokenizer = tokenizer;
			this.maxLength = maxLength;
		}

		/** {@inheritDoc} */
		@Override
		public void featurize(DynamicBuffer buf, String input) {
			Vocabulary vocab = tokenizer.getVocabulary();
			// convert sentence to tokens (toLowerCase for uncased model)
			List<String> tokens = tokenizer.tokenize(input.toLowerCase());
			// trim the tokens to maxLength
			tokens = tokens.size() > maxLength ? tokens.subList(0, maxLength) : tokens;
			// BERT embedding convention "[CLS] Your Sentence [SEP]"
			buf.put(vocab.getIndex("[CLS]"));
			tokens.forEach(token -> buf.put(vocab.getIndex(token)));
			buf.put(vocab.getIndex("[SEP]"));
		}
	}

	private CsvDataset getDataset(int batchSize, BertFullTokenizer tokenizer, int maxLength, int limit, String file) {
		Path datasetPath = FileSystems.getDefault().getPath("resources", file);
		float paddingToken = tokenizer.getVocabulary().getIndex("[PAD]");
		return CsvDataset.builder().optCsvFile(datasetPath) // load from file
				.setCsvFormat(CSVFormat.DEFAULT) // Setting CSV loading format
				.setSampling(batchSize, true) // make sample size and random access
				.optLimit(limit) //limit on token sizes
				.addFeature(new CsvDataset.Feature("sentence1", new BertFeaturizer(tokenizer, maxLength)))
				.addFeature(new CsvDataset.Feature("sentence2", new BertFeaturizer(tokenizer, maxLength)))
				.addLabel(new CsvDataset.Feature("label", (buf, data) -> buf.put(Float.parseFloat(data) - 1.0f)))
				.addCategoricalLabel("sentence1")
				.addCategoricalLabel("sentence1")
				.addCategoricalFeature("label")
				.optDataBatchifier(PaddingStackBatchifier.builder().optIncludeValidLengths(false)
						.addPad(0, 0, (m) -> m.ones(new Shape(1)).mul(paddingToken)).build()) 
				.build();
	}

	public void process() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
		// MXNet base model
		String modelUrls = "https://resources.djl.ai/test-models/distilbert.zip";
		if ("PyTorch".equals(Engine.getInstance().getEngineName())) {
			modelUrls = "https://resources.djl.ai/test-models/traced_distilbert_wikipedia_uncased.zip";
		}

		Criteria<NDList, NDList> criteria = Criteria.builder().optApplication(Application.NLP.WORD_EMBEDDING)
				.setTypes(NDList.class, NDList.class).optModelUrls(modelUrls).optProgress(new ProgressBar()).build();
		ZooModel<NDList, NDList> embedding = criteria.loadModel();

		Predictor<NDList, NDList> embedder = embedding.newPredictor();
		Block classifier = new SequentialBlock().add( // Text embedding layer
				ndList -> {
					NDArray data = ndList.singletonOrThrow();
					NDList inputs = new NDList();
					long batchSize = data.getShape().get(0);
					float maxLength = data.getShape().get(1);

					if ("PyTorch".equals(Engine.getInstance().getEngineName())) {
						inputs.add(data.toType(DataType.INT64, false));
						inputs.add(data.getManager().full(data.getShape(), 1, DataType.INT64));
						inputs.add(data.getManager().arange(maxLength).toType(DataType.INT64, false)
								.broadcast(data.getShape()));
					} else {
						inputs.add(data);
						inputs.add(data.getManager().full(new Shape(batchSize), maxLength));
					}
					// run embedding
					try {
						return embedder.predict(inputs);
					} catch (TranslateException e) {
						throw new IllegalArgumentException("embedding error", e);
					}
				})
				// classification layer
				.add(Linear.builder().setUnits(768).build()) // pre classifier
				.add(Activation::relu).add(Dropout.builder().optRate(0.2f).build())
				.add(Linear.builder().setUnits(2).build()) // 2 star rating
				.addSingleton(nd -> nd.get(":,0")); // Take [CLS] as the head
		
		Model model = Model.newInstance("SentenceSimilarityClassification");
		model.setBlock(classifier);
		
		try {
			train(model, embedding);
			predict(model, embedding);
		} catch(IOException e) {
			e.printStackTrace();
		} catch (TranslateException e) {
			e.printStackTrace();
		}
	}

		
	public void train(Model model, ZooModel<NDList, NDList> embedding) throws IOException, TranslateException {
		// Prepare the vocabulary
		DefaultVocabulary vocabulary = DefaultVocabulary.builder().addFromTextFile(embedding.getArtifact("vocab.txt"))
				.optUnknownToken("[UNK]").build();
		// Prepare dataset
		int maxTokenLength = 500; // cutoff tokens length
		int batchSize = 8;
		int limit = Integer.MAX_VALUE;
		// int limit = 512; // uncomment for quick testing

		BertFullTokenizer tokenizer = new BertFullTokenizer(vocabulary, true);
		
		// load the training and evaluation dataset's
		RandomAccessDataset trainingSet = getDataset(batchSize, tokenizer, maxTokenLength, limit, "snli-1.0-train-cleaned.csv");
		RandomAccessDataset validationSet = getDataset(batchSize, tokenizer, maxTokenLength, limit, "snli-1.0-train-cleaned.csv");

		SaveModelTrainingListener listener = new SaveModelTrainingListener("build/model");
		listener.setSaveModelCallback(trainer -> {
			TrainingResult result = trainer.getTrainingResult();
			Model model2 = trainer.getModel();
			// track for accuracy and loss
			float accuracy = result.getValidateEvaluation("Accuracy");
			model2.setProperty("Accuracy", String.format("%.5f", accuracy));
			model2.setProperty("Loss", String.format("%.5f", result.getValidateLoss()));
		});
		
		DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.softmaxCrossEntropyLoss()) // loss type
				.addEvaluator(new Accuracy()).optDevices(Engine.getInstance().getDevices(1)) // train using single GPU
				.addTrainingListeners(TrainingListener.Defaults.logging("build/model")).addTrainingListeners(listener);

		int epoch = 2;

		Trainer trainer = model.newTrainer(config);
		trainer.setMetrics(new Metrics());
		Shape encoderInputShape = new Shape(batchSize, maxTokenLength);
		
		// initialize trainer with proper input shape
		trainer.initialize(encoderInputShape);
		EasyTrain.fit(trainer, epoch, trainingSet, validationSet);
		System.out.println(trainer.getTrainingResult());

		model.save(Paths.get("build/model"), "amazon-review.param");
	}
	
	public void predict(Model model, ZooModel<NDList, NDList> embedding) throws TranslateException, IOException {
		BertFullTokenizer tokenizer = new BertFullTokenizer(DefaultVocabulary.builder().addFromTextFile(embedding.getArtifact("vocab.txt"))
				.optUnknownToken("[UNK]").build(), true);
		String review = "It works great, but it takes too long to update itself and slows the system";
		Predictor<String, Classifications> predictor = model.newPredictor(new ModelTranslator(tokenizer));

		predictor.predict(review);
	}

	private class ModelTranslator implements Translator<String, Classifications> {

		private BertFullTokenizer tokenizer;
		private Vocabulary vocab;
		private List<String> ranks;

		public ModelTranslator(BertFullTokenizer tokenizer) {
			this.tokenizer = tokenizer;
			vocab = tokenizer.getVocabulary();
			ranks = Arrays.asList("1", "2"); // 1 is correspondence and 2 is contradiction
		}

		@Override
		public Batchifier getBatchifier() {
			return Batchifier.STACK;
		}

		@Override
		public NDList processInput(TranslatorContext ctx, String input) {
			List<String> tokens = tokenizer.tokenize(input);
			float[] indices = new float[tokens.size() + 2];
			indices[0] = vocab.getIndex("[CLS]");
			for (int i = 0; i < tokens.size(); i++) {
				indices[i + 1] = vocab.getIndex(tokens.get(i));
			}
			indices[indices.length - 1] = vocab.getIndex("[SEP]");
			return new NDList(ctx.getNDManager().create(indices));
		}

		@Override
		public Classifications processOutput(TranslatorContext ctx, NDList list) {
			return new Classifications(ranks, list.singletonOrThrow().softmax(0));
		}
	}
	
	public static void main(String[] args) {
		try {
			new DLJBertModel().process();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
