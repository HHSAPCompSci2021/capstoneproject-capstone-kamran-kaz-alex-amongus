package ServerClient;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.*;

/**
 * A Class that loads a pre-trained BERT Classification Model and adds a Long-Short Term Memory 
 * neural network on top of BERT Transformers for extremely high adaptability and accuracy (85%+ model accuracy).
 * This model is currently non functional because the TensorFlow Build is not functional. 
 * This model is currently non functional because it is unable to locate the binary.
 * 
 * @author Kamran Hussain
 *
 */
@SuppressWarnings("rawtypes")
public class TFBertModel {

    public static void main(String[] args) throws Exception {
        System.out.println("TensorFlow version: " + TensorFlow.version());
        Path modelPath = Paths.get(TFBertModel.class.getResource("/rsc/saved_model.pb").toURI());
        byte[] graph = Files.readAllBytes(modelPath);

        try (Graph g = new Graph()) {
            g.importGraphDef(graph);

            //Just print needed operations for debug
            System.out.println(g.operation("input").output(0));
            System.out.println(g.operation("not_activated_output").output(0));

            //open session using imported graph
            try (Session sess = new Session(g)) {
                float[][] inputData = {{4, 3, 2, 1}};

                // We have to create tensor to feed it to session,
                Tensor inputTensor = Tensor.create(inputData, Float.class);
//                float[][] output = predict(sess, inputTensor);
//                for (int i = 0; i < output[0].length; i++) {
//                    System.out.println(output[0][i]);
//                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static String predict(Session sess, Tensor inputTensor) {
        Tensor result = sess.runner()
                .feed("input", inputTensor)
                .fetch("not_activated_output").run().get(0);
        float[][] outputBuffer = new float[1][3];
        result.copyTo(outputBuffer);
        
        String[] labelDict = new String[] {"contradiction", "correlation", "neutral"};
        
        float sum = 0;
        for(int i = 0; i<outputBuffer.length; i++) {
        	for(int j = 0; j<outputBuffer[0].length; i++) {
        		sum+= outputBuffer[i][j];
        	}
        }
        return labelDict[(int)sum];
    }

}