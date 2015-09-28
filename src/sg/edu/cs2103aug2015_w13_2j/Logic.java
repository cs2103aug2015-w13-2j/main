package sg.edu.cs2103aug2015_w13_2j;

import java.util.*;

public class Logic {
    private ArrayList<Task> tasks;
    
    public Logic(){
        tasks = new ArrayList<Task>();	
    }
    
    public void addTask(Task task){
    	tasks.add(task);
    }
}
