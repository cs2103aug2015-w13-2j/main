package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;

// @@author A0121410H

/**
 * This class provides methods to manage {@link Filter} objects added to the
 * chain of filters.
 * 
 * @author Zhu Chunqi
 */
public class FilterChain {
    private static final Logger LOGGER = Logger
            .getLogger(FilterChain.class.getName());

    private final Stack<Filter> mFilters = new Stack<Filter>();

    /**
     * Zero-parameter constructor. Internally calls
     * {@link #FilterChain(ArrayList)} with an empty list of Task objects
     */
    public FilterChain() {
        this(new ArrayList<Task>());
    }

    /**
     * FilterChain constructor. Initializes a new {@link IdentityFilter} as the
     * root of the filter chain stack
     * 
     * @param tasks
     *            List of Task objects to initialize the root filter with
     */
    public FilterChain(ArrayList<Task> tasks) {
        Filter root = new IdentityFilter();
        root.applyFilter(tasks);
        mFilters.push(root);
    }

    /**
     * Retrieves the number of {@link Filter} objects in the filter chain. This
     * number is always at least 1 as the root {@link IdentityFilter} cannot be
     * removed
     * 
     * @return Number of filters in the filter chain
     */
    public int size() {
        return mFilters.size();
    }

    /**
     * Retrieves a sorted list of Task objects after being filtered through the
     * filter chain
     * 
     * @return Sorted list of Task objects
     */
    public ArrayList<Task> getTasksForDisplay() {
        Collections.sort(mFilters.peek().getTasks());
        return mFilters.peek().getTasks();
    }

    /**
     * Retrieves a sorted list of Task objects from the root filter in the
     * filter chain, i.e. the master list of <b>all</b> Task objects
     * 
     * @return Sorted list of Task objects
     */
    public ArrayList<Task> getTasks() {
        Collections.sort(mFilters.elementAt(0).getTasks());
        return mFilters.elementAt(0).getTasks();
    }

    /**
     * Adds the provided Task object to the list of Task objects in the root
     * filter of the filter chain. The change is then propagated through the
     * filter chain by calling {@link #updateFilters()}
     * 
     * @param task
     *            Task object to be added
     */
    public void addTask(Task task) {
        mFilters.elementAt(0).addTask(task);
        updateFilters();
    }

    /**
     * Retrieves the Task object associated with the provided index after
     * filtering through the filter chain. Throws an exception if the index is
     * out of bounds
     * 
     * @param index
     *            Index of Task object to retrieve
     * @return Task object associated with the provided index
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds
     */
    public Task getTask(int index) throws TaskNotFoundException {
        return mFilters.peek().getTask(index);
    }

    /**
     * Removes the Task object associated with the provided index after
     * filtering through the filter chain. Throws an exception if the index is
     * out of bounds
     * 
     * @param index
     *            Index of Task object to remove
     * @return Task object associated with the provided index
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds
     */
    public Task removeTask(int index) throws TaskNotFoundException {
        Task task = mFilters.peek().getTask(index);
        return mFilters.elementAt(0).removeTask(task);
    }

    /**
     * Updates all filters in the filter chain, beginning at the root filter
     */
    public void updateFilters() {
        ArrayList<Task> tasks = mFilters.elementAt(0).getTasks();
        for (Filter filter : mFilters) {
            filter.applyFilter(tasks);
            tasks = filter.getTasks();
        }
    }

    /**
     * Re-seeds the filter chain with the provided list of Task objects.
     * Internally calls {@link Filter#applyFilter(ArrayList)} on the root filter
     * with the provided list of Task objects and calls {@link #updateFilters()}
     * 
     * @param tasks
     *            List of Task objects to seed the root filter
     */
    public void updateFilters(ArrayList<Task> tasks) {
        mFilters.get(0).applyFilter(tasks);
    }

    /**
     * Adds the provided {@link Filter} object to the stack of filters and
     * applies its filter functionality. It then becomes the final filter from
     * which Task objects' indices are derived
     * 
     * @param filter
     *            Filter object to be added to the filter chain
     */
    public void pushFilter(Filter filter) {
        filter.applyFilter(mFilters.peek().getTasks());
        mFilters.push(filter);
        LOGGER.log(Level.INFO, "Pushed filter: " + filter.getFilterName());
    }

    /**
     * Pops the final {@link Filter} object from the stack of filters and
     * returns the removed {@link Filter} object. If there is only the root
     * filter left, nothing will be done and {@code null} is returned
     * 
     * @return {@link Filter} object removed or {@code null} if the only filter
     *         remaining is the root filter
     */
    public Filter popFilter() {
        if (mFilters.size() > 1) {
            LOGGER.log(Level.INFO,
                    "Popped filter: " + mFilters.peek().getFilterName());
            return mFilters.pop();
        } else {
            LOGGER.log(Level.WARNING, "Cannot pop root filter");
            return null;
        }
    }

    public String getFilterChain() {
        StringBuilder sb = new StringBuilder("/");
        for (Filter filter : mFilters) {
            sb.append(filter.getFilterName());
            sb.append("/");
        }
        return sb.toString();
    }
}
