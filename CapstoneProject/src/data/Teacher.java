package data;

public class Teacher {
	
	private String name;

	public Teacher() {
		name = "";
	}
	/**
	 * 
	 * @param username Name of the teacher
	 */
	public Teacher(String username) {
		name = username;
	}
	
	public String getName() {
		return name;
	}
}