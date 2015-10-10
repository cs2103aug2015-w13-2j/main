package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Vector;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;

/**
 * This interface contains methods for classes implementing it as well as for
 * other components Parser, Formatter, TextUI, Controller to access it
 * 
 * @author Nguyen Tuong Van
 */

public interface LogicInterface {
	/**
	 * Executes the command represented by the parsed tokens
	 * 
	 * @param tokens
	 *            The parsed tokens
	 */
	public void executeCommand(ArrayList<Pair<Token, String>> tokens);

	/**
	 * This method adds in a new task to the to-do list
	 *
	 * @param task
	 *            the new task to be added
	 */

	public void addTask(Task task);



	/**
	 * This method delete the task with the specified index from the to-do list
	 *
	 * @param taskName
	 *            the name requested by user or other classes
	 * @param currentList
	 *            current view of tasks 
	 * @return task the deleted task with the requested index
	 */

	public Task deleteTask(ArrayList<Task> currentList, int taskIndex);

	/**
	 * This method archives the task with the specified index from the to-do list
	 *
	 * @param taskName
	 *            the index requested by user or other classes
	 * @param currentList
	 *            current view of tasks 
	 */

	public void archiveTask(ArrayList<Task> currentList, int taskIndex);
	/**
	 * This method marks the task with the specified index from the to-do list as
	 * completed
	 *
	 * @param taskName
	 *            the index requested by user or other classes
	 * @param currentList
	 *            current view of tasks 
	 */

	public void markTaskCompleted(ArrayList<Task> currentList, int taskIndex);

	/**
	 * This method updates the task original task
	 * by merging the task with the new Task object created by user's command
	 *
	 * @param original
	 *            the original task
	 * @param task
	 *            the new Task object created by user's command
	 * @return task the updated task with the original 
	 */

	public Task editTask(Task original, Task edittingTask);

	public ArrayList<Task> sortByDeadline();

	public ArrayList<Task> list();

	

	/**
	 * Echos back the command entered. For testing purposes and as a stub
	 * 
	 * @param s
	 *            The string to echo back
	 */
	public void echo(String s);

}
