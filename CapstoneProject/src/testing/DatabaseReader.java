package testing;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class DatabaseReader implements ValueEventListener{

	private User user;
	
	public DatabaseReader() {
		
	}
	
	@Override
	public void onCancelled(DatabaseError databaseError) {
		// TODO Auto-generated method stub
		System.out.println("The read failed: " + databaseError.getCode());
	}

	@Override
	public void onDataChange(DataSnapshot dataSnapshot) {
		// TODO Auto-generated method stub
		System.out.println("onDataChange() called");
		user = dataSnapshot.getValue(User.class);
		
		String key = dataSnapshot.getKey();
		System.out.println("location: " + key);
		
		System.out.println(user);
		System.out.println("onDataChange() finished");
	}
	
	public User getUser() {
		return user;
	}

}
