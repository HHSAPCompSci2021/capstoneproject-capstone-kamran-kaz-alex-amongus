package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import data.Classroom;
import data.DatabaseModifier;
import data.Rubric;
import data.RubricRow;
import data.Teacher;
/**
 * Allows a teacher user to view all of the assignments in a classroom or create a new assignment for the classroom.
 * @author Kaz Nakao
 *
 */
public class AssignmentViewer extends JFrame implements ActionListener {

	/**
	 * File separator character used by the OS
	 */
	public final static String fileSeparator = System.getProperty("file.separator");
	/**
	 * User directory
	 */
	public final static String userDir = System.getProperty("user.dir");
	/**
	 * The line separator used by the OS
	 */
	public final static String lineSeparator = System.getProperty("line.separator");
	
	private Classroom classroom;
	private JList<String> list;
	private Teacher teacher;
	
	private JButton go;
	private JButton back;
	private JButton createAssignment;
	/**
	 * 
	 * @param classroom Classroom to view
	 * @param teacher Teacher user that is accessing the classroom
	 */
	public AssignmentViewer(Classroom classroom, Teacher teacher) {
		super("Assignments");
		
		this.classroom = classroom;
		this.teacher = teacher;
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel top = new JPanel();
		JLabel title = new JLabel ("Select an assignment to view");
		createAssignment = new JButton("Create a new Assignment");
		createAssignment.addActionListener(this);
		top.add(title);
		top.add(createAssignment);
		panel.add(top, BorderLayout.PAGE_START);
		
		ArrayList<Rubric> assignments = classroom.getAssignments();
		String[] options = new String[assignments.size()];
		
		for (int i = 0; i < options.length; i++) {
			options[i] = assignments.get(i).getName();
		}
		
		list = new JList<String>(options);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane scroll = new JScrollPane(list);
		panel.add(scroll, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();		
		
		go = new JButton("view");
		go.addActionListener(this);
		back = new JButton("back");
		back.addActionListener(this);
		
		bottom.add(go);
		bottom.add(back);
		
		panel.add(bottom, BorderLayout.PAGE_END);
		add(panel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(go)) {
			int index = list.getSelectedIndex();
			System.out.println("selected index: " + index);
			if (index > -1) {
				JFrame window = new TeacherSubmissionViewer(classroom, index, teacher);
				window.setBounds(100, 100, 800, 600);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ImageIcon logo = new ImageIcon("resources/GRADEME-logo.png");
		    	window.setIconImage(logo.getImage());
				window.setResizable(true);
				window.setVisible(true);
			}
		}
		else if (e.getSource().equals(createAssignment)) {
			String name = JOptionPane.showInputDialog("What is the name of the new assignment");
			
			JFileChooser choose = new JFileChooser(userDir);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv", "text");
			choose.setFileFilter(filter);
			int returnVal = choose.showOpenDialog(null);
			
			String input = null;
			Rubric newAssignment = null;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				newAssignment = Rubric.makeRubric(choose.getSelectedFile().getPath(), name);
				classroom.addAssignment(newAssignment);
				
				String key = DatabaseModifier.getKey(classroom);
				DatabaseModifier.set(key, classroom);
			}	
		}
		else if (e.getSource().equals(back)) {
			this.setVisible(false);
		}
	}
}
