package ServerClient;

import java.io.IOException;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.nd4j.linalg.ops.transforms.Transforms;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import data.Submission;

/**
 * The class that grades essays and checks for plagiarism. This loads the model and updates the students grade. It also checks for plagiarism.
 * @author Kamran Hussain
 *
 */
public class Grader {
	
	private CustomModel model;
	
	public Grader() {
		model = new CustomModel();
	}

	public String getGrade(String document, String[][] rubric[][]) {
		try {
			model.predict(document, rubric[0][0]);
		} catch (IOException | TranslateException | ModelException e) {
			e.printStackTrace();
			return "FATAL MODEL ERROR, PLEASE REBUILD THE MODEL";
		}
		
		if(isPlagiarized(document, rubric[0][0][0][0]) ) {
			return "F, plagiarized";
		}
		return "UNGRADED";
	}
	
	/**
     * Calculates the semantic similarity of two student essays
     * @param studentDocument1 The first student essay as a String
     * @param studentDocument2 The second student essay as a String
     * @return A percentage representing how semantically similar the two essays are
     */
    public double cosineSimForSentence(String studentDocument1, String studentDocument2) {
    	Word2Vec vector = new Word2Vec();
        return Transforms.cosineSim(vector.getWordVectorMatrix(studentDocument1), vector.getWordVectorMatrix(studentDocument2)) * 100;
    }
    
    /**
     * Compares the similarity
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
    	for(int i = 0; i < copy1.length(); i++) {
    		if(copy1.charAt(i) == copy2.charAt(i)) {
    			percentage++;
    		}
    	}
    	return (percentage/studentDocument1.length()) * 100;
    }
    
    /**
     * Checks if the two student documents 
     * @param studentDocument1
     * @param studentDocument2
     * @return True if the documents have more than 40% literal similarity
     */
    public boolean isPlagiarized(String studentDocument1, String studentDocument2) {
    	return literalSimilarity(studentDocument1, studentDocument2) > 40.0;
    }
}
