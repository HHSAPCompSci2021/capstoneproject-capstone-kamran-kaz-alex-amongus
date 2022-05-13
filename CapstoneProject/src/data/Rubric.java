package data;

public class Rubric {

	private String name;
	
	/**
	 * No Args constructor. Will automatically set the name to an empty String.
	 */
	public Rubric() {
		name = "";
	}
	
	/**
	 * 
	 * @param name The name of the rubric
	 */
	public Rubric(String name) {
		this.name = name;
	}
	
	
	/**
	 * Getter for Rubric name
	 * @return Rubric Name
	 */
	public String getName() {
		return name;
	}
}
