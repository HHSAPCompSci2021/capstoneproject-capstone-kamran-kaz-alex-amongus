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
		if (students == null || teachers == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.students = students;
		this.teachers = teachers;
	}
	
	public void addStudent(String name) {
		students.add(new Student(name));
	}
	
	public void addStudent(Student student) {
		if (student == null) 
			throw new IllegalArgumentException("Student must be properly initialized");
		students.add(student);
	}
	
	public void addTeacher(String name) {
		teachers.add(new Teacher(name));
	}
	
	public void addTeacher(Teacher teacher) {
		if (teacher == null) 
			throw new IllegalArgumentException("Teacher must be properly initialized");
		teachers.add(teacher);
	}
	/**
	 * This method finds and returns the student 
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
	
	public Teacher getTeacher(String teacherName) {
		
		return null;
	}
	
	
	
}
