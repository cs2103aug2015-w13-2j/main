package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Parent;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.storage.Storage;

// @@author NOAUTHOR

public class TextUIStub implements UIInterface {
    private static final Logger LOGGER = Logger
            .getLogger(TextUIStub.class.getName());

    private ArrayList<Task> mTasks;
    private FeedbackMessage mFeedback;
    private String mDisplayString;
    private String mFilterChain;
    private Storage sStorage = Storage.getInstance();
    @Override
    public void injectDependency(LogicInterface logic) {
        // Do nothing
    }

    @Override
    public void display(ArrayList<Task> tasks) {
        LOGGER.log(Level.INFO, tasks.size() + " tasks sent for display");
        for (Task task : tasks) {
            LOGGER.log(Level.FINEST, task.toString());
        }
        mTasks = tasks;
    }

    @Override
    public void display(String s) {
        LOGGER.log(Level.INFO, s);
        mDisplayString = s;
    }

    @Override
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            return mTasks.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public void feedback(FeedbackMessage f) {
        LOGGER.log(Level.INFO, f.getMessage());
        mFeedback = f;
    }

    /**
     * Retrieves the list of Task objects that was sent for display via the
     * {@link UIInterface#display(ArrayList)}
     * 
     * @return List of Task objects that was sent for display
     */
    public ArrayList<Task> getTasksForDisplay() {
    	mTasks = sStorage.readTasksFromDataFile(); 
    	return mTasks;
    }

    /**
     * Retrieves the string that was sent for display using the
     * {@link UIInterface#display(String)} method
     * 
     * @return String that was sent for display
     */
    public String getDisplayString() {
        return mDisplayString;
    }

    /**
     * Retrieves the FeedbackMessage object that was sent for display via the
     * {@link UIInterface#feedback(FeedbackMessage)}
     * 
     * @return FeedbackMessage that was sent for display
     */
    public FeedbackMessage getFeedbackMessage() {
        return mFeedback;
    }

    /**
     * Retrieves the string that was sent for display using the
     * {@link UIInterface#setFilter(String)}
     * 
     * @return String representing the currently active filter chain that was
     *         sent for display
     */
    public String getFilterChain() {
        return mFilterChain;
    }

    @Override
    public void pushFilter(Filter filter) {
        // TODO Auto-generated method stub

    }

    @Override
    public Filter popFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFeedBackMessage() {
        // TODO Auto-generated method stub
        return mFeedback.getMessage();
    }

    @Override
    public boolean showChangeDataFilePathDialog() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Parent getUI() {
        // TODO Auto-generated method stub
        return null;
    }
}
