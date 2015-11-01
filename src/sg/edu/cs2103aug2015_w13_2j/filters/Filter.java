package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;

// @@author A0121410H

/**
 * Base Filter class that all functional Filters extend from.
 * 
 * @author Zhu Chunqi
 */
public abstract class Filter {
    protected String FILTER_NAME = "filter";
    protected ArrayList<Task> mTasks;

    /**
     * Applies this filter's functionality to the provided list of Task objects.
     * The filtered list of Task objects can be retrieved by calling
     * {@link #getTasks()}
     * 
     * @param tasks
     *            List of Task objects to be filtered
     */
    public abstract void applyFilter(ArrayList<Task> tasks);

    /**
     * Retrieves the filtered list of Task objects
     * 
     * @return Filtered list of Task objects
     */
    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    /**
     * Adds a Task object to the list of Task objects stored in this filter
     * 
     * @param task
     *            Task object to be added
     */
    public void addTask(Task task) {
        mTasks.add(task);
    }

    /**
     * Retrieves the Task object associated with the provided index. Throws an
     * exception if the index is out of bounds
     * 
     * @param index
     *            Index of Task object to retrieve
     * @return Task object associated with the provided index
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds
     */
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            return mTasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Removes the Task object associated with the provided index. Throws an
     * exception if the index is out of bounds
     * 
     * @param index
     *            Index of Task object to remove
     * @return Task object associated with the provided index
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds
     */
    public Task removeTask(Task task) throws TaskNotFoundException {
        try {
            int index = mTasks.indexOf(task);
            return mTasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Retrieves the name of this filter
     * 
     * @return String name of this filter
     */
    public String getFilterName() {
        return FILTER_NAME;
    }
}
