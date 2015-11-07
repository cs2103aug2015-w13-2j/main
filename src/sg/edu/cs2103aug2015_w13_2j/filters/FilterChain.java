package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0121410H

/**
 * The FilterChain class encapsulates a {@link Stack} of {@link Filter} objects
 * and provides methods to {@code push} and {@code pop} {@link Filter} objects
 * to the {@link Stack}.
 * 
 * @author Zhu Chunqi
 */
public class FilterChain {
    private static final Logger LOGGER = Logger
            .getLogger(FilterChain.class.getName());

    private final Stack<Filter> mFilters = new Stack<Filter>();

    /**
     * Zero-parameter constructor. Internally calls the alternative constructor
     * {@link #FilterChain(ArrayList)} with a new empty list.
     */
    public FilterChain() {
        this(new ArrayList<Task>());
    }

    /**
     * Class constructor. Initializes a new {@link IdentityFilter} as the root
     * of the filter chain stack seeded with the provided list of {@link Task}
     * objects.
     * 
     * @param tasks
     *            List of {@link Task} objects to seed the root {@link Filter}
     *            object.
     */
    public FilterChain(ArrayList<Task> tasks) {
        Filter root = new IdentityFilter();
        root.applyFilter(tasks);
        mFilters.push(root);
    }

    /**
     * Retrieves the size of the filter chain stack. The number returned is
     * always <b>at least 1</b> as the root {@link IdentityFilter} cannot be
     * removed.
     * 
     * @return Number of {@link Filter} objects in this {@link FilterChain}.
     */
    public int size() {
        return mFilters.size();
    }

    /**
     * Retrieves a sorted list of {@link Task} objects after being filtered
     * through the filter chain.
     * 
     * @return Sorted list of {@link Task} objects.
     */
    public ArrayList<Task> getTasksForDisplay() {
        Collections.sort(mFilters.peek().getTasks());
        return mFilters.peek().getTasks();
    }

    /**
     * Retrieves a sorted list of {@link Task} objects from the <b>root</b>
     * {@link Filter} object in the filter chain, i.e. the list of {@link Task}
     * objects that this {@link FilterChain} was seeded with.
     * 
     * @return Sorted list of {@link Task} objects.
     */
    public ArrayList<Task> getTasks() {
        Collections.sort(mFilters.elementAt(0).getTasks());
        return mFilters.elementAt(0).getTasks();
    }

    /**
     * Updates all {@link Filter}s in the filter chain stack by chaining calls
     * to {@link Filter#applyFilter(ArrayList)}, beginning with the list of
     * {@link Task} objects in the root {@link Filter}.
     */
    public void updateFilters() {
        ArrayList<Task> tasks = mFilters.elementAt(0).getTasks();
        for (Filter filter : mFilters) {
            filter.applyFilter(tasks);
            tasks = filter.getTasks();
        }
    }

    /**
     * Re-seeds the filter chain with the provided list of {@link Task} objects.
     * Internally calls {@link Filter#applyFilter(ArrayList)} on the root
     * {@link Filter} object with the provided list of Task objects and then
     * propagates the changes by calling {@link #updateFilters()}.
     * 
     * @param tasks
     *            List of {@link Task} objects to seed the root {@link Filter}.
     */
    public void updateFilters(ArrayList<Task> tasks) {
        mFilters.get(0).applyFilter(tasks);
        updateFilters();
    }

    /**
     * Adds the provided {@link Filter} object to the filter chain stack and
     * applies its filter functionality. It then becomes the final filter from
     * which the indices of displayed {@link Task} objects are derived.
     * 
     * @param filter
     *            {@link Filter} object to add to the filter chain stack.
     */
    public void pushFilter(Filter filter) {
        filter.applyFilter(mFilters.peek().getTasks());
        mFilters.push(filter);
        LOGGER.log(Level.INFO, "Pushed filter: " + filter.getFilterName());
    }

    /**
     * Pops and returns the final {@link Filter} object from the filter chain
     * stack. If only the root {@link IdentityFilter} left, this method does
     * nothing and returns {@code null}.
     * 
     * @return Popped {@link Filter} or {@code null} if only the root
     *         {@link IdentityFilter} is left.
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

    /**
     * Retrieves a string representation of all the {@link Filter} objects in
     * the filter chain stack. The name of each {@link Filter} object is
     * retrieved via {@link Filter#getFilterName()} and appended with {@code /}
     * as the separator.
     * 
     * @return String representation of all the {@link Filter} objects in the
     *         filter chain stack.
     */
    public String getFilterChain() {
        StringBuilder sb = new StringBuilder("/");
        for (Filter filter : mFilters) {
            sb.append(filter.getFilterName());
            sb.append("/");
        }
        return sb.toString();
    }
}
