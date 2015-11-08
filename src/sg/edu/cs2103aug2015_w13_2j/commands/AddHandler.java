package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * {@link CommandHandler} which handles adding a {@link Task} object to the
 * master list of {@link Task} objects. A name for the {@link Task} is required
 * and optionally the start and end dates can be specified.<br>
 * <br>
 * User feedback {@value #ADD_SUCCESS} will be displayed to indicate that the
 * task was added successfully. If no task name was specified, the
 * {@link FeedbackMessage#ERROR_INVALID_TASK} error message will be shown.
 * 
 * @author Zhu Chunqi
 */
public class AddHandler extends CommandHandler {
    public static final String ADD_SUCCESS = "Task added successfully.";

    private static final String NAME = "Add Task";
    private static final String SYNTAX = "<TASK_NAME> [-e DATETIME] [-s DATETIME]";
    private static final String[] FLAGS = { FLAG_START, FLAG_END };
    private static final String[] OPTIONS = { OPTION_TASK_NAME, OPTION_DATETIME };
    private static final String[] RESERVED = { "add", "a" };

    public AddHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(LogicInterface logic, Command command) {
        Task task = new Task();
        try {
            updateTask(command, task);
            assert (task.isValid());
            logic.addTask(task);
            logic.clearRedoHistory();
            logic.storeCommandInHistory();
            logic.feedback(new FeedbackMessage(ADD_SUCCESS, FeedbackType.INFO));
        } catch (InvalidTaskException e) {
            logic.feedback(FeedbackMessage.ERROR_INVALID_TASK);
        }
    }
}
