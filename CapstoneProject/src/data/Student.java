package data;

import java.util.ArrayList;
/**
 * The Student class models a student in the online classroom.
 * A student will have a list of the submissions that they have made to all classrooms. 
 * A student will be able to make submissions to a classroom. 
 * @author Kaz Nakao
 */

public class Student {
	
	private ArrayList<Submission> submissions;
	private String name;
	private String id;
	
	/**
	 * No args Constructor. The submissions for the student will be set to an empty list and the name and id will be set to an empty string.
	 */
	public Student() {
		submissions = new ArrayList<Submission>();
		name = "";
		id = "";
	}
	
	/**
	 * Creates a new student
	 * @param username Name of the student
	 * @param idnum ID number of the student
	 */
	public Student(String username, String idnum) {
		submissions = new ArrayList<Submission>();
		name = username;
		id = idnum;
	}

	/**
	 * Adds a new submission for the student
	 * @param submissionName The name of the new submission being created
	 * @param submission The content that will be inside the submission
	 */
	public void add(String submissionName, String submission) {
		if (submissionName == null || submission.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission name cannot be null or be blank");
		if (submission == null || submission.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission cannot be null or be blank");
		submissions.add(new Submission(submissionName, submission));
	}
	
	public void add(Submission s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		submissions.add(s);
	}
	
	
	/**
	 * 
	 * @return An ArrayList of the submissions that the student has
	 */
	public ArrayList<Submission> getSubmissions() {
		return submissions;
	}
	
	/**
	 * 
	 * @return The name of the student
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return The id number of the student as a String
	 */
	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		Student other = null;
		if (obj instanceof Student) 
			other = (Student)obj;
		else 
			throw new IllegalArgumentException("Must be type Student");
		
		return name.equals(other.getName()) && submissions.equals(other.getSubmissions()) && id.equals(other.getId());
	}
	
	
	@Override
	public String toString() {
		String output = "Name: " + name + ", ";
		output += "ID: " + id;
		return output;
	}
}
