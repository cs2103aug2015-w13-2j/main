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

public class ArchiveHandler extends CommandHandler {
    private static final Logger LOGGER = Logger
            .getLogger(SearchHandler.class.getName());
    private static final String[] RESERVED = { "archive", "ar" };
    private static final String ARCHIVE_SUCCESS = "Task archived successfully.";
    
    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ID) {
                try {
                    Task task = Logic.getInstance().getTask(
                            Integer.parseInt(pair.getValue()));
                    task.setArchived("TRUE");
                    logArchivedTask(task);
                    return new FeedbackMessage(ARCHIVE_SUCCESS, FeedbackType.INFO);
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
    
    private void logArchivedTask(Task archivedTask) {
        String nameOfArchivedTask = archivedTask.getName();
        
        LOGGER.info("[CommandHandler][ArchiveHandler] '" + nameOfArchivedTask 
                    + "' archived status is: " + archivedTask.getArchived());
    }
}
