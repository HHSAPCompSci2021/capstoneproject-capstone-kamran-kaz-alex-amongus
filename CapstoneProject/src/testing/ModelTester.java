package testing;

import ServerClient.BertSemanticGraderModel;

public class ModelTester {

	public static void main(String[] args) {
		try {
			new BertSemanticGraderModel(false).predict("hello world", "hi world");
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
