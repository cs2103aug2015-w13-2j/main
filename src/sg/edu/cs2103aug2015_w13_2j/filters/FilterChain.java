package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.filters.SortFilter.InvalidSortFilterException;

public class FilterChain {
    private Stack<Filter> mFilters =  new Stack<Filter>();
    
    public FilterChain() {
        this(new ArrayList<Task>());
    }
    
    public FilterChain(ArrayList<Task> tasks) {
        Filter root = new IdentityFilter();
        root.applyFilter(tasks);
        mFilters.push(root);
    }
    
    public ArrayList<Task> getTasksForDisplay() {
    	//for debugging
    	 for (int i = 0; i < mFilters.peek().getTasks().size(); i++) {
             System.out.println("task " + mFilters.peek().getTasks().get(i));
    	 }
    	Collections.sort(mFilters.peek().getTasks());
        return mFilters.peek().getTasks();
    }
    
    public ArrayList<Task> getTasks() {
        return mFilters.elementAt(0).getTasks();
    }
    
    public void addTask(Task task) {
        mFilters.elementAt(0).addTask(task);
        updateFilters();
    }
    
    public Task getTask(int index) {
        return mFilters.peek().getTask(index);
    }
    
    public Task removeTask(int index) {
        Task task = mFilters.peek().getTask(index);
        Collections.sort(mFilters.elementAt(0).getTasks());
        return mFilters.elementAt(0).removeTask(task);
    }
    
    public void updateFilters() {
        ArrayList<Task> tasks = mFilters.elementAt(0).getTasks();
        Collections.sort(tasks);
        for(Filter filter : mFilters) {
            filter.applyFilter(tasks);
            tasks = filter.getTasks();
        }
    }
    
    public void pushFilter(Filter filter) {
        filter.applyFilter(mFilters.peek().getTasks());
        mFilters.push(filter);
    }
    
    public Filter popFilter() {
        if(mFilters.size() > 1) {
            return mFilters.pop();
        } else {
            return null;
        }
    }
    
    public String getFilterChain() {
        StringBuilder sb = new StringBuilder("/");
        for(Filter filter : mFilters) {
            sb.append(filter.getFilterName());
            sb.append("/");
        }
        return sb.toString();
    }
}
