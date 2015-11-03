package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class LoadHandler extends CommandHandler {
    private static final String NAME = "Load Data File";
    private static final String SYNTAX = "";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "load" };
    private static final String LOAD_SUCCESS = 
            "Loaded new to-do list successfully!";

    public LoadHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        logic.showChangeDataFilePathDialog();
        logic.feedback(new FeedbackMessage(LOAD_SUCCESS, 
                FeedbackType.INFO));
    }
}
