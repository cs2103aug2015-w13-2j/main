package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author NOAUTHOR

public class LoadHandler extends CommandHandler {
    private static final String NAME = "Load Data File";
    private static final String SYNTAX = "";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "load" };
    private static final String LOAD_SUCCESS = "Loaded FunDUE data file successfully!";
    private static final String LOAD_CANCEL = "FunDUE data file was not changed.";

    public LoadHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        if (logic.showChangeDataFilePathDialog()) {
            logic.feedback(new FeedbackMessage(LOAD_SUCCESS, FeedbackType.INFO));
        } else {
            logic.feedback(new FeedbackMessage(LOAD_CANCEL, FeedbackType.INFO));
        }
    }
}
