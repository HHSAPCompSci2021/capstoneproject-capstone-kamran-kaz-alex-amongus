package firebaseTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


public class Main {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.ERROR);
	    
	    
		DatabaseModifier m = new DatabaseModifier();
		/*
		User user = new User("john");
		user.addThing("apcs");
		user.addThing("hey ap computer science");
		Age age = user.getAge();
		age.add("2022");
		
		System.out.println("user: " + user);
		
		m.addUser(user);
		System.out.println("added user");
		*/
		HashMap<String, User> users = m.getUsers();
		/*
		System.out.println();
		System.out.println("users: " + users);
		
		System.out.println("found users");
		
		System.out.println("setting value");
		
		User user = new User("kam");
		String loc = null;
		Set<String> keys = users.keySet();
		boolean found = false;
		for (String key : keys) {
			User u = users.get(key);
			if (user.equals(u)) {
				System.out.println("found a matching user");
				found = true;
				user = u;
				loc = key;
				break;
			}
		}
		
		if (found) {
			user.addThing("new thing");
			m.set(loc, user);
			System.out.println("updated item at: " + loc);
		}
		
		users = m.getUsers();
		System.out.println(users);
		*/
		
		User user = new User("testing 2D arraylist");
		user.addThing("test 1D array");
		Age age = user.getAge();
		ArrayList<String> newAges = new ArrayList<String>();
		newAges.add("testing 2D array");
		newAges.add("testing 2D array");
		age.add(newAges);
		
		m.addUser(user);
		
		
		//User newUser = users.get(0);
		//System.out.println("database user: " + newUser);
		
		
	}

}
