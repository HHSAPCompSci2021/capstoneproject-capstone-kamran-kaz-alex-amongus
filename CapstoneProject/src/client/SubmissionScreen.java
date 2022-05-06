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
	
	
	
	private DatabaseReference postsRef;
	
	
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
		
		
		
		// DATABASE SETUP
		FileInputStream refreshToken;
		try {
			
			refreshToken = new FileInputStream("grademe-e5a48-firebase-adminsdk-wmfna-32b875a640.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.fromStream(refreshToken))
				    .setDatabaseUrl("https://grademe-e5a48-default-rtdb.firebaseio.com/")
				    .build();

				FirebaseApp.initializeApp(options);
				DatabaseReference database = FirebaseDatabase.getInstance().getReference();
				postsRef = database.child("submissions");

				postsRef.addChildEventListener(new DatabaseChangeListener());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (!messageField.getText().trim().isEmpty())
			postsRef.push().setValueAsync(new Submission(name, messageField.getText()));
		messageField.setText("");
		
	}
	
	

	/**
	 * 
	 * Handles all changes to the database reference. Because Firebase uses a separate thread than most other processes we're using (both Swing and Processing),
	 * we need to have a strategy for ensuring that code is executed somewhere besides these methods.
	 * 
	 * @author john_shelby
	 *
	 */
	class DatabaseChangeListener implements ChildEventListener {


		@Override
		public void onCancelled(DatabaseError arg0) {
			// TODO Auto-generated method stub

		}


		@Override
		public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
			
			SwingUtilities.invokeLater(new Runnable() {  // This threading strategy will work with Swing programs. Just put whatever code you want inside of one of these "runnable" wrappers.

				@Override
				public void run() {
					
					Submission submission = dataSnapshot.getValue(Submission.class);

					String text = output.getText();
					text += submission + "\n";

					output.setText(text);
					
				}
				
			});
			
			
		}


		@Override
		public void onChildChanged(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub

		}


		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub

		}


		@Override
		public void onChildRemoved(DataSnapshot arg0) {
			// TODO Auto-generated method stub

		}

	}



}
