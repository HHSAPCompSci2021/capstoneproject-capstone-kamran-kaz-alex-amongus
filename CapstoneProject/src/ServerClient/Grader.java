package ServerClient;

import java.io.IOException;
import java.util.ArrayList;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.ops.transforms.Transforms;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import data.Submission;

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
		model = new BertSemanticGraderModel();
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
		
		String[] labels = new String[] {"A", "B", "C", "D", "F"};
		try {
			String[] grades = new String[rubric.length];
			for (int i = 0; i < rubric.length; i++) {
				//check this rubric row
				ArrayList<Float> similarity = new ArrayList<>();
				for (int j = 0; j < rubric[i].length; j++) {
					similarity.add(model.predict(document, rubric[i][j]));
				}
				
				//add the greatest similarity index to the grade array
				float greatestSim = 0;
				for(int a = 0; i<similarity.size(); i++) {
					if(similarity.get(i) > greatestSim) {
						greatestSim = similarity.get(i);
					}
				}
				grades[i] = labels[similarity.indexOf(greatestSim)];
			}
			return grades;
		} catch (IOException | TranslateException | ModelException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calculates the semantic similarity of two student essays
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
	 * Compares the similarity
	 * 
	 * @param studentDocument1
	 * @param studentDocument2
	 * @return A percentage representing how literally similar the two essays are
	 */
	public double literalSimilarity(String studentDocument1, String studentDocument2) {
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

	/**
	 * Checks if the two student documents
	 * 
	 * @param studentDocument1
	 * @param studentDocument2
	 * @return True if the documents have more than 40% literal similarity
	 */
	public boolean isPlagiarized(String studentDocument1, String studentDocument2) {
		return literalSimilarity(studentDocument1, studentDocument2) > 40.0;
	}
}
