package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * Edits a task in the master task list. An index that refers to the task to be
 * edited is expected to be specified. A task name and optional flags/options
 * that need to be edited for that task will also be specified.
 * 
 * A user feedback message will subsequently be displayed to indicate that this
 * task was edited successfully. If no valid task name was specified in the user
 * command, a user error message will be returned. If the Task index specified
 * is out of range, or does not exist, a user error message will be returned.
 * 
 * @author Zhu Chunqi
 */
public class EditHandler extends CommandHandler {
    private static final String NAME = "Edit Task";
    private static final String SYNTAX = "<TASK_ID> [TASK_NAME] [-e DATETIME] [-s DATETIME]";
    private static final String[] FLAGS = { FLAG_START, FLAG_END };
    private static final String[] OPTIONS = { OPTION_TASK_ID, OPTION_TASK_NAME,
            OPTION_DATETIME };
    private static final String[] RESERVED = { "edit", "e" };
    public static final String EDIT_SUCCESS = "Task edited successfully.";

    public EditHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        Token id = command.getIdToken();
        try {
            Task task = logic.getTask(Integer.parseInt(id.value));
            updateTask(command, task);
            logic.storeCommandInHistory();
            logic.clearRedoHistory();
            logic.feedback(
                    new FeedbackMessage(EDIT_SUCCESS, FeedbackType.INFO));
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        } catch (InvalidTaskException e) {
            logic.feedback(FeedbackMessage.ERROR_INVALID_TASK);
        }
    }
}
