package client;

import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args) {
		ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
	    root.setLevel(ch.qos.logback.classic.Level.ERROR);
	    
	    String[] options = { "Student", "Teacher" };
	    
	    int demo = JOptionPane.showOptionDialog(null, "Are you a STUDENT or TEACHER", 
	    		"Networking Demo", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		
	}

}
