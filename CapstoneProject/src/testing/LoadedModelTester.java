package testing;

import java.io.IOException;
import java.util.Scanner;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;

import ServerClient.BertSemanticGraderModel;

public class LoadedModelTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BertSemanticGraderModel model = new BertSemanticGraderModel(true);
		try {
			Scanner kboard = new Scanner(System.in);
			System.out.println("Enter document param: ");
			String doc = kboard.nextLine();
			System.out.println("Enter rubric param: ");
			String rubric = kboard.nextLine();
			ComputationGraph mdl = model.loadTrained();
			model.predictTrained(doc, rubric, mdl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKerasConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedKerasConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
