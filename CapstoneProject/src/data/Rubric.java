package data;

import java.util.ArrayList;

public class Rubric {

	private String name;
	private ArrayList<String> criteria;
	
	/**
	 * No Args constructor. Will automatically set the name to an empty String.
	 */
	public Rubric() {
		name = "";
		criteria = new ArrayList<String>();
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
	
	/**
	 * 
	 * @return Returns an ArrayList of the criteria of the rubric.
	 */
	public ArrayList<String> getCriteria() {
		return criteria;
	}
}
