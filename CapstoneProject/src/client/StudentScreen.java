package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import data.Classroom;
import data.DatabaseModifier;
import data.Student;
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
	
	/** 
	 * Sets up the submission screen as a JPanel. Shows the screen for student's submissions and an option for submitting a new submission.
	 */
	public StudentScreen() {
		super(new BorderLayout());
		
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
		int index = list.getSelectedIndex();
		System.out.println("selected index: " + index);
		if (index > -1) {
			Classroom classroom = classList.get(index);
			String key = getKey(classroom);
			//System.out.println("key: " + key);
			JFrame window = new StudentClassroomScreen(classroom, key, student);
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			ImageIcon logo = new ImageIcon("resources/GRADEME-logo.png");
	    	window.setIconImage(logo.getImage());
			window.setResizable(true);
			window.setVisible(true);
		}
	}
	
	
	/**
	 * 
	 * @param classroom classroom to get key from
	 * @return key of the classroom
	 */
	private String getKey(Classroom classroom) {
		Set<String> keys = classrooms.keySet();
		for (String key : keys) {
			if (classrooms.get(key) == classroom) {
				return key;
			}
		}
		return "";
	}
	
	/**
	 * Asks for student name and id for login. Will then use that information to search for a similar student in the database.
	 */
	private void setupStudent() {
		String name;
		int response = 0;
		String[] nameOptions = {"Re-enter name", "Exit program"};
		
		// prompt name input - no input => response = 0
		do {
			name = JOptionPane.showInputDialog("What is your name?");
			if (name == null) {
				response = JOptionPane.showOptionDialog(null, "Please enter a name to proceed.", "GRADEME",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, nameOptions, nameOptions[0]);				
			}
			System.out.println("------------------------- " + response);
		} while (name == null && response == 0);
		
		// user chose to exit
		if (response == 1) {
			System.exit(0);
		}
		
		String id = null;
		int idResponse = 0;
		String[] idOptions = {"Re-enter ID", "Exit program"};
		
		// promopt ID number unless user chooses exit
		do {
			id = JOptionPane.showInputDialog("What is your id number?");
			if (id == null) {
				idResponse = JOptionPane.showOptionDialog(null, "Please enter an ID to proceed.", "GRADEME",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, idOptions, idOptions[0]);	
			} else {
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
		} while (id == null && idResponse == 0);
		
		// user chose to exit
		if (idResponse == 1) {
			System.exit(0);
		}
		
		student = new Student(name, id);
		System.out.println("searching for classrooms");
		
		classrooms = DatabaseModifier.getClassrooms();
		
		System.out.println("found classrooms");
		
	}

}
