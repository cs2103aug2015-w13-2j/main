package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public class TextUIStub implements TextUIInterface {
	private ArrayList<Task> mTasks = new ArrayList<Task>();
	private FeedbackMessage mFeedback = null;

	@Override
	public void display(ArrayList<Task> tasks) {
		mTasks = new ArrayList<Task>(tasks);
	}

	@Override
	public void feedback(FeedbackMessage m) {
		mFeedback = m;
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
}
