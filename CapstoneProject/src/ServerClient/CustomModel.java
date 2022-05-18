package ServerClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.modality.nlp.bert.BertFullTokenizer;
import ai.djl.modality.nlp.qa.QAInput;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

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
@SuppressWarnings("unused")
public final class CustomModel {
	
	public CustomModel() {
		
	}

    /**
     * Answers a question based on the input essay.
     * @param paragraph The document as a string
     * @param rubric The question that needs to be answered
     * @return The model generated answer String
     * @throws IOException If the resource directory is not found
     * @throws TranslateException If the translation layer fails
     * @throws ModelException If the model cannot be loaded
     */
    public String predict(String paragraph, String rubric) throws IOException, TranslateException, ModelException {
        QAInput input = new QAInput(rubric, paragraph);

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
     * Tokenizes the input document with the BERT Tokenizer
     * @param document Student Document as a String 
     * @return A List of tokens for BERT processing
     */
    private List<String> tokenize(String document) {
    	BertFullTokenizer tokenizer = new BertFullTokenizer(null, true);
    	return tokenizer.tokenize(document);
    }
}