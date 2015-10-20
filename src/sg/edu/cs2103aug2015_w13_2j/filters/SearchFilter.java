package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public class SearchFilter extends Filter {
    private String mNeedle;
    
    public SearchFilter(String needle) {
        FILTER_NAME = "search:" + needle;
        mNeedle = needle;
    }
    
    @Override
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for(Task task : tasks) {
            if(task.getName().contains(mNeedle)) {
                mTasks.add(task);
            }
        }
    }

}
