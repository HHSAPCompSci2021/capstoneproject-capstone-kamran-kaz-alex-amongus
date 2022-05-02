package client;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import data.Student;

public class StudentScreen extends JPanel {
	
	
	private DefaultListModel<String> model;

	private DatabaseReference classRef;
	
	public StudentScreen() {
		super(new BorderLayout());
		
		String name = JOptionPane.showInputDialog("What is your name?");
		
		FileInputStream refreshToken;
		try {

			refreshToken = new FileInputStream("grademe-e5a48-firebase-adminsdk-wmfna-32b875a640.json");

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.setDatabaseUrl("https://grademe-e5a48-default-rtdb.firebaseio.com/")
					.build();

			FirebaseApp.initializeApp(options);
			DatabaseReference database = FirebaseDatabase.getInstance().getReference();
			classRef = database.child("arcade");

			classRef.addChildEventListener(new DatabaseChangeListener());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Student getStudent(ArrayList<Student> students) {
		
		
		
		return null;
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

				public void run() {
					
					String name = dataSnapshot.child("name").getValue(String.class);
					model.add(0,name);
					
				}
				
			});
			
			
		}


		@Override
		public void onChildChanged(DataSnapshot dataSnapshot, String arg1) {
			// TODO Auto-generated method stub

		}


		@Override
		public void onChildMoved(DataSnapshot dataSnapshot, String arg1) {
			// TODO Auto-generated method stub

		}


		@Override
		public void onChildRemoved(DataSnapshot dataSnapshot) {
			
			SwingUtilities.invokeLater(new Runnable() {
			    public void run() {
			    	String name = dataSnapshot.child("name").getValue(String.class);
			    	model.removeElement(name);
			    }
			});

		}

	}


	
}
