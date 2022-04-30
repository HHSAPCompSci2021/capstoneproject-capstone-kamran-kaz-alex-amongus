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
	 * @Precondition Name of the classroom should have characters in it and be properly initialized
	 * @param name Name of the classroom
	 */
	public Classroom(String name) {
		if (StringChecker.isEmpty(name))
			throw new IllegalArgumentException("Classroom name should be properly initialized and not be blank");
		this.name = name;
		students = new ArrayList<Student>();
		teachers = new ArrayList<Teacher>();
	}
	/**
	 * Creates a new classroom with predetermined students and teachers
	 * @Precondition Name of the classroom should have characters in it and be properly initialized. Parameters should not be null
	 * @param name Name of the classroom
	 * @param students A list of students
	 * @param teachers A list of teachers in the classroom
	 */
	public Classroom(String name, ArrayList<Student> students, ArrayList<Teacher> teachers) {
		if (StringChecker.isEmpty(name))
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
	 * Adds a new Student object to the list of students in the classroom
	 * @Precondition student name should be initialized
	 * @param name The name of the new student
	 */
	public void addStudent(String name) {
		if (StringChecker.isEmpty(name))
			throw new IllegalArgumentException("student name must be initialized and not be blank");
		students.add(new Student(name));
	}
	/**
	 * Adds a Student object to the list of students in the classroom
	 * @Precondition Student object must be initialized
	 * @param student Student object to add to the list of students
	 */
	public void addStudent(Student student) {
		if (student == null) 
			throw new IllegalArgumentException("Student must be properly initialized");
		students.add(student);
	}
	/**
	 * Adds a new Teacher object to the list of students in the classroom
	 * @Precondition Teacher name should be initialized
	 * @param name The name of the new Teacher
	 */
	public void addTeacher(String name) {
		if (StringChecker.isEmpty(name))
			throw new IllegalArgumentException("teacher name must be initialized and not be blank");
		teachers.add(new Teacher(name));
	}
	/**
	 * Adds a Student object to the list of Teachers in the classroom
	 * @Precondition Teacher object should be initialized. 
	 * @param teacher Teacher object to add to the list of students
	 */
	public void addTeacher(Teacher teacher) {
		if (teacher == null) 
			throw new IllegalArgumentException("Teacher must be properly initialized");
		teachers.add(teacher);
	}
	/**
	 * This method finds and returns the student corresponding to the name passed in.
	 * @param studentName The name of the student that you want to find
	 * @return The Student object that with the same name as studentName. Will return null if no such student exists.
	 */
	public Student getStudent(String studentName) {
		if (StringChecker.isEmpty(studentName))
			throw new IllegalArgumentException("student name must be initialized and not be blank");
		int index = getStudentIndex(studentName);
		if (index > 0) {
			return students.get(index);
		}
		return null;
	}
	
	private int getStudentIndex(String studentName) {
		for (int i = 0; i < students.size(); i++) {
			Student s = students.get(i);
			if (s.getName().equals(studentName)) 
				return i;
		}
		return -1;
	}
	/**
	 * This method finds and returns the teacher corresponding to the name passed in. 
	 * @param teacherName The name of the teacher that you want to find
	 * @return The Teacher object that with the same name as studentName. Will return null if no such Teacher exists.
	 */
	public Teacher getTeacher(String teacherName) {
		if (StringChecker.isEmpty(teacherName))
			throw new IllegalArgumentException("teacher name must be initialized and not be blank");
		int index = getTeacherIndex(teacherName);
		if (index > 0) {
			return teachers.get(index);
		}
		return null;
	}
	
	private int getTeacherIndex(String teacherName) {
		for (int i = 0; i < teachers.size(); i++) {
			Teacher t = teachers.get(i);
			if (t.getName().equals(teacherName))
				return i;
		}
		return -1;
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
