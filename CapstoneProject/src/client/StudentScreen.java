package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import data.Classroom;
import data.DatabaseModifier;
import data.Student;
import data.Submission;
/**
 * The screen for the students. Displays the classrooms. 
 * @author Kaz Nakao
 *
 */
public class StudentScreen extends JPanel implements ActionListener{
	
	
	private Student student;
	private HashMap<String, Classroom> classrooms;
	private ArrayList<Classroom> classList;
	private JList<String> list;
	private DatabaseModifier m;
	
	/** 
	 * Sets up the submission screen as a JPanel. Shows the screen for student's submissions and an option for submitting a new submission.
	 */
	public StudentScreen(DatabaseModifier m) {
		super(new BorderLayout());
		
		this.m = m;
		
		setupStudent();
		
		JLabel title = new JLabel("Classrooms");
		add(title, BorderLayout.PAGE_START);
		
		
		String options[] = new String[classrooms.size()];
		classList = new ArrayList<Classroom>();
		Set<String> keys = classrooms.keySet();
		int i = 0;
		for (String key : keys) {
			Classroom classroom = classrooms.get(key);
			String name = classroom.getName();
			options[i] = name;
			classList.add(classroom);
			i++;
		}
		
		list = new JList<String>(options);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane scroll = new JScrollPane(list);
		add(scroll, BorderLayout.CENTER);
		
		JButton submit = new JButton("go to class");
		submit.addActionListener(this);
		add(submit, BorderLayout.PAGE_END);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int index = list.getSelectedIndex();
		if (index > -1) {
			Classroom classroom = classList.get(index);
			String key = getKey(classroom);
		}
	}
	
	
	
	private String getKey(Classroom classroom) {
		Set<String> keys = classrooms.keySet();
		for (String key : keys) {
			if (classrooms.get(key) == classroom) {
				return key;
			}
		}
		return "";
	}
	
	private void setupStudent() {
		
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
		
		
		student = new Student(name, id);
		System.out.println("searching for classrooms");
		
		classrooms = m.getClassrooms();
		
		
	}

}
