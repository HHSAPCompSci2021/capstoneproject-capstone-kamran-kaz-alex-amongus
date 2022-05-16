package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

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

public class StudentClassroomScreen extends JFrame implements ActionListener{
	
	private Classroom classroom;
	private String key;
	private Student student;
	private DatabaseModifier m;
	
	private JList list;
	
	
	public StudentClassroomScreen(Classroom classroom, String key, DatabaseModifier m, Student s) {
		super("Classroom Viewer");
		this.classroom = classroom;
		this.key = key;
		this.m = m;
		this.student = s;
		setup();
	}
	
	
	private void setup() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel top = new JPanel(new FlowLayout());
		JLabel label = new JLabel(classroom.getName());
		top.add(label);
		JButton newSubmission = new JButton("submit a new assignment");
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

		
		JButton view = new JButton("view");
		view.addActionListener(this);
		panel.add(view, BorderLayout.PAGE_END);
		
		add(panel);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
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
	}


	

}
