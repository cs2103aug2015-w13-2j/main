package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Arrays;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class EditHandler extends CommandHandler {
    private static final String NAME = "Edit Task";
    private static final String SYNTAX = "<TASK_ID> [TASK_NAME] [-e DATETIME] [-s DATETIME]";
    private static final String[] FLAGS = { FLAG_START, FLAG_END };
    private static final String[] OPTIONS = { OPTION_TASK_ID, OPTION_TASK_NAME,
            OPTION_DATETIME };
    private static final String[] RESERVED = { "edit", "e" };
    private static final String EDIT_SUCCESS = "Task edited successfully.";

    public EditHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        Token id = command.getIdToken();
        try {
            Task task = logic.getTask(Integer.parseInt(id.value));
            updateTask(command, task);
            logic.feedback(new FeedbackMessage(EDIT_SUCCESS, FeedbackType.INFO));
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        } catch (InvalidTaskException e) {
            logic.feedback(FeedbackMessage.ERROR_INVALID_TASK);
        }
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
