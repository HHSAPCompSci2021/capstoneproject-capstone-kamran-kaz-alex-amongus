package demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import ServerClient.Grader;

import java.util.ArrayList;

import ai.djl.ModelException;
import ai.djl.translate.TranslateException;
import data.Classroom;
import data.Rubric;
import data.RubricRow;
import data.Submission;

/**
 * Runs the server side program and initalizes its datacollection
 * 
 * @author Kamran Hussain
 *
 */
public class Main {

	public static void main(String[] args) throws IOException, TranslateException, ModelException {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory
				.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
		root.setLevel(ch.qos.logback.classic.Level.ERROR);

		// Load essays and rubric from database
		DemoDatabaseModifier.setupDatabase(); // initializes firebase things on machine

		// Put them into the hashmap
		Grader g = new Grader();

		// Goes through each classroom in the database
		// Finds the ungraded classrooms and collects them as a HashMap of submissions
		// and rubrics as 2D arrays
		// Get the grade for the assignment from the model, update the grade of the
		// submisson
		// After going through all of the ungraded assignments in the classroom, updates
		// all the submissions.

		ConcurrentLinkedQueue<Map.Entry<String, Classroom>> queue = DemoDatabaseModifier.getQueue();

		while (true) {
			// refresh the collection of classroomsRef
			if (queue.size() > 0) {
				Map.Entry<String, Classroom> entry = queue.remove();
				Classroom classroom = entry.getValue();
				HashMap<Submission, String[][]> ungraded = getSubmissions(classroom);
				for (Map.Entry<Submission, String[][]> submit : ungraded.entrySet()) {
					String content = submit.getKey().getContent();
					String[] grades = g.getGrade(content, submit.getValue());
					String grade = getGrades(grades);
					submit.getKey().setGrade(grade);
				}
				DemoDatabaseModifier.set(entry.getKey(), classroom);
			}
		}
		// Updates database if hashmap is empty
	}

	/**
	 * Gets the collection of ungraded submissions along with the rubrics that come
	 * along with it.
	 * 
	 * @param classroom The classroom from which the ungraded submissions are being
	 *                  called from
	 * @return A HashMap with a submission object as the key and the rubric as a 2D
	 *         array as the parameter
	 */
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

	/**
	 * Finds the average of the grades calculated by the model
	 * 
	 * @param grades The string array of grades for each rubric category produced by
	 *               the model
	 * @return The final, overall grade that is averaged.
	 */
	private static String getGrades(String[] grades) {
		String[] labels = new String[] { "A", "B", "C", "D", "F" };
		int[] score = new int[] { 5, 4, 3, 2, 1 };
		float sum = 0;
		for (int i = 0; i < grades.length; i++) {
			int match = 0;
			for (int j = 0; j < labels.length; j++) {
				if (labels[j].equals(grades[i])) {
					match = j;
					break;
				}
			}
			sum += score[match];
		}
		sum = (int) (Math.round(sum / grades.length));

		String response = "";

		for (int i = 0; i < labels.length; i++) {
			if (score[i] == (int) sum) {
				response = labels[i];
			}
		}

		return response;
	}
}
