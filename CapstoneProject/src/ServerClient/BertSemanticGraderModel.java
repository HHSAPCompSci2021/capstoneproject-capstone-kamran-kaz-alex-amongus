package ServerClient;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.basicdataset.tabular.CsvDataset;
import ai.djl.basicdataset.utils.DynamicBuffer;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.metric.Metrics;
import ai.djl.modality.Classifications;
import ai.djl.modality.nlp.DefaultVocabulary;
import ai.djl.modality.nlp.Vocabulary;
import ai.djl.modality.nlp.bert.BertFullTokenizer;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Activation;
import ai.djl.nn.Block;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.nn.norm.Dropout;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.EasyTrain;
import ai.djl.training.Trainer;
import ai.djl.training.TrainingResult;
import ai.djl.training.dataset.RandomAccessDataset;
import ai.djl.training.evaluator.Accuracy;
import ai.djl.training.listener.SaveModelTrainingListener;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.Batchifier;
import ai.djl.translate.PaddingStackBatchifier;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

/**
 * A BERT Model Version using the Deep Java Library Bi-directional Encoders for Transformers (BERT) Wrapper. This model uses the DistilBERT 
 * version of the greater BERT model, a lighter, faster model with less parameters. We are using BERT's built in tokenizer to convert the 
 * input strings into numerical tokens that capture a feature of the text. There are two private data classes contained within this model as 
 * overrides in order to specialize the BERT model for classification and similarity calculations. 
 * 
 * Adapted from DLJ Examples.
 * 
 * @author Kamran Hussain
 * @version 1.0.1
 * 
 *
 */
public class BertSemanticGraderModel {
	
	private ZooModel<NDList, NDList> embedding;
	private Model model;

	/**
	 * Creates a new DLJ BERT model object and builds the necessary engines and support frameworks.
	 */
	public BertSemanticGraderModel() {
		System.out.println("You are using: " + Engine.getInstance().getEngineName() + " Engine");
		
		try {
			buildModel();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The BERT Featurizer that tokenizes the input data and does preprocessing
	 * @author Kamran Hussain
	 *
	 */
	private final class BertFeaturizer implements CsvDataset.Featurizer {

		private final BertFullTokenizer tokenizer;
		private final int maxLength; // the cut-off length

		/**
		 * Creates a new featurizer and uses it to tokenize the input data.
		 * @param tokenizer  A BERT tokenizer that is loaded and used to produce numerical vector representations of the input data
		 * @param maxLength The max length of the token vectors
		 * @postcondition The Model WILL crash if the model cannot be loaded for any reason
		 */
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

	/**
	 * Reads a CSV and creates a new dataset with the given parameters
	 * @param batchSize Training Batch size that will be incorporated into the dataset
	 * @param tokenizer The BERT Tokenizer object completely loaded and pretrained for tokenization
	 * @param maxLength Maximum token length for the model, tune to highest possible accounting for training time and processing power
	 * @param limit Set limit on token sizes
	 * @param file File to read into a dataset. Must be a Comma Separated Value (CSV) file.
	 * @return A CsvDataset Object containing the loaded data for training
	 */
	private CsvDataset getDataset(int batchSize, BertFullTokenizer tokenizer, int maxLength, int limit, String file) {
		Path datasetPath = FileSystems.getDefault().getPath("resources", file);
		float paddingToken = tokenizer.getVocabulary().getIndex("[PAD]");
		return CsvDataset.builder().optCsvFile(datasetPath) // load from file
				.setCsvFormat(CSVFormat.DEFAULT.withHeader()) // Setting CSV loading format
				.setSampling(batchSize, true) // make sample size and random access
				.optLimit(limit) //limit on token sizes
				.addFeature(new CsvDataset.Feature("sentence1", new BertFeaturizer(tokenizer, maxLength)))
				.addFeature(new CsvDataset.Feature("sentence2", new BertFeaturizer(tokenizer, maxLength)))
				.addLabel(new CsvDataset.Feature("label", (buf, data) -> buf.put(Float.parseFloat(data) - 1.0f)))
				.optDataBatchifier(PaddingStackBatchifier.builder()
						.optIncludeValidLengths(false)
						.addPad(0, 0, (m) -> m.ones(new Shape(1)).mul(paddingToken)).build()) 
				.build();
	}

	/**
	 * Procedural code for building and loading the model then creating the necessary datasets. 
	 * @throws ModelNotFoundException If the BERT model cannot be located from a save or downloaded from the hugging face hub or DeeJavaLibrary
	 * @throws MalformedModelException If the model is loaded improperly or missing some weights/dependencies.
	 * @throws IOException If dependencies cannot be found or properly loaded.
	 * @throws TranslateException If an error occurs when setting the weights of the model.
	 */
	public void buildModel() throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
		// MXNet base model
		String modelUrls = "https://resources.djl.ai/test-models/distilbert.zip";
		if ("PyTorch".equals(Engine.getInstance().getEngineName())) {
			modelUrls = "https://resources.djl.ai/test-models/traced_distilbert_wikipedia_uncased.zip";
		}

		Criteria<NDList, NDList> criteria = Criteria.builder().optApplication(Application.NLP.WORD_EMBEDDING)
				.setTypes(NDList.class, NDList.class).optModelUrls(modelUrls).optProgress(new ProgressBar()).build();
		embedding = criteria.loadModel();

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
		
		model = Model.newInstance("SentenceSimilarityClassification");
		model.setBlock(classifier);
		
		System.out.println("MODEL LOADED PROPERLY\nNREADY FOR INFERENCING OR TRAINING");
		
		try {
			train();
		} catch(IOException e) {
			e.printStackTrace();
		} catch (TranslateException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Trains the model by loading datasets and beginning the training loop. Calculates training time and loss while operating.
	 * @throws IOException If any dependencies or output directories are unaccessible
	 * @throws TranslateException If the translator model is unable to accept and properly process the input data
	 */
	public void train() throws IOException, TranslateException {
		// Prepare the vocabulary
		DefaultVocabulary vocabulary = DefaultVocabulary.builder().addFromTextFile(embedding.getArtifact("vocab.txt"))
				.optUnknownToken("[UNK]").build();
		// Prepare dataset
		int maxTokenLength = 500; // cutoff tokens length
		int batchSize = 8;
		int limit = Integer.MAX_VALUE;
		//int limit = 512; // uncomment for quick testing

		BertFullTokenizer tokenizer = new BertFullTokenizer(vocabulary, true);
		
		// load the training and evaluation dataset's
		RandomAccessDataset trainingSet = getDataset(batchSize, tokenizer, maxTokenLength, limit, "snli-1.0-train-cleaned.csv");
		RandomAccessDataset validationSet = getDataset(batchSize, tokenizer, maxTokenLength, limit, "snli-1.0-test-cleaned.csv");

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

		model.save(Paths.get("build/model"), "SemanticSimilarityClassification.param");
	}
	
	/**
	 * Predicts the similarity and whether or not the students essay satisfies the requirements outlined by the rubric.
	 * @param document The student essay to comapre to the rubric
	 * @param rubricCategory The String of the rubric category loaded from the dataset
	 * @throws TranslateException If an error occurs during prediction in the predictor layer.
	 * @throws IOException If any resources or dependencies cannot be found.
	 */
	public void predict(String document, String rubricCategory) throws TranslateException, IOException {
		BertFullTokenizer tokenizer = new BertFullTokenizer(DefaultVocabulary.builder().addFromTextFile(embedding.getArtifact("vocab.txt"))
				.optUnknownToken("[UNK]").build(), true);
		Predictor<String, Classifications> predictor = model.newPredictor(new ModelTranslator(tokenizer));

		predictor.predict(document); //need to modify to accept two string inputs
	}

	
	private class ModelTranslator implements Translator<String, Classifications> {

		private BertFullTokenizer tokenizer;
		private Vocabulary vocab;
		private List<String> ranks;

		public ModelTranslator(BertFullTokenizer tokenizer) {
			this.tokenizer = tokenizer;
			vocab = tokenizer.getVocabulary();
			ranks = Arrays.asList("1", "2", "3"); // 1 is correspondence and 2 is contradiction
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
}
