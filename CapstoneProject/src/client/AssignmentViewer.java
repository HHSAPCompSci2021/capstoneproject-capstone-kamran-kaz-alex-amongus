package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import data.Rubric;
import data.Teacher;

public class AssignmentViewer extends JFrame implements ActionListener {

	private Classroom classroom;
	private DatabaseModifier m;
	private JList<String> list;
	private Teacher teacher;
	
	public AssignmentViewer(Classroom classroom, DatabaseModifier m, Teacher teacher) {
		super("Assignments");
		
		this.classroom = classroom;
		this.m = m;
		this.teacher = teacher;
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel top = new JPanel();
		JLabel title = new JLabel ("Select an assignment to view");
		JButton createAssignment = new JButton("Create a new Assignment");
		createAssignment.addActionListener(new ButtonListener());
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
		
		JButton go = new JButton("view");
		go.addActionListener(this);
		panel.add(go, BorderLayout.PAGE_END);
		
		add(panel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int index = list.getSelectedIndex();
		System.out.println("selected index: " + index);
		if (index > -1) {
			JFrame window = new TeacherSubmissionViewer(m, classroom, index, teacher);
			window.setBounds(100, 100, 800, 600);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
			window.setVisible(true);
		}
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = JOptionPane.showInputDialog("What is the name of the new assignment");
			Rubric assignment = new Rubric(name);
			
			String key = m.getKey(classroom);
			
			classroom.addAssignment(assignment);
			
			m.set(key, classroom);
			
		}
		
	}
	
}
