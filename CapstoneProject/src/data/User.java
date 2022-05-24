package data;

/**
 * The User class is a template for the users that will be using the Database. 
 * They each contain an ID number and a name that allows them to be uniquely identified. 
 * @author Kaz Nakao
 *
 */
public class User {
	
	private String name;
	private String id;
	
	/**
	 * No args constructor. Sets the name and the ID number of the user to a blank string.
	 */
	public User() {
		name = "";
		id = "";
	}
	
	/**
	 * A constructor that takes in the name and ID number of the new user
	 * @param name Name of the user
	 * @param id ID number of the student
	 */
	public User(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Gets the name of the user
	 * @return The name of the user as a String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the ID number of the user
	 * @return The ID number of the user as a String
	 */
	public String getId() {
		return id;
	}
	

	@Override
	public boolean equals(Object obj) {
		Student other = null;
		if (obj instanceof Student) 
			other = (Student)obj;
		else 
			throw new IllegalArgumentException("Must be type Student");
		
		return name.equals(other.getName()) && id.equals(other.getId());
	}
	
	
	@Override 
	public String toString() {
		return "name: " + name + ", id: " + id;
	}

}
