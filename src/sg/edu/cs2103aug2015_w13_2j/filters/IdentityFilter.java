package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public class IdentityFilter extends Filter {
    public IdentityFilter() {
        FILTER_NAME = "all";
    }
    
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>(tasks);
    }
}
