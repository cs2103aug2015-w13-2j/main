package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * Deletes a task, or multiple tasks from the master task list. An index that
 * refers to that task, or indexes that refer to the multiple tasks to be
 * deleted is expected to be specified.
 * 
 * A user feedback message will subsequently be displayed to indicate that this
 * task was deleted successfully. If the Task index specified is out of range,
 * or does not exist, a user error message will be returned.
 */
public class DeleteHandler extends CommandHandler {
    public static final String DELETE_SUCCESS = "Task deleted successfully.";
    public static final String DELETE_FAILURE = "Task index entered is invalid. "
            + "Please provide a valid index!";
    private static final Logger LOGGER = Logger.getLogger(Logic.class.getName());
    private static final String NAME = "Delete Task";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {
            "TASK_ID: Index of the Task to be deleted as displayed in the ID column" };
    private static final String[] RESERVED = { "delete", "del", "remove",
            "rm" };
    private Logic mLogic;
    
    public DeleteHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }
    
    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }

    // @@author A0130894B
    
    @Override
    public void execute(Logic logic, Command command) {
        mLogic = logic;
        try {
            ArrayList<Integer> deleteIndexes = command.getAllIdTokenValues();
            boolean noValidIndexFound = deleteIndexes.isEmpty();
            if (noValidIndexFound) {
                mLogic.feedback(
                        new FeedbackMessage(DELETE_FAILURE, FeedbackType.ERROR));
            } else {
                deleteAllSelectedTasks(deleteIndexes);
                mLogic.feedback(
                        new FeedbackMessage(DELETE_SUCCESS, FeedbackType.INFO));
            }
        } catch (TaskNotFoundException e) {
            mLogic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    /**
     * Processes the {@link Task} objects to be deleted.
     * 
     * @param deleteIndexes
     *            The list of indexes that represent the item to delete
     * @throws TaskNotFoundException
     *            Thrown when the provided index is out of bounds.
     */
    private void deleteAllSelectedTasks(ArrayList<Integer> deleteIndexes) 
            throws TaskNotFoundException {
        ArrayList<Task> deleteTaskList = getAllTasksToDelete(deleteIndexes);
        removeSelectedTasks(deleteTaskList);
        mLogic.clearRedoHistory();
        mLogic.storeCommandInHistory();
    }
    
    /**
     * Retrieves the list of {@link Task} objects associated with the list of 
     * indexes specified by the user.
     * 
     * @param deleteIndexes
     *            The list of indexes that represent the item to delete
     * @return A list of {@link Task} objects that are to be deleted.
     * @throws TaskNotFoundException
     *            Thrown when the provided index is out of bounds.
     */
    private ArrayList<Task> getAllTasksToDelete(ArrayList<Integer> deleteIndexes)
            throws TaskNotFoundException {
        ArrayList<Task> deleteTaskList = new ArrayList<Task>();
        
        for (Integer index : deleteIndexes) {
            Task taskToDelete = mLogic.getTask(index);
            deleteTaskList.add(taskToDelete);
        }
        return deleteTaskList;
    }
    
    /**
     * Removes all {@link Task} objects specified in the list of {@link Task} 
     * objects to be deleted from the master task list.
     * 
     * @param deleteTaskList
     *            Contains the {@link Task}s to be removed. 
     * @throws TaskNotFoundException
     *            Thrown when the provided index is out of bounds.
     */
    private void removeSelectedTasks(ArrayList<Task> deleteTaskList) throws TaskNotFoundException {
        for (Task taskToDelete : deleteTaskList) {
            LOGGER.log(Level.INFO, "Deleting task: '" + taskToDelete.getName() 
                    + "' created on " + taskToDelete.getCreated());
            mLogic.removeTask(taskToDelete);
        }
    }
}
