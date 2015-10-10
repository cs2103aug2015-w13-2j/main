package sg.edu.cs2103aug2015_w13_2j;

/**
This class implements methods from LogicInterface
@@author A0133387B 

 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;

public class Logic implements LogicInterface {
	private FunDUE mAppInstance;

	// This ArrayList includes all added tasks, excluding deleted ones
	private ArrayList<Task> tasks = new ArrayList<Task>();

	/**
	 * Constructor for the Logic component
	 * 
	 */
	public Logic(FunDUE appInstance) {
		mAppInstance = appInstance;
	}

	public void init() {
		try {
			String DATA_FILE_PATH = mAppInstance.getStorageInstance().readRawFile("DATA_FILE_PATH");
			List<Task> testing = mAppInstance.getStorageInstance().readFile(DATA_FILE_PATH);
			// checkStatus();
			mAppInstance.getStorageInstance().writeFile(tasks, "output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeCommand(Vector<Pair<Token, String>> tokens){
		
	}
	
	/**
	 * This method returns all the tasks inside the to-do list, excluding only deleted tasks 
	 */

	public ArrayList<Task> getAllTasks() {
		return tasks;
	}
	
	/**
	 * Get a task based on the index of the main arrayList
	 * @param index
     *            the index of the task required
	 */
	
	public Task getTask(int index) {
		return tasks.get(index);
	}

	 /**
     * Find a task based on name
     * @param name
     *            the name being searched for 
     * @return 
     *        the Task with the name requested if there is only one task with that name, or 
     *        a list of tasks with the same name
     */
    public ArrayList<Task> findTaskByName(String name){
    	ArrayList<Task> userView = new ArrayList<Task>();
    	for(int i = 0; i < tasks.size(); i++){
    		if(tasks.get(i).getName().equals(name)){
    			userView.add(tasks.get(i));
    		}
    	}
    	
       return userView;
    }
	
	/**
	 * Add a new task to the main arrayList and also determine the type of the task
	 * 
	 * @param task
	 *            the new task to be added
	 * 
	 */
	public void addTask(Task task) {
		tasks.add(task);

		determineType(task);

		System.out.println("Added " + task.getName());
		System.out.println(mAppInstance.getFormatterInstance());
		System.out.println(FormatterInterface.Format.LIST);
		mAppInstance.getFormatterInstance().format(task, FormatterInterface.Format.LIST);

		try {
			mAppInstance.getStorageInstance().writeFile(tasks, "output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Determine the type of a task based on its start (if any) and end (if any)
	 * times
	 * 
	 * @param task
	 *            the new task to be categorized
	 */
	public void determineType(Task task) {
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


	/**
	 * Delete a task
	 * 
	 * @param task
	 *            the task to be deleted
	 * 
	 */
	public Task deleteTask(ArrayList<Task> currentList, int taskIndex) {
        Task removedTask = currentList.remove(taskIndex);
    	
    	if(!currentList.equals(tasks)){
    		tasks.remove(removedTask);
    	}
		mAppInstance.getFormatterInstance().format(removedTask, FormatterInterface.Format.LIST); 
		try {
			mAppInstance.getStorageInstance().writeFile(tasks, "output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return removedTask;
	}

	/**
	 * Archive a task
	 * 
	 * @param task
	 *            the task to be archived
	 * 
	 */

	public void archiveTask(ArrayList<Task> currentList, int taskIndex){
    	Task archivedTask = currentList.get(taskIndex);
    	archivedTask.setArchived("TRUE");
		mAppInstance.getFormatterInstance().format(archivedTask, FormatterInterface.Format.LIST); 
		try {
			mAppInstance.getStorageInstance().writeFile(tasks, "output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void markTaskCompleted(ArrayList<Task> currentList, int taskIndex){
    	Task task = currentList.get(taskIndex);
    	task.setCompleted("TRUE");
		mAppInstance.getFormatterInstance().format(task, FormatterInterface.Format.LIST); 
		try {
			mAppInstance.getStorageInstance().writeFile(tasks, "output.txt");
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * public void checkStatus(){ Date date = new Date(); for(int i = 0; i <
	 * tasks.size(); i++){ if(tasks.get(i).getEnd() != null &&
	 * tasks.get(i).getEnd().compareTo(date) < 0){ userView.add(tasks.get(i)); }
	 * } }
	 * 
	 */

	public ArrayList<Task> list() {
		// checkStatus();
		ArrayList<Task> userView = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getArchived().equals("FALSE")) {
				userView.add(tasks.get(i));
			}
		}
		mAppInstance.getFormatterInstance().format(userView, FormatterInterface.Format.LIST); 
		return userView;
	}

	/**
	 * This method sorts a list of tasks according to their deadlines(if any)
	 * The tasks with deadlines takes priority, followed by events sorted
	 * according to start time and floats to be added last, sorted by their
	 * names
	 * 
	 */

	public ArrayList<Task> sortByDeadline() {
		ArrayList<Task> list = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			list.add(tasks.get(i));
		}
		Collections.sort(list, new Comparator<Task>() {
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
		mAppInstance.getFormatterInstance().format(list, FormatterInterface.Format.LIST); // TODO:
		// to be
		// updated
		return list;
	}

	/**
	 * This method merges the details of a new task and the original task by
	 * checking the fields of the new task Those that are blank are kept
	 * unchanged in the original task While the fields that are not null in the
	 * new task will be updated in the original task
	 * 
	 * @param original
	 *            the task that is being updated
	 * @param newTask
	 *            the task that is updating the original task
	 * @return the updated original task
	 */

	public Task mergeDetails(Task original, Task newTask) {
		// possible fields to update: name, start, end
		if (newTask.getName() != null) {
			original.setName(newTask.getName());
		}

		if (newTask.getEnd() != null) {
			original.setEnd(newTask.getEnd());
		}

		if (newTask.getStart() != null) {
			original.setStart(newTask.getStart());
		}

		if (newTask.getCompleted().equals("TRUE")) {
			original.setCompleted("TRUE");
		}


		if (newTask.getArchived().equals("TRUE")) {
			original.setArchived("TRUE");
		}

		return original;
	}

	/**
	 * This method edits a task according to the fields specified by the user It
	 * takes in a task formed by the name input by user, as well as the fields
	 * to changed It then updates the task identified by the name field in the
	 * method signature the updating is taken care of by the method mergeDetails
	 * Changes in the type of the task due to changes in the start and end time
	 * or status are also updated This information is updated manually by
	 * checking the changes (if any) in existence of start and deadline
	 * 
	 * @param the
	 *            name of the Task user wishes to edit
	 * @param the
	 *            new Task object formed by the requested changes
	 * @return the updated task
	 * 
	 */
	public Task editTask(Task original, Task edittingTask) {
	
		// potential edits to the type of task

		if (original.getStart() == null && original.getEnd() == null) {// originally
																		// float
			if (edittingTask.getStart() != null && edittingTask.getEnd() != null) {// edited to
																	// events
				original.setType("EVENT");
			}

			if (edittingTask.getStart() == null && edittingTask.getEnd() != null) {// edited to
																	// deadline
																	// tasks
				original.setType("DEADLINE");
			}
		} else if (original.getStart() != null && original.getEnd() != null) {// originally
																				// event
			if (edittingTask.getEnd() == null) {// change to float
				original.setType("FLOAT");

			} else if (edittingTask.getStart() == null) {// change to task with deadline
				original.setType("DEADLINE");
			}
		} else if (original.getStart() == null && original.getEnd() != null) {// originally
																				// task
																				// with
																				// deadline
			if (edittingTask.getEnd() == null) {// change to float
				original.setType("FLOAT");

			} else if (edittingTask.getStart() != null && edittingTask.getEnd() != null) {// edited
																			// to
																			// event
				original.setType("EVENT");

			}
		}

		mergeDetails(original, edittingTask);
		mAppInstance.getFormatterInstance().format(original, FormatterInterface.Format.LIST); 
		try {
			mAppInstance.getStorageInstance().writeFile(tasks, "output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return original;
	}

	/**
	 * This method updates the tasks list upon a new session It reads the file
	 * from the storage and fills up the internal ArrayList
	 * 
	 */

	public void readFile() {
		try {
			tasks = (ArrayList) mAppInstance.getStorageInstance().readFile("output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void echo(String s) {
		mAppInstance.getFormatterInstance().passThrough(s);
	}

   

	/*
	 * public static void main(String[] args) { Logic logic = new Logic(); Task
	 * task = new Task("first test task"); logic.addTask(task);
	 * 
	 * System.out.println(task.getType());
	 * 
	 * task.setStart(new Date(new Long("23456")));// setStart seems to have //
	 * some bug logic.determineType(task); System.out.println(task.getType());
	 * System.out.println("Start = " + task.getStart());
	 * 
	 * task.setEnd(new Date()); logic.determineType(task);
	 * System.out.println(task.getType()); System.out.println("Deadline = " +
	 * task.getEnd());
	 * 
	 * Task newTask = new Task("first test task"); newTask.setEnd(new Date());
	 * logic.determineType(newTask); logic.editTask("first test task", newTask);
	 * System.out.println(newTask.getType());
	 * System.out.println(task.getType()); // logic.addTask(new Task(
	 * "second test task")); // System.out.println("First task was created at "
	 * + // logic.findTaskByName("first test task").getCreated()); }
	 */
}
