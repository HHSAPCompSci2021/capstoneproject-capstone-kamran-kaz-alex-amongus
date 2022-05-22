package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import data.Student;
import data.Submission;
import data.Teacher;
/**
 * Allows a user to view a submission. Important information about the submission is listed at the top. The essay content is located in the center of the screen
 * @author Kaz Nakao
 *
 */
public class ViewSubmissionScreen extends JFrame implements ActionListener{

	/**
	 * Creates a screen that will let you view the contents of your submission.
	 * @param submission Submission that you want to view
	 * @param student Student object that you are using to view the submission
	 */
	public ViewSubmissionScreen(Submission submission, Student student) {
		
		String title = student.getName() + "    " + submission.getName() + "    " + "Grade : " + submission.getGrade();
		
		setup(submission, title);
	}
	
	/**
	 * Creates a screen that will let you view the contents of the selected submission
	 * @param submission Submission that you want to view
	 * @param teacher Teacher object that you are using to view the submission
	 */
	public ViewSubmissionScreen(Submission submission, Teacher teacher) {
		String title = teacher.getName() + "    " + submission.getName() + "     " + "Grade : " + submission.getGrade();
		setup(submission, title);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private void setup(Submission submission, String title) {
		JPanel viewSubmission = new JPanel(new BorderLayout());
		
		JLabel t = new JLabel(title);
		viewSubmission.add(t, BorderLayout.PAGE_START);
		
		String content = submission.getContent();
		JTextArea c = new JTextArea(content);
		JScrollPane scroll = new JScrollPane(c);
		viewSubmission.add(scroll, BorderLayout.CENTER);
		
		JButton back = new JButton("Back");
		back.addActionListener(this);
		viewSubmission.add(back, BorderLayout.PAGE_END);
		
		this.add(viewSubmission);
		
	}

	
	
	
}
