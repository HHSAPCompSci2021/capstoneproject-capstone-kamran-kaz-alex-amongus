package data;

import java.util.ArrayList;

public class Student extends User{

	private ArrayList<String> submissionNames;
	private ArrayList<String> submissions;
	
	public Student(String username) {
		super(username);
		
	}

	public void add(String submissionName, String submission) {
		if (submissionName == null) {
			throw new IllegalArgumentException();
		}
		if (submission == null) {
			throw new IllegalArgumentException();
		}
		submissionNames.add(submissionName);
		submissions.add(submission);
	}
	
	public String getSubmission(String name) {
		int index = getIndex(name);
		if (index > 1) {
			return submissions.get(index);
		}
		return null;
	}
	
	public String getSubmissionName(int index) {
		return submissionNames.get(index);
	}
	
	private int getIndex(String name) {
		for (int i = 0; i < submissionNames.size(); i++) {
			if (submissionNames.get(i).equals(name)) {
				return i;
			}
		}
		return -1;
	}
}
