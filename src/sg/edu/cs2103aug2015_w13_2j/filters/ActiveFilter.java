package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0121410H

/**
 * {@link Filter} class that filters for active tasks. An active {@link Task} is
 * one that is <b>not</b> marked as complete.<br>
 * <br>
 * The filter predicate is as follows:<br>
 * 
 * <pre>
 * !task.isCompleted()
 * </pre>
 * 
 * @author Zhu Chunqi
 */
public class ActiveFilter extends Filter {
    public ActiveFilter() {
        mName = "is:active";
    }

    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (!task.isCompleted()) {
                mTasks.add(task);
            }
        }
    }
}
