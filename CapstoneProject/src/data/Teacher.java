package data;
/**
 * Models a teacher user that will be part of a classroom. A Teacher has a name, ID number, and a subject that they teach.
 * @author Kaz Nakao
 *
 */
public class Teacher extends User{
	

	private String subject;
	
	/**
	 * No args constructor. Sets the subject of the teacher to a blank screen along with the name and id number
	 */
	public Teacher() {
		super();
		this.subject = "";
	}
	
	/**
	 * Creates a teacher with a name and id number. The subject is undecided
	 * @param name Name of the teacher
	 * @param id the user id number inputted by user
	 */
	public Teacher(String name, String id) {
		super(name, id);
		this.subject = "";
	}
	
	/**
	 * Creates a teacher with a name, id number, and a subject from arguments in the constructor. 
	 * @param name Name of the teacher
	 * @param id ID number of the teacher as a String
	 * @param subject The subject of the teacher
	 */
	public Teacher(String name, String id, String subject) {
		super(name, id);
		this.subject = subject;
	}
	
	/**
	 * Gets the subject of the teacher
	 * @return The subject of the teacher as a string
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Sets the subject to a certain subject
	 * @param subject The subject that you want to change the subject ot as a string
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Override
	public String toString() {
		return super.toString() + " Subject: " + subject;
	}
	
}
