package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * {@link CommandHandler} which handles editing the details of a {@link Task}
 * object in the master list of {@link Task} objects. An index referring to the
 * {@link Task} object to be edited must be specified. Optionally, a new task
 * name, start date or end date can be specified.
 * 
 * User feedback {@value #EDIT_SUCCESS} will be displayed to indicate that the
 * {@link Task} object was edited successfully. If the index specified is out of
 * range, the {@link FeedbackMessage#ERROR_TASK_NOT_FOUND} error message will be
 * shown. If the {@link Task} object becomes invalid after the edit, the
 * {@link FeedbackMessage#ERROR_INVALID_TASK} error message will be shown.
 * 
 * @author Zhu Chunqi
 */
public class EditHandler extends CommandHandler {
    public static final String EDIT_SUCCESS = "Task edited successfully.";

    private static final String NAME = "Edit Task";
    private static final String SYNTAX = "<TASK_ID> [TASK_NAME] [-e DATETIME] [-s DATETIME]";
    private static final String[] FLAGS = { FLAG_START, FLAG_END };
    private static final String[] OPTIONS = { OPTION_TASK_ID, OPTION_TASK_NAME,
            OPTION_DATETIME };
    private static final String[] RESERVED = { "edit", "e" };

    public EditHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(LogicInterface logic, Command command) {
        Token id = command.getIdToken();
        try {
            int indexToEdit = Integer.parseInt(id.value);
            Task task = logic.getTask(indexToEdit);
            updateTask(command, task);
            assert (task.isValid());
            logic.storeCommandInHistory();
            logic.clearRedoHistory();
            logic.feedback(new FeedbackMessage(EDIT_SUCCESS, FeedbackType.INFO));
        } catch (NumberFormatException e) {
            logic.feedback(FeedbackMessage.ERROR_INVALID_INDEX);
        } catch (TaskNotFoundException e) {
            logic.feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
        } catch (InvalidTaskException e) {
            logic.feedback(FeedbackMessage.ERROR_INVALID_TASK);
        }
    }
}
