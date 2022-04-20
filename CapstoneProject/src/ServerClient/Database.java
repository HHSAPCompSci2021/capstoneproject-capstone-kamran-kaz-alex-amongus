package ServerClient;

import java.util.ArrayList;

public class Database {
	
	private final String rootPath = "/user/apps/GradeMe/";
	@SuppressWarnings("unused")
	private ArrayList<ArrayList<ArrayList<String>>> database = new ArrayList<>();
	
	public Database() {
		
	}
	
	public String fetchStudent(String classroom, String student) {
		String path = rootPath + "/" + classroom + "/" + student;
		return path;
	}

	public String fetch(String classroom, String student, String assignment) {
		String path = rootPath + "/" + classroom + "/" + student;
		return path;
	}
	
	public void addAssignment(String classroom, String assignment) {
		ArrayList<ArrayList<String>> classrm = database.get(database.indexOf(classroom));
		for (int i = 0; i < classrm.size(); i++) {
			classrm.get(i).add(assignment);
		}
	}
	
	public void addStudent(String classroom, String student) {
		ArrayList<String> stdnt = new ArrayList();
		database.get(database.indexOf(classroom)).add(stdnt);
	}
	
	public void addClassroom(String classroom, String[] students) {
		
	}
	
	public void deleteStudent(String classroom, String student) {
		
	}
	
	public void deleteAssignment(String classroom) {
		
	}
	
	public void deleteClassroom(String classroom) {
		
	}
}
