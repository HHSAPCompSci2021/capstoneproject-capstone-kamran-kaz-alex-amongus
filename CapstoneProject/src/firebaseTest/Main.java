package firebaseTest;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DatabaseModifier m = new DatabaseModifier();
		
		User user = new User("john");
		user.addThing("Height");
		user.addThing("age");
		
		System.out.println("user: " + user);
		
		m.addUser(user);
		System.out.println("added user");
		
		User newUser = null;
		
		while (newUser == null) {
			//System.out.println("newUser is null");
			newUser = m.getUser();
		}
		
		System.out.println("found user");
		
		System.out.println("database user: " + newUser);
		
	}

}
