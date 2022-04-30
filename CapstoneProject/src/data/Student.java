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
	 * 
	 * @param username Name of the student
	 */
	public Student(String username) {
		super(username);
		submissions = new ArrayList<Submission>();
		
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
		if (index > 1) {
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
}
