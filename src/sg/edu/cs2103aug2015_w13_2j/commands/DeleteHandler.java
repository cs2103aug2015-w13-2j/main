package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Arrays;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class DeleteHandler extends CommandHandler {
    private static String[] RESERVED = { "delete", "del", "remove", "rm" };
    private static String DELETE_SUCCESS = "Task deleted successfully.";

    @Override
    public FeedbackMessage execute(Logic logic, Command command) {
        Token id = command.getIdToken();
        try {
            logic.removeTask(Integer.parseInt(id.value));
            return new FeedbackMessage(DELETE_SUCCESS, FeedbackType.INFO);
        } catch (TaskNotFoundException e) {
            return FeedbackMessage.ERROR_TASK_NOT_FOUND;
        }
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
