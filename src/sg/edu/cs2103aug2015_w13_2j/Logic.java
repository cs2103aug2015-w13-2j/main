package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Parser;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIInterface;

public class Logic implements LogicInterface {
    private static final Logger LOGGER = Logger
            .getLogger(Logic.class.getName());

    private static Logic sInstance;

    private TextUIInterface mTextUI;
    private StorageInterface mStorage;
    private HashMap<String, CommandHandler> mCommandHandlers = new HashMap<String, CommandHandler>();
    private FilterChain mFilterChain = new FilterChain();
    private Stack<ArrayList<Task>> mHistoryTasks;

    /**
     * Protected constructor
     */
    protected Logic() {
        // Do nothing
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

    public void injectDependencies(StorageInterface storage, TextUIInterface textUI) {
        mStorage = storage;
        mTextUI = textUI;
    }

    public Set<String> getReservedKeywords() {
        return mCommandHandlers.keySet();
    }

    public HashMap<String, CommandHandler> getCommandHandlers() {
        return mCommandHandlers;
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

    public void executeCommand(String commandString) {
        Command command = Parser.getInstance()
                .parseCommand(this, commandString);
        Token reserved = command.getReservedToken();
        if (commandString.isEmpty()) {
            feedback(FeedbackMessage.CLEAR);
            display();
        } else if (reserved == null) {
            feedback(FeedbackMessage.ERROR_UNRECOGNIZED_COMMAND);
        } else {
            CommandHandler handler = mCommandHandlers.get(reserved.value);
            if (handler != null) {
                handler.execute(this, command);
                mFilterChain.updateFilters();
                if (handler.shouldDisplay()) {
                    display();
                }
            }
        }
        writeTasks();
    }

    public void display() {
        mTextUI.display(mFilterChain.getTasksForDisplay());
        mTextUI.setFilter(mFilterChain.getFilterChain());
    }

    public void display(String s) {
        mTextUI.display(s);
    }

    public void feedback(FeedbackMessage m) {
        mTextUI.feedback(m);
    }

    public void setFilter(String s) {
        mTextUI.setFilter(s);
    }

    public void addTask(Task task) {
        mFilterChain.addTask(task);
    }

    /**
     * Retrieves the task with the index specified by user input. Throws an
     * exception if the index is out of bounds
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
        if (filter == null) {
            LOGGER.log(Level.WARNING, "Cannot pop root identity filter");
        } else {
            LOGGER.log(Level.INFO, "Popped filter: " + filter.getFilterName());
        }
    }

    public void storeCommandInHistory() {
        ArrayList<Task> rootTaskList = mFilterChain.getRootTasks();
        mHistoryTasks.push(rootTaskList);
    }
    
    public ArrayList<Task> restoreCommandFromHistory() {
        boolean rootHistoryReached = mHistoryTasks.size() == 1;
        if (rootHistoryReached) {
            return null;
        } else {
            mHistoryTasks.pop();
            mFilterChain.setRootTasks(mHistoryTasks.peek());
            return mFilterChain.getRootTasks();
        }
    }
    
    public void showChangeDataFilePathDialog() {
        mStorage.showChangeDataFilePathDialog();
    }
    
    public void readTasks() {
        ArrayList<Task> tasksFromDataFile = mStorage.readTasksFromDataFile();
        mFilterChain = new FilterChain(tasksFromDataFile);
        mHistoryTasks = new Stack<ArrayList<Task>>();
        mHistoryTasks.push(tasksFromDataFile);
    }

    private void writeTasks() {
        mStorage.writeTasksToDataFile(mFilterChain.getTasks());
    }
}