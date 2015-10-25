package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

/**
 * Unarchives a Task specified by the user.
 * 
 * This class includes an execution method that sets the "ARCHIVED" label of the
 * Task to false. A user feedback message will subsequently be returned upon
 * setting the label. If the Task index specified is out of range, or does not
 * exist, a user error message will be returned.
 * 
 * @author Natasha Koh Sze Sze
 */
public class RetrieveHandler extends CommandHandler {
    private static final Logger LOGGER = Logger.getLogger(RetrieveHandler.class
            .getName());
    private static final String NAME = "Retrieve Archived Task";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_TASK_ID };
    private static final String[] RESERVED = { "retrieve", "ret" };
    private static final String RETRIEVE_SUCCESS = "Task retrieved successfully.";

    public RetrieveHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        Token id = command.getIdToken();
        try {
            Task task = logic.getTask(Integer.parseInt(id.value));
            task.setArchived(false);
            logRetrievedTask(task);
            logic.feedback(new FeedbackMessage(RETRIEVE_SUCCESS,
                    FeedbackType.INFO));
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }

    private void logRetrievedTask(Task retrievedTask) {
        String nameOfArchivedTask = retrievedTask.getName();
        LOGGER.info("[CommandHandler][RetrieveHandler] '" + nameOfArchivedTask
                + "' archived status is: " + retrievedTask.isArchived());
    }
}
