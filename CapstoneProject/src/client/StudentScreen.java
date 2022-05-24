package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import data.Classroom;
import data.DatabaseModifier;
import data.Student;
/**
 * The screen for the students. Displays the classrooms. 
 * @author Kaz Nakao, Alex Wang
 *
 */
public class StudentScreen extends HomeScreen implements ActionListener{
	
	private Student student;
	
	/** 
	 * Sets up the submission screen as a JPanel. Shows the screen for student's submissions and an option for submitting a new submission.
	 */
	public StudentScreen() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}

	@Override
	public void identify(Map.Entry<String, String> identification) {
		// TODO Auto-generated method stub
		HashMap<String, Classroom> classrooms = DatabaseModifier.getClassrooms();
		student = new Student(identification.getKey(), identification.getValue());
	}

	@Override
	public JFrame getScreen(Classroom classroom) {
		// TODO Auto-generated method stub
		
		return new StudentSubmissionScreen(classroom, student);
	}
}
