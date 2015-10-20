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

public class MarkImportantHandler extends CommandHandler {
    private static final String[] RESERVED = { "important", "impt", "!" };
    private static final String SET_IMPORTANT_SUCCESS = 
            "Task has been set as important.";
    private static final String SET_UNIMPORTANT_SUCCESS = 
            "Task has been set as unimportant.";
    
    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ID) {
                try {
                    Task task = Logic.getInstance().getTask(
                            Integer.parseInt(pair.getValue()));
                    String isImportantTaskIndicator = task.getImportant();
                    System.out.println("is Impt?" + isImportantTaskIndicator);
                    switch (isImportantTaskIndicator) {
                    case "TRUE":
                        task.setImportant("FALSE");
                        return new FeedbackMessage(
                                SET_UNIMPORTANT_SUCCESS, FeedbackType.INFO);
                    case "FALSE":
                        task.setImportant("TRUE");
                        return new FeedbackMessage(
                                SET_IMPORTANT_SUCCESS, FeedbackType.INFO);
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
