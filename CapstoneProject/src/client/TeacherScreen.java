package client;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.Submission;
import data.Teacher;
/**
 * 
 * Models the Teacher GUI. Will be able to click on an assignment and be able to view the 
 * @author Kaz Nakao
 *
 */
public class TeacherScreen extends JPanel implements ListSelectionListener{
	
	private ArrayList<Submission> submissions;
	private JList<String> list;
	private Teacher teacher;
	
	
	/**
	 * Creates a screen for teacher screen. Will be able to view all submissions.
	 */
	public TeacherScreen() {
		super(new BorderLayout());
		
		teacher = new Teacher();
		
		
		
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
		
		String stats = "";
		
		for (Submission s : submissions) {
			stats += s.getGrade() + ", ";
		}
		
		JLabel grades = new JLabel("grades " + stats);
		add(grades, BorderLayout.PAGE_END);
		
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

	
	
}
