package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class RetrieveHandler extends CommandHandler {
    private static final Logger LOGGER = Logger
            .getLogger(SearchHandler.class.getName());
    private static final String[] RESERVED = { "retrieve", "ret" };
    private static final String RETRIEVE_SUCCESS = "Task retrieved successfully.";
    
    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ID) {
                try {
                    Task task = Logic.getInstance().getTask(
                            Integer.parseInt(pair.getValue()));
                    task.setArchived("FALSE");
                    logRetrievedTask(task);
                    return new FeedbackMessage(RETRIEVE_SUCCESS, FeedbackType.INFO);
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
    
    private void logRetrievedTask(Task retrievedTask) {
        String nameOfArchivedTask = retrievedTask.getName();
        
        LOGGER.info("[CommandHandler][RetrieveHandler] '" + nameOfArchivedTask 
                    + "' archived status is: " + retrievedTask.getArchived());
    }
}
