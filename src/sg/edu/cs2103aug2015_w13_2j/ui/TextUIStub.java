package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;

public class TextUIStub implements TextUIInterface {
    private static final Logger LOGGER = Logger.getLogger(TextUIStub.class
            .getName());

    private ArrayList<Task> mTasks;
    private FeedbackMessage mFeedback;
    private String mDisplayString;
    private String mFilterChain;
    
    @Override
    public void injectDependency(LogicInterface logic) {
        // Do nothing
    }

    @Override
    public void display(ArrayList<Task> tasks) {
        LOGGER.log(Level.INFO, tasks.size() + " tasks sent for display");
        for(Task task : tasks) {
            LOGGER.log(Level.FINEST, task.toString());
        }
        mTasks = new ArrayList<Task>(tasks);
    }

    @Override
    public void display(String s) {
        LOGGER.log(Level.INFO, s);
        mDisplayString = s;
    }

    @Override
    public void feedback(FeedbackMessage f) {
        LOGGER.log(Level.INFO, f.getMessage());
        mFeedback = f;
    }

    @Override
    public void setFilter(String s) {
        LOGGER.log(Level.INFO, s);
        mFilterChain = s;
    }

    /**
     * Retrieves the list of Task objects that was sent for display via the
     * {@link TextUIInterface#display(ArrayList)}
     * 
     * @return List of Task objects that was sent for display
     */
    public ArrayList<Task> getTasksForDisplay() {
        return mTasks;
    }

    /**
     * Retrieves the string that was sent for display using the
     * {@link TextUIInterface#display(String)} method
     * 
     * @return String that was sent for display
     */
    public String getDisplayString() {
        return mDisplayString;
    }

    /**
     * Retrieves the FeedbackMessage object that was sent for display via the
     * {@link TextUIInterface#feedback(FeedbackMessage)}
     * 
     * @return FeedbackMessage that was sent for display
     */
    public FeedbackMessage getFeedbackMessage() {
        return mFeedback;
    }

    /**
     * Retrieves the string that was sent for display using the
     * {@link TextUIInterface#setFilter(String)}
     * 
     * @return String representing the currently active filter chain that was
     *         sent for display
     */
    public String getFilterChain() {
        return mFilterChain;
    }
}
