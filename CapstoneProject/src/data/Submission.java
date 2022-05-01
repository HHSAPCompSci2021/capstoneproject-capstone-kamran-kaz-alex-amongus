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
	private char grade;
	private String content;
	private String submissionDate;
	/**
	 * No args constructor, will initialize the name and content to a blank string.
	 */
	public Submission() {
		name = "";
		grade = 0;
		content = "";
		submissionDate = new Date().toString();
	}
	
	/**
	 * Creates a new submission object.
	 * @param name Name of the submission
	 * @param content The essay material that is in the submission
	 */
	public Submission(String name, String content) {
		if (StringChecker.isEmpty(name)) 
			throw new IllegalArgumentException("Submission must have a name");
		if (StringChecker.isEmpty(content)) 
			throw new IllegalArgumentException("Submission cannot be empty");
		
		this.name = name;
		this.content = content;
		this.submissionDate = new Date().toString();
		this.grade = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public String getContent() {
		return content;
	}
	
	public char getGrade() {
		return grade;
	}
	
	public void setGrade(char g) {
		grade = g;
	}
	
	public String getDate() {
		return submissionDate;
	}
}
