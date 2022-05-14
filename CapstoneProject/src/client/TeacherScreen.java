package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.Classroom;
import data.DatabaseModifier;
import data.Submission;
import data.Teacher;
/**
 * 
 * Models the Teacher GUI. Will be able to click on an assignment and be able to view the 
 * @author Kaz Nakao
 *
 */
public class TeacherScreen extends JPanel implements ListSelectionListener, ActionListener{
	
	private ArrayList<Submission> submissions;
	private JList<String> list;
	private Teacher teacher;
	private Classroom classroom;
	
	private DatabaseModifier m;
	
	/**
	 * Creates a screen for teacher screen. Will be able to view all submissions.
	 */
	public TeacherScreen(DatabaseModifier m) {
		
		super(new BorderLayout());
		this.m = m;
		setupTeacher();
		
		submissions = new ArrayList<Submission>();
		submissions.add(new Submission("hamlet", "Shakespear did a thing"));
		submissions.add(new Submission("romeo and juliet", "love and stuff ig"));
		submissions.add(new Submission("test submission", "testing testing testing"));
		
		
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
		
		JButton newClass = new JButton("Create a new Classroom");
		newClass.addActionListener(this);
		
		add(newClass, BorderLayout.PAGE_END);
	}
	
	private void setupTeacher() {
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
		
		teacher = new Teacher(name, id);
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
		int index = list.getMinSelectionIndex();
		if (index >= 0) {
			Submission submission = submissions.get(index);
			JFrame window = new ViewSubmissionScreen(submission,teacher);
			window.setBounds(100, 100, 800, 600);
			window.setResizable(true);
			window.setVisible(true);
			
			
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Classroom newClass = new Classroom("Test Classroom");
		newClass.addTeacher(teacher);
		m.submitClassroomToDatabase(newClass);
		
	}

	
	
}
