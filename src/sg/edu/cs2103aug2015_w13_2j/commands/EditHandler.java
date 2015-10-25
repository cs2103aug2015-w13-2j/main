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
    private static String[] RESERVED = { "edit", "e" };
    private static String EDIT_SUCCESS = "Task edited successfully.";

    @Override
    public FeedbackMessage execute(Logic logic, Command command) {
        Token id = command.getIdToken();
        try {
            Task task = logic.getTask(Integer.parseInt(id.value));
            updateTask(command, task);
            return new FeedbackMessage(EDIT_SUCCESS, FeedbackType.INFO);
        } catch (TaskNotFoundException e) {
            return FeedbackMessage.ERROR_TASK_NOT_FOUND;
        } catch (InvalidTaskException e) {
            return FeedbackMessage.ERROR_INVALID_TASK;
        }
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
