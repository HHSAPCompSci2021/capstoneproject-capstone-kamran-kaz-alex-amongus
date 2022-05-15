package ServerClient;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerGui  {
	public static void main(String[] args) {
		JFrame frame = new JFrame("GradeMe Server");
	    frame.setBounds(100, 100, 1200, 650);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setBackground(new Color(211,211,211));
		frame.setVisible(true);
		JLabel label = new JLabel("Database Connected");
		label.setBounds(50,100,20,70);
		frame.add(label);
	}
}
