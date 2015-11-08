package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0130894B

/**
 * Marks a Task indicated by the user as "Completed" or "Uncompleted". An index
 * that refers to that task, or indexes that refer to the multiple tasks to be
 * marked is expected to be specified.
 * 
 * A user feedback message will subsequently be returned upon marking the task.
 * If the Task index specified is out of range, or does not exist, a user error
 * message will be returned.
 * 
 * @author Natasha Koh Sze Sze
 */
public class MarkCompletedHandler extends CommandHandler {
    public static final String SET_COMPLETED_SUCCESS = "Completed task!";
    public static final String SET_UNCOMPLETED_SUCCESS = "Task has been set as uncompleted.";
    private static final Logger LOGGER = Logger
            .getLogger(MarkCompletedHandler.class.getName());
    private static final String NAME = "Mark Completed";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "mark", "done", "tick" };
    
    private Logic mLogic;
    
    public MarkCompletedHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        assert(logic != null);
        mLogic = logic;
        try {
            ArrayList<Integer> markIndexes = command.getAllIdTokenValues();
            assert(markIndexes != null);
            boolean noValidIndexFound = markIndexes.isEmpty();
            if (noValidIndexFound) {
                mLogic.feedback(FeedbackMessage.ERROR_INVALID_INDEX);
            } else {
                markAllSelectedTasks(markIndexes);
            }
        } catch (TaskNotFoundException e) {
            mLogic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    /**
     * Marks the list of {@link Task} objects associated with the list of 
     * indexes specified by the user as completed.
     * 
     * @param markIndexes
     *            The list of indexes that represent the items to 
     *            mark as completed.
     * @throws TaskNotFoundException
     *            Thrown when the provided index is out of bounds.
     */
    private void markAllSelectedTasks(ArrayList<Integer> markIndexes)
            throws TaskNotFoundException {
        for (Integer index : markIndexes) {
            Task task = mLogic.getTask(index);
            if (task.isCompleted()) {
                task.setCompleted(false);
                logCompletedTask(task);
                mLogic.feedback(new FeedbackMessage(SET_UNCOMPLETED_SUCCESS,
                        FeedbackType.INFO));
            } else {
                task.setCompleted(true);
                logCompletedTask(task);
                mLogic.feedback(new FeedbackMessage(SET_COMPLETED_SUCCESS,
                        FeedbackType.INFO));
            }
        }
        mLogic.clearRedoHistory();
        mLogic.storeCommandInHistory();
    }

    /**
     * Logs the name and date created of the Task object being processed.
     * 
     * @param task
     *          Task object to be logged.
     */
    private void logCompletedTask(Task task) {
        String nameOfTask = task.getName();
        LOGGER.info("[CommandHandler][MarkCompletedHandler] '" + nameOfTask
                + "' completed status is: " + task.isCompleted());
    }
}
