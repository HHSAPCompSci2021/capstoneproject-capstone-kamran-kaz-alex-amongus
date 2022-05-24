package ServerClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.ops.transforms.Transforms;

import ai.djl.MalformedModelException;
import ai.djl.ModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;

import data.Submission;
import data.Classroom;
import data.DatabaseModifier;

/**
 * Automatically grades essays and checks for plagiarism. This loads the model
 * and updates the students grade. it will loop through the ungraded essays and 
 * queue them into the grader. When the model returns, it finds the rubric
 * item with the highest correspondence and assigns that grade. When the calculations
 * are complete, the grade is updated in the database.
 * 
 * @author Kamran Hussain
 *
 */
public class Grader {

	private BertSemanticGraderModel model;

	public Grader() {
		model = new BertSemanticGraderModel(true);
		try {
			model.loadAndTrainModel(1, 4, 500, "snli-1.0-train-cleaned-short.csv", "snli-1.0-test-cleaned-short.csv");
		} catch (ModelNotFoundException | MalformedModelException | TranslateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * Calculates the student grades for each category and runs the student 
	 * essay and rubric categories through the model for inferencing.
	 * 
	 * @param document Student essay as a string
	 * @param rubric Assignment rubric as an array of strings
	 * @return An array of grades for each rubric category
	 */
	public String[] getGrade(String document, String[][] rubric) {
		if(isPlagiarized(document)) {
			return new String[] {"Plagiarized"};
		}
		
		String[] labels = new String[] {"A", "B", "C", "D", "F"};
		try {
			String[] grades = new String[rubric.length];
			for (int i = 0; i < rubric.length; i++) {
				//check this rubric row
				int j = 0;
				String match = "";
				while(!match.equals("correlation")) {
					match = model.predict(document, rubric[i][j]);
					if(j == labels.length) {
						match = "correlation";
					}
					j++;
				}
				grades[i] = labels[j];
			}
			return grades;
		} catch (IOException | TranslateException e) {
			e.printStackTrace();
		}
		
		return new String[] {"Un-Graded"};
	}
	
	/**
	 * Checks for plagiarism using cosine similarity and literal similarity checks. If either return a high enough threshhold, 
	 * @param stdntDoc content of a particular submission
	 * @return true if the document is deemed to be plagiarized and false if it is not
	 */
	public boolean isPlagiarized(String stdntDoc) {
		//get the database essays that have already been graded
		HashMap<String, Classroom> storedEssays = DatabaseModifier.getClassrooms();
		
		ArrayList<Submission> graded = new ArrayList<Submission>();
		
		for (Classroom c : storedEssays.values()) {
			for (int i = 0; i < c.getAssignments().size(); i++ ) {
				ArrayList<Submission> temp = c.getGraded(i);
				graded.addAll(temp);
			}
		}
		
		for(Submission s : graded) {
			 if (cosineSimForSentence(stdntDoc, s.getContent()) > 85.0 || literalSimilarity(stdntDoc, s.getContent()) > 40) {
				 return true;
			 }
		}
		
		return false;
	}

	/**
	 * Calculates the semantic similarity of two student essays using cosine similarity.
	 * 
	 * @param studentDocument1 The first student essay as a String
	 * @param studentDocument2 The second student essay as a String
	 * @return A percentage representing how semantically similar the two essays are
	 */
	public double cosineSimForSentence(String studentDocument1, String studentDocument2) {
		Word2Vec vector = new Word2Vec();
		return Transforms.cosineSim(vector.getWordVectorMatrix(studentDocument1),
				vector.getWordVectorMatrix(studentDocument2)) * 100;
	}

	/**
	 * Compares the similarity of two strings in terms of the repeating characters that exist in the two strings.
	 * 
	 * @param studentDocument1 first student document content
	 * @param studentDocument2 second student document content
	 * @return A percentage representing how literally similar the two essays are
	 */
	public double literalSimilarity(String studentDocument1, String studentDocument2) {
		if(studentDocument1.equals(studentDocument2)) return 100.0;
		String copy1 = String.copyValueOf(studentDocument1.toCharArray());
		String copy2 = String.copyValueOf(studentDocument2.toCharArray());
		copy1.replaceAll(" ", "");
		copy2.replaceAll(" ", "");
		double percentage = 0;
		for (int i = 0; i < copy1.length(); i++) {
			if (copy1.charAt(i) == copy2.charAt(i)) {
				percentage++;
			}
		}
		return (percentage / studentDocument1.length()) * 100;
	}
}
