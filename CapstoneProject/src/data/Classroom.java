package data;

import java.util.ArrayList;
/**
 * The Classroom class models an online classroom. The classroom has a name, a list of teachers, and a list of students.  
 * @author Kaz Nakao
 *
 */
public class Classroom {

	private String name;
	private ArrayList<Student> students;
	private ArrayList<Teacher> teachers;
	private ArrayList<Rubric> assignments;
	
	/**
	 * No args constructor to create a new Classroom with empty properties.
	 */
	public Classroom() {
		name = "";
		students = new ArrayList<Student>();
		teachers = new ArrayList<Teacher>();
		assignments = new ArrayList<Rubric>();
	}
	
	/**
	 * Will create a new classroom with no students and no teachers. Useful for creating a blank classroom with just a name.
	 * @pre Name of the classroom should have characters in it and be properly initialized
	 * @param name Name of the classroom
	 */
	public Classroom(String name) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Classroom name should be properly initialized and not blank");
		this.name = name;
		students = new ArrayList<Student>();
		teachers = new ArrayList<Teacher>();
		assignments = new ArrayList<Rubric>();
	}
	/**
	 * Creates a new classroom with predetermined students and teachers
	 * @pre Name of the classroom should have characters in it and be properly initialized. Parameters should not be null
	 * @param name Name of the classroom
	 * @param students A list of students
	 * @param teachers A list of teachers in the classroom
	 */
	public Classroom(String name, ArrayList<Student> students, ArrayList<Teacher> teachers, ArrayList<Rubric> assignments) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Classroom name must not be blank or null");
		if (students == null) 
			throw new IllegalArgumentException("List of Students must be properly initialized");
		if (teachers == null) 
			throw new IllegalArgumentException("List of Teachers must be properly initialized");
		if (assignments == null) 
			throw new IllegalArgumentException("List of Rubrics must be properly initialized");
		
		this.name = name;
		this.students = students;
		this.teachers = teachers;
		this.assignments = assignments;
	}
	
	/**
	 * 
	 * @return The ArrayList of students in the classroom
	 */
	public ArrayList<Student> getStudents() {
		return students;
	}
	
	/**
	 * 
	 * @return The ArrayList of teachers in the classroom
	 */
	public ArrayList<Teacher> getTeachers() {
		return teachers;
	}
	
	/**
	 * 
	 * @return A list of Rubric objects that represent the assignments in the classroom.
	 */
	public ArrayList<Rubric> getAssignments() {
		return assignments;
	}
	
	/**
	 * Adds a Student object to the list of students in the classroom
	 * @pre Student must be initialized.
	 * @param student
	 */
	public void addStudent(Student student) {
		if (student == null) 
			throw new IllegalArgumentException();
		students.add(student);
	}
	
	/**
	 * Adds a Teacher object to the list of teachers in the classroom
	 * @pre Teacher must be initialized.
	 * @param teacher
	 */
	public void addTeacher(Teacher teacher) {
		if (teacher == null) 
			throw new IllegalArgumentException();
		teachers.add(teacher);
	}
	/**
	 * Adds a Rubric object to the list of assignments. Will be displayed as being an "assignment"
	 * @pre Rubric must be initialized
	 * @param rubric The new assignment to be added to the classroom
	 */
	public void addAssignment(Rubric rubric) {
		if (rubric == null) 
			throw new IllegalArgumentException();
		assignments.add(rubric);
	}
	
	public boolean hasStudent(Student student) {
		for (Student s : students) {
			if (s.equals(student)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasTeacher(Teacher teacher) {
		for (Teacher t : teachers) {
			if (t.equals(teacher)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Goes through each students submissions to find which ones have not been graded yet that are associated to a particular rubric
	 * @param index The index of the rubric in the list of rubrics in the classroom
	 * @return An ArrayList with Submission objects that contain submissions without a grade that have the matching rubric index.
	 */
	public ArrayList<Submission> getUngraded(int index) {
		
		if (index >= assignments.size()) {
			throw new IndexOutOfBoundsException("index must be between 0 and the number of rubrics - 1");
		}
		
		ArrayList<Submission> ungraded = new ArrayList<Submission>();
		
		for (Student s : students) {
			ArrayList<Submission> submissions = s.getSubmissions();
			for (Submission submit : submissions) {
				if (submit.getGrade().equals(Submission.ungraded) && submit.getRubric() == index) {
					ungraded.add(submit);
				}
			}
		}
		
		return ungraded;
	}
	
	/**
	 * 
	 * @return Name of the classroom
	 */
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		String output = "Classroom " + name + ": \n";
		output += "Teachers: ";
		output += teachers;
		output += "\n";
		output += "Students";
		output += students;
		return output;
	}
	
}
