package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;

//@@author A0121410H

public interface UIInterface {
    /**
     * Injects the dependency on an object implementing the
     * {@link LogicInterface} into this {@link UIInterface} component
     * 
     * @param logic
     *            An object implementing the {@link LogicInterface}. A handle to
     *            this object will be retained so that
     *            {@link LogicInterface#executeCommand(String)} can be called
     */
    public void injectDependency(LogicInterface logic);

    /**
     * Displays the provided list of Task objects
     * 
     * @param tasks
     *            List of Task objects to be displayed
     */
    public void display(ArrayList<Task> tasks);

    /**
     * Displays the provided string directly on the TextPane via
     * {@link TextPane#print(String)}
     * 
     * @param s
     *            String to be displayed
     */
    public void display(String s);

    /**
     * Retrieves the Task object associated with the index provided. The general
     * contract is that the display will associate an unique integer index to
     * each Task object whenever {@link #display(ArrayList)} is called
     * 
     * @param index
     *            Integer index associated with a displayed Task object
     * @return Task object associated with the provided index
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds
     */
    public Task getTask(int index) throws TaskNotFoundException;

    /**
     * Displays the provided FeedbackMessage object to the user. The
     * FeedbackMessage object encapsulates the feedback message text as well as
     * the styling with which to display the message
     * 
     * @param f
     *            FeedbackMessage object to be displayed
     * @see FeedbackMessage
     */
    public void feedback(FeedbackMessage f);

    public String getFeedBackMessage();

    /**
     * Adds the provided {@link Filter} object to the filter chain. The filtered
     * list of Task objects will then be displayed
     * 
     * @param filter
     *            Filter object to add to the filter chain
     */
    public void pushFilter(Filter filter);

    /**
     * Removes the final {@link Filter} object in the filter chain and returns
     * it. If only the root filter is remaining, this method does nothing and
     * returns {@code null}
     * 
     * @return Popped {@link Filter} object or {@code null} if only the root
     *         {@link Filter} remains
     */
    public Filter popFilter();

    /**
     * Re-seeds the root {@link Filter} object with the provided list of Task
     * objects and updates the entire filter chain
     * 
     * @param tasks
     *            List of Task objects
     */
    public void updateFilters(ArrayList<Task> tasks);

    /**
     * Shows a file picker dialog which prompts the user to select a FunDUE data
     * file. If a file is successfully chosen, the user's preference will be
     * recorded via {@link StorageInterface#setDataFile(java.io.File)} and
     * the selected file will used as the data file.
     */
    public void showChangeDataFilePathDialog();
}
