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
import data.Teacher;
/**
 * 
 * Models the Teacher GUI. Will be able to click on an assignment and be able to view the 
 * @author Kaz Nakao
 *
 */
public class TeacherScreen extends JPanel implements ActionListener{
	
	private HashMap<String, Classroom> classrooms;
	private JList<String> list;
	private ArrayList<Classroom> classList;
	private Teacher teacher;
	
	
	/**
	 * Creates a screen for teacher screen. Will be able to view all submissions.
	 */
	public TeacherScreen() {
		
		super(new BorderLayout());
		
		setupTeacher();
		
		updateGUI();
		
	}
	
	private void setupTeacher() {
		
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
		
		
		teacher = new Teacher(name, id);
		
		classrooms = DatabaseModifier.getClassrooms();
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int index = list.getSelectedIndex();
		System.out.println("selected index: " + index);
		if (index > -1) {
			Classroom classroom = classList.get(index);
			String key = DatabaseModifier.getKey(classroom);
			
			JFrame window = new AssignmentViewer(classroom, teacher);
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);
		}
	}
	
	private class CreateClassroomListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = JOptionPane.showInputDialog("Choose a name for the classroom");
			Classroom classroom = new Classroom(name);
			ArrayList<Teacher> teachers = classroom.getTeachers();
			
			classroom.addTeacher(teacher);
			DatabaseModifier.addClassroom(classroom);
		}
		
	}
	
	private void updateGUI() {
		JPanel panel = new JPanel();
		JLabel title = new JLabel("Select a Classroom");
		JButton createClass = new JButton("Create a new classroom");
		createClass.addActionListener(new CreateClassroomListener());
		panel.add(title);
		panel.add(createClass);
		add(panel, BorderLayout.PAGE_START);
		
		String[] options = new String[classrooms.size()];
		classList = new ArrayList<Classroom>();
		Set<String> keys = classrooms.keySet();
		int i = 0;
		for (String key : keys) {
			Classroom classroom = classrooms.get(key);
			classList.add(classroom);
			options[i] = classroom.getName();
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
	
}
