package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class AddHandler extends CommandHandler {
    private static final String[] RESERVED = { "add", "a" };
    private static final String ADD_SUCCESS = "Task added successfully.";

    @Override
    public FeedbackMessage execute(Logic logic,
            ArrayList<Pair<Token, String>> tokens) {
        Task task = new Task();
        try {
            updateTask(tokens, task);
        } catch (InvalidTaskException e) {
            return FeedbackMessage.getInvalidTaskError();
        }
        logic.addTask(task);

        return new FeedbackMessage(ADD_SUCCESS, FeedbackType.INFO);
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
