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
	 * No args constructor, will initialize the name and content to an empty string.
	 */
	public Submission() {
		name = "";
		grade = "";
		content = "";
		submissionDate = new Date().toString();
		throw new IllegalArgumentException("Submission name may not be null");
	}
	
	/**
	 * Constructor with arguments for 
	 */
	public Submission(String name, String content, String grade) {
		this.name=name;
		this.grade=grade;
		this.content=content;
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
	
	/**
	 *
	 * @return The title of the submission.
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return The content of the submission.
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * 
	 * @return The grade that has been given to the submission. If no grade has been given yet, the method will return an empty string.
	 */
	public String getGrade() {
		return grade;
	}
	
	/** 
	 * Changes the grade of the submission to a set grade.
	 * @param g The grade that you want to assign. 
	 */
	public void setGrade(String g) {
		grade = g;
	}
	
	/**
	 * 
	 * @return The date and time that the submission was made.
	 */
	public String getSubmissionDate() {
		return submissionDate;
	}
	
	@Override 
	public boolean equals(Object obj) {
		Submission other = null;
		if (obj instanceof Submission)
			other = (Submission)obj;
		else
			throw new IllegalArgumentException("Must be type Submission");
		
		return name.equals(other.getName()) && grade.equals(other.getGrade()) && content.equals(other.getContent()) 
				&& submissionDate.equals(other.getSubmissionDate());
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
