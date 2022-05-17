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
//		String paragraph = "BBC Japan was a general entertainment Channel. "
//			                + "Which operated between December 2004 and April 2006. "
//			                + "It ceased operations after its Japanese distributor folded.";
//		System.out.print("Enter Question: ");
//		try (Scanner kboard = new Scanner(System.in)) {
//			CustomModel mdl = new CustomModel();
//			String question = kboard.nextLine();
//			String answer = mdl.predict(paragraph, question);
//			System.out.println(answer);
//			String[] rubric = new String[] {"The quick brown fox", "jumped over the lazy dog."};
//			mdl.predict(paragraph, rubric);
//		}
		
		// get essays & rubric
		HashMap<String, String[][]> map = new HashMap<>();
		
		Grader g = new Grader();
		
		for (Map.Entry<String, String[][]> entry : map.entrySet()) {
			// entry.getKey(), entry.getValue()
			String grade = g.getGrade(entry.getKey(), entry.getValue());
			
			
		}
		
		
		
		// ["essay", String[][]] 
		// [0, 1]
		// [0, 1]
		
		
		
		// loop and feed to grader
		
		
		
	}
}
