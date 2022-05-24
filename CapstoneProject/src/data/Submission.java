package data;

/**
 * The Submission class models a submission that a student makes to the teacher. A submission will contain 
 * the names of the submission as well as the content inside.
 * This class is immutable.
 * @author Kaz Nakao
 *
 */
public class Submission {
	/**
	 * The default String for which ungraded assignments recieve
	 */
	public static final String UNGRADED = "UNGRADED";
	
	private String name;
	private String grade;
	private String content;
	private String submissionDate;
	private int rubric;
	
	
	/**
	 * No args constructor, will initialize the name and content to an empty string.
	 */
	public Submission() {
		name = "";
		grade = UNGRADED;
		content = "";
		//submissionDate = new Date().toString();
		submissionDate = "";
		rubric = 0;
	}
	
	/**
	 * For initializing a submission with a grade already set
	 * @param name Title of the submission
	 * @param content The "content" of the submission. Essay material
	 * @param grade The grade of the submission
	 * @param rubric The index of the rubric in the classroom that this submission will be found in. 
	 */
	public Submission(String name, String content, String grade, int rubric) {
		this.name=name;
		this.grade=grade;
		this.content=content;
		//submissionDate = new Date().toString();
		submissionDate = "";
		this.rubric = rubric;
	}
	
	/**
	 * Creates a new submission object.
	 * @param name Name of the submission
	 * @param content The essay material that is in the submission
	 * @param rubric The index of the rubric in the classroom that this submission will be found in. 
	 */
	public Submission(String name, String content, int rubric) {
		if (name == null || name.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission must have a name");
		if (content == null || content.trim().isEmpty()) 
			throw new IllegalArgumentException("Submission cannot be empty");
		
		this.name = name;
		this.content = content;
		//this.submissionDate = new Date().toString();
		submissionDate = "";
		this.grade = UNGRADED;
		this.rubric = rubric;
	}
	
	/**
	 * Gets the title of the submission.
	 * @return The title of the submission.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Gets the "essay" of teh submission. The content of the submission
	 * @return The content of the submission.
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * The grade of the submission. Will be "UNGRADED" by default.
	 * @return The grade that has been given to the submission. If no grade has been given yet, the method will return an empty string.
	 */
	public String getGrade() {
		return grade;
	}
	
	/**
	 * The rubric index that is being used to grade this assignment. The index corresponds to a rubric object
	 * that exists in the list of rubric assignments in the classroom.
	 * @return The index position of the rubric being used to grade this submission.
	 */
	public int getRubric() {
		return rubric;
	}
	
	/** 
	 * Changes the grade of the submission to a set grade.
	 * @param g The grade that you want to assign. 
	 */
	public void setGrade(String g) {
		grade = g;
	}
	
	/**
	 * Gets the date at which the submission was made. 
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
