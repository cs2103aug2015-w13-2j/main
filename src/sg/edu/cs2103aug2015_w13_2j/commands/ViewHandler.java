package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

/**
 * TODO:
 * Execution method to toggle view should be added when the filter views for 
 * archived tasks are added and in working order.
 * 
 * Toggles the view of the user interface. It switches from the 
 * user's default view to the view that displays all archived tasks and 
 * vice versa. 
 * 
 * @author Natasha Koh Sze Sze
 */
public class ViewHandler extends CommandHandler {
    private static final Logger LOGGER = Logger
            .getLogger(ViewHandler.class.getName());
    private static final String[] RESERVED = { "view" };
    private static final String VIEW_SWITCH_SUCCESS = "View switched!";
    
    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        //TODO:
        // Stub for the toggle between "default" view and "archives" view.
        // This functionality should be added when the filter views for 
        // archived tasks are added and in working order.
        for (Pair<Token, String> pair : tokens) {
            return null;
        }
        return null;
    }
    
    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
}
