package client;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import data.Student;
import data.Submission;
/**
 * The screen for the students 
 * @author Kaz Nakao
 *
 */
public class StudentScreen extends JPanel {
	
	
	private DefaultListModel<String> model;

	private DatabaseReference classRef;
	
	private Student student;
	ArrayList<Submission> submissions;
	
	/** 
	 * Sets up the submission screen as a JPanel
	 */
	public StudentScreen() {
		super(new BorderLayout());
		
		String name = JOptionPane.showInputDialog("What is your name?");
		
		String id = null;
		
		while (id == null) {
			id = JOptionPane.showInputDialog("What is your id number?");
			char[] chars = id.toCharArray();
			for (char c : chars) {
				if (!Character.isDigit(c)) {
					id = null;
					String[] options = { "OK" };
					JOptionPane.showOptionDialog(null, "ID number can only contain numbers", 
				    		"GRADEME", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}
			}
		}
		
		final String idnum = id; //use this string for when you intialize the student if needed
		
		//submissions = student.getSubmissions();
		submissions = new ArrayList<Submission>();
		submissions.add(new Submission("hamlet", "Shakespear did a thing"));
		submissions.add(new Submission("romeo and juliet", "love and stuff ig"));
		submissions.add(new Submission("test submission", "testing testing testing"));
		
		
		JLabel title = new JLabel("Submissions");
		add(title, BorderLayout.PAGE_START);
		
		JScrollPane scroll = new JScrollPane();
		String[] options = null;
		if (submissions != null) {
			options = new String[submissions.size()];
			for (int i = 0; i < submissions.size(); i++) {
				options[i] = submissions.get(i).getName();
			}
		}
		
		JList<String> list = new JList<String>(options);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		scroll.add(list);
		add(list, BorderLayout.CENTER);
		
	}	
}
