package ServerClient;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import data.Submission;

/**
 * Handles changes with Firebase database reference
 * @author Alex Wang, John Shelby for assistance in Firebase logic
 *
 */
public class DatabaseChangeListener implements ChildEventListener {
	private ConcurrentLinkedQueue<Runnable> tasks;
	
	public DatabaseChangeListener() {   // This threading strategy will work with Processing programs. Just use this code inside your PApplet.
		tasks = new ConcurrentLinkedQueue<Runnable>();
	}
	
	
	public void post() {
		while (!tasks.isEmpty()) {
			Runnable r = tasks.remove();
			r.run();
		}
	}
	
	
	@Override
	public void onCancelled(DatabaseError err) {
		System.out.println("Database read failed. Code: " + err.getCode());
	}

	@Override
	/**
	 * This method is called automatically every time something is added to the database (by you or
	 * someone else). It is also called at the beginning of the program for all existing database posts. 
	 */
	public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
		System.out.println("onChildAdded() called");
		tasks.add(new Runnable() {

			@Override
			public void run() {
				// get value of stored information and turn back to Java object
				Submission studentSubmission = dataSnapshot.getValue(Submission.class);
				
				// transfer to appropriate GUI components
			}
			
		});
		
		
	}

	@Override
	public void onChildChanged(DataSnapshot arg0, String arg1) {
		System.out.println("onChildChanged() called");
	}

	@Override
	public void onChildMoved(DataSnapshot arg0, String arg1) {
		System.out.println("onChildMoved() called");
	}

	@Override
	public void onChildRemoved(DataSnapshot arg0) {
		System.out.println("onChildRemoved() called");
//		tasks.add(new Runnable() {
//
//			@Override
//			public void run() {
//				currentDrawing.clear();
//			}
//			
//		});
		
	}
}
