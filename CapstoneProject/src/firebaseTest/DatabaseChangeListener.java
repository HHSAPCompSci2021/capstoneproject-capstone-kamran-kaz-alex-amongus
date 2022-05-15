package firebaseTest;

import java.util.HashMap;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class DatabaseChangeListener implements ChildEventListener{
	
	private HashMap<String, User> users;
	
	public DatabaseChangeListener() {
		users = new HashMap<String, User>();
	}
	
	@Override
	public void onCancelled(DatabaseError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildAdded(DataSnapshot dataSnapshot, String arg1) {
		// TODO Auto-generated method stub
		System.out.println("onChildAdded() called");
		String key = dataSnapshot.getKey();
		User user = dataSnapshot.getValue(User.class);
		users.put(key, user);
		System.out.println(user);
		
		System.out.println("finished calling onChildAdded()");
	}

	@Override
	public void onChildChanged(DataSnapshot dataSnapshot, String arg1) {
		// TODO Auto-generated method stub
		System.out.println("onChildChanged() called");
		String key = dataSnapshot.getKey();
		User user = dataSnapshot.getValue(User.class);
		users.put(key, user);
	}

	@Override
	public void onChildMoved(DataSnapshot arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildRemoved(DataSnapshot arg0) {
		// TODO Auto-generated method stub
		
	}

	public HashMap<String, User> getUsers() {
		
		return users;
	}
	
}
