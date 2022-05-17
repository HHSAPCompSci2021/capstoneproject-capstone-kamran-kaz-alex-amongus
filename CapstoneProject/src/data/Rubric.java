package data;

import java.util.ArrayList;

public class Rubric {

	private String name;
	private ArrayList<ArrayList<String>> criteria;
	
	/**
	 * No Args constructor. Will automatically set the name to an empty String.
	 */
	public Rubric() {
		name = "";
		criteria = new ArrayList<ArrayList<String>>();
	}
	
	/**
	 * 
	 * @param name The name of the rubric
	 */
	public Rubric(String name) {
		this.name = name;
		criteria = new ArrayList<ArrayList<String>>();
	}
	
	/**
	 * 
	 * @param name Name of the assignment
	 * @param criteria A 2D arraylist of Strings that each outline an aspect that the assignment should have
	 */
	public Rubric(String name, ArrayList<ArrayList<String>> criteria) {
		this.name = name;
		this.criteria = criteria;
	}
	
	
	public void addCriteria(ArrayList<String> newCriteria) {
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
	public ArrayList<ArrayList<String>> getCriteria() {
		return criteria;
	}
}
