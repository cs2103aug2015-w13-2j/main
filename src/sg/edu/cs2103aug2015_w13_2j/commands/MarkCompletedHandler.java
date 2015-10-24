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

// @@author A0130894B

/**
 * Marks a Task indicated by the user as "Completed" and archives it. For a Task
 * that has been marked as "Completed", it will be marked as "Uncompleted" and
 * be unarchived.
 * 
 * A user feedback message will subsequently be returned upon marking the task
 * as "Completed" or "Uncompleted". If the Task index specified is out of range,
 * or does not exist, a user error message will be returned.
 * 
 * @author Natasha Koh Sze Sze
 */
public class MarkCompletedHandler extends CommandHandler {
    private static final Logger LOGGER = Logger
            .getLogger(MarkCompletedHandler.class.getName());
    private static final String[] RESERVED = { "mark", "done", "tick" };
    private static final String SET_COMPLETED_SUCCESS = "Completed task!";
    private static final String SET_UNCOMPLETED_SUCCESS = "Task has been set as uncompleted.";

    @Override
    public FeedbackMessage execute(Logic logic,
            ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ID) {
                try {
                    Task task = logic
                            .getTask(Integer.parseInt(pair.getValue()));
                    if (task.isCompleted()) {
                        task.setCompleted(false);
                        task.setArchived(false);
                        logCompletedArchivedTask(task);
                        return new FeedbackMessage(SET_UNCOMPLETED_SUCCESS,
                                FeedbackType.INFO);
                    } else {
                        task.setCompleted(true);
                        task.setArchived(true);
                        logCompletedArchivedTask(task);
                        return new FeedbackMessage(SET_COMPLETED_SUCCESS,
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

    private void logCompletedArchivedTask(Task task) {
        String nameOfTask = task.getName();

        LOGGER.info("[CommandHandler][MarkCompletedHandler] '" + nameOfTask
                + "' completed status is: " + task.isCompleted()
                + " archived status is: " + task.isArchived());
    }
}
