package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.filters.SearchFilter;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.parser.Token.Type;

//@@author A0121410H

/**
* Searches for a task or multiple tasks, matching a search term or multiple 
* search terms to the master task list.
* A search term or multiple search terms are expected to be specified in 
* the user command.
* 
* A user feedback message will subsequently be displayed to indicate that 
* this task was found, or multiple tasks were found that matched the search 
* term(s).
* 
* @author Zhu Chunqi
*/
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
