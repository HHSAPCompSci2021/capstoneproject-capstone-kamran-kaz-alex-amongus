package data;

public class Teacher {
	
	private String name;
	private int id;

	public Teacher() {
		name = "";
		id = 0;
	}
	/**
	 * 
	 * @param username Name of the teacher
	 */
	public Teacher(String name, int id) {
		this.name = name;
		this.id = id;;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
}
