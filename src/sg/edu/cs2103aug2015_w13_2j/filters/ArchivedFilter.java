package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public class ArchivedFilter extends Filter {
    public ArchivedFilter() {
        FILTER_NAME = "is:archived";
    }
    
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for(Task task : tasks) {
            if(task.isArchived()) {
                mTasks.add(task);
            }
        }
    }
}
