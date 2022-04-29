package data;

import java.util.ArrayList;

public class Classroom {

	private String name;
	private ArrayList<Student> students;
	private ArrayList<Teacher> teachers;
	public Classroom(String name) {
		this.name = name;
		students = new ArrayList<Student>();
		teachers = new ArrayList<Teacher>();
	}
	
	public Classroom(String name, ArrayList<Student> students, ArrayList<Teacher> teachers) {
		if (students == null) {
			throw new IllegalArgumentException("List of Students must be properly initialized");
		}
		if (teachers == null) {
			throw new IllegalArgumentException("List of Teachers must be properly initialized");
		}
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
		if (name == null)
			throw new IllegalArgumentException("student name must be initialized");
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
	
	public void addTeacher(String name) {
		if (name == null)
			throw new IllegalArgumentException("teacher name must be initialized");
		teachers.add(new Teacher(name));
	}
	
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
		int index = getStudentIndex(studentName);
		if (index > 0) {
			return students.get(index);
		}
		return null;
	}
	
	private int getStudentIndex(String studentName) {
		for (int i = 0; i < students.size(); i++) {
			Student s = students.get(i);
			if (s.getUsername().equals(studentName)) 
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
		int index = getTeacherIndex(teacherName);
		if (index > 0) {
			return teachers.get(index);
		}
		return null;
	}
	
	private int getTeacherIndex(String teacherName) {
		for (int i = 0; i < teachers.size(); i++) {
			Teacher t = teachers.get(i);
			if (t.getUsername().equals(teacherName))
				return i;
		}
		return -1;
	}
	
	public ArrayList<Student> getStudents() {
		return students;
	}
	
}
