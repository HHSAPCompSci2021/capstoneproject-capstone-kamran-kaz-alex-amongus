package ServerClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;

/**
 * Runs the server side program and initalizes its datacollection
 * @author Kamran Hussain
 *
 */
public class Main {
	private static final String ArrayList = null;

	public static void main(String[] args) throws IOException, TranslateException, ModelException {
		
		// get essays & rubric
		HashMap<String, String[][]> map = new HashMap<>();
		
		Grader g = new Grader();
		
		for (Map.Entry<String, String[][]> entry : map.entrySet()) {
			// entry.getKey(), entry.getValue()
			String[] grade = g.getGrade(entry.getKey(), entry.getValue());
			
		}
		
		// ["essay", String[][]] 
		// [0, 1]
		// [0, 1]
		
		
		
		// loop and feed to grader
		
		
		
		
		
	}
}
