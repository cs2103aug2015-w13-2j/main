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

public class MarkImportantHandler extends CommandHandler {
    private static final Logger LOGGER = Logger
            .getLogger(MarkImportantHandler.class.getName());
    private static final String[] RESERVED = { "important", "impt", "!" };
    private static final String SET_IMPORTANT_SUCCESS = "Task has been set as important.";
    private static final String SET_UNIMPORTANT_SUCCESS = "Task has been set as unimportant.";

    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ID) {
                try {
                    Task task = Logic.getInstance().getTask(
                            Integer.parseInt(pair.getValue()));
                    if (task.isImportant()) {
                        task.setImportant(false);
                        logImportantTask(task);
                        return new FeedbackMessage(SET_UNIMPORTANT_SUCCESS,
                                FeedbackType.INFO);
                    } else {
                        task.setImportant(true);
                        logImportantTask(task);
                        return new FeedbackMessage(SET_IMPORTANT_SUCCESS,
                                FeedbackType.INFO);
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
    
    private void logImportantTask(Task task) {
        String nameOfTask = task.getName();

        LOGGER.info("[CommandHandler][MarkImportantHandler] '" + nameOfTask
                + "' importance status is: " + task.isImportant());
    }
}
