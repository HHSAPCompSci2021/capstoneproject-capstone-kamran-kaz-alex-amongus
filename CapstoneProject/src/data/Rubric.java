package data;

import java.util.ArrayList;

public class Rubric {

	private String name;
	private ArrayList<RubricRow> criteria;
	
	/**
	 * No Args constructor. Will automatically set the name to an empty String.
	 */
	public Rubric() {
		name = "";
		criteria = new ArrayList<RubricRow>();
	}
	
	/**
	 * 
	 * @param name The name of the rubric
	 */
	public Rubric(String name) {
		this.name = name;
		criteria = new ArrayList<RubricRow>();
	}
	
	/**
	 * 
	 * @param name Name of the assignment
	 * @param criteria A 2D arraylist of Strings that each outline an aspect that the assignment should have
	 */
	public Rubric(String name, ArrayList<RubricRow> criteria) {
		this.name = name;
		this.criteria = criteria;
	}
	
	/**
	 * Adds a new criteria point to the rubric
	 * @param newCriteria
	 */
	public void addCriteria(RubricRow newCriteria) {
		criteria.add(newCriteria);
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
	public ArrayList<RubricRow> getCriteria() {
		return criteria;
	}
	
	
	@Override
	public String toString() {
		String output = name + "\n";
		for (RubricRow r : criteria) {
			output += r + "\n";
		}
		return output;
	}
	
}
