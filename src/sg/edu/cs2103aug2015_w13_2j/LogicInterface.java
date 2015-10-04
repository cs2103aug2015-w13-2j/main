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

    public void deleteTask(Task task);

    public void archiveTask(Task task);

    public void sortByDeadLine();
    
    public void sortByDeadLine(ArrayList<Task> list);

    public ArrayList<Task> getAllTask();

    /**
     * Echos back the command entered. For testing purposes and as a stub
     * 
     * @param s
     *            The string to echo back
     */
    public void echo(String s);

}
