package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class DeleteHandler extends CommandHandler {
    private static String[] RESERVED = { "delete", "del", "remove", "rm" };
    private static String DELETE_SUCCESS = "Task deleted successfully.";
    
    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for(Pair<Token, String> pair : tokens) {
            if(pair.getKey() == Token.ID) {
                try {
                    Logic.getInstance().removeTask(Integer.parseInt(pair.getValue()));
                    return new FeedbackMessage(DELETE_SUCCESS, FeedbackType.INFO);
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
