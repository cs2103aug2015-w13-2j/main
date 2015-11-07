package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0121410H

/**
 * {@link Filter} class that does not modify the {@link Task} list in any way.
 * Suitable to be used as the root {@link Filter} of a {@link FilterChain} to
 * contain the seed list of {@link Task} objects.
 * 
 * @author Zhu Chunqi
 *
 */
public class IdentityFilter extends Filter {
    public IdentityFilter() {
        mName = "all";
    }

    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = tasks;
    }
}
