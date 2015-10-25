package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Arrays;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

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
    public FeedbackMessage execute(Logic logic, Command command) {
        Task task = new Task();
        try {
            updateTask(command, task);
        } catch (InvalidTaskException e) {
            return FeedbackMessage.ERROR_INVALID_TASK;
        }
        logic.addTask(task);

        return new FeedbackMessage(ADD_SUCCESS, FeedbackType.INFO);
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
