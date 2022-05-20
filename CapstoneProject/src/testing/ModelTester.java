package testing;

import java.util.Scanner;

import ServerClient.BertSemanticGraderModel;

public class ModelTester {

	public static void main(String[] args) {
		try {
			Scanner kboard = new Scanner(System.in);
			System.out.println("Enter first phrase: ");
			String in1 = kboard.nextLine();
			System.out.println("Enter second phrase: ");
			String in2 = kboard.nextLine();
			new BertSemanticGraderModel().predict("Hello World", "Hi there");
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
