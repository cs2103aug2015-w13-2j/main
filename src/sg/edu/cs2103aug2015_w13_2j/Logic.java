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
    private ArrayList<Task> events;//TBC if needed
    private ArrayList<Task> deadlines;//TBC if needed
    private ArrayList<Task> floats;//TBC if needed
    private ArrayList<Task> userView;//to store the user's latest requested list if any
                                     //eg. user filter(completed) all completed tasks will be store
                                     //here in case user wants to perform further request 
   /**
    * Constructor for the Logic component
    * 
    */
    public Logic(){
        tasks = new ArrayList<Task>();	
        events = new ArrayList<Task>();	
        deadlines = new ArrayList<Task>();	
        floats = new ArrayList<Task>();	
        userView = new ArrayList<Task>();
        readFile();
    }
    
    /**
     * Add a new task
     * @param task
     *            the new task to be added
     * 
     */
    public void addTask(Task task){
    	tasks.add(task);
    	
    	if(task.getType().equals("EVENT")){
    		events.add(task);
    	} else if (task.getType().equals("DUE")){
    		deadlines.add(task);
    	} else {
    		floats.add(task);
    	}
    }
    
    public Task getTask(int index){
    	return tasks.get(index);
    }
    
    /**
     * Delete a task
     * @param task
     *            the new task to be deleted
     * 
     */
    public void deleteTask(Task task){
    	task.markDeleted();
    	tasks.remove(task);
    	events.remove(task);
    	deadlines.remove(task);
    	floats.remove(task);
    }
    
    public void archiveTask(Task task){
    	task.markArchived();
    }
    
    /**
     * This method lets user see all tasks they have previously marked as completed
     * It traverses the tasks list to take out all completed task, put them into the
     * userView list and prints out the tasks
     *  
     * @return
     *            the list of completed tasks
     * 
     */
    
    public ArrayList<Task> viewCompleted(){
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getStatus().equals("COMPLETED")){
    			userView.add(tasks.get(i));
    		}
    	}
    	
    	return userView;
    }
    
    /**
     * This method lets user see all tasks they have previously marked as completed
     * It works the same way as viewCompleted does
     *  
     * @return
     *            the list of archived tasks
     * 
     */
    public ArrayList<Task> viewArchived(){
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getStatus().equals("ARCHIVED")){
    			userView.add(tasks.get(i));
    		}
    	}
    	
    	return userView;
    }
    
    public ArrayList<Task> viewOverdue(){
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getStatus().equals("OVERDUE")){
    			userView.add(tasks.get(i));
    		}
    	}
    	
    	return userView;
    }
    
    public void sortByDeadLine(){
    	
    }
    
    public void readFile(){
    	
    }
    
    public ArrayList<Task> getAllTask(){
    	return tasks;
    }
    
    public void echo(String s){
    	FunDUE.sFormatter.passThrough(s);
    }
    
    /**
     * Find a task based on name
     * @param name
     *            the name being searched for 
     * @return 
     *        the Task with the name requested
     */
    public Task findTaskByName(String name){
    	//for the case of only one task with the name first
    	Task result = new Task();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getName().equals(name)){
    			result = tasks.get(i);
    			break; //for now, only output the first occurrence
    		}
    	}
    	
       return result;
    }
}
