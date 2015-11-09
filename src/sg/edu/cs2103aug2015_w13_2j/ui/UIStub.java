package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.scene.Parent;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.exceptions.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;

// @@author A0121410H

/**
 * {@link UIInterface} component for testing purposes. Does <b>not</b> present
 * an actual user interface. Provides methods to retrieve values passed to the
 * public {@link UIInterface} methods for assertion.
 * 
 * @author Zhu Chunqi
 */
public class UIStub implements UIInterface {
    private static final Logger LOGGER = Logger.getLogger(UIStub.class
            .getName());

    private ArrayList<Task> mOrderedTasks;
    private FeedbackMessage mFeedback;
    private FilterChain mFilterChain;

    public UIStub() {
        mFilterChain = new FilterChain();
        mOrderedTasks = new ArrayList<Task>();
    }

    @Override
    public void injectDependency(LogicInterface Logic) {
        // Do nothing
    }

    @Override
    public void display(ArrayList<Task> tasks) {
        // Re-seed the filter chain
        mFilterChain.updateFilters(tasks);

        // Clear the ordered task list
        mOrderedTasks.clear();

        if (mFilterChain.size() > 1) {
            List<Task> filteredTasks = mFilterChain.getTasksForDisplay();
            mOrderedTasks.addAll(filteredTasks);
        } else {
            // Someday
            List<Task> floatingTasks = tasks.stream().sorted()
                    .filter((Task t) -> t.getEnd() == null)
                    .collect(Collectors.toList());
            mOrderedTasks.addAll(floatingTasks);

            // Upcoming
            List<Task> upcomingTasks = tasks.stream().sorted()
                    .filter((Task t) -> t.getEnd() != null)
                    .collect(Collectors.toList());
            mOrderedTasks.addAll(upcomingTasks);
        }
    }

    @Override
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            // NOTE: list is zero indexed whereas display is 1 indexed
            return mOrderedTasks.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public void feedback(FeedbackMessage feedback) {
        mFeedback = feedback;
        LOGGER.log(Level.INFO, mFeedback.getMessage());
    }

    @Override
    public String getFeedbackMessageString() {
        return mFeedback.getMessage();
    }

    // @@author A0133387B

    @Override
    public void pushFilter(Filter filter) {
        mFilterChain.pushFilter(filter);
    }

    @Override
    public Filter popFilter() {
        return mFilterChain.popFilter();
    }

    /**
     * Does nothing.
     * 
     * @return Always {@code true}.
     */
    @Override
    public boolean showChangeDataFilePathDialog() {
        return true;
    }

    /**
     * Does nothing.
     * 
     * @return Always {@code true}.
     */
    @Override
    public boolean showHelpPage() {
        return true;
    }

    /**
     * Does nothing.
     */
    @Override
    public void focusCommandBar() {
        // Do nothing
    }

    /**
     * Does nothing.
     * 
     * @return Always {@code null}.
     */
    @Override
    public Parent getUI() {
        return null;
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

    /**
     * Creates a new instance of {@link FilterChain} with no {@link Task}
     * objects.
     */
    public void refreshFilter() {
        mFilterChain = new FilterChain();
    }
}
