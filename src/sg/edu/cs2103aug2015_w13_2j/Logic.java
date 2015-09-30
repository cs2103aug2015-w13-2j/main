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
    private HashMap<String, ArrayList<Task>> types;

    
    public Logic(){
        tasks = new ArrayList<Task>();	
        types = new HashMap<String, ArrayList<Task>>();
        types.put("EVENT", null);
        types.put("DEADLINE", null);
        types.put("FLOAT", null);
    }
    
    public void addTask(Task task){
    	tasks.add(task);
    }
    
    public Task getTask(int index){
    	return tasks.get(index);
    }
    
    public void deleteTask(Task task){
    	tasks.remove(task);
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
    
    //WIP
    public Task findTaskByName(String name){
    	//for(int index = 0; index<)
    	return null;
    }
}
