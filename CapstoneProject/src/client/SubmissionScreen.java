package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import data.Submission;


public class SubmissionScreen extends JPanel implements ActionListener{

	private JTextField messageField;
	private JTextArea output;
	private String name;
	
	
	/**
	 * Will create a system to upload a new submission for the user
	 * @param name name of the submission
	 */
	public SubmissionScreen(String name) {
		super(new BorderLayout());
		
		this.name = name;
		
		// UI SETUP
		output = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(output);
		add(scrollPane, BorderLayout.CENTER);
		
		messageField = new JTextField(50);
		
		JButton goButton = new JButton("POST");
		goButton.addActionListener(this);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

		
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel("Message: "));
		messagePanel.add(messageField);
		
		
		bottomPanel.add(messagePanel);
		bottomPanel.add(goButton);
		JPanel holder = new JPanel();
		holder.add(bottomPanel);
		add(holder, BorderLayout.SOUTH);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// NOTE - get values of Text Fields and create Submission object
		// pass Submission along to ServerClient package (specifically DatabaseModifier.java)
		
	}
	



}
