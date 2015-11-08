package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.filters.ActiveFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.ImportantFilter;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * {@link CommandHandler} which handles filtering {@link Task} objects to obtain
 * a subset of {@link Task} objects with specific properties. A valid filter
 * name must be specified.
 * 
 * User feedback {@value #FILTER_SUCCESS} will be displayed to indicate that the
 * specified filter was applied successfully. If the filter specified is not
 * valid, the {@link FeedbackMessage#ERROR_INVALID_FILTER} error message will be
 * shown.
 */
public class FilterHandler extends CommandHandler {
    public static final String FILTER_SUCCESS = "Tasks filtered.";

    private static final String NAME = "Filter Tasks";
    private static final String SYNTAX = "<FILTER_NAME>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_FILTER_NAME };
    private static final String[] RESERVED = { "filter" };

    public FilterHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(LogicInterface logic, Command command) {
        Token alpha = command.getAlphaToken();
        switch (alpha.value) {
        case "active" :
            logic.pushFilter(new ActiveFilter());
            break;
        case "important" :
            logic.pushFilter(new ImportantFilter());
            break;
        default :
            logic.feedback(FeedbackMessage.ERROR_INVALID_FILTER);
            return;
        }
        logic.feedback(new FeedbackMessage(FILTER_SUCCESS, FeedbackType.INFO));
    }
}
