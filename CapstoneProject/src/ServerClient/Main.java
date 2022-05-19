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
		
		// Load essays and rubric from database
		
		
		//Put them into the hashmap
		HashMap<String, String[][]> map = new HashMap<>();
		
		//
		Grader g = new Grader();
		
		//grades
		for (Map.Entry<String, String[][]> entry : map.entrySet()) {
			// get grade from grader
			String[] grade = g.getGrade(entry.getKey(), entry.getValue());
		}
		
		//Updates database if hashmap is empty
		
		
	}
}
