package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.filters.ActiveFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.ArchivedFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.IdentityFilter;
import sg.edu.cs2103aug2015_w13_2j.filters.ImportantFilter;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class FilterHandler extends CommandHandler {
    private static final String[] RESERVED = { "filter" };
    private static final String FILTER_SUCCESS = "Tasks filtered.";

    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.ALPHA_NUM) {
                switch (pair.getValue()) {
                case "active":
                    Logic.getInstance().pushFilter(new ActiveFilter());
                    break;
                case "is:archived":
                    Logic.getInstance().pushFilter(new ArchivedFilter());
                    break;
                case "is:important":
                    Logic.getInstance().pushFilter(new ImportantFilter());
                    break;
                default:
                    return FeedbackMessage.getInvalidFilterError();
                }
                return new FeedbackMessage(FILTER_SUCCESS, FeedbackType.INFO);
            }
        }
        return FeedbackMessage.getInvalidFilterError();
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }

}
