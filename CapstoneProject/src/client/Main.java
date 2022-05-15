package client;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.DatabaseModifier;

public class Main {
	
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.ERROR);
	    
	    JPanel screen = null;
	    
	    String[] options = { "Student", "Teacher" };
	    
	    int user = JOptionPane.showOptionDialog(null, "Are you a STUDENT or TEACHER", 
	    		"GRADEME", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		
	    System.out.println("syncing with server...");
	    DatabaseModifier m = new DatabaseModifier();
	    System.out.println("synced with server");
	    
	    JFrame w = new JFrame("GRADEME Client");
	    w.setBounds(100, 100, 800, 600);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    if (user == 0) {
	    	screen = new StudentScreen(m);
	    	//screen = new SubmissionScreen("test");
	    }
	    else if (user == 1) {
	    	screen = new TeacherScreen(m);
	    }
	    w.add(screen);
		w.setResizable(true);
		w.setVisible(true);
	}

}
