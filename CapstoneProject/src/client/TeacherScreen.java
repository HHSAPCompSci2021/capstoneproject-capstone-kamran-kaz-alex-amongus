package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Classroom;
import data.DatabaseModifier;
import data.Teacher;
/**
 * 
 * Models the Teacher GUI. Will be able to click on an assignment and be able to view the 
 * @author Kaz Nakao, Alex Wang
 *
 */
public class TeacherScreen extends HomeScreen implements ActionListener{
	
	private Teacher teacher;
	
	private JButton createClass;
	
	/**
	 * Creates a screen for teacher screen. Will be able to view all submissions.
	 */
	public TeacherScreen() {
		setup();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource().equals(createClass)) {
			String name = JOptionPane.showInputDialog("Choose a name for the classroom");
			
			if (name != null && !name.equals("")) {
				Classroom classroom = new Classroom(name);
				
				classroom.addTeacher(teacher);
				DatabaseModifier.addClassroom(classroom);				
			}
			else if (name.equals("")) {
				String[] options = { "OK" };
				JOptionPane.showOptionDialog(null, "Classroom name cannot be blank.", 
			    		"GRADEME", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			}
			else {
				System.out.println("Exitted classroom creation.");
			}	
		}
	}
	
	/**
	 * shows list of classrooms
	 */
	private void setup() {
		JPanel top = new JPanel();
		JLabel title = new JLabel("Classrooms");
		top.add(title, BorderLayout.PAGE_START);
		createClass = new JButton("Create a New Classroom");
		createClass.addActionListener(this);
		top.add(createClass, BorderLayout.PAGE_START);
		add(top, BorderLayout.PAGE_START);
	}


	@Override
	public void identify(Entry<String, String> identification) {
		// TODO Auto-generated method stub
		teacher = new Teacher(identification.getKey(), identification.getValue());
	}


	@Override
	public JFrame getScreen(Classroom classroom) {
		// TODO Auto-generated method stub
		
		return new AssignmentViewer(classroom, teacher);
	}
	
}
