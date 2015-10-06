package sg.edu.cs2103aug2015_w13_2j;

/**
This class implements methods from LogicInterface
@@author A0133387B 

 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Logic implements LogicInterface{
    private ArrayList<Task> tasks; //this ArrayList includes all added tasks, excluding deleted ones
    private ArrayList<Task> events;//TBC if needed
    private ArrayList<Task> deadlines;//TBC if needed
    private ArrayList<Task> floats;//TBC if needed
    private ArrayList<Task> userView;//to store the user's latest requested list if any
                                     //eg. user filter(completed) all completed tasks will be store
                                     //here in case user wants to perform further request 
    private ArrayList<Task> archive;
    private ArrayList<Task> deleted;
    
    /**
    * Constructor for the Logic component
    * 
    */
    public Logic(){
        tasks = new ArrayList<Task>();	
        events = new ArrayList<Task>();	
        deadlines = new ArrayList<Task>();	
        floats = new ArrayList<Task>();	
        archive = new ArrayList<Task>();
        deleted = new ArrayList<Task>();	
        userView = new ArrayList<Task>();
        //readFile();   <--- currently having an error
        checkStatus();
        try{
    		FunDUE.sStorage.writeFile(tasks, "output.txt");//TODO: to be updated
    	} catch (Exception e){
    		e.printStackTrace();
    	}
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

    	FunDUE.sFormatter.format(task, FormatterInterface.Format.LIST);//TODO: to be updated
    	
    	try{
    		FunDUE.sStorage.writeFile(tasks, "output.txt");//TODO: to be updated
    	} catch (Exception e){
    		e.printStackTrace();
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
    
    
    public ArrayList<Task> getEvents(){
    	return events;
    }

    public ArrayList<Task> getDeadlines(){
    	return deadlines;
    }
    
    public ArrayList<Task> getFloats(){
    	return floats;
    }
    
    /**
     * Delete a task
     * @param task
     *            the task to be deleted
     * 
     */
    public Task deleteTask(String taskName){
    	Task task = findTaskByName(taskName);
    	task.markDeleted();
    	tasks.remove(task);
    	events.remove(task);
    	deadlines.remove(task);
    	floats.remove(task);
    	archive.remove(task);
    	deleted.add(task);
    	FunDUE.sFormatter.format(task, FormatterInterface.Format.LIST); //TODO: to be updated
    	
    	try{
    		FunDUE.sStorage.writeFile(tasks, "output.txt");//TODO: to be updated
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    	return task;
    }
    
    /**
     * Archive a task
     * @param task
     *            the task to be archived
     * 
     */
    
    public void archiveTask(String taskName){
    	Task archivedTask = findTaskByName(taskName);
    	archivedTask.markArchived();
    	archive.add(archivedTask);
    	FunDUE.sFormatter.format(archivedTask, FormatterInterface.Format.LIST); //TODO: to be updated
    }
    
    
    public Task retrieveTask(String taskName){
    	return findTaskByName(taskName);
    }
    
    
    public void markTaskCompleted(String taskName){
    	Task task = findTaskByName(taskName);
    	task.markCompleted();
    	FunDUE.sFormatter.format(task, FormatterInterface.Format.LIST); //TODO: to be updated
    	try{
    		FunDUE.sStorage.writeFile(tasks, "output.txt");//TODO: to be updated
    	} catch (Exception e){
    		e.printStackTrace();
    	}
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
    	
    	FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
    	return userView;
    }
    
    /**
     * This method lets user see all tasks they have previously archived
     *  
     * @return
     *            the list of archived tasks
     * 
     */
    public ArrayList<Task> viewArchived(){
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < archive.size(); i++){
    		userView.add(archive.get(i));
    		
    	}
    	FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
    	return userView;
    }
    
    /**
     * This method is NOT for user to implement
     * It's used to ease the UNDO function later on
     * 
     * @return
     *            the list of deleted tasks
     * 
     */
    public ArrayList<Task> viewDeleted(){
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < deleted.size(); i++){
    		userView.add(deleted.get(i));
    		
    	}
    	FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
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
    	Date date = new Date();
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getDeadline() != null && tasks.get(i).getDeadline().compareTo(date) < 0){
    			userView.add(tasks.get(i));
    			tasks.get(i).markOverdue();
    		}
    	}
    	FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
    	return userView;
    }
    
    public void checkStatus(){
    	Date date = new Date();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getDeadline() != null && tasks.get(i).getDeadline().compareTo(date) < 0){
    			userView.add(tasks.get(i));
    			tasks.get(i).markOverdue();
    		}
    	}
    }
    
    /**
     * This method lets user see all tasks are still active (excluding those overdue, completed, deleted,
     * and archived
     * @return
     *            the list of active tasks
     * 
     */
    public ArrayList<Task> viewOngoing(){
    	ArrayList<Task> overdues = viewOverdue(); //this method filters out the tasks overdue by now
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getStatus().equals("ONGOING")){
    			userView.add(tasks.get(i));
    		}
    	}
    	sortByDeadline(userView);
    	FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
    	return userView;
    }
    
    public ArrayList<Task> list(){
    	userView = viewOverdue();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getStatus().equals("ONGOING") || tasks.get(i).getStatus().equals("COMPLETED")){
    			userView.add(tasks.get(i));
    		}
    	}
    	FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
    	return userView;
    }
    
    
    /**
     * This method sorts a list of tasks according to their deadlines(if any)
     * The tasks with deadlines takes priority, followed by events sorted according to start time
     * and floats to be added last, sorted by their names
     *  
     *  */
    
    public ArrayList<Task> sortByDeadline(ArrayList<Task> list){
    	Collections.sort(list, new Comparator<Task>(){
    		public int compare(Task task1, Task task2){
    			if(task1.getType().equals(task2.getType())){
    				if(task1.getType().equals("DUE")){
    				    return task1.getDeadline().compareTo(task2.getDeadline()); 
    				} else if (task1.getType().equals("EVENT")){
    					return task1.getStart().compareTo(task2.getStart()); 
    				} else {
    					return task1.getName().compareTo(task2.getName()); 
    				}
    			} else {
    				return task1.getType().compareTo(task2.getType());
    			}
    		}
    	});
    	FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
    	return list;
    }
    
    public ArrayList<Task> sortByDeadline(){
    	//TODO: add suitable sorting algorithm here
    	//this method is invoked if no parameter is passed in -> sort the whole tasks
    	//list and put them into the userView
    	userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		userView.add(tasks.get(i));
    	}
    	//FunDUE.sFormatter.format(userView, FormatterInterface.Format.LIST); //TODO: to be updated
    	return sortByDeadline(userView);
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
    		deleteTask(original.getName());
    	}
    	
    	if(newTask.getStatus().equals("ARCHIVED")) {
    		archiveTask(original.getName());
    	}
    	
    	return original;
    }
    
    /**
     * This method edits a task according to the fields specified by the user
     * It takes in a task formed by the name input by user, as well as the fields to changed
     * It then updates the task identified by the name field in the method signature
     * the updating is taken care of by the method mergeDetails
     * Changes in the type of the task due to changes in the start and end time or status are also updated
     * This information is updated manually by checking the changes (if any) in existence of start and deadline
     * @param 
     *            the name of the Task user wishes to edit
     * @param 
     *            the new Task object formed by the requested changes
     * @return
     *            the updated task
     * 
     */
    public Task editTask(String name, Task task){
    	Task original = findTaskByName(name);
	
    	//potential edits to the type of task
    	
    	if(original.getStart() == null && original.getDeadline() == null ){//originally float
    		if(task.getStart() != null && task.getDeadline() != null)  {//edited to events
    			original.setTypeEvent();
    			floats.remove(original);
    			events.add(original);
    		}
    		
    		if(task.getStart() == null && task.getDeadline() != null)  {//edited to deadline tasks
    			original.setTypeDeadline();
    			floats.remove(original);
    			deadlines.add(original);
    		}
    	} else if (original.getStart() != null && original.getDeadline() != null ){//originally event
    		if(task.getDeadline() == null){//change to float
    			original.setTypeFloat();
    			events.remove(original);
    			floats.add(original);
    			
    		} else if(task.getStart() == null){//change to task with deadline
    			original.setTypeDeadline();
    			events.remove(original);
    			deadlines.add(original);
    		}
    	} else if (original.getStart() == null && original.getDeadline() != null){//originally task with deadline
    		if(task.getDeadline() == null){//change to float
    			original.setTypeFloat();
    			deadlines.remove(original);
    			floats.add(original);
    		} else if(task.getStart() != null && task.getDeadline() != null)  {//edited to event
    			original.setTypeEvent();
    			deadlines.remove(original);
    			events.add(original);
    		}
    	}
    	
    	mergeDetails(original, task);
    	FunDUE.sFormatter.format(original, FormatterInterface.Format.LIST); //TODO: to be updated
    	try{
    		FunDUE.sStorage.writeFile(tasks, "output.txt");//TODO: to be updated
    	} catch (Exception e){
    		e.printStackTrace();
    	}
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
    /**
     * This method returns all the tasks inside the to-do list (which excludes deleted tasks) 
     *  */    
    
    
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

    
    /*
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
    	
    	Task newTask = new Task("first test task");
		newTask.setDeadline(new Date());
		logic.determineType(newTask);
		logic.editTask("first test task", newTask);
    	System.out.println(newTask.getType());
    	System.out.println(task.getType());
    	//logic.addTask(new Task("second test task"));
    	//System.out.println("First task was created at " + logic.findTaskByName("first test task").getCreated());
    }
    
    */
}
