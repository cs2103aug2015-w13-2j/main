package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;

/**
This class implements methods from LogicInterface
@@author A0133387B 

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
        //readFile();
    }
    
    /**
     * Add a new task to the main arrayList and also determine the type of the task
     * @param task
     *            the new task to be added
     * 
     */
    public void addTask(Task task){
    	tasks.add(task);
    	
    	determineType(task);
    	 
    	   if(task.getType().equals("EVENT")){
    		  events.add(task);
    	   } else if (task.getType().equals("DUE")){
    		  deadlines.add(task);
    	   } else {
    		  floats.add(task);
    	   }
    	
    }
    /**
     * Determine the type of a task based on its start (if any) and end (if any) times
     * @param task
     *            the new task to be categorized
     * 
     */
    
    public void determineType(Task task){
    	if(task.getDeadline() == null){// if deadLine == null, float
    		task.setTypeFloat();
    	} else {
    		if (task.getStart() != null){//if deadLine != null and start != null, event
    		    task.setTypeEvent();
    	    } else {//if deadLine != null but start == null, then it's a task with deadline
    		    task.setTypeDeadline();
    	    }
    	}
    }
    
    public Task getTask(int index){
    	return tasks.get(index);
    }
    
    /**
     * Delete a task
     * @param task
     *            the task to be deleted
     * 
     */
    public Task deleteTask(Task task){
    	task.markDeleted();
    	tasks.remove(task);
    	events.remove(task);
    	deadlines.remove(task);
    	floats.remove(task);
    	
    	return task;
    }
    
    /**
     * Archive a task
     * @param task
     *            the task to be archived
     * 
     */
    
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
    
    /**
     * This method lets user see all tasks which have not been marked completed after the deadline
     * This only applies for tasks with due dates
     * It works the same way as viewCompleted does
     *  
     * @return
     *            the list of overdue tasks
     * 
     */
    public ArrayList<Task> viewOverdue(){
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getStatus().equals("OVERDUE")){
    			userView.add(tasks.get(i));
    		}
    	}
    	
    	return userView;
    }
    
    /**
     * This method lets user see all tasks are still active
     * It works the same way as viewCompleted does
     * @return
     *            the list of active tasks
     * 
     */
    public ArrayList<Task> viewOngoing(){
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getStatus().equals("ONGOING")){
    			userView.add(tasks.get(i));
    		}
    	}
    	
    	return userView;
    }
    
    /**
     * This method sorts a list of tasks according to their deadlines(if any)
     * The tasks with deadlines takes priority, followed by events sorted according to start time
     * and floats to be added last, sorted by their names
     *  
     *  */
    
    public void sortByDeadLine(ArrayList<Task> list){
    	//TODO: add suitable sorting algorithm here
    	
    }
    
    public void sortByDeadLine(){
    	//TODO: add suitable sorting algorithm here
    	//this method is invoked if no parameter is passed in -> sort the whole tasks
    	//list and put them into the userView
    }
    
    /**This method merges the details of a new task and the original task
     * by checking the fields of the new task
     * Those that are blank are kept unchanged in the original task
     * While the fields that are not null in the new task will be updated in the original  task
     * @param original
     *                the task that is being updated
     * @param newTask 
     *                the task that is updating the original task
     * @return 
     *                the updated original task
     */
    
    
    public Task mergeDetails(Task original, Task newTask){
    	//possible fields to update: name, start, end
    	if(newTask.getName() != null){
    		original.setName(newTask.getName());
    	}
    	
    	if(newTask.getDeadline() != null){
    		original.setDeadline(newTask.getDeadline());
    	}
    	
    	if(newTask.getStart() != null){
    		original.setStart(newTask.getStart());
    	}
    	
    	if(newTask.getStatus().equals("COMPLETED")) {
    		original.markCompleted();
    	}
    	
    	if(newTask.getStatus().equals("DELETED")) {
    		deleteTask(original);
    	}
    	
    	if(newTask.getStatus().equals("ARCHIVED")) {
    		archiveTask(original);
    	}
    	
    	return original;
    }
    
    /**
     * This method edits a task according to the fields specified by the user
     * It takes in a task formed by the name input by user, as well as the fields to changed
     * It then updates the task identified by the name
     * the updating is taken care of by the method mergeDetails
     * Changes in the type of the task due to changes in the start and end time or status are also updated
     * This information is updated manually by checking the changes (if any) in existence of start and deadline
     * @param 
     *            the new Task object formed by the requested changes
     * @return
     *            the updated task
     * 
     */
    public Task editTask(Task task){
    	Task original = findTaskByName(task.getName());
	
    	//potential edits to the type of task
    	
    	if(original.getStart() == null && original.getDeadline() == null ){//originally float
    		if(task.getStart() != null && task.getDeadline() != null)  {//edited to events
    			original.setTypeEvent();
    		}
    		
    		if(task.getStart() == null && task.getDeadline() != null)  {//edited to deadline tasks
    			original.setTypeDeadline();
    		}
    	} else if (original.getStart() != null && original.getDeadline() != null ){//originally event
    		if(task.getDeadline() == null){//change to float
    			original.setTypeFloat();
    		} else if(task.getStart() == null){//change to task with deadline
    			original.setTypeDeadline();
    		}
    	} else if (original.getStart() == null && original.getDeadline() != null){//originally task with deadline
    		if(task.getDeadline() == null){//change to float
    			original.setTypeFloat();
    		} else if(task.getStart() != null && task.getDeadline() != null)  {//edited to event
    			original.setTypeEvent();
    		}
    	}
    	
    	mergeDetails(original, task);
    	return original;
    }
    
    /**
     * This method updates the tasks list upon a new session
     * It reads the file from the storage and fills up the internal ArrayList
     *  
     *  */
    
    public void readFile(){
    	try{
    	    tasks = (ArrayList)FunDUE.sStorage.readFile("output.txt");//what should be the fileName field?
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    public ArrayList<Task> getAllTasks(){
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
    
    
    public static void main (String[] args){
    	Logic logic = new Logic();
    	Task task = new Task("first test task");
    	logic.addTask(task);
    	/*
    	System.out.println(task.getType());
    	
    	task.setStart(new Date(new Long("23456")));//setStart seems to have some bug
    	logic.determineType(task);
    	System.out.println(task.getType());
    	System.out.println("Start = " + task.getStart());
    	
    	task.setDeadline(new Date());
    	logic.determineType(task);
    	System.out.println(task.getType());
    	System.out.println("Deadline = "  + task.getDeadline());    
    	*/
    	Task newTask = new Task("first test task");
		newTask.setDeadline(new Date());
		logic.determineType(newTask);
		logic.editTask(newTask);
    	System.out.println(newTask.getType());
    	System.out.println(task.getType());
    	//logic.addTask(new Task("second test task"));
    	//System.out.println("First task was created at " + logic.findTaskByName("first test task").getCreated());
    }
    
    
}
