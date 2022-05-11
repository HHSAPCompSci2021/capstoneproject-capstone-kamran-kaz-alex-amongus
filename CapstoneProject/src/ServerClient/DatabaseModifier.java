package ServerClient;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.*;
import com.google.firebase.database.*;

import data.Submission;

/**
 * Creates and interacts with Firebase; handles actions used to submit, fetch, and access essays, submissions, and other parts
 * 
 * @author Alex Wang, John Shelby for assistance in Firebase logic
 *
 */
public class DatabaseModifier {
	
	private DatabaseReference postsRef;

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
			postsRef = database.child("posts");

				postsRef.addChildEventListener(new DatabaseChangeListener());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Submit submission to Database
	 * @param studentSubmission Submission object to be submitted
	 * @return postID from new data path (DatabaseReference) used to retrieve data
	 */
	public String submitToDatabase(Submission studentSubmission) {
		String postID = "";
		
		try {
			System.out.println("submitToDatabase() called");
			DatabaseReference pushedPostRef = postsRef.push();
			pushedPostRef.setValueAsync(studentSubmission);
			
			// get unique key generated by push()
			postID += pushedPostRef.getKey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return postID;
	}
	
	
	
	/**
	 * METHOD FOR TESTING ONLY - DO NOT JAVADOC OR INCLUDE IN DISTRIBUTED VERSIONS
	 */
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.DEBUG);  // This only shows us firebase errors. Change "ERROR" to "DEBUG" to see lots of database info.
	    
		DatabaseModifier m = new DatabaseModifier();
		m.setupDatabase();
		
		Submission s = new Submission("name", "content");
		m.submitToDatabase(s);
	}
}
