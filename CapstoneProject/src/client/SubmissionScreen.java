package client;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import data.Classroom;
import data.DatabaseModifier;
import data.Student;

public class SubmissionScreen extends JFrame{

	public final static String fileSeparator = System.getProperty("file.separator");
	public final static String userDir = System.getProperty("user.dir");
	public final static String lineSeparator = System.getProperty("line.separator");

	
	private Classroom classroom;
	private Student student;
	private DatabaseModifier m;
	private int rubric;
	
	public SubmissionScreen(Classroom classroom, Student student, DatabaseModifier m, int rubric) {
		this.classroom = classroom;
		this.student = student;
		this.m = m;
		this.rubric = rubric;
		
		
		
		
	}
	
	
	
	private String readFile(String inputFile) throws IOException{
		Scanner scan = null;
		StringBuffer fileData = new StringBuffer();
		try {
			FileReader reader = new FileReader(inputFile);
			scan = new Scanner(reader);
			while(scan.hasNext()) {
				fileData.append(scan.nextLine());
				fileData.append(lineSeparator);
			}
		} finally {
			if (scan != null) 
				scan.close();
		}
		return fileData.toString();
	}
	

	
}
