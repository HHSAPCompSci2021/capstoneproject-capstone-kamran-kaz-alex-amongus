package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import data.Classroom;
import data.Submission;
import data.Teacher;
/**
 * Allows a teacher to view all of the submissions that have been made to a particular assignment in a classroom. 
 * The assignments have been divided into two lists: ungraded and graded.
 * @author Kaz Nakao, Alex Wang
 *
 */
public class TeacherSubmissionViewer extends JFrame implements ActionListener {

	
	private Teacher teacher;
	
	private ArrayList<Submission> gradedSubmissions;
	private ArrayList<Submission> ungradedSubmissions;
	private JList<String> gradedList;
	private JList<String> ungradedList;
	private JPanel scrollPanel;	
	private JButton view, back;
	
	private int recentlySelectedList;
	
	/**
	 * 
	 * @param classroom Classroom that the assignment is being viewed in
	 * @param rubric Index of the rubric
	 * @param teacher Teacher that is accessing the submissions
	 */
	public TeacherSubmissionViewer(Classroom classroom, int rubric, Teacher teacher) {
		this.teacher = teacher;
		recentlySelectedList = 0;
		
		gradedSubmissions = classroom.getGraded(rubric);
		ungradedSubmissions = classroom.getUngraded(rubric);
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel title = new JLabel("Choose a submission. top is ungraded, bottom is graded");
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
		
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		scrollPanel.add(scroll1);
		scrollPanel.add(scroll2);
		
		panel.add(scrollPanel, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		
		view = new JButton("View Submissions");
		view.addActionListener(this);
		
		back = new JButton("back");
		back.addActionListener(this);
		
		bottom.add(view);
		bottom.add(back);
		
		panel.add(bottom, BorderLayout.PAGE_END);
		
		add(panel);
			
		addFocusListener();
	}

	private void addFocusListener() {		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener("focusOwner", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Component component = (Component) evt.getNewValue();
				
				// keep track of most recently in focus list
				if (component != null && component.equals(gradedList))
					recentlySelectedList = 1;
				else if (component != null && component.equals(ungradedList))
					recentlySelectedList = 0;
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(view)) {	
			int index1 = ungradedList.getSelectedIndex();
			int index2 = gradedList.getSelectedIndex();

			ImageIcon logo = new ImageIcon("resources/GRADEME-logo.png");
			
			// ungraded assignment selected
			if (recentlySelectedList == 0 && index1 > -1) {
				JFrame window = new ViewSubmissionScreen(ungradedSubmissions.get(index1), teacher);
				window.setBounds(100, 100, 800, 600);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setIconImage(logo.getImage());
				window.setResizable(true);
				window.setVisible(true);
			}
			// graded assignment selected
			else if (recentlySelectedList == 1 && index2 > -1) {
				JFrame window = new ViewSubmissionScreen(gradedSubmissions.get(index2), teacher);
				window.setBounds(100, 100, 800, 600);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setIconImage(logo.getImage());
				window.setResizable(true);
				window.setVisible(true);
			}
		}
		else if (e.getSource().equals(back)) {
			this.setVisible(false);
		}
	}
}
