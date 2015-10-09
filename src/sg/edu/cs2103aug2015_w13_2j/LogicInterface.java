package sg.edu.cs2103aug2015_w13_2j;

import java.util.*;

/**
 * This interface contains methods for classes implementing it as well as for other components
 * Parser, Formatter, TextUI, Controller to access it
 * 
 * @@author Nguyen Tuong Van
 */

public interface LogicInterface {
    /** This method adds in a new task to the to-do list
    *
    *@param task
    *           the new task to be added
    */
	
    public void addTask(Task task);

    /** This method retrieves the task with the specified name 
    *
    *@param taskName
    *               the name requested by user or other classes
    *@return task
    *               the task with the requested name 
    */
    
    public Task retrieveTask(String taskName);

    
    /** This method delete the task with the specified name from the to-do list 
    *
    *@param taskName
    *               the name requested by user or other classes
    *@return task
    *               the deleted task with the requested name 
    */
    
    public Task deleteTask(String taskName);

    /** This method archives the task with the specified name from the to-do list 
    *
    *@param taskName
    *               the name requested by user or other classes
    */
    
    public void archiveTask(String taskName);
    
    /** This method marks the task with the specified name from the to-do list as completed 
    *
    *@param taskName
    *               the name requested by user or other classes
    */
    
    public void markTaskCompleted(String taskName);
    
    
    /** This method updates the task with the specified name from the to-do list by
     * merging the task with the new Task object created by user's command
    *
    *@param taskName
    *               the name requested by user or other classes
    *@param task
    *               the new Task object created by user's command
    *@return task
    *               the updated task with the original requested name 
    */
    
    public Task editTask(String taskName, Task task);
    
    public ArrayList<Task> sortByDeadline();

    public ArrayList<Task> getAllTasks();
    
    public ArrayList<Task> list();
    
    public ArrayList<Task> viewOverdue();
    
    public ArrayList<Task> viewCompleted();
    
    public ArrayList<Task> viewArchived();

    public ArrayList<Task> viewDeleted();
    /**
     * Echos back the command entered. For testing purposes and as a stub
     * 
     * @param s
     *            The string to echo back
     */
    public void echo(String s);

}
