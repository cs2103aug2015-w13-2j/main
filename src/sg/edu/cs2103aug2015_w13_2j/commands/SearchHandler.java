package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;

public class SearchHandler extends CommandHandler {
    private static final Logger LOGGER = Logger
            .getLogger(SearchHandler.class.getName());
    
    private static final String[] RESERVED = { "search", "find" };
    private static final String TASK_FOUND_SUCCESS = "Task found!";
    private static final String TASK_NOT_FOUND = "Task not found!";
    private static final String INVALID_SEARCH_TERM = 
            "Invalid search term. Did you enter it with double quotes?";
    
    /* TODO: NOTE:
     * 
     * This method currently does not display the tasks that are found on the User Interface.
     * This functionality should be added when the User Interface supports displaying a
     * view of archived, searched & found tasks, etc.
     * 
     * (non-Javadoc)
     * @see sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler#execute(java.util.ArrayList)
     */
    @Override
    public FeedbackMessage execute(ArrayList<Pair<Token, String>> tokens) {
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.NAME) {
                ArrayList<Task> tasksFound =  Logic.getInstance().search(pair.getValue());
                logFoundTasks(tasksFound);
                
                if (!tasksFound.isEmpty()) {
                    return new FeedbackMessage(TASK_FOUND_SUCCESS, FeedbackType.INFO);
                } else {
                    return new FeedbackMessage(TASK_NOT_FOUND, FeedbackType.INFO);
                }
            }
        }
        return new FeedbackMessage(INVALID_SEARCH_TERM, FeedbackType.ERROR);
    }
    
    @Override
    public List<String> getReservedKeywords() {
        return Arrays.asList(RESERVED);
    }
    
    /**
     * A utility logger that lists all task names in a given list of 
     * Task objects.
     * 
     * @param tasksFound
     *              List of Tasks objects
     */
    private void logFoundTasks(ArrayList<Task> tasksFound) {
        String tasksFoundNames = "";
        for (Task task: tasksFound) {
            tasksFoundNames += task.getName() + " | ";
        }
        LOGGER.info("[CommandHandler][SearchHandler] All searched tasks: " + tasksFoundNames);
    }
}
