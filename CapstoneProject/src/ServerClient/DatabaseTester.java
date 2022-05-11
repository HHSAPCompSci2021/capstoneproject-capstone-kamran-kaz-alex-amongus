package ServerClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Submission;

/**
 * TEST CLASS FOR DatabaseModifier.java and DatabaseChangeListener.java
 * REMOVE IN FINAL PRODUCTION
 * 
 * @author Alex Wang
 *
 */
public class DatabaseTester extends JPanel {
	public DatabaseTester() {
		super();
		
		JLabel title = new JLabel("DatabaseTester");
		add(title, BorderLayout.NORTH);
		
		JFrame frame = new JFrame("DatabaseTester");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(900, 500));
		
		
		// text field
		JLabel nameLabel = new JLabel("Name:");
		this.add(nameLabel);
		JLabel contentLabel = new JLabel("Content:");
		this.add(contentLabel);
		
		
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.DEBUG);  // This only shows us firebase errors. Change "ERROR" to "DEBUG" to see lots of database info.
	    
	    DatabaseTester tester = new DatabaseTester();
	    
	    
//	    DatabaseModifier m = new DatabaseModifier();
//	    Scanner input = new Scanner(System.in);
//	    
//	    boolean exit = false;
//	    
//	    while (!exit) {
//	    	System.out.println("Input name: ");
//	    	String name = input.nextLine();
//	    	
//	    	System.out.println("Input content: ");
//	    	String content = input.nextLine();
//	    	
//	    	Submission s = new Submission(name, content);
//	    	m.submitToDatabase(s);
//	    	System.out.println("submission initiated.");
//	    }
//	    
		
	}
}
