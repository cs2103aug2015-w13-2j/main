package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

//@@author A0130894B-unused

//Unused as team decided to do away with Archive and Unarchive features 
//as they, in fact, complicate the task management process for the user.

/**
 * Archives a Task specified by the user.
 * 
 * This class includes an execution method that sets the "ARCHIVED" label of the
 * Task to true. A user feedback message will subsequently be returned upon
 * setting the label. If the Task index specified is out of range, or does not
 * exist, a user error message will be returned.
 * 
 * @author Natasha Koh Sze Sze
 */
public class ArchiveHandler extends CommandHandler {
    private static final Logger LOGGER = Logger.getLogger(ArchiveHandler.class
            .getName());
    private static final String NAME = "Archive Task";
    private static final String SYNTAX = "";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "archive", "ar" };
    private static final String ARCHIVE_SUCCESS = "Task archived successfully.";

    public ArchiveHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        try {
            ArrayList<Integer> archiveIndexes = command.getAllIdTokenValues();
            for (Integer index : archiveIndexes) {
                Task task = logic.getTask(index);
                task.setArchived(true);
                logic.storeCommandInHistory();
                logArchivedTask(task);
            }
            logic.feedback(new FeedbackMessage(ARCHIVE_SUCCESS,
                    FeedbackType.INFO));
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    private void logArchivedTask(Task archivedTask) {
        String nameOfArchivedTask = archivedTask.getName();
        LOGGER.info("[CommandHandler][ArchiveHandler] '" + nameOfArchivedTask
                + "' archived status is: " + archivedTask.isArchived());
    }
}
