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
    private static final Logger LOGGER = Logger
            .getLogger(MarkImportantHandler.class.getName());
    private static final String NAME = "Mark Important";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_TASK_ID };
    private static final String[] RESERVED = { "i", "impt", "!" };
    private static final String SET_IMPORTANT_SUCCESS = "Task has been set as important.";
    private static final String SET_UNIMPORTANT_SUCCESS = "Task has been set as unimportant.";

    public MarkImportantHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        try {
            ArrayList<Integer> markIndexes = command.getAllIdTokenValues();
            for (Integer index : markIndexes) {
                Task task = logic.getTask(index);
                if (task.isImportant()) {
                    task.setImportant(false);
                    logImportantTask(task);
                    logic.feedback(new FeedbackMessage(SET_UNIMPORTANT_SUCCESS,
                            FeedbackType.INFO));
                } else {
                    task.setImportant(true);
                    logImportantTask(task);
                    logic.feedback(new FeedbackMessage(SET_IMPORTANT_SUCCESS,
                            FeedbackType.INFO));
                }
            }
            logic.clearRedoHistory();
            logic.storeCommandInHistory();
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    private void logImportantTask(Task task) {
        String nameOfTask = task.getName();
        LOGGER.info("[CommandHandler][MarkImportantHandler] '" + nameOfTask
                + "' importance status is: " + task.isImportant());
    }
}
