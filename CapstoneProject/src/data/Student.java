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
	
	public Submission getSubmission(String name) {
		int index = getIndex(name);
		if (index > 0) {
			return submissions.get(index);
		}
		return null;
	}
	
	public Submission getSubmission(int index) {
		return submissions.get(index);
	}
	
	private int getIndex(String name) {
		for (int i = 0; i < submissions.size(); i++) {
			if (submissions.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
}
