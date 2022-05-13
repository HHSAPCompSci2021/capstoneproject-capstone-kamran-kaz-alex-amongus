package ServerClient;

/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.modality.nlp.qa.QAInput;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import edu.stanford.nlp.pipeline.*;

/**
 * An example of inference using BertQA.
 * 
 * @author Amazon.com - DeepJavaLibrary, Kamran Hussain
 * 
 *
 * <p>See:
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
    	
    	System.out.println(this.predict(doc.toString(), rub.toString()));
    }
    
    /**
     * Tokenizes and splits the input document
     * @param document Document or Essay as a string
     * @return A CoreDocument Object containing all pre-processing annotations
     */
    public CoreDocument tokenize(String document) {
    	CoreDocument doc = new CoreDocument(document);
    	nlp.annotate(doc);
    	System.out.println(doc.annotation().toString());
    	return doc;
    }
}