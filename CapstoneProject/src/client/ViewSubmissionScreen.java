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

public class ViewSubmissionScreen extends JFrame implements ActionListener{


	public ViewSubmissionScreen(Submission submission, Student student) {
		JPanel viewSubmission = new JPanel(new BorderLayout());
		
		String title = student.getName() + "    " + submission.getName() + "    " + "Grade : " + submission.getGrade();
		JLabel t = new JLabel(title);
		viewSubmission.add(t, BorderLayout.PAGE_START);
		
		String content = submission.getContent();
		JLabel c = new JLabel(content);
		viewSubmission.add(c, BorderLayout.CENTER);
		
		JButton back = new JButton("Back");
		back.addActionListener(this);
		viewSubmission.add(back, BorderLayout.PAGE_END);
		
		this.add(viewSubmission);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}

	
	
	
}
