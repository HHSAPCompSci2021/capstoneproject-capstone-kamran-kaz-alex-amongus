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
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An example of inference using BertQA.
 * 
 * @author Amazon.com - DeepJavaLibrary
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

    private static final Logger logger = LoggerFactory.getLogger(Model.class);

    public static void main(String[] args) throws IOException, TranslateException, ModelException {
    	System.out.print("Enter Question: ");
    	Scanner kboard = new Scanner(System.in);
        String question = kboard.nextLine();
        String paragraph =
                "BBC Japan was a general entertainment Channel. "
                        + "Which operated between December 2004 and April 2006. "
                        + "It ceased operations after its Japanese distributor folded.";
        String answer = Model.predict(paragraph, question);
        logger.info("Answer: {}", answer);
        System.out.println(answer);
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
    public static String predict(String paragraph, String question) throws IOException, TranslateException, ModelException {
        QAInput input = new QAInput(question, paragraph);
        logger.info("Paragraph: {}", input.getParagraph());
        logger.info("Question: {}", input.getQuestion());

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
}