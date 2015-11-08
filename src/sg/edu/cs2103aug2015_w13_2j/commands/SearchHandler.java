package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.filters.SearchFilter;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

// @@author A0121410H

/**
 * {@link CommandHandler} which handles searching for {@link Task} objects
 * containing one or more specified search terms. One or more single word or
 * quoted phrase search terms must be specified.
 * 
 * User feedback {@value #SEARCH_SUCCESS} will be displayed to indicate that the
 * search filter was applied successfully.
 * 
 * @author Zhu Chunqi
 */
public class SearchHandler extends CommandHandler {
    public static final String SEARCH_SUCCESS = "Search filter added.";

    private static final String NAME = "Search For Tasks";
    private static final String SYNTAX = "<SEARCH_TERM>...";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_SEARCH_TERM };
    private static final String[] RESERVED = { "s", "search" };

    public SearchHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(LogicInterface logic, Command command) {
        ArrayList<String> needles = new ArrayList<String>();
        command.removeReservedToken();
        for (Token token : command) {
            needles.add(token.value);
        }
        logic.pushFilter(new SearchFilter(needles));
        logic.feedback(new FeedbackMessage(SEARCH_SUCCESS, FeedbackType.INFO));
    }
}
