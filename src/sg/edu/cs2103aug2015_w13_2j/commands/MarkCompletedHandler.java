package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class MarkCompletedHandler extends CommandHandler {
    private static final String[] RESERVED = { "mark", "done", "tick" };
    private static final String SET_COMPLETED_SUCCESS = 
            "Completed task!";
    private static final String SET_UNCOMPLETED_SUCCESS = 
            "Task has been set as uncompleted.";
    
    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ID) {
                try {
                    Task task = Logic.getInstance().getTask(
                            Integer.parseInt(pair.getValue()));
                    String isCompletedTaskIndicator = task.getCompleted();

                    switch (isCompletedTaskIndicator) {
                    case "TRUE":
                        task.setCompleted("FALSE");
                        return new FeedbackMessage(
                                SET_UNCOMPLETED_SUCCESS, FeedbackType.INFO);
                    case "FALSE":
                        task.setCompleted("TRUE");
                        return new FeedbackMessage(
                                SET_COMPLETED_SUCCESS, FeedbackType.INFO);
                    default:
                        // Do nothing
                        break;
                    }
                } catch (TaskNotFoundException e) {
                    return FeedbackMessage.getTaskNotFoundError();
                }
            }
        }
        return FeedbackMessage.getTaskNotFoundError();
    }
    
    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
