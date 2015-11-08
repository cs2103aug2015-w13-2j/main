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
 * 
 * @author Zhu Chunqi
 */
public class DeleteHandler extends CommandHandler {
    private static final Logger LOGGER = Logger.getLogger(Logic.class.getName());
    private static final String NAME = "Delete Task";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {
            "TASK_ID: Index of the Task to be deleted as displayed in the ID column" };
    private static final String[] RESERVED = { "delete", "del", "remove",
            "rm" };
    public static final String DELETE_SUCCESS = "Task deleted successfully.";
    
    public DeleteHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        try {
            ArrayList<Integer> deleteIndexes = command.getAllIdTokenValues();
            ArrayList<Task> deleteTaskList = getAllTasksToDelete(logic, deleteIndexes);
            removeSelectedTasks(logic, deleteTaskList);
            logic.clearRedoHistory();
            logic.storeCommandInHistory();
            logic.feedback(
                    new FeedbackMessage(DELETE_SUCCESS, FeedbackType.INFO));
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
    
    /**
     * Retrieves the list of {@link Task} objects associated with the list of 
     * indexes specified by the user.
     * 
     * @param logic
     *            The component involved in removing Task objects from the 
     *            master {@link Task} list.
     * @param deleteIndexes
     *            The list of indexes that represent the item to delete
     * @return A list of {@link Task} objects that are to be deleted.
     * @throws TaskNotFoundException
     *            Thrown when the provided index is out of bounds.
     */
    private ArrayList<Task> getAllTasksToDelete(Logic logic, ArrayList<Integer> deleteIndexes)
            throws TaskNotFoundException {
        ArrayList<Task> deleteTaskList = new ArrayList<Task>();
        
        for (Integer index : deleteIndexes) {
            Task taskToDelete = logic.getTask(index);
            deleteTaskList.add(taskToDelete);
        }
        return deleteTaskList;
    }
    
    /**
     * Removes all {@link Task} objects specified in the list of {@link Task} 
     * objects to be deleted from the master task list.
     * 
     * @param logic
     *            The component involved in removing Task objects from the 
     *            master {@link Task} list.
     * @param deleteTaskList
     *            Contains the {@link Task}s to be removed. 
     * @throws TaskNotFoundException
     *            Thrown when the provided index is out of bounds.
     */
    private void removeSelectedTasks(Logic logic, ArrayList<Task> deleteTaskList) throws TaskNotFoundException {
        for (Task taskToDelete : deleteTaskList) {
            LOGGER.log(Level.INFO, "Deleting task: '" + taskToDelete.getName() 
                    + "' created on " + taskToDelete.getCreated());
            logic.removeTask(taskToDelete);
        }
    }
}
