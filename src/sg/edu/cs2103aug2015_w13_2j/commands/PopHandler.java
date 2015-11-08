package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * Pops or removes a filter already specified by the user.
 * 
 * A user feedback message will subsequently be displayed to indicate that this
 * filter was removed successfully. If there are no more applied filter(s)
 * remaining, a user feedback message will be displayed to inform the user.
 */
public class PopHandler extends CommandHandler {
    private static final String NAME = "Pop Filter";
    private static final String SYNTAX = "";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "pop", "back" };
    public static final String POP_SUCCESS = "Last filter removed.";
    public static final String POP_FAIL = "No filters active.";

    public PopHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(LogicInterface logic, Command command) {
        if (logic.popFilter() == null) {
            logic.feedback(new FeedbackMessage(POP_FAIL, FeedbackType.ERROR));
        } else {
            logic.feedback(new FeedbackMessage(POP_SUCCESS, FeedbackType.INFO));
        }
    }
}
