package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUI;

public class Logic {
    private static Logic sInstance;
    private static final Logger LOGGER = Logger
            .getLogger(Logic.class.getName());
    private HashMap<String, CommandHandler> mCommandHandlers = new HashMap<String, CommandHandler>();
    private FilterChain mFilterChain;
    
    /**
     * Protected constructor
     * NOTE: modified method signature to facilitate testing
     */
    protected Logic(Storage storage, TextUI textUI) {
        mFilterChain = new FilterChain(storage
                .readTasksFromDataFile());
        textUI.display(mFilterChain.getTasksForDisplay());
        textUI.setFilter(mFilterChain.getFilterChain());
    }

    protected Logic(){
    	this(Storage.getInstance(), TextUI.getInstance());
    }
    /**
     * Retrieves the singleton instance of the Logic component
     * 
     * @return Logic component
     */
    public static Logic getInstance() {
        if (sInstance == null) {
            sInstance = new Logic();
        }
        return sInstance;
    }

    public Set<String> getReservedKeywords() {
        return mCommandHandlers.keySet();
    }

    public void registerCommandHandler(CommandHandler handler) {
        List<String> reserved = handler.getReservedKeywords();
        for (String keyword : reserved) {
            if (mCommandHandlers.containsKey(keyword)) {
                // TODO: Throw exception
            } else {
                mCommandHandlers.put(keyword, handler);
            }
        }
    }

    public void executeCommand(String command, TextUI textUI, Storage storage) {
        ArrayList<Pair<Token, String>> tokens = Parser.getInstance()
                .parseCommand(command);
        FeedbackMessage feedback = new FeedbackMessage(
                "Command not recognized.", FeedbackType.ERROR);
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.RESERVED) {
                CommandHandler handler = mCommandHandlers.get(pair.getValue());
                if (handler != null) {
                    feedback = handler.execute(tokens);
                    mFilterChain.updateFilters();
                }
                break;
            }
        }
        storage.writeTasksToDataFile(mFilterChain.getTasks());
        textUI.feedback(feedback);
        textUI.display(mFilterChain.getTasksForDisplay());
        textUI.setFilter(mFilterChain.getFilterChain());
    }

    public void executeCommand(String command){
    	executeCommand(command, TextUI.getInstance(), Storage.getInstance());
    }
    
    public void addTask(Task task) {
        mFilterChain.addTask(task);
    }

    /**
     * Convenience method to retrieve a task with an index specified by
     * non-sanitized user input or to be chained with the return value of
     * {@link Logic#getTaskIndexByName(String)}. Throws an exception if the
     * index is out of bounds
     * 
     * @param index
     *            The index of the Task object to retrieve
     * @return The Task object with the specified index
     * @throws TaskNotFoundException
     *             Thrown when the index specified is out of bounds
     */
    public Task getTask(int index) throws TaskNotFoundException {
        Task task = mFilterChain.getTask(index);
        if (task == null) {
            throw new TaskNotFoundException();
        } else {
            return task;
        }
    }

    /**
     * Convenience method to remove a task with an index specified by
     * non-sanitized user input. Throws an exception if the index is out of
     * bounds
     * 
     * @param index
     *            The index of the Task object to be removed
     * @return The Task object that was removed
     * @throws TaskNotFoundException
     *             Thrown when the index specified is out of bounds
     */
    public Task removeTask(int index) throws TaskNotFoundException {
        Task task = mFilterChain.removeTask(index);
        if (task == null) {
            throw new TaskNotFoundException();
        } else {
            return task;
        }
    }

    public void pushFilter(Filter filter) {
        mFilterChain.pushFilter(filter);
        LOGGER.log(Level.INFO, "Pushed filter: " + filter.getFilterName());
    }

    public void popFilter() {
        Filter filter = mFilterChain.popFilter();
        if(filter == null) {
            LOGGER.log(Level.WARNING, "Cannot pop root identity filter");
        } else {
            LOGGER.log(Level.INFO, "Popped filter: " + filter.getFilterName());
        }
    }
}