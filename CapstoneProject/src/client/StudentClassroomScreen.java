package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import data.Classroom;
import data.DatabaseModifier;
import data.Student;
import data.Submission;

/**
 * The StudentClassroomScreen class allows a student to view their past assignments and an option to create a new submission.
 * @author Kaz
 *
 */
public class StudentClassroomScreen extends JFrame implements ActionListener{
	
	private Classroom classroom;
	private String key;
	private Student student;
	
	private JList<String> list;
	private JButton newSubmission;
	private JButton view;
	/**
	 * Shows past submissions of the student and allows user to submit new assignments.
	 * @param classroom Classroom object that the student is viewing
	 * @param key The "key" location of the classroom in the database
	 * @param s The student object that you are viewing the submission through
	 */
	public StudentClassroomScreen(Classroom classroom, String key, Student s) {
		super("Classroom Viewer");
		this.classroom = classroom;
		this.key = key;
		this.student = s;
		setup();
	}
	
	/**
	 * makes a gui with a list of past assignments
	 */
	private void setup() {
		
		//GUI
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel top = new JPanel(new FlowLayout());
		JLabel label = new JLabel(classroom.getName());
		top.add(label);
		newSubmission = new JButton("submit a new assignment");
		newSubmission.addActionListener(this);
		top.add(newSubmission);
		
		panel.add(top, BorderLayout.PAGE_START);
		
		ArrayList<Student> students = classroom.getStudents();
		matchStudent(students);
		
		ArrayList<Submission> submissions = student.getSubmissions();
		String[] options = new String[submissions.size()];
		for (int i = 0; i < options.length; i++) {
			options[i] = submissions.get(i).getName();
		}
		
		list = new JList<String>(options);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane scroll = new JScrollPane(list);
		panel.add(scroll, BorderLayout.CENTER);

		
		view = new JButton("view");
		view.addActionListener(this);
		panel.add(view, BorderLayout.PAGE_END);
		
		add(panel);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource().equals(newSubmission)) {
			JFrame window = new SelectAssignmentScreen(student, classroom);
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);
		}
		else if (e.getSource().equals(view)) {
			int index = list.getSelectedIndex();
			if (index > -1) {
				Submission selected = student.getSubmissions().get(index);
				JFrame window = new ViewSubmissionScreen(selected, student);
				window.setBounds(100, 100, 800, 600);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setResizable(true);
				window.setVisible(true);
			}
		}
		
	}
	
	/**
	 * finds a matching student or adds a new student. If new student is added, database is updated.
	 * @param students arraylist of students
	 */
	private void matchStudent(ArrayList<Student> students) {
		for (Student s : students) {
			if (s.equals(student)) {
				student = s;
				return;
			}
		}
		classroom.addStudent(student);
		DatabaseModifier.set(key, classroom);
	}

}
