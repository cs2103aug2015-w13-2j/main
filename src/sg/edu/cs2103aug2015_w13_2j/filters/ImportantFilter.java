package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author NOAUTHOR

public class ImportantFilter extends Filter {
    public ImportantFilter() {
        FILTER_NAME = "is:important";
    }
    
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for(Task task : tasks) {
            if(task.isImportant()) {
                mTasks.add(task);
            }
        }
    }
}
