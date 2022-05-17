package data;

import java.util.ArrayList;

public class RubricRow {
	
	private ArrayList<String> grades;
	
	public RubricRow() {
		grades = new ArrayList<String>();
	}
	
	public RubricRow(String name) {
		this.grades = new ArrayList<String>();
	}
	
	public ArrayList<String> getGrades() {
		return grades;
	}
	
	/**
	 * 
	 * @param grade 
	 */
	public void add(String grade) {
		grades.add(grade);
	}
	
	
	@Override
	public String toString() {
		return grades.toString();
	}
	

}
