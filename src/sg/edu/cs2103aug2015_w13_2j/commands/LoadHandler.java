package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * {@link CommandHandler} which handles showing a file picker dialog to change
 * the FunDUE Data File. Internally calls
 * {@link LogicInterface#showChangeDataFilePathDialog()}.
 * 
 * User feedback {@value #LOAD_SUCCESS} will be displayed to indicate that the
 * FunDUE Data File has been changed. If no file was chosen in the file picker
 * dialog, the {@value #LOAD_CANCEL} message will be shown.
 * 
 * @author Zhu Chunqi
 */
public class LoadHandler extends CommandHandler {
    private static final String LOAD_SUCCESS = "Loaded FunDUE Data File successfully!";
    private static final String LOAD_CANCEL = "FunDUE Data File was not changed.";

    private static final String NAME = "Load FunDUE Data File";
    private static final String SYNTAX = "";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "load" };

    public LoadHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(LogicInterface logic, Command command) {
        if (logic.showChangeDataFilePathDialog()) {
            logic.feedback(new FeedbackMessage(LOAD_SUCCESS, FeedbackType.INFO));
        } else {
            logic.feedback(new FeedbackMessage(LOAD_CANCEL, FeedbackType.INFO));
        }
    }
}
