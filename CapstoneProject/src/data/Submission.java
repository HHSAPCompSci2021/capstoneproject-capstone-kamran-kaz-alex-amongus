package data;

import java.util.Date;

/**
 * The Submission class models a submission that a student makes to the teacher. A submission will contain 
 * the names of the submission as well as the content inside.
 * This class is immutable.
 * @author Kaz Nakao
 *
 */
public class Submission {
	
	private String name;
	private String grade;
	private String content;
	private String submissionDate;
	/**
	 * No args constructor, will initialize the name and content to a blank string.
	 */
	public Submission() {
		name = "";
		grade = "";
		content = "";
		submissionDate = new Date().toString();
	}
	
	/**
	 * Creates a new submission object.
	 * @param name Name of the submission
	 * @param content The essay material that is in the submission
	 */
	public Submission(String name, String content) {
		if (name == null || name.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission must have a name");
		if (content == null || content.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission cannot be empty");
		
		this.name = name;
		this.content = content;
		this.submissionDate = new Date().toString();
		this.grade = "";
	}
	
	public String getName() {
		return name;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String g) {
		grade = g;
	}
	
	public String getSubmissionDate() {
		return submissionDate;
	}
	
	@Override
	public String toString() {
		String output = name + "\n";
		output += content + "\n";
		output += submissionDate + "\n";
		output += grade + "\n";
		return output;
	}
}
