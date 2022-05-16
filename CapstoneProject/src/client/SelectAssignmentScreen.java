package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import data.Classroom;
import data.DatabaseModifier;
import data.Student;
import data.Submission;
import data.Rubric;

/**
 * Models a page to show a screen for submitting an assignment to the classroom. Will prompt the user to select a file to submit. 
 * The file will be converted to a simple text format and will submit that along with a submission title. 
 * @author Kaz Nakao
 *
 */
public class SelectAssignmentScreen extends JFrame implements ActionListener{
	
	
	private String input;
	private DatabaseModifier m;
	private Student student;
	private Classroom classroom;
	
	JList list;
	
	
	
	/**
	 * Will create a system to upload a new submission for the user. Will have a text field in the center to add a title to the submission.
	 * 
	 */
	public SelectAssignmentScreen(Student student, DatabaseModifier m, Classroom classroom) {
		super("Submission Screen");
		this.student = student;
		this.m = m;
		this.classroom = classroom;
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel label = new JLabel("Select an assignment to make a submission to");
		panel.add(label, BorderLayout.PAGE_START);
		
		ArrayList<Rubric> assignments = classroom.getAssignments();
		String[] options = new String[assignments.size()];
		for (int i = 0; i < assignments.size(); i++) {
			options[i] = assignments.get(i).getName();
		}
		
		list = new JList<String>(options);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		panel.add(list, BorderLayout.CENTER);
		
		JButton button = new JButton("Submit Assignment");
		button.addActionListener(this);
		panel.add(button, BorderLayout.PAGE_END);
		
		add(panel);
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		int index = list.getSelectedIndex();
		
		if (index > -1) {
			
			JFrame window = new SubmissionScreen(classroom, student, m, index);
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);
			
		}
		
		
	}
	

	

}
