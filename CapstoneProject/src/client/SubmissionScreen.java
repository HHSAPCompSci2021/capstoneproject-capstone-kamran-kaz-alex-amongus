package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ServerClient.DatabaseModifier;
import data.Submission;


public class SubmissionScreen extends JFrame implements ActionListener{
	
	public final static String fileSeparator = System.getProperty("file.separator");
	public final static String userDir = System.getProperty("user.dir");
	public final static String lineSeparator = System.getProperty("line.separator");

	private String input;
	private DatabaseModifier m;
	private JTextField title;
	
	/**
	 * Will create a system to upload a new submission for the user
	 * @param name name of the submission
	 */
	public SubmissionScreen() {
		super("Submission Screen");
		
		JFileChooser chooser = new JFileChooser(userDir);
		int returnVal = chooser.showOpenDialog(null);
		
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	
	    	try  {
	    		input = readFile(chooser.getSelectedFile().getPath());
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    }
		
	    JPanel screen = new JPanel(new BorderLayout());
	    JLabel instructions = new JLabel("insert the title of your submisison below");
	    screen.add(instructions, BorderLayout.PAGE_START);
	    title = new JTextField(20);
	    screen.add(title, BorderLayout.CENTER);
	    JButton post = new JButton("Upload");
	    post.addActionListener(this);
	    screen.add(post, BorderLayout.PAGE_END);
	    
	    this.add(screen);
	    
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String name = title.getText();
		Submission s = new Submission(name, input);
		DatabaseModifier m = new DatabaseModifier();
		
		m.submitToDatabase(s);
		
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
