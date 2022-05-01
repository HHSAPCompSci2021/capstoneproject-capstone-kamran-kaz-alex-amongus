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
	
	public Student() {
		submissions = new ArrayList<Submission>();
		name = "";
		id = "";
	}
	
	/**
	 * Creates a new student
	 * @param username Name of the student
	 */
	public Student(String username, String idnum) {
		submissions = new ArrayList<Submission>();
		name = username;
		id = idnum;
	}

	public void add(String submissionName, String submission) {
		if (StringChecker.isEmpty(submissionName)) 
			throw new IllegalArgumentException("Submission name cannot be null or be blank");
		if (StringChecker.isEmpty(submission)) 
			throw new IllegalArgumentException("Submission cannot be null or be blank");
		submissions.add(new Submission(submissionName, submission));
	}
	
	public ArrayList<Submission> getSubmissions() {
		return submissions;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
}
