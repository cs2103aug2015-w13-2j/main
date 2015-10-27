package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

/**
 * Unarchives a Task specified by the user.
 * 
 * This class includes an execution method that sets the "ARCHIVED" label of the
 * Task to false. A user feedback message will subsequently be returned upon
 * setting the label. If the Task index specified is out of range, or does not
 * exist, a user error message will be returned.
 * 
 * @author Natasha Koh Sze Sze
 */
public class UnarchiveHandler extends CommandHandler {
    private static final Logger LOGGER = Logger
            .getLogger(UnarchiveHandler.class.getName());
    private static final String NAME = "Retrieve Archived Task";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_TASK_ID };
    private static final String[] RESERVED = { "unarchive", "unar" };
    private static final String RETRIEVE_SUCCESS = "Task unarchived successfully.";

    public UnarchiveHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        try {
            ArrayList<Integer> unarchiveIndexes = command.getAllIdTokenValues();
            for (Integer index : unarchiveIndexes) {
                Task task = logic.getTask(index);
                task.setArchived(false);
                logUnarchivedTask(task);
            }
            logic.feedback(new FeedbackMessage(RETRIEVE_SUCCESS,
                    FeedbackType.INFO));
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    private void logUnarchivedTask(Task retrievedTask) {
        String nameOfArchivedTask = retrievedTask.getName();
        LOGGER.info("[CommandHandler][RetrieveHandler] '" + nameOfArchivedTask
                + "' archived status is: " + retrievedTask.isArchived());
    }
}
