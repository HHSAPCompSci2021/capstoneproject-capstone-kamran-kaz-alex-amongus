package ServerClient;

import java.util.ArrayList;

import data.Submission;

public class Queue {
	
	private ArrayList<Submission> queue;
	private int currentIndex;
	
	/**
	 * Constructs a new Queue and initializes the currently processing index to the first item in the queue
	 */
	public Queue() {
		queue = new ArrayList<>();
		currentIndex = 0;
	}
	
	/**
	 * Adds a new submission to the queue
	 * @param sub Submission object that should be added
	 */
	public void add(Submission sub) {
		queue.add(sub);
	}
	
	/**
	 * Removes a submission from the queue based on its index
	 * The index cannot be removed if it is currently being processed.
	 * @param index Index of the submission to be removed
	 */
	public void remove(int index) {
		if(index == currentIndex) {
			throw new IllegalArgumentException("Cannot Remove Currently Processed Submission");
		}
		queue.remove(index);
	}
	
	/**
	 * Removes a submission from the queue based on its title
	 * @param title Title of the essay submission to be removed
	 */
	public void remove(String title) {
		Submission res = new Submission();
		for(Submission e : queue) {
			if(e.getName().equals(title)) {
				res = e;
			}
		}
		queue.remove(res);
	}
	
	/**
	 * Returns a string detailing the status of the queue
	 * @return A String mentioning the current index being processed relative to the size of the queue
	 */
	public String getStatus() {
		return "Currently processing " + currentIndex + " of " + queue.size();
	}
	
	/**
	 * @return The index of the submission currently being processed
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	/**
	 * @return The size of the queue
	 */
	public int getQueueSize() {
		return queue.size();
	}
	
	/**
	 * Moves to the next item in the queue
	 * @return The submission currently being processed
	 */
	public Submission forwardCurrentIndex() { 
		currentIndex++;
		return queue.get(currentIndex);
	}

}
