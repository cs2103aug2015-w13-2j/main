package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * Adds a task to the master task list.
 * A task name and optional flags/options for that task will be specified.
 * 
 * A user feedback message will subsequently be displayed to indicate that 
 * this task was added successfully. If no valid task name was specified 
 * in the user command, a user error message will be returned.
 * 
 * @author Zhu Chunqi
 */
public class AddHandler extends CommandHandler {
    private static final String NAME = "Add Task";
    private static final String SYNTAX = "<TASK_NAME> [-e DATETIME] [-s DATETIME]";
    private static final String[] FLAGS = { FLAG_START, FLAG_END };
    private static final String[] OPTIONS = { OPTION_TASK_NAME, OPTION_DATETIME };
    private static final String[] RESERVED = { "add", "a" };
    private static final String ADD_SUCCESS = "Task added successfully.";

    public AddHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        Task task = new Task();
        try {
            updateTask(command, task);
            logic.addTask(task);
            logic.clearRedoHistory();
            logic.storeCommandInHistory();
            logic.feedback(new FeedbackMessage(ADD_SUCCESS, FeedbackType.INFO));
        } catch (InvalidTaskException e) {
            logic.feedback(FeedbackMessage.ERROR_INVALID_TASK);
        }
    }
}
