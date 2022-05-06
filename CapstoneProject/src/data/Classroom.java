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
	/**
	 * Will create a new classroom with no students and no teachers
	 * @pre Name of the classroom should have characters in it and be properly initialized
	 * @param name Name of the classroom
	 */
	public Classroom(String name) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Classroom name should be properly initialized and not be blank");
		this.name = name;
		students = new ArrayList<Student>();
		teachers = new ArrayList<Teacher>();
	}
	/**
	 * Creates a new classroom with predetermined students and teachers
	 * @pre Name of the classroom should have characters in it and be properly initialized. Parameters should not be null
	 * @param name Name of the classroom
	 * @param students A list of students
	 * @param teachers A list of teachers in the classroom
	 */
	public Classroom(String name, ArrayList<Student> students, ArrayList<Teacher> teachers) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Classroom name must not be blank or null");
		if (students == null) 
			throw new IllegalArgumentException("List of Students must be properly initialized");
		if (teachers == null) 
			throw new IllegalArgumentException("List of Teachers must be properly initialized");
		this.name = name;
		this.students = students;
		this.teachers = teachers;
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
	 * Adds a Student object to the list of students in the classroom
	 * @param student
	 */
	public void addStudent(Student student) {
		if (student == null) 
			throw new IllegalArgumentException();
		students.add(student);
	}
	
	/**
	 * Adds a Teacher object to the list of teachers in the classroom
	 * @param teacher
	 */
	public void addTeacher(Teacher teacher) {
		if (teacher == null) 
			throw new IllegalArgumentException();
		teachers.add(teacher);
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
