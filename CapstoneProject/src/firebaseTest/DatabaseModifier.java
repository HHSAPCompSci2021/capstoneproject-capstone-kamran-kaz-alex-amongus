package firebaseTest;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseModifier {
	
	DatabaseReference ref;
	DatabaseChangeListener listener;
	
	public DatabaseModifier() {
		FileInputStream refreshToken;
		
		try {
			refreshToken = new FileInputStream("GradeMeFirebaseKey.json");
			
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
				    .setDatabaseUrl("https://grademe-e5a48-default-rtdb.firebaseio.com/")
				    .build();

			FirebaseApp.initializeApp(options);
			
			DatabaseReference database = FirebaseDatabase.getInstance().getReference();
			
			// structure = root/classrooms
			ref = database.child("users");
			
			
			// always keep local variables synced up
			listener = new DatabaseChangeListener();
			ref.addChildEventListener(listener);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Pushes a new User object to firebase
	 * @param user
	 */
	public void addUser(User user) {
		ref.push().setValueAsync(user);
	}
	/**
	 * Returns the most recently added user
	 * @return
	 */
	public User getUser() {
		return listener.getUser();
	}

}
