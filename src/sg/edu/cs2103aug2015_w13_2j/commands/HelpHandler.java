package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * {@link CommandHandler} which handles showing the FunDUE Help Page to the
 * user. Internally calls {@link LogicInterface#showHelpPage()}.
 * 
 * User feedback {@value #HELP_SUCCESS} will be displayed to indicate that the
 * FunDUE Help Page has been opened. If the FunDUE Help Page was already open,
 * the {@value #HELP_NOACTION} message will be shown.
 * 
 * @author Zhu Chunqi
 */
public class HelpHandler extends CommandHandler {
    public static final String HELP_SUCCESS = "Showing FunDUE Help Page.";
    public static final String HELP_NOACTION = "FunDUE Help Page is already open.";

    private static final String NAME = "Help";
    private static final String SYNTAX = "";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "h", "help", "?" };

    public HelpHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(LogicInterface logic, Command command) {
        if (logic.showHelpPage()) {
            logic.feedback(new FeedbackMessage(HELP_SUCCESS, FeedbackType.INFO));
        } else {
            logic.feedback(new FeedbackMessage(HELP_NOACTION, FeedbackType.INFO));
        }
    }
}
