package testing;

import java.io.IOException;

import ServerClient.BertSemanticGraderModel;
import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;

public class ModelTrainer {

	public static void main(String[] args) {
		BertSemanticGraderModel model = new BertSemanticGraderModel();
		
		try {
			model.loadAndTrainModel(2, 32, 500);
		} catch (ModelNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranslateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
