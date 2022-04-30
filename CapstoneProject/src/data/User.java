package data;
/**
 * The User class models a user of the program. The user object will have a name. 
 * This class is immutable.
 * @author Kaz Nakao
 *
 */
public class User {
	
	private String name;
	/**
	 * 
	 * @param username name of the user
	 */
	public User(String name) {
		if (StringChecker.isEmpty(name)) 
			throw new IllegalArgumentException("Username cannot be blank");
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		User compare = (User)obj;
		if (compare.getName().equals(name))
			return true;
		return false;
	}

}
