package data;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Handles changes with Firebase database reference. 
 * Whenever an action occurs in the Database, one of the DatabaseChangeListener's implemented methods will be called.
 * onChildAdded() and onChildChanged() are used to detect edits in the database. A HashMap of key String and value Classroom is kept in sync with the Database. 
 * The HashMap containing the information about the Database can be accessed. To keep the database in sync, the HashMap should only be used to read rather than to write. 
 * 
 * @author Alex Wang, Kaz Nakao, John Shelby for assistance in Firebase logic
 *
 */
public class DatabaseChangeListener implements ChildEventListener {
	private ConcurrentLinkedQueue<Map.Entry<String, Classroom>> tasks;
	private HashMap<String, Classroom> classrooms;

	/**
	 * Creates a new DatabaseChangeListener objects
	 */
	public DatabaseChangeListener() { // This threading strategy will work with Processing programs. Just use this
										// code inside your PApplet.
		tasks = new ConcurrentLinkedQueue<Map.Entry<String, Classroom>>();
		classrooms = new HashMap<String, Classroom>();
	}

	/**
	 * Removes tasks from the queue
	 */
	/*
	public void post() {
		while (!tasks.isEmpty()) {
			Runnable r = tasks.remove();
			r.run();
		}
	}
	*/

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
		updateQueue(dataSnapshot);
		//Classroom classroom = dataSnapshot.getValue(Classroom.class);
		//System.out.println("SYNCED, CLASSROOM = " + classroom.toString() + "\nCLASSROOMS: = " + classrooms.toString());
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String arg1) {
		System.out.println("onChildChanged() called");
		
		updateMap(dataSnapshot);
		updateQueue(dataSnapshot);
		//Classroom classroom = dataSnapshot.getValue(Classroom.class);
		//System.out.println("SYNCED, PRINT = " + classroom.toString() + "\nCLASSROOMS: = " + classrooms.toString());
		
	}

	@Override
	public void onChildMoved(DataSnapshot arg0, String arg1) {
		System.out.println("onChildMoved() called");
	}

	@Override
	public void onChildRemoved(DataSnapshot arg0) {
		System.out.println("onChildRemoved() called");
	}
	
	/**
	 * Return a HashMap with a key as a String of the locatio of the object in the database and values of classrooms as the value. The classroom objects are copies of the ones that exist in firebase.
	 * @return A HashMap of String key, Classroom value where the key is the location of the classroom in the database. The classroom that it maps to corresponds to the classroom object that is stored in the database.
	 */
	public HashMap<String, Classroom> getClassrooms() {
		return classrooms;
	}
	
	public ConcurrentLinkedQueue<Map.Entry<String, Classroom>> getQueue() {
		return tasks;
	}
	
	/**
	 * Updates the hashmap upon updates in the server
	 * @param dataSnapshot The datasnapshot from when the event was triggered in the database
	 */
	private void updateMap(DataSnapshot dataSnapshot) {
		String key = dataSnapshot.getKey();
		Classroom classroom = dataSnapshot.getValue(Classroom.class);
		classrooms.put(key, classroom);
	}
	
	private void updateQueue(DataSnapshot dataSnapshot) {
		String key = dataSnapshot.getKey();
		Classroom classroom = dataSnapshot.getValue(Classroom.class);
		for (Student s : classroom.getStudents()) {
			for (Submission submit : s.getSubmissions()) {
				if (!submit.getGrade().equals(Submission.UNGRADED)) {
					HashMap<String, Classroom> entry = new HashMap<String, Classroom>();
					entry.put(key, classroom);
					for (Map.Entry<String, Classroom> queueEntry : entry.entrySet()) {
						tasks.add(queueEntry);
					}
				}
			}
		}
	}
}
