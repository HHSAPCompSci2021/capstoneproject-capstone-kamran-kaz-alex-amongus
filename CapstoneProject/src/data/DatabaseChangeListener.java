package data;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Handles changes with Firebase database reference
 * 
 * @author Alex Wang, John Shelby for assistance in Firebase logic
 *
 */
public class DatabaseChangeListener implements ChildEventListener {
	private ConcurrentLinkedQueue<Runnable> tasks;
	private HashMap<String, Classroom> classrooms;

	public DatabaseChangeListener() { // This threading strategy will work with Processing programs. Just use this
										// code inside your PApplet.
		tasks = new ConcurrentLinkedQueue<Runnable>();
		classrooms = new HashMap<String, Classroom>();
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
	 * This method is called automatically every time something is added to the
	 * database (by you or someone else). It is also called at the beginning of the
	 * program for all existing databases.
	 */
	public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
		System.out.println("onChildAdded() called");
		
		updateMap(dataSnapshot);
		
		Classroom classroom = dataSnapshot.getValue(Classroom.class);
		System.out.println("SYNCED, CLASSROOM = " + classroom.toString() + "\nCLASSROOMS: = " + classrooms.toString());
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String arg1) {
		System.out.println("onChildChanged() called");
		
		updateMap(dataSnapshot);
		
		Classroom classroom = dataSnapshot.getValue(Classroom.class);
		System.out.println("SYNCED, PRINT = " + classroom.toString() + "\nCLASSROOMS: = " + classrooms.toString());
		
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
	
	public HashMap<String, Classroom> getClassrooms() {
		return classrooms;
	}
	
	private void updateMap(DataSnapshot dataSnapshot) {
		String key = dataSnapshot.getKey();
		Classroom classroom = dataSnapshot.getValue(Classroom.class);
		
		classrooms.put(key, classroom);
	}
}
