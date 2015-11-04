package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public class ActiveFilter extends Filter {
    public ActiveFilter() {
        FILTER_NAME = "is:active";
    }
    
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for(Task task : tasks) {
            if(!task.isCompleted()
                    // overdue tasks are considered active
                    && (!task.isOverdue() || task.getStart() == null
                    && task.getEnd() != null)) {
                mTasks.add(task);
            }
        }
    }
}
