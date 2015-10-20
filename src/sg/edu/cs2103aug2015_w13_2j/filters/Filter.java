package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public abstract class Filter {
    protected String FILTER_NAME = "filter";
    protected ArrayList<Task> mTasks;

    public abstract void applyFilter(ArrayList<Task> tasks);

    public ArrayList<Task> getTasks() {
        return mTasks;
    }
    
    public void addTask(Task task) {
        mTasks.add(task);
    }

    public Task getTask(int index) {
        try {
            return mTasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Task removeTask(Task task) {
        try {
            int index = mTasks.indexOf(task);
            return mTasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String getFilterName() {
        return FILTER_NAME;
    }
}
