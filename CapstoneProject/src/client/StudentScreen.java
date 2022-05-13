package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ServerClient.DatabaseChangeListener;
import ServerClient.DatabaseModifier;
import data.Classroom;
import data.Student;
import data.Submission;
/**
 * The screen for the students 
 * @author Kaz Nakao
 *
 */
public class StudentScreen extends JPanel implements ListSelectionListener, ActionListener{
	
	
	private Student student;
	private ArrayList<Submission> submissions;
	private JList<String> list;
	
	/** 
	 * Sets up the submission screen as a JPanel. Shows the screen for student's submissions and an option for submitting a new submission.
	 */
	public StudentScreen() {
		super(new BorderLayout());
		
		JLabel title = new JLabel("Submissions");
		add(title, BorderLayout.PAGE_START);
		
		JScrollPane scroll = new JScrollPane();
		String[] options = null;
		if (submissions != null) {
			options = new String[submissions.size()];
			for (int i = 0; i < submissions.size(); i++) {
				options[i] = submissions.get(i).getName();
			}
		}
		
		list = new JList<String>(options);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.addListSelectionListener(this);
		scroll.add(list);
		add(list, BorderLayout.CENTER);
		
		JButton submit = new JButton("Submit a new Submission");
		submit.addActionListener(this);
		add(submit, BorderLayout.PAGE_END);
		
	}


	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		int index = list.getMinSelectionIndex();
		if (index >= 0) {
			Submission submission = submissions.get(index);
			JFrame window = new ViewSubmissionScreen(submission,student);
			window.setBounds(100, 100, 800, 600);
			window.setResizable(true);
			window.setVisible(true);
			
			
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFrame window = new SubmissionScreen();
		window.setBounds(100, 100, 800, 600);
		window.setResizable(true);
		window.setVisible(true);
		
		
	}
	
	private void setupStudent() {
		

		String name = JOptionPane.showInputDialog("What is your name?");
		
		String id = null;
		
		while (id == null) {
			id = JOptionPane.showInputDialog("What is your id number?");
			char[] chars = id.toCharArray();
			for (char c : chars) {
				if (!Character.isDigit(c)) {
					id = null;
					String[] options = { "OK" };
					JOptionPane.showOptionDialog(null, "ID number can only contain numbers", 
				    		"GRADEME", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}
			}
		}
		
		final String idnum = id; //use this string for when you intialize the student if needed
		
		Student check = new Student(name, id);
		
		
		DatabaseModifier m = new DatabaseModifier();
		Classroom classroom = m.getClassroom();
		
		
		ArrayList<Student> students = classroom.getStudents();
		
		boolean found = false;
		
		for (Student s : students) {
			if (check.equals(s)) {
				student = s;
				found = true;
				break;
			}
		}
		
		if (!found) {
			student = new Student(name, id);
		}
		
		classroom.addStudent(student);
		
		m.submitClassroomToDatabase(classroom);
		
	}

}
