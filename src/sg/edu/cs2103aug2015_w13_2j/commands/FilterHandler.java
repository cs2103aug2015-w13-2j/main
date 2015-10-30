package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.filters.ActiveFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.ArchivedFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.ImportantFilter;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class FilterHandler extends CommandHandler {
    private static final String NAME = "Add Filter";
    private static final String SYNTAX = "<FILTER_NAME>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_FILTER_NAME };
    private static final String[] RESERVED = { "filter" };
    private static final String FILTER_SUCCESS = "Tasks filtered.";

    public FilterHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        Token alpha = command.getAlphaToken();
        switch (alpha.value) {
        case "active":
            logic.pushFilter(new ActiveFilter());
            break;
        case "is:archived":
            logic.pushFilter(new ArchivedFilter());
            break;
        case "is:important":
            logic.pushFilter(new ImportantFilter());
            break;
        default:
            logic.feedback(FeedbackMessage.ERROR_INVALID_FILTER);
            return;
        }
        logic.feedback(new FeedbackMessage(FILTER_SUCCESS, FeedbackType.INFO));
    }
}
