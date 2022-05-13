package ServerClient;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Student;
import data.Submission;

/**
 * TEST CLASS FOR DatabaseModifier.java and DatabaseChangeListener.java
 * REMOVE IN FINAL PRODUCTION
 * 
 * @author Alex Wang
 *
 */
@SuppressWarnings("serial")
public class DatabaseTester extends JPanel implements ActionListener {
	private JTextField nameField, contentField, usernameField, idField, gradeField;
	private DatabaseModifier m;
	
	public DatabaseTester() {
		super(new GridLayout(6,1));
		
		JFrame frame = new JFrame("DatabaseTester");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(900, 500));
		
		// text field
		JLabel nameLabel = new JLabel("Name:");
		this.add(nameLabel);
		nameField = new JTextField(20);
		this.add(nameField);
		
		JLabel contentLabel = new JLabel("Content:");
		this.add(contentLabel);
		contentField = new JTextField(20);
		this.add(contentField);
		
		JLabel gradeLabel = new JLabel("Grade:");
		this.add(gradeLabel);
		gradeField = new JTextField(20);
		this.add(gradeField);
		
		JLabel usernameLabel = new JLabel("Student Username:");
		this.add(usernameLabel);
		usernameField = new JTextField(20);
		this.add(usernameField);
		
		JLabel idLabel = new JLabel("Student ID:");
		this.add(idLabel);
		idField = new JTextField(20);
		this.add(idField);
		
		
		JButton updateButton = new JButton();
		updateButton.setText("Post to Firebase");
		updateButton.addActionListener(this);
		this.add(updateButton);
		
		
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.DEBUG);  // This only shows us firebase errors. Change "ERROR" to "DEBUG" to see lots of database info.
	    
	    DatabaseTester tester = new DatabaseTester();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    if (m == null) {
	    	m = new DatabaseModifier();	
	    }
	    
	    Submission s = new Submission(nameField.getText(), contentField.getText(), gradeField.getText());
	    String submissionID = m.submitToDatabase(s);
	    System.out.println("SUBMISSION ID = " + submissionID);
	    		
	}
}
