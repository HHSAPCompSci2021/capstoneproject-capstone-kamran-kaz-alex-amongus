package testing;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ServerClient.BertSemanticGraderModel;

public class ModelTester {

	public static void main(String[] args) {
		try {
			test();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String test() {
		try {
			ProcessBuilder pb = new ProcessBuilder("python3", System.getProperty("user.dir") 
					+ "/build/model/ModelServerInterface.py", "Hello World", "hi world");
			
			Process process = pb.start();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			
			String lines = "";
			while((lines=reader.readLine())!=null) {
				System.out.println(lines);
			}
			
			while((lines=readers.readLine()) != null) {
				System.out.println(lines);
				
			}
			
			return lines;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return"";
	}
}
