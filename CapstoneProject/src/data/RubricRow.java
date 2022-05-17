package data;

public class RubricRow {
	
	private String name, grade1, grade2, grade3, grade4, grade5;
	
	public RubricRow() {
		name = "";
		grade1 = "";
		grade2 = "";
		grade3 = "";
		grade4 = "";
		grade5 = "";
	}
	
	public RubricRow(String name, String grade1, String grade2, String grade3, String grade4, String grade5) {
		this.name = name;
		this.grade1 = grade1;
		this.grade2 = grade2;
		this.grade3 = grade3;
		this.grade4 = grade4;
		this.grade5 = grade5;
	}
	
	/**
	 * 
	 * @return Title of the criteria
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The grade content requirement for 1 point
	 */
	public String getGrade1() {
		return grade1;
	}
	
	/**
	 * 
	 * @return The grade content requirement for 2 points
	 */
	public String getGrade2() {
		return grade2;
	}
	
	/**
	 * 
	 * @return The grade content requirement for 3 points
	 */
	public String getGrade3() {
		return grade3;
	}
	
	/**
	 * 
	 * @return The grade content requirement for 4 points
	 */
	public String getGrade4() {
		return grade4;
	}
	
	/**
	 * 
	 * @return The grade content requirement for 5 points
	 */
	public String getGrade5() {
		return grade5;
	}
	
	
	@Override
	public String toString() {
		return name + ", " + grade5 + ", " + grade4 + ", " + grade3 + ", " + grade2 + ", " + grade1;
	}
	

}
