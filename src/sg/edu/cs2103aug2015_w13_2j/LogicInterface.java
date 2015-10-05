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

    public Task deleteTask(String taskName);

    public void archiveTask(String taskName);
    
    public Task retrieveTask(String taskName);

    public ArrayList<Task> sortByDeadline();
    
    public ArrayList<Task> sortByDeadline(ArrayList<Task> list);

    public ArrayList<Task> getAllTasks();
    
    public ArrayList<Task> getEvents();
    
    public ArrayList<Task> getDeadlines();
    
    public ArrayList<Task> getFloats();

    /**
     * Echos back the command entered. For testing purposes and as a stub
     * 
     * @param s
     *            The string to echo back
     */
    public void echo(String s);

}
