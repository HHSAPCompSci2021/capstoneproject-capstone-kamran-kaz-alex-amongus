package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Classroom;
import data.DatabaseModifier;
import data.Student;
import data.Submission;

public class SubmissionScreen extends JFrame implements ActionListener{

	public final static String fileSeparator = System.getProperty("file.separator");
	public final static String userDir = System.getProperty("user.dir");
	public final static String lineSeparator = System.getProperty("line.separator");

	
	private Classroom classroom;
	private Student student;
	private DatabaseModifier m;
	private int rubric;
	private String input;
	private JTextField textInput;
	
	
	
	public SubmissionScreen(Classroom classroom, Student student, DatabaseModifier m, int rubric) {
		this.classroom = classroom;
		this.student = student;
		this.m = m;
		this.rubric = rubric;
		
		JFileChooser chooser = new JFileChooser(userDir);
		int returnVal = chooser.showOpenDialog(null);
		
		
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	
	    	try {
	    		input = readFile(chooser.getSelectedFile().getPath());
	    	} 
	    	catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
	    //title of the submission
	    JPanel panel = new JPanel();
	    JLabel label = new JLabel("input a title");
	    textInput = new JTextField();
	    
	    panel.add(label);
	    panel.add(textInput);
	    
	    JButton submit = new JButton("submit");
	    submit.addActionListener(this);
	    
	    panel.add(submit);
	    
		add(panel);
	    
		
	}
	
	
	
	private String readFile(String inputFile) throws IOException{
		Scanner scan = null;
		StringBuffer fileData = new StringBuffer();
		try {
			FileReader reader = new FileReader(inputFile);
			scan = new Scanner(reader);
			while(scan.hasNext()) {
				fileData.append(scan.nextLine());
				fileData.append(lineSeparator);
			}
		} finally {
			if (scan != null) 
				scan.close();
		}
		return fileData.toString();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (!textInput.getText().isEmpty()) {
			Submission submission = new Submission(textInput.getText(), input, rubric);
			student.add(submission);
			String key = m.getKey(classroom);
			m.set(key, classroom);
			this.setVisible(false);
		}
	}
	

	
}
