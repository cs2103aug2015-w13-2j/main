package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public class TextUIStub implements TextUIInterface {
	private ArrayList<Task> mTasks;
	private FeedbackMessage mFeedback;
	private String mFilterChain = "";

	@Override
	public void display(ArrayList<Task> tasks) {
		mTasks = new ArrayList<Task>(tasks);
	}

	@Override
	public void feedback(FeedbackMessage m) {
		mFeedback = m;
	}

	@Override
	public void setFilter(String s) {
		mFilterChain = s;
	}

	/**
	 * Retrieves the list of Task objects that was sent from the Logic component
	 * to be displayed after the execution of the last command
	 * 
	 * @return List of Task objects to be displayed
	 */
	public ArrayList<Task> getTasksForDisplay() {
		return mTasks;
	}

	/**
	 * Retrieves the last FeedbackMessage object that was sent from the Logic
	 * component to be displayed after the execution of the last command
	 * 
	 * @return FeedbackMessage object to be displayed
	 */
	public FeedbackMessage getFeedbackMessage() {
		return mFeedback;
	}

	/**
	 * Retrieves the string representing the currently active filter chain which
	 * may have been changed by the execution of the last command
	 * 
	 * @return String representation of the currently active filter chain
	 */
	public String getFilterChain() {
		return mFilterChain;
	}
}
