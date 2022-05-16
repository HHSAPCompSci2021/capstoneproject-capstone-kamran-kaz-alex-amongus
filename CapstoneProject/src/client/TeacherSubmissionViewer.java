package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import data.Classroom;
import data.DatabaseModifier;
import data.Submission;
import data.Teacher;

public class TeacherSubmissionViewer extends JFrame implements ActionListener{

	private DatabaseModifier m;
	private int rubric;
	private Classroom classroom;
	private Teacher teacher;
	
	private ArrayList<Submission> gradedSubmissions;
	private ArrayList<Submission> ungradedSubmissions;
	private JList<String> gradedList;
	private JList<String> ungradedList;
	
	public TeacherSubmissionViewer(DatabaseModifier m, Classroom classroom, int rubric, Teacher teacher) {
		
		this.m = m;
		this.classroom = classroom;
		this.rubric = rubric;
		this.teacher = teacher;
		
		gradedSubmissions = classroom.getGraded(rubric);
		ungradedSubmissions = classroom.getUngraded(rubric);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel title = new JLabel("Choose a submission. Left is ungraded, Right is graded");
		panel.add(title, BorderLayout.PAGE_START);
		
		String[] options1 = new String[ungradedSubmissions.size()];
		for (int i = 0; i < options1.length; i++) {
			options1[i] = ungradedSubmissions.get(i).getName();
		}
		
		String[] options2 = new String[gradedSubmissions.size()];
		for (int i = 0; i < options2.length; i++) {
			options2[i] = gradedSubmissions.get(i).getName();
		}
		
		ungradedList = new JList<String>(options1);
		gradedList = new JList<String>(options2);
		
		JScrollPane scroll1 = new JScrollPane(ungradedList);
		JScrollPane scroll2 = new JScrollPane(gradedList);
		
		panel.add(scroll1, BorderLayout.LINE_START);
		panel.add(scroll2, BorderLayout.LINE_END);
		
		JButton view = new JButton("View Submissions");
		view.addActionListener(this);
		panel.add(view, BorderLayout.PAGE_END);
		
		add(panel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int index1 = ungradedList.getSelectedIndex();
		int index2 = gradedList.getSelectedIndex();
		
		if (index1 > -1) {
			JFrame window = new ViewSubmissionScreen(ungradedSubmissions.get(index1), teacher);
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);
		} else if (index2 > -1) {
			JFrame window = new ViewSubmissionScreen(ungradedSubmissions.get(index2), teacher);
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);
		}
	}
}
