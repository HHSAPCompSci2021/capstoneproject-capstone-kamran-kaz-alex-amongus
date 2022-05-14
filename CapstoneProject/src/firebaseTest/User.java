package firebaseTest;

import java.util.ArrayList;

public class User {


	private String name;
	private ArrayList<String> things;
	
	public User() {
		name = "";
		things = new ArrayList<String>();
	}
	
	public User(String name) {
		this.name = name;
		things = new ArrayList<String>();
	}
	
	/**
	 * Adds a thing to the list of things.
	 * @param thing Thing you want to add to the list of things
	 */
	public void addThing(String thing) {
		things.add(thing);
	}
	
	/**
	 * 
	 * @return name of user
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return list of things as an arraylist of strings
	 */
	public ArrayList<String> getThings() {
		return things;
	}
	
	@Override
	public String toString() {
		String output = "Name : " + name + ", things: " + things;
		return output;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User other = (User)obj;
			return other.getName().equals(name);
		}
		return false;
	}
	
}
