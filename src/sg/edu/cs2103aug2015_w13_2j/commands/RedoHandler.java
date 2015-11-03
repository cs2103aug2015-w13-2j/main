package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

//@@author A0130894B

/**
* Redoes a command previously undone using the "Undo" command by the user.
* This class calls upon the Logic class to process and restore a user command 
* from Logic's internal redo history stack.
* 
* A user feedback message will subsequently be returned upon redoing a command 
* successfully. If the user has redone all the commands that were previously undone, 
* a user feedback message will be returned to indicate that no more commands 
* can be redone.
* 
* @author Natasha Koh Sze Sze
*/
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
