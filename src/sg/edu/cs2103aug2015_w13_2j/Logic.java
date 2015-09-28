package sg.edu.cs2103aug2015_w13_2j;

import java.util.*;

public class Logic implements LogicInterface{
    private ArrayList<Task> tasks;
    
    public Logic(){
        tasks = new ArrayList<Task>();	
    }
    
    public void addTask(Task task){
    	tasks.add(task);
    }
    
    public void deleteTask(Task task){
    	
    }
    public void archiveTask(Task task){
    	
    }
    public void sort(){
    	
    }
    public ArrayList<Task> getAllTask(){
    	return tasks;
    }
    
    /*should display be in Logic ?*/
    public void display(){
    	
    }
}
