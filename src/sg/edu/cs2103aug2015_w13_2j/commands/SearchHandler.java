package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.filters.SearchFilter;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.parser.Token.Type;

public class SearchHandler extends CommandHandler {
    private static final String NAME = "Search For Tasks";
    private static final String SYNTAX = "<SEARCH_TERM>...";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_SEARCH_TERM };
    private static final String[] RESERVED = { "s", "search" };
    
    public SearchHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        ArrayList<String> needles = new ArrayList<String>();
        for(Token token : command) {
            if(token.type == Type.NAME || token.type == Type.ALPHA_NUM) {
                needles.add(token.value);
            }
        }
        logic.pushFilter(new SearchFilter(needles));
    }
}
