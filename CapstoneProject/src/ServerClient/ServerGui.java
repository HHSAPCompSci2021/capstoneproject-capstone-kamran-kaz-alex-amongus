package ServerClient;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 * Screen to visualize Server task progress
 * @author Alex wang
 *
 */
public class ServerGui  {
	private JProgressBar taskProgressBar;
	private JLabel progressLabel, lossLabel;
	private int progressVal, lossVal;
	
	public static void main(String[] args) {
		ServerGui serverGui = new ServerGui();
		
		
		// testing code
		Scanner in = new Scanner(System.in);
		
		while (true) {
			System.out.println("Enter ProgressVal and LossVal");
			serverGui.setProgressVal(in.nextInt());
			serverGui.setLossVal(in.nextInt());
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
		
		progressVal = lossVal = 0; // dummy data
		progressLabel = new JLabel(String.format("Task Progress: %d", progressVal));
		
		taskProgressBar = new JProgressBar(0, 100); // modify max val as needed
		taskProgressBar.setValue(progressVal);
		taskProgressBar.setStringPainted(true);
		
		// indeterminate mode for no progress
		updateIndeterminateMode();
		
		progressRow.add(Box.createRigidArea(new Dimension(20, 0)));
		progressRow.add(progressLabel);
		progressRow.add(Box.createRigidArea(new Dimension(10, 0)));
		progressRow.add(taskProgressBar);
		progressRow.add(Box.createRigidArea(new Dimension(20, 0)));
		
		panel.add(Box.createRigidArea(new Dimension(0, 50)));
		panel.add(progressRow);
//		panel.add(Box.createVerticalGlue()); // space goes to middle of vertical layout
		panel.add(Box.createRigidArea(new Dimension(0, 50)));
		
		// loss value label
		lossLabel = new JLabel(String.format("Loss: %d", lossVal));
		panel.add(lossLabel);
//		panel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		XYDataset ds = getDataset();
        JFreeChart chart = ChartFactory.createXYLineChart("Loss vs. Time",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);

        ChartPanel cp = new ChartPanel(chart);

        panel.add(cp);

		
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	/**
	 * Syncs GUI elements with values
	 * Should be called on value update
	 */
	public void syncValues() {
		taskProgressBar.setValue(progressVal);
		progressLabel.setText(String.format("Task Progress: %d", progressVal));
		lossLabel.setText(String.format("Loss: %d", lossVal));
		updateIndeterminateMode();
		
	}
	
	/**
	 * Sets the progress value
	 * @param progressVal value to be set
	 */
	public void setProgressVal(int progressVal) {
		this.progressVal = progressVal;
		syncValues();
	}
	
	/**
	 * Sets the loss
	 * @param loss value to be set
	 */
	public void setLossVal(int loss) {
		lossVal = loss;
		syncValues();
	}
	
	private XYDataset getDataset() {
		DefaultXYDataset ds = new DefaultXYDataset();

		// current hard coded data
        double[][] data = { {0.1, 0.2, 0.3}, {1, 2, 5} };

        ds.addSeries("loss", data);

        return ds;
	}
	
	/**
	 * Updates the progress bar state according to progress value
	 * Called by other methods within ServerGui
	 */
	private void updateIndeterminateMode() {
		taskProgressBar.setIndeterminate(progressVal == 0);
	}
	
}
