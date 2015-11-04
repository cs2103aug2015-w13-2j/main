package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author NOAUTHOR

public class ActiveFilter extends Filter {
    public ActiveFilter() {
        FILTER_NAME = "is:active";
    }

    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            // overdue tasks are considered active
            if (!task.isCompleted() && (!task.isOverdue()
                    || task.getStart() == null && task.getEnd() != null)) {
                mTasks.add(task);
            }
        }
    }
}
