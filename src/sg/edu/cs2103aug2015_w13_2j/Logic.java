package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.FormatterInterface.Format;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;

public class Logic implements LogicInterface {
	private FunDUE mAppInstance;
	private ArrayList<Task> mTasks = new ArrayList<Task>();

	public Logic(FunDUE appInstance) {
		mAppInstance = appInstance;
	}

	/*
	 * public void init() { try { String DATA_FILE_PATH =
	 * mAppInstance.getStorageInstance().readRawFile("DATA_FILE_PATH");
	 * List<Task> testing =
	 * mAppInstance.getStorageInstance().readFile(DATA_FILE_PATH); //
	 * checkStatus(); <<<<<<< HEAD
	 * mAppInstance.getStorageInstance().writeFile(mTasks, "output.txt");
	 * ======= mAppInstance.getStorageInstance().writeFile(tasks,
	 * "DATA_FILE_PATH"); >>>>>>> 2f2762fa379c83f29936541cc4744bd9db8a7932 }
	 * catch (Exception e) { e.printStackTrace(); } }
	 */
	public void executeCommand(ArrayList<Pair<Token, String>> tokens) {
		for (Pair<Token, String> pair : tokens) {
			if (pair.getKey() == Token.RESERVED) {
				try {
					switch (pair.getValue()) {
					case "add":
						Task task = addTask(tokens);
						mAppInstance.getFormatterInstance().format(task, Format.LIST);
						break;
					case "edit":
						editTask(tokens);
						break;
					case "list":
						mAppInstance.getFormatterInstance().format(mTasks, Format.LIST);
						break;
					case "delete":
						deleteTask(tokens);
						break;
					default:
						// TODO: Unimplemented command
						break;
					}
				} catch (InvalidTaskException e) {
					System.err.println("[Logic] Invalid Task");
					// TODO: Dispatch to error class
				} catch (TaskNotFoundException e) {
					System.err.println("[Logic] Task not found");
					// TODO: Dispatch to error class
				}
				return;
			}
		}
	}
	
	public void echo(String s) {
		mAppInstance.getFormatterInstance().passThrough(s);
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
	private Task updateTask(ArrayList<Pair<Token, String>> tokens, Task task) throws InvalidTaskException {
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
					if (iter.hasNext()) {
						Pair<Token, String> nextPair = iter.next();
						assert(nextPair.getKey() == Token.DATE || nextPair.getKey() == Token.DATE_INVALID);
						// Only set valid dates
						if (nextPair.getKey() == Token.DATE) {
							if (flag == Parser.FLAG_END) {
								task.setEnd(nextPair.getValue());
							} else if (flag == Parser.FLAG_START) {
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
		if (task.isValid()) {
			return task;
		} else {
			return null;
		}
	}

	private Task addTask(ArrayList<Pair<Token, String>> tokens) throws InvalidTaskException {
		Task task = updateTask(tokens, new Task());
		mTasks.add(task);
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
	 * This method updates the tasks list upon a new session It reads the file
	 * from the storage and fills up the internal ArrayList
	 * 
	 */

	/*
	public void readFile() {
		try {
			tasks = (ArrayList) mAppInstance.getStorageInstance().readFile("DATA_FILE_PATH");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

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
			return mTasks.remove(index);
		} else {
			throw new TaskNotFoundException();
		}
	}
}
