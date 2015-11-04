package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * Deletes a task, or multiple tasks from the master task list. An index that
 * refers to that task, or indexes that refer to the multiple tasks to be
 * deleted is expected to be specified.
 * 
 * A user feedback message will subsequently be displayed to indicate that this
 * task was deleted successfully. If the Task index specified is out of range,
 * or does not exist, a user error message will be returned.
 * 
 * @author Zhu Chunqi
 */
public class DeleteHandler extends CommandHandler {
    private static final String NAME = "Delete Task";
    private static final String SYNTAX = "<TASK_ID>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {
            "TASK_ID: Index of the Task to be deleted as displayed in the ID column" };
    private static final String[] RESERVED = { "delete", "del", "remove",
            "rm" };
    private static final String DELETE_SUCCESS = "Task deleted successfully.";

    public DeleteHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        try {
            ArrayList<Integer> markIndexes = command.getAllIdTokenValues();
            // Orders in descending order so task removal is independent of each
            // other
            Collections.sort(markIndexes, Collections.reverseOrder());
            for (Integer index : markIndexes) {
                logic.removeTask(index);
            }
            logic.clearRedoHistory();
            logic.storeCommandInHistory();
            logic.feedback(
                    new FeedbackMessage(DELETE_SUCCESS, FeedbackType.INFO));
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        }
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
