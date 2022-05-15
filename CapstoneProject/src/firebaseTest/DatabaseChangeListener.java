package firebaseTest;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class DatabaseChangeListener implements ChildEventListener{
	
	private ArrayList<User> users;
	
	public DatabaseChangeListener() {
		users = new ArrayList<User>();
	}
	
	@Override
	public void onCancelled(DatabaseError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildAdded(DataSnapshot arg0, String arg1) {
		// TODO Auto-generated method stub
		System.out.println("onChildAdded() called");
		String path = arg0.getKey();
		System.out.println("key :" + path);
		User user = arg0.getValue(User.class);
		System.out.println(user);
		users.add(user);
		System.out.println("finished calling onChildAdded()");
	}

	@Override
	public void onChildChanged(DataSnapshot arg0, String arg1) {
		// TODO Auto-generated method stub
		System.out.println("onChildChanged() called");
		User user = arg0.getValue(User.class);
	}

	@Override
	public void onChildMoved(DataSnapshot arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildRemoved(DataSnapshot arg0) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<User> getUsers() {
		
		return users;
	}
	
}
