package ServerClient;

import java.io.IOException;
import java.util.Scanner;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;

/**
 * Runs the server side program
 * @author Kamran Hussain
 *
 */
public class Main {
	public static void main(String[] args) throws IOException, TranslateException, ModelException {
		String paragraph = "BBC Japan was a general entertainment Channel. "
			                + "Which operated between December 2004 and April 2006. "
			                + "It ceased operations after its Japanese distributor folded.";
		Model mdl = new Model();
		System.out.println(mdl.predict(paragraph, paragraph));
//		System.out.print("Enter Question: ");
//		try (Scanner kboard = new Scanner(System.in)) {
//			String question = kboard.nextLine();
//			String answer = mdl.predict(paragraph, question);
//			System.out.println(answer);
//		}
	}
}
