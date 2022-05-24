package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
/**
 * A Screen that models the startup screen of a student or a teacher. Shows a list of classrooms and will allow the user to access the classroom.
 * @author Kaz Nakao
 *
 */
public abstract class HomeScreen extends JPanel implements ActionListener{
	
	private JList<String> list;
	private JButton refresh, submit;
	private ArrayList<Classroom> classList;
	
	/**
	 * Launches the GUI of the home sreen. Also asks for the identification of the user. 
	 */
	public HomeScreen() {
		super(new BorderLayout());
		Map.Entry<String, String> identification = enterName(); //enter username and id
		identify(identification); //match the user inside the database
		constructView();
	}

	/**
	 * Prompts the user to enter a name and ID number. 
	 * @return A Map Entry in the format of key as name followed by ID number as the value. 
	 */
	public Map.Entry<String, String> enterName() {
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
		
		// prompt ID number unless user chooses exit
		do {
			id = JOptionPane.showInputDialog("What is your id number?");
			if (id == null) {
				idResponse = JOptionPane.showOptionDialog(null, "Please enter an ID to proceed.", "GRADEME",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, idOptions, idOptions[0]);	
			} else {
				boolean isValid = true;
				
				char[] chars = id.toCharArray();
				for (char c : chars) {
					if (!Character.isDigit(c)) {
						isValid = false;
						id = null;
					}
				}	
				
				if (!isValid) {
					String[] options = { "OK" };
					JOptionPane.showOptionDialog(null, "ID number can only contain numbers", 
							"GRADEME", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				}
				
			}
		} while (id == null && idResponse == 0);
		
		// user chose to exit
		if (idResponse == 1) {
			System.exit(0);
		}
		
		Map.Entry<String, String> identification = new AbstractMap.SimpleEntry<String, String>(name, id);
		return identification;
	}
	
	/**
	 * Show the list of classrooms and all the necessary GUI elements
	 */
	public void constructView() {
		JLabel title = new JLabel("Classrooms");
		add(title, BorderLayout.PAGE_START);
		
		HashMap<String, Classroom> classrooms = DatabaseModifier.getClassrooms();
		
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
		
		JPanel bottom = new JPanel();
		
		submit = new JButton("go to class");
		submit.addActionListener(this);
		bottom.add(submit);
		
		refresh = new JButton("refresh");
		refresh.addActionListener(this);
		bottom.add(refresh);
		
		add(bottom, BorderLayout.PAGE_END);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(refresh)) {
			// remove old components
			Component[] componentList = getComponents();
			
			for(Component c : componentList){
			    remove(c);
			}
			
			revalidate();
			repaint();
			
			constructView();
		}
		else if (e.getSource().equals(submit)){
			int index = list.getSelectedIndex();
			System.out.println("selected index: " + index);
			if (index > -1) {
				Classroom selected = classList.get(index);
				//System.out.println("key: " + key);
				JFrame window = getScreen(selected);
				window.setBounds(100, 100, 800, 600);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				ImageIcon logo = new ImageIcon("resources/GRADEME-logo.png");
				window.setIconImage(logo.getImage());
				window.setResizable(true);
				window.setVisible(true);
			}		
		}
	}
	
	/**
	 * Matches a user with a user in the classroom. 
	 * @param identification A map entry with a key as the name of the user and the value being the ID number
	 */
	public abstract void identify(Map.Entry<String, String> identification);
	
	/**
	 * Creates a new JFrame that lets the user view the contents inside of the classroom in some way.
	 * @param classroom The classroom that has been selected to view.
	 * @return Based on the user information that has been found through the identify method, a new JFrame that shows the proper classroom viewer JFrame.
	 * 
	 */
	public abstract JFrame getScreen(Classroom classroom);
	
	
}
