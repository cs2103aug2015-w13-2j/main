package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class EditHandler extends CommandHandler {
    private static String[] RESERVED = { "edit", "e" };
    private static String EDIT_SUCCESS = "Task edited successfully.";
    private static String EDIT_FAILURE = 
            "Option not recognized. Did you enter the flag correctly?";

    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ID) {
                try {
                    Task task = Logic.getInstance().getTask(
                            Integer.parseInt(pair.getValue()));
                    updateTask(tokens, task);
                    return new FeedbackMessage(EDIT_SUCCESS, FeedbackType.INFO);
                } catch (TaskNotFoundException e) {
                    return FeedbackMessage.getTaskNotFoundError();
                } catch (InvalidTaskException e) {
                    return FeedbackMessage.getInvalidTaskError();
                }
            } else if (pair.getKey() == Token.ID_INVALID) {
                return new FeedbackMessage(EDIT_FAILURE, FeedbackType.ERROR);
            }
        }
        return FeedbackMessage.getTaskNotFoundError();
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }

}
