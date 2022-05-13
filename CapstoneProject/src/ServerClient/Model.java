package ServerClient;

import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.modality.nlp.qa.QAInput;
import ai.djl.ndarray.NDArray;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;

import edu.stanford.nlp.pipeline.*;

/**
 * The automatic grading model using BERT via DeepLearningJava and Word2Vec. 
 * 
 * @author Kamran Hussain
 * 
 *
 * <p>See for references:
 *
 * <ul>
 *   <li>the <a
 *       href="https://github.com/deepjavalibrary/djl/blob/master/jupyter/BERTQA.ipynb">jupyter
 *       demo</a> with more information about BERT.
 *   <li>the <a
 *       href="https://github.com/deepjavalibrary/djl/blob/master/examples/docs/BERT_question_and_answer.md">docs</a>
 *       for information about running this example.
 * </ul>
 */
public final class Model {
	
	private Properties props;
	private StanfordCoreNLP nlp;
	
	public Model() {
		props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit");
		nlp = new StanfordCoreNLP(props);
	}

    /**
     * Answers a question based on the input essay.
     * @param paragraph The document as a string
     * @param question The question that needs to be answered
     * @return The model generated answer String
     * @throws IOException If the resource directory is not found
     * @throws TranslateException If the translation layer fails
     * @throws ModelException If the model cannot be loaded
     */
    public String predict(String paragraph, String question) throws IOException, TranslateException, ModelException {
        QAInput input = new QAInput(question, paragraph);

        Criteria<QAInput, String> criteria =
                Criteria.builder()
                        .optApplication(Application.NLP.QUESTION_ANSWER)
                        .setTypes(QAInput.class, String.class)
                        .optFilter("backbone", "bert")
                        .optEngine(Engine.getDefaultEngineName())
                        .optProgress(new ProgressBar())
                        .build();

        try (ZooModel<QAInput, String> model = criteria.loadModel()) {
            try (Predictor<QAInput, String> predictor = model.newPredictor()) {
                return predictor.predict(input);
            }
        }
    }
    
    /**
     * Procedural code for grading the essay
     * @param document The Student essay
     * @param rubric The Rubric for the assignment
     * @throws ModelException 
     * @throws TranslateException 
     * @throws IOException 
     */
    public void predict(String document, String[] rubric) throws IOException, TranslateException, ModelException {
    	CoreDocument doc = tokenize(document);
    	ArrayList<CoreDocument> rub = new ArrayList<>();
    	for(String str : rubric) { 
    		rub.add(tokenize(str));
    	}
    	
    	ArrayList<double[]> tokens = vectorize(doc);
    	
    	System.out.println(this.predict(doc.toString(), rub.toString()) + tokens.toString());
    }
    
    /**
     * Tokenizes and splits the input document
     * @param document Document or Essay as a string
     * @return A CoreDocument Object containing all pre-processing annotations
     */
    private CoreDocument tokenize(String document) {
    	CoreDocument doc = new CoreDocument(document);
    	nlp.annotate(doc);
    	System.out.println(doc.annotation().toString());
    	return doc;
    }
    
    /**
     * Vectorize's the document from the segmented tokens 
     * @param doc Tokenized and pre-processed CoreDocument object
     * @return A Float array containing the word2vec embeddings of the document
     */
    private ArrayList<double[]> vectorize(CoreDocument doc) {
    	ArrayList<double[]> res = new ArrayList<>();
    	try {
			WordVectors wordVectors = WordVectorSerializer.loadTxtVectors(new File("glove.6B.50d.txt"));
			for(CoreSentence s : doc.sentences()) {
				res.add(wordVectors.getWordVector(s.toString()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	return res;
    }
}