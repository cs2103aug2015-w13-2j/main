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
 * Toggles a Task specified by the user as "Important" or "Unimportant". An
 * index that refers to that task, or indexes that refer to the multiple tasks
 * to be marked is expected to be specified.
 * 
 * A user feedback message will subsequently be returned upon marking the task.
 * If the Task index specified is out of range, or does not exist, a user error
 * message will be returned.
 * 
 * @author Natasha Koh Sze Sze
 */
public class MarkImportantHandler extends CommandHandler {
    public static final String SET_IMPORTANT_SUCCESS = "Task has been set as important.";
    public static final String SET_UNIMPORTANT_SUCCESS = "Task has been set as unimportant.";
    public static final String IMPORTANT_SET_FAILURE = "Task index entered is invalid. "
            + "Please provide a valid index!";
    private static final Logger LOGGER = Logger
            .getLogger(MarkImportantHandler.class.getName());
    private static final String NAME = "Mark Important";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_TASK_ID };
    private static final String[] RESERVED = { "i", "impt", "!" };
    private Logic mLogic;
    
    public MarkImportantHandler() {
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
                mLogic.feedback(
                        new FeedbackMessage(IMPORTANT_SET_FAILURE, FeedbackType.ERROR));
            } else {
                markAllSelectedTasks(markIndexes);
            }
        } catch (TaskNotFoundException e) {
            mLogic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    /**
     * Marks the list of {@link Task} objects associated with the list of 
     * indexes specified by the user as important.
     * 
     * @param markIndexes
     *            The list of indexes that represent the items to 
     *            mark as completed.
     * @throws TaskNotFoundException
     *            Thrown when the provided index is out of bounds.
     */
    private void markAllSelectedTasks(ArrayList<Integer> markIndexes) throws TaskNotFoundException {
        for (Integer index : markIndexes) {
            Task task = mLogic.getTask(index);
            if (task.isImportant()) {
                task.setImportant(false);
                logImportantTask(task);
                mLogic.feedback(new FeedbackMessage(SET_UNIMPORTANT_SUCCESS,
                        FeedbackType.INFO));
            } else {
                task.setImportant(true);
                logImportantTask(task);
                mLogic.feedback(new FeedbackMessage(SET_IMPORTANT_SUCCESS,
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
    private void logImportantTask(Task task) {
        String nameOfTask = task.getName();
        LOGGER.info("[CommandHandler][MarkImportantHandler] '" + nameOfTask
                + "' importance status is: " + task.isImportant());
    }
}
