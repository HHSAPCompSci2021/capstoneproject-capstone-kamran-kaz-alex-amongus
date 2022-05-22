package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Models an assignment for a classroom. The Rubric class contains the name of
 * the submission and a list of RubricRow objects that contain grading criteria.
 * The data is similar to a 2D array. It is recommended to initialize the Rubric through the input of a csv file.
 * The first column lists the names of the grading criteria. The top row lists the score that is given to each of the aspects of criteria. 
 * @author Kaz Nakao
 *
 */
public class Rubric {

	private String name;
	private ArrayList<RubricRow> criteria;

	/**
	 * No Args constructor. Will automatically set the name to an empty String.
	 */
	public Rubric() {
		name = "";
		criteria = new ArrayList<RubricRow>();
	}

	/**
	 * 
	 * @param name The name of the rubric
	 */
	public Rubric(String name) {
		this.name = name;
		criteria = new ArrayList<RubricRow>();
	}

	/**
	 * 
	 * @param name     Name of the assignment
	 * @param criteria A 2D arraylist of Strings that each outline an aspect that
	 *                 the assignment should have
	 */
	public Rubric(String name, ArrayList<RubricRow> criteria) {
		this.name = name;
		this.criteria = criteria;
	}

	/**
	 * Adds a new criteria point to the rubric
	 * 
	 * @param newCriteria new criteria
	 */
	public void addCriteria(RubricRow newCriteria) {
		criteria.add(newCriteria);
	}

	/**
	 * Getter for Rubric name
	 * 
	 * @return Rubric Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return Returns an ArrayList of the criteria of the rubric
	 */
	public ArrayList<RubricRow> getCriteria() {
		return criteria;
	}

	/**
	 * Creates a rubric class based on a .rubric file input
	 * 
	 * @pre input String must be in the proper format of a .csv file
	 * @param path the path to the .csv file on the computer
	 * @param name name of rubric
	 * @return Rubric containing
	 */
	public static Rubric makeRubric(String path, String name) {
		Rubric rubric = new Rubric(name);
		
		List<String[]> records = new ArrayList<>();
		try (CSVReader csvReader = new CSVReader(new FileReader(path));) {
		    String[] values = null;
		    while ((values = csvReader.readNext()) != null) {
		        records.add(values);
		        RubricRow row = new RubricRow();
		        for (String s : values) {
		        	row.add(s);
		        }
		        rubric.addCriteria(row);
		    }
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return rubric;
	}

	
	@Override
	public String toString() {
		String output = name + "\n";
		for (RubricRow r : criteria) {
			output += r + "\n";
		}
		return output;
	}

}
