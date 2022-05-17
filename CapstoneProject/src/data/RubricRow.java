package data;

import java.util.ArrayList;
/**
 * Models a row of a rubric with a grading criteria. 
 * @author Kaz
 *
 */
public class RubricRow {
	
	private ArrayList<String> grades;
	/**
	 * Will create a new blank RubricRow
	 */
	public RubricRow() {
		grades = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @return A list of criteria of the particular aspect of the essay
	 */
	public ArrayList<String> getGrades() {
		return grades;
	}
	
	/**
	 * 
	 * @param grade Adds a new aspect to the criteria of rubric
	 */
	public void add(String grade) {
		grades.add(grade);
	}
	
	
	@Override
	public String toString() {
		return grades.toString();
	}
	

}
