package data;
/**
 * Models a teacher user that will be part of a classroom
 * @author Kaz Nakao
 *
 */
public class Teacher {
	
	private String name;
	private String id;

	public Teacher() {
		name = "";
		id = "";
	}
	/**
	 * 
	 * @param name Name of the teacher
	 * @param id the user id number inputted by user
	 */
	public Teacher(String name, String id) {
		this.name = name;
		this.id = id;;
	}
	
	/**
	 * 
	 * @return Name of the teacher
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The id number of the teacher
	 */
	public String getId() {
		return id;
	}
}
