package ServerClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import data.Classroom;
import data.DatabaseModifier;
import data.Rubric;
import data.RubricRow;
import data.Submission;

/**
 * Runs the server side program and initalizes its datacollection
 * @author Kamran Hussain
 *
 */
public class Main {
	private static final String ArrayList = null;

	public static void main(String[] args) throws IOException, TranslateException, ModelException {
		
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.ERROR);
	    
		// Load essays and rubric from database
		DatabaseModifier.setupDatabase(); //initializes firebase things on machine
		
		HashMap<String, Classroom> classrooms = DatabaseModifier.getClassrooms();
		//Put them into the hashmap
		//
		Grader g = new Grader();
		
		//Goes through each classroom in the database
		//Finds the ungraded classrooms and collects them as a HashMap of submissions and rubrics as 2D arrays
		//Get the grade for the assignment from the model, update the grade of the submisson
		//After going through all of the ungraded assignments in the classroom, updates all the submissions. 
		for (Map.Entry<String, Classroom> classroom : classrooms.entrySet()) {
			String key = classroom.getKey();
			HashMap<Submission, String[][]> submission = getSubmissions(classroom.getValue());
			//grades
			for (Map.Entry<Submission, String[][]> entry : submission.entrySet()) {
				// get grade from grader
				String[] grade = g.getGrade(entry.getKey().getContent(), entry.getValue());
				entry.getKey().setGrade(grade.toString());
			}
			
			DatabaseModifier.set(key, classroom.getValue());
		}
		
		//Updates database if hashmap is empty
		
		
	}
	
	public static HashMap<Submission, String[][]> getSubmissions(Classroom classroom) {
		
		HashMap<Submission, String[][]> map = new HashMap<Submission, String[][]>();
		int numAssign = classroom.getAssignments().size();
		
		for (int i = 0; i < numAssign; i++) {
			ArrayList<Submission> submissions = classroom.getUngraded(i);
			Rubric rubric = classroom.getAssignments().get(i);
			ArrayList<RubricRow> criteria = rubric.getCriteria();
			int rows = criteria.size();
			int cols = criteria.get(0).getGrades().size();
			String[][] grades = new String[rows][cols];
			for (int j = 0; j < grades.length; j++) {
				for (int k = 0; k < grades[j].length; k++) {
					grades[j][k] = criteria.get(j).getGrades().get(k);
				}
			}
			
			for (Submission s : submissions) {
				map.put(s, grades);
			}
			
		}
		
		return map;
	}
}
