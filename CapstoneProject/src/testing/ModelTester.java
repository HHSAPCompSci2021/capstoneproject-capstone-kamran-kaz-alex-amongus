package testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ServerClient.BertSemanticGraderModel;

public class ModelTester {

	public static void main(String[] args) {
		try {
			System.out.println(test());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String test() {
		try {
			ProcessBuilder pb = new ProcessBuilder("python", System.getProperty("user.dir") 
					+ "/build/model/ModelServerInterface.py", "Hello World", "Hi world");
			
			Process process = pb.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
			String res = "";
			String lines = "";
			while((lines=reader.readLine())!=null) {
				System.out.println(lines);
			}
			
			while((lines=readers.readLine()) != null) {
				System.out.println(lines);
			}
			
			return res;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return"";
	}
}
