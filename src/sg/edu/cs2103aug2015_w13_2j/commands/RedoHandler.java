package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class RedoHandler extends CommandHandler {
    private static final String NAME = "Redo";
    private static final String SYNTAX = "<COMMAND_NAME>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_COMMAND_NAME };
    private static final String[] RESERVED = { "redo" };
    private static final String REDO_SUCCESS = "Previous command redone!";
    private static final String REDO_FAILURE = "No more commands to redo! "
            + "Please undo a command first!";

    public RedoHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }
    
    @Override
    public void execute(Logic logic, Command command) {
        ArrayList<Task> restoredTaskList = logic.restoreCommandFromRedoHistory();
        
        if (restoredTaskList == null) {
            logic.feedback(new FeedbackMessage(REDO_FAILURE, FeedbackType.INFO));
        } else {
            logic.feedback(new FeedbackMessage(REDO_SUCCESS, FeedbackType.INFO));
        }
    }
}
