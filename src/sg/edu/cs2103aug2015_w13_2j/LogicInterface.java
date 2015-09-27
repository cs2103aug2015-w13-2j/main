package sg.edu.cs2103aug2015_w13_2j;

import java.util.*;
/*This class contains methods for classes implementing the abstract class LogicInterface
Haven't put in edit due to uncertainty of format 
@Nguyen Tuong Van
*/

public interface LogicInterface {
	
    public void addTask(Task task);
    public void deleteTask(Task task);
    public void archiveTask(Task task);
    public void sort();
    public ArrayList<Task> getAllTask();
}
