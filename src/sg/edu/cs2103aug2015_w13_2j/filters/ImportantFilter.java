package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0121410H

/**
 * {@link Filter} class that filters for important tasks. An important
 * {@link Task} is one which has been marked as complete via
 * {@link Task#setCompleted(boolean)} with a value of {@code true}.<br>
 * <br>
 * The filter predicate is as follows:<br>
 * 
 * <pre>
 * task.isImportant()
 * </pre>
 * 
 * @author Zhu Chunqi
 */
public class ImportantFilter extends Filter {
    public ImportantFilter() {
        mName = "is:important";
    }

    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.isImportant()) {
                mTasks.add(task);
            }
        }
    }
}
