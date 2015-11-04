package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0130894B

/**
* Undoes a command made by the user.
* This class calls upon the Logic class to process and restore a user command 
* from Logic's internal undo history stack.
* 
* A user feedback message will subsequently be returned upon undoing a command 
* successfully. If the user has undone all the commands issued to FunDUE, a user 
* feedback message will be returned to indicate that no more commands can be undone.
* 
* @author Natasha Koh Sze Sze
*/
public class UndoHandler extends CommandHandler {
    private static final String NAME = "Undo";
    private static final String SYNTAX = "<COMMAND_NAME>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_COMMAND_NAME };
    private static final String[] RESERVED = { "undo" };
    private static final String UNDO_SUCCESS = "Previous command undone!";
    private static final String UNDO_FAILURE = "No more commands to undo!";

    public UndoHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }
    
    @Override
    public void execute(Logic logic, Command command) {
        ArrayList<Task> restoredTaskList = logic.restoreCommandFromHistory();
        if (restoredTaskList == null) {
            logic.feedback(new FeedbackMessage(UNDO_FAILURE, FeedbackType.INFO));
        } else {
            logic.feedback(new FeedbackMessage(UNDO_SUCCESS, FeedbackType.INFO));
        }
    }
}
