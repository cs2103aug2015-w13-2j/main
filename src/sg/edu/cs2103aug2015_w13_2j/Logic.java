package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.TextUI.Message;

public class Logic implements LogicInterface {
	private FunDUE mAppInstance;
	private ArrayList<Task> mTasks = new ArrayList<Task>();

	public Logic(FunDUE appInstance) {
		mAppInstance = appInstance;
	}

	public void executeCommand(ArrayList<Pair<Token, String>> tokens) {
	    // Remove whitespaces first
	    Iterator<Pair<Token, String>> iter = tokens.iterator();
	    while(iter.hasNext()) {
	        Pair<Token, String> pair = iter.next();
	        if(pair.getKey() == Token.WHITESPACE) {
	            iter.remove();
	        }
	    }
	    
		for (Pair<Token, String> pair : tokens) {
			if (pair.getKey() == Token.RESERVED) {
				try {
					switch (pair.getValue()) {
					case "add":
						addTask(tokens);
						mAppInstance.getTextUIInstance().feedback(Message.LOGIC_ADDED);
						break;
					case "edit":
						editTask(tokens);
						mAppInstance.getTextUIInstance().feedback(Message.LOGIC_EDITED);
						break;
					case "list":
						mAppInstance.getTextUIInstance().feedback(Message.CLEAR);
						break;
					case "delete":
						deleteTask(tokens);
						mAppInstance.getTextUIInstance().feedback(Message.LOGIC_DELETED);
						break;
					default:
						System.err.println("[Logic] Unimplemented command: " + pair.getValue());
						mAppInstance.getTextUIInstance().feedback(Message.ERROR_COMMAND_NOT_IMPLEMENTED);
						break;
					}
				} catch (InvalidTaskException e) {
					System.err.println("[Logic] Invalid Task");
					mAppInstance.getTextUIInstance().feedback(Message.ERROR_INVALID_TASK);
				} catch (TaskNotFoundException e) {
					System.err.println("[Logic] Task not found");
					mAppInstance.getTextUIInstance().feedback(Message.ERROR_TASK_NOT_FOUND);
				}
				mAppInstance.getTextUIInstance().display(mTasks);
				return;
			}
		}
		mAppInstance.getTextUIInstance().feedback(Message.ERROR_COMMAND_NOT_RECOGNIZED);
	}
	
	public void echo(String s) {
		//mAppInstance.getTextUIInstance().print(s);
	}
	
	/**
	 * Reads the tasks from data file to mTasks
	 */
	private void readFile() {
		try {
			mTasks = (ArrayList) mAppInstance.getStorageInstance().readTasksFromDataFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes the list of tasks in mTasks to the data file
	 */
	private void writeFile() {
		try {
			mAppInstance.getStorageInstance().writeTasksToDataFile(mTasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the passed in Task object based on the parsed tokens
	 * 
	 * @param tokens
	 *            The tokens parsed from the command <b>including</b> the
	 *            command token itself but <b>excluding</b> any previously used
	 *            identifiers
	 * @throws InvalidTaskException
	 *             Thrown when the Task constructed from the parsed tokens is
	 *             invalid
	 */
	private void updateTask(ArrayList<Pair<Token, String>> tokens, Task task) throws InvalidTaskException {
		Iterator<Pair<Token, String>> iter = tokens.iterator();

		while (iter.hasNext()) {
			Pair<Token, String> pair = iter.next();
			switch (pair.getKey()) {
			case FLAG:
				String flag = pair.getValue();
				switch (flag) {
				// Flags which expect the next token to be a date
				case Parser.FLAG_END:
				case Parser.FLAG_START:
				    System.out.println("[Logic] Flag encountered: " + flag);
					if (iter.hasNext()) {
						Pair<Token, String> nextPair = iter.next();
						assert(nextPair.getKey() == Token.DATE || nextPair.getKey() == Token.DATE_INVALID);
						// Only set valid dates
						if (nextPair.getKey() == Token.DATE) {
							if (flag.compareTo(Parser.FLAG_END) == 0) {
								task.setEnd(nextPair.getValue());
							} else if (flag.compareTo(Parser.FLAG_START) == 0) {
								task.setStart(nextPair.getValue());
							}
						}
					}
					break;
				}
				break;
			case NAME:
				task.setName(pair.getValue());
				break;
			case ALPHA_NUM:
			case DATE:
			case DATE_INVALID:
			case FLAG_INVALID:
			case ID:
			case ID_INVALID:
			case RESERVED:
			case WHITESPACE:
				// Do nothing
				break;
			}
		}

		// Check if the constructed Task is valid
		task.isValid();
	}

	private Task addTask(ArrayList<Pair<Token, String>> tokens) throws InvalidTaskException {
		Task task = new Task();
		updateTask(tokens, task);
		mTasks.add(task);
		determineType(task);
		sortByDeadline();
		System.out.println("[Logic] Added task");
		System.out.print(task);
		return task;
	}

	private void editTask(ArrayList<Pair<Token, String>> tokens) throws TaskNotFoundException, InvalidTaskException {
		Iterator<Pair<Token, String>> iter = tokens.iterator();
		Task task = null;

		// First iteration to retrieve the corresponding Task object
		while (iter.hasNext() && task == null) {
			Pair<Token, String> pair = iter.next();
			switch (pair.getKey()) {
			case NAME:
			case ID:
				int index = pair.getKey() == Token.ID ? Integer.parseInt(pair.getValue())
						: getTaskIndexByName(pair.getValue());
				task = getTask(index);
				// Remove the first identifying name or ID such that future ones
				// will update the Task's name
				iter.remove();
				break;
			default:
				break;
			}
		}

		System.out.println("[Logic] Editing task");
		System.out.println("[Logic] Before");
		System.out.print(task);
		updateTask(tokens, task);
		determineType(task);
		sortByDeadline();
		System.out.println("[Logic] After");
		System.out.print(task);
		
	}

	/**
	 * Deletes the task with the specified ID or name (ID has greater precedence
	 * over name) from the master task list
	 *
	 * @param tokens
	 *            The tokens parsed from the command <b>including</b> the
	 *            command token itself
	 * @throws TaskNotFoundException
	 *             Thrown when the provided task name could not be found or ID
	 *             is out of bounds
	 */
	private void deleteTask(ArrayList<Pair<Token, String>> tokens) throws TaskNotFoundException {
		Iterator<Pair<Token, String>> iter = tokens.iterator();

		while (iter.hasNext()) {
			Pair<Token, String> pair = iter.next();
			switch (pair.getKey()) {
			case ALPHA_NUM:
			case DATE:
			case DATE_INVALID:
			case FLAG:
			case FLAG_INVALID:
				// Do nothing
				break;
			case ID_INVALID:
				// Do nothing
				break;
			case ID:
			case NAME:
				int index = pair.getKey() == Token.ID ? Integer.parseInt(pair.getValue())
						: getTaskIndexByName(pair.getValue());
				System.out.println("[Logic] Removing task");
				Task task = removeTask(index);
				System.out.print(task);
				return;
			case RESERVED:
			case WHITESPACE:
				// Do nothing
				break;
			}
		}
		
	}

	/**
	 * Retrieves the index of the first occurrence of a Task object with the
	 * name provided
	 * 
	 * @param name
	 *            The name of the Task object to find
	 * @return The index of the Task object or -1 if none is found
	 */
	private int getTaskIndexByName(String name) {
		for (int i = 0; i < mTasks.size(); i++) {
			if (mTasks.get(i).getName().compareTo(name) == 0) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Convenience method to retrieve a task with an index specified by
	 * non-sanitized user input or to be chained with the return value of
	 * {@link Logic#getTaskIndexByName(String)}. Throws an exception if the
	 * index is out of bounds
	 * 
	 * @param index
	 *            The index of the Task object to retrieve
	 * @return The Task object with the specified index
	 * @throws TaskNotFoundException
	 *             Thrown when the index specified is out of bounds
	 */
	private Task getTask(int index) throws TaskNotFoundException {
		if (index >= 0 && index < mTasks.size()) {
			return mTasks.get(index);
		} else {
			throw new TaskNotFoundException();
		}
	}

	/**
	 * Convenience method to remove a task with an index specified by
	 * non-sanitized user input or to be chained with the return value of
	 * {@link Logic#getTaskIndexByName(String)}. Throws an exception if the
	 * index is out of bounds
	 * 
	 * @param index
	 *            The index of the Task object to be removed
	 * @return The Task object that was removed
	 * @throws TaskNotFoundException
	 *             Thrown when the index specified is out of bounds
	 */
	private Task removeTask(int index) throws TaskNotFoundException {
		if (index >= 0 && index < mTasks.size()) {
			Task toRemove = mTasks.remove(index);
			sortByDeadline();
			return toRemove;
		} else {
			throw new TaskNotFoundException();
		}
	}
	
	
	//@@author A0133387B
	
	private Task archiveTask(int index) throws TaskNotFoundException {
		Task archivedTask = new Task();
		if (index >= 0 && index < mTasks.size()) {
			archivedTask = mTasks.get(index);
			archivedTask.setArchived("TRUE");
			return archivedTask;
		} else {
			throw new TaskNotFoundException();
		}
	}
	
	private Task retrieveTask(int index) throws TaskNotFoundException {
		Task retrievedTask = new Task();
		if (index >= 0 && index < mTasks.size()) {
			retrievedTask = mTasks.get(index);
			retrievedTask.setArchived("FALSE");
			return retrievedTask;
		} else {
			throw new TaskNotFoundException();
		}
	}
	
	/**
	 * This method sorts a list of tasks according to their deadlines(if any)
	 * The tasks with deadlines takes priority, followed by events sorted
	 * according to start time and floats to be added last, sorted by their
	 * names
	 * 
	 */
	private ArrayList<Task> sortByDeadline() {
		Collections.sort(mTasks, new Comparator<Task>() {
			public int compare(Task task1, Task task2) {
				if (task1.getType().equals(task2.getType())) {
					if (task1.getType().equals("DEADLINE")) {
						return task1.getEnd().compareTo(task2.getEnd());
					} else if (task1.getType().equals("EVENT")) {
						return task1.getStart().compareTo(task2.getStart());
					} else {
						return task1.getName().compareTo(task2.getName());
					}
				} else {
					return task1.getType().compareTo(task2.getType());
				}
			}
		});

		return mTasks;
	}
	
	
	private ArrayList<Task> search(String keyword) {
		ArrayList<Task> containKeyword = new ArrayList<Task>();
		for(int i= 0; i < mTasks.size(); i++){
			if(mTasks.get(i).getName().toLowerCase().contains(keyword.toLowerCase())){
				containKeyword.add(mTasks.get(i));
			}
		}

		return mTasks;
	}
	

	/**
	 * Determine the type of a task based on its start (if any) and end (if any)
	 * times
	 * 
	 * @param task
	 *            the new task to be categorized
	 */
	private void determineType(Task task) {
		if (task.getEnd() == null) {
			// if end == null, float
			task.setType("FLOAT");
		} else {
			if (task.getStart() != null) {
				// if end != null and start != null, event
				task.setType("EVENT");
			} else {
				// if end != null but start == null, deadline
				task.setType("DEADLINE");
			}
		}
	}

}
