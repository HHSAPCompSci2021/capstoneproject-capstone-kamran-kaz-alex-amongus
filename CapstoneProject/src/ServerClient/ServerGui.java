package ServerClient;

import java.awt.Color;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Screen to visualize Server task progress
 * @author Alex wang
 *
 */
public class ServerGui  {
	private JProgressBar taskProgressBar;
	private int progressVal;
	
	public static void main(String[] args) {
		ServerGui serverGui = new ServerGui();
		
		
		// testing code
		Scanner in = new Scanner(System.in);
		
		while (true) {
			System.out.println("Enter ProgressVal: ");
			serverGui.setProgressVal(in.nextInt());
		}
	}
	
	public ServerGui() {
		JFrame frame = new JFrame("GradeMe Server");
	    frame.setBounds(100, 100, 1200, 650);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBackground(new Color(211,211,211));

		// current task title
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		// current task progress percentage & progress bar
		JPanel progressRow = new JPanel();
		progressRow.setLayout(new BoxLayout(progressRow, BoxLayout.LINE_AXIS));
		
		progressVal = 0; // dummy data
		JLabel progressLabel = new JLabel(String.format("Task Progress: %d", progressVal));
		
		taskProgressBar = new JProgressBar(0, 100); // modify max val as needed
		taskProgressBar.setValue(progressVal);
		taskProgressBar.setStringPainted(true);
		
		progressRow.add(progressLabel);
		progressRow.add(taskProgressBar);
		
		panel.add(progressRow);
		
		// loss value label
		
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	public void setProgressVal(int progressVal) {
		this.progressVal = progressVal;
	}
}
