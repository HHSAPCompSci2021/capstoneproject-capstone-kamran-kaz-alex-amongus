package data;

import java.util.ArrayList;
/**
 * The Student class models a student in the online classroom.
 * A student will have a list of the submissions that they have made to all classrooms. 
 * A student will be able to make submissions to a classroom. 
 * @author Kaz Nakao
 */

public class Student extends User{
	
	private ArrayList<Submission> submissions;
	/**
	 * No args Constructor. The submissions for the student will be set to an empty list and the name and id will be set to an empty string.
	 */
	public Student() {
		super();
		submissions = new ArrayList<Submission>();
	}
	
	/**
	 * Creates a new student
	 * @param username Name of the student
	 * @param idnum ID number of the student
	 */
	public Student(String username, String idnum) {
		super(username, idnum);
		submissions = new ArrayList<Submission>();
	}

	/**
	 * Adds a new submission for the student
	 * @param submissionName The name of the new submission being created
	 * @param submission The content that will be inside the submission
	 * @param rubric The index of the rubric corresponding to the assignment
	 */
	public void add(String submissionName, String submission, int rubric) {
		if (submissionName == null || submission.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission name cannot be null or be blank");
		if (submission == null || submission.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission cannot be null or be blank");
		submissions.add(new Submission(submissionName, submission, rubric));
	}
	
	/**
	 * Adds a new submission to the list of submissions that the student has
	 * @param s The submission object that you want to add
	 */
	public void add(Submission s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		submissions.add(s);
	}
	
	
	/**
	 * Returns an arraylist of the submissions of the student in a particular classroom. 
	 * @return An ArrayList of the submissions that the student has
	 */
	public ArrayList<Submission> getSubmissions() {
		return submissions;
	}
	
	
	
	@Override
	public String toString() {
		String output = super.toString();
		output += " submisisons: " + submissions.size();
		return output;
	}
}
