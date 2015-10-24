package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class PopHandler extends CommandHandler {
    private static final String[] RESERVED = { "pop" };
    private static final String POP_SUCCESS = "Last filter removed.";

    @Override
    public FeedbackMessage execute(Logic logic,
            ArrayList<Pair<Token, String>> tokens) {
        logic.popFilter();
        return new FeedbackMessage(POP_SUCCESS, FeedbackType.INFO);
    }

    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }

}
