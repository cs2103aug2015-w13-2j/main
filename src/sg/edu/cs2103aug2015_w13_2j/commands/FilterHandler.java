package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Arrays;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.filters.ActiveFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.ArchivedFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.ImportantFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.SearchFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.SortFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.SortFilter.InvalidSortFilterException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class FilterHandler extends CommandHandler {
    private static final String[] RESERVED = { "filter" };
    private static final String FILTER_SUCCESS = "Tasks filtered.";

    @Override
    public FeedbackMessage execute(Logic logic, Command command) {
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
            String[] filter = alpha.value.split(":", 2);
            if (filter.length == 2) {
                switch (filter[0]) {
                case "search":
                    String needle = filter[1];
                    logic.pushFilter(new SearchFilter(needle));
                    break;
                case "sort":
                    String sortBy = filter[1];
                    try {
                        logic.pushFilter(new SortFilter(sortBy));
                        break;
                    } catch (InvalidSortFilterException e) {
                        // Do nothing, fall through to default case
                    }
                default:
                    return FeedbackMessage.ERROR_INVALID_FILTER;
                }
            } else {
                return FeedbackMessage.ERROR_INVALID_FILTER;
            }
        }
        return new FeedbackMessage(FILTER_SUCCESS, FeedbackType.INFO);
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
