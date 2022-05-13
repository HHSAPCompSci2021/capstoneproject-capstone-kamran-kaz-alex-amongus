package ServerClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import data.Student;
import data.Submission;


public class DatabaseReader extends DatabaseChangeListener{
	
	private DatabaseReference postsRef;
	private ArrayList<Submission> submissions;

	public DatabaseReader() {
		setupDatabase();
		submissions = new ArrayList<Submission>();
	}
	
	/**
	 * Creates a Database Reference and sets up necessary Firebase options
	 */
	public void setupDatabase() {
		FileInputStream refreshToken;
		
		try {
			refreshToken = new FileInputStream("GradeMeFirebaseKey.json");
			
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
				    .setDatabaseUrl("https://grademe-e5a48-default-rtdb.firebaseio.com/")
				    .build();

			FirebaseApp.initializeApp(options);
			
			DatabaseReference database = FirebaseDatabase.getInstance().getReference();
			
			postsRef = database.child("submissions");
			// structure = root/submissions
			postsRef.addChildEventListener(new DatabaseChangeListener());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Submission> getSubmissions() {
		System.out.println(submissions);
		return submissions;
	}
	
	public Student getStudent(String name, String id) {
		
		
		return new Student();
	}
	
	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {

		SwingUtilities.invokeLater(new Runnable() {  // This threading strategy will work with Swing programs. Just put whatever code you want inside of one of these "runnable" wrappers.

			@Override
			public void run() {
				
				Submission submission = dataSnapshot.getValue(Submission.class);
				System.out.println(submission);
				submissions.add(submission);
				
			}
			
		});
		
	}

}
