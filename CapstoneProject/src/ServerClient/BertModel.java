package ServerClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.proto.framework.GraphDef;

public class BertModel {
	
	private TensorFlow tf;

	public BertModel() {
		System.out.println("constructing bert model");
		Path modelPath = Paths.get(BertModel.class.getResource("saved_model.pb").toURI());
		byte[] graph = Files.readAllBytes(modelPath);

		try (Graph g = new Graph()) {
		    g.importGraphDef(graph);
		    //open session using imported graph
		    try (Session sess = new Session(g)) {
		        float[][] inputData = {{4, 3, 2, 1}};
		        Tensor inputTensor = Tensor.create(inputData, Float.class);
		        float[][] output = predict(sess, inputTensor);
		        for (int i = 0; i < output[0].length; i++) {
		            System.out.println(output[0][i]); //should be 41. 51.5 62.
		        }
		    }
		}
	}
	
	private static float[][] predict(Session sess, Tensor inputTensor) {
	    Tensor result = sess.runner()
	            .feed("input", inputTensor)
	            .fetch("not_activated_output").run().get(0);
	    float[][] outputBuffer = new float[1][3];
	    result.copyTo(outputBuffer);
	    return outputBuffer;
	}
}
