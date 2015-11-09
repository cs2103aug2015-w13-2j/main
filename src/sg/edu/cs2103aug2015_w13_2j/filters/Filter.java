package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.exceptions.TaskNotFoundException;

// @@author A0121410H

/**
 * Base Filter class which all functional Filters extend from.
 * 
 * @author Zhu Chunqi
 */
public abstract class Filter {
    protected String mName = "filter";
    protected ArrayList<Task> mTasks;

    /**
     * Applies the functionality of this {@link Filter} to the provided list of
     * {@link Task} objects. The filtered list of {@link Task} objects can be
     * retrieved by calling {@link #getTasks()}.
     * 
     * @param tasks
     *            List of {@link Task} objects to be filtered.
     */
    public abstract void applyFilter(ArrayList<Task> tasks);

    /**
     * Retrieves the filtered list of {@link Task} objects.
     * 
     * @return Filtered list of {@link Task} objects.
     */
    public ArrayList<Task> getTasks() {
        return mTasks;
    }

    /**
     * Adds the provided {@link Task} object to the list of {@link Task} objects
     * stored in this {@link Filter}.
     * 
     * @param task
     *            {@link Task} object to be added.
     */
    public void addTask(Task task) {
        mTasks.add(task);
    }

    /**
     * Retrieves the {@link Task} object associated with the provided index.
     * Throws an exception if the provided index is out of bounds.
     * 
     * @param index
     *            Index of {@link Task} object to retrieve.
     * @return {@link Task} object associated with the provided index.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            return mTasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Removes the {@link Task} object associated with the provided index.
     * Throws an exception if the index is out of bounds.
     * 
     * @param task
     *            {@link Task} object to be removed.
     * @return {@link Task} object associated with the provided index.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
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
     * Retrieves the name of this {@link Filter}.
     * 
     * @return String name of this {@link Filter}.
     */
    public String getFilterName() {
        return mName;
    }
}
