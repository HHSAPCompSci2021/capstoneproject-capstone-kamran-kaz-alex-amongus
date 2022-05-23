package client;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import data.DatabaseModifier;
/**
 * Client App main method
 * @author Kaz Nakao, Alex Wang
 *
 */
public class ClientApp {
	
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.ERROR);
	    
	    JPanel screen = null;
	    
	    String[] modes = {"Light", "Dark"};
	    
	    int modeChoice = JOptionPane.showOptionDialog(null, "Select Viewing Mode", "GRADEME", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, modes, modes[0]);
	    
	    if (modeChoice == 1) {	    	
	    	FlatDraculaIJTheme.installLafInfo();
	    	try {
	    		UIManager.setLookAndFeel(new FlatDraculaIJTheme());
	    	} catch (UnsupportedLookAndFeelException e) {
	    		e.printStackTrace();
	    	}
	    }
	    else if (modeChoice == -1) {
	    	JOptionPane.showMessageDialog(screen, "No mode is selected. Default mode will be light mode.");

	    }	    
	    
	    String[] options = { "Student", "Teacher" };
	    
	    int user = JOptionPane.showOptionDialog(null, "Are you a STUDENT or TEACHER", 
	    		"GRADEME", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    
	    // if user selected valid response
	    if (user != -1) {
	    	System.out.println("syncing with server...");
	    	DatabaseModifier.setupDatabase();
	    	System.out.println("synced with server");
	    	
	    	//DatabaseModifier.addClassroom(new Classroom("dummy classroom"));
	    	
	    	JFrame w = new JFrame("GRADEME Client");
	    	w.setBounds(100, 100, 800, 600);
	    	w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	
	    	ImageIcon logo = new ImageIcon("resources/GRADEME-logo.png");
	    	w.setIconImage(logo.getImage());
	    	
	    	if (user == 0) {
	    		screen = new StudentScreen();
	    		//screen = new SubmissionScreen("test");
	    	}
	    	else if (user == 1) {
	    		screen = new TeacherScreen();
	    	}
	    	
	    	
	    	
	    	w.add(screen);
	    	w.setResizable(true);
	    	w.setVisible(true);	    	
	    } else {
	    	JOptionPane.showMessageDialog(screen, "You must select a response in order to proceed. Exiting program...");
	    }
	    
	}
	
	

}
