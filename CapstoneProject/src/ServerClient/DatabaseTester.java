package ServerClient;

import java.util.Scanner;

import data.Submission;

/**
 * TEST CLASS FOR DatabaseModifier.java and DatabaseChangeListener.java
 * REMOVE IN FINAL PRODUCTION
 * 
 * @author Alex Wang
 *
 */
public class DatabaseTester {

	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.DEBUG);  // This only shows us firebase errors. Change "ERROR" to "DEBUG" to see lots of database info.
	    
	    DatabaseModifier m = new DatabaseModifier();
	    Scanner input = new Scanner(System.in);
	    
	    boolean exit = false;
	    
	    while (!exit) {
	    	System.out.println("Input name: ");
	    	String name = input.nextLine();
	    	
	    	System.out.println("Input content: ");
	    	String content = input.nextLine();
	    	
	    	Submission s = new Submission(name, content);
	    	m.submitToDatabase(s);
	    	System.out.println("submission initiated.");
	    }
	    
		
	}
}
