package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

public class AssignmentViewer extends JFrame implements ActionListener {

	
	public final static String fileSeparator = System.getProperty("file.separator");
	public final static String userDir = System.getProperty("user.dir");
	public final static String lineSeparator = System.getProperty("line.separator");
	
	
	private Classroom classroom;
	private JList<String> list;
	private Teacher teacher;
	
	private JButton go;
	private JButton createAssignment;
	
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
		
		go = new JButton("view");
		go.addActionListener(this);
		panel.add(go, BorderLayout.PAGE_END);
		
		add(panel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(go)) {
			int index = list.getSelectedIndex();
			System.out.println("selected index: " + index);
			if (index > -1) {
				JFrame window = new TeacherSubmissionViewer(classroom, index, teacher);
				window.setBounds(100, 100, 800, 600);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.setResizable(true);
				window.setVisible(true);
			}
		}
		else if (e.getSource().equals(createAssignment)) {
			String name = JOptionPane.showInputDialog("What is the name of the new assignment");
			
			JFileChooser choose = new JFileChooser(userDir);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Rubric Files", "rubric", "text");
			choose.setFileFilter(filter);
			int returnVal = choose.showOpenDialog(null);
			
			String input = null;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					input = readFile(choose.getSelectedFile().getPath());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
			Rubric newAssignment = Rubric.makeRubric(input, name);
			classroom.addAssignment(newAssignment);
			
			String key = DatabaseModifier.getKey(classroom);
			
			DatabaseModifier.set(key, classroom);
		}
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
