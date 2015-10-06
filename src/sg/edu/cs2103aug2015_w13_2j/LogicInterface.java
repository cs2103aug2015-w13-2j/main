package sg.edu.cs2103aug2015_w13_2j;

import java.util.*;

/**
 * This class contains methods for classes implementing the abstract class
 * 
 * @@author Nguyen Tuong Van
 */

public interface LogicInterface {

    public void addTask(Task task);

    public Task getTask(int index);
    
    public Task retrieveTask(String taskName);

    public Task deleteTask(String taskName);

    public void archiveTask(String taskName);
    
    public void markTaskCompleted(String taskName);
    
    public Task editTask(String taskName, Task task);
    
    public ArrayList<Task> sortByDeadline();
    
    public ArrayList<Task> sortByDeadline(ArrayList<Task> list);

    public ArrayList<Task> getAllTasks();
    
    public ArrayList<Task> getEvents();
    
    public ArrayList<Task> getDeadlines();
    
    public ArrayList<Task> getFloats();
    
    public ArrayList<Task> list();
    
    public ArrayList<Task> viewOngoing();
    
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
