package firebaseTest;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Main {
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.ERROR);
	    
	    
		DatabaseModifier m = new DatabaseModifier();
		
		User user = new User("alex");
		user.addThing("drums");
		user.addThing("cs nerd");
		
		System.out.println("user: " + user);
		
		m.addUser(user);
		System.out.println("added user");
		
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<User> users = m.getUsers();
		
		System.out.println();
		System.out.println("users: " + users);
		
		System.out.println("found users");
		//User newUser = users.get(0);
		//System.out.println("database user: " + newUser);
		
		
	}

}
