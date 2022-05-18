package data;

import java.util.ArrayList;
/**
 * Models an assignment for a classroom. The Rubric class contains the name of the submission and a list of RubricRow objects that contain grading criteria
 * @author Kaz
 *
 */
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
	 * @return Returns an ArrayList of the criteria of the rubric
	 */
	public ArrayList<RubricRow> getCriteria() {
		return criteria;
	}
	
	/**
	 * Creates a rubric class based on a .rubric file input
	 * @pre input String must be in the proper format of a .rubric file
	 * @param input .rubric file text as String
	 * @param name name of rubric
	 * @return Rubric containing 
	 */
	public static Rubric makeRubric(String input, String name) {

		String lineSeparator = System.getProperty("line.separator");
		int start = 0;
		Rubric rubric = new Rubric(name);
		RubricRow temp = new RubricRow();
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '`') {
				temp.add(input.substring(start, i));
				start = i + 2;
			}
			else if (input.charAt(i) == lineSeparator.charAt(0)) {
				temp.add(input.substring(start, i));
				start = i + 1;
				rubric.addCriteria(temp);
				temp = new RubricRow();
			}
		}
		
		return rubric;
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
