package sg.edu.cs2103aug2015_w13_2j;

/**
This class implements methods from LogicInterface
@author Nguyen Tuong Van  

TODO: Should we include a HashMap besides the ArrayList of Tasks
so as to store the task names as keys?
 */

import java.util.*;

public class Logic implements LogicInterface{
    private ArrayList<Task> tasks;
    private ArrayList<Task> events;
    private ArrayList<Task> deadLines;
    private ArrayList<Task> floatingTasks;
    
 //   private HashMap<String, Task> names;
    
    public Logic(){
        tasks = new ArrayList<Task>();	
    }
    
    public void addTask(Task task){
    	tasks.add(task);
    }
    
    public Task getTask(int index){
    	return tasks.get(index);
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
    
    public void echo(String s){
    	FunDUE.sFormatter.passThrough(s);
    }
}
