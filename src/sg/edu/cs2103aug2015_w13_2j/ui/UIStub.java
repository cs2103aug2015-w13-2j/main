package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.scene.Parent;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;

// @@author A0121410H

/**
 * {@link UIInterface} component for testing purposes. Does <b>not</b> present
 * an actual user interface. Provides methods to retrieve values passed to the
 * public {@link UIInterface} methods for assertion.
 * 
 * @author Zhu Chunqi
 *
 */
public class UIStub implements UIInterface {
    private static final Logger LOGGER = Logger
            .getLogger(UIStub.class.getName());

    private ArrayList<Task> mOrderedTasks;
    private FeedbackMessage mFeedback;
    private String mDisplayString;
    private FilterChain mFilterChain;
    private StorageInterface sStorage;
    public UIStub(){
    	mFilterChain = new FilterChain();
    	mOrderedTasks = new ArrayList<Task>();
    }
    @Override
    public void injectDependency(LogicInterface Logic) {
        // Do nothing
    }
    
    /**
     * Retrieves the string that was sent for display via
     * {@link UIInterface#display(String)}.
     * 
     * @return String that was sent for display.
     */
    public String getDisplayString() {
        return mDisplayString;
    }

    /**
     * Retrieves the {@link FeedbackMessage} object that was sent for display
     * via {@link UIInterface#feedback(FeedbackMessage)}.
     * 
     * @return {@link FeedbackMessage} that was sent for display.
     */
    public FeedbackMessage getFeedbackMessage() {
        return mFeedback;
    }

    /**
     * Does nothing.
     */
    @Override
    public boolean showChangeDataFilePathDialog() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * Does nothing.
     * 
     * @return {@code null}.
     */
    @Override
    public Parent getUI() {
        // TODO Auto-generated method stub
        return null;
    }
    
  //@@author A0133387B
    @Override
    public void pushFilter(Filter filter) {
        mFilterChain.pushFilter(filter);
    }

    @Override
    public Filter popFilter() {
        return mFilterChain.popFilter();
    }

    @Override
    public String getFeedBackMessage() {
        // TODO Auto-generated method stub
        return mFeedback.getMessage();
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
    
    public void injectDependency(StorageInterface storageTest) {
    	sStorage = storageTest;
    }

    public void display(ArrayList<Task> tasks) {
    	LOGGER.log(Level.INFO, "FILTER " + mFilterChain.getFilterChain());
        // Re-seed the filter chain
        mFilterChain.updateFilters(tasks);

        // Clear the ordered task list
        mOrderedTasks = new ArrayList<Task>();
        Collections.sort(tasks);
        
        if (mFilterChain.size() > 1) {
            List<Task> filteredTasks = mFilterChain.getTasksForDisplay();
            mOrderedTasks.addAll(filteredTasks);
            LOGGER.log(Level.INFO, filteredTasks.size() + " tasks sent for display in UISTub FILTERED");

        } else {
            // Someday
            List<Task> floatingTasks = tasks.stream()
                    .filter((Task t) -> t.getEnd() == null)
                    .collect(Collectors.toList());
            LOGGER.log(Level.INFO, "There are " + floatingTasks.size() + " floating tasks");
            mOrderedTasks.addAll(floatingTasks);
            // Upcoming
            List<Task> upcomingTasks = tasks.stream()
                    .filter((Task t) -> t.getEnd() != null)
                    .collect(Collectors.toList());
            mOrderedTasks.addAll(upcomingTasks);
            LOGGER.log(Level.INFO, "There are " + upcomingTasks.size() + " upcoming tasks");
            LOGGER.log(Level.INFO, "There are in total " + mOrderedTasks.size() + " to display");
      
        }
    }

    @Override
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            return mOrderedTasks.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    
    /**
     * Retrieves the list of {@link Task} objects that was sent for display via
     * {@link UIInterface#display(ArrayList)}.
     * 
     * @return List of {@link Task} objects that was sent for display.
     */
    public ArrayList<Task> getTasksForDisplay() {    	
    	return mOrderedTasks;
    }
    
    public void refreshTaskList() {    	
    	mOrderedTasks = sStorage.readTasksFromDataFile();
    }
    
    public void refreshFilter() {    	
    	mFilterChain = new FilterChain();
    }
}
