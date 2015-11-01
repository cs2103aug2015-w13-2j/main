package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Parser;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.UIInterface;

public class Logic implements LogicInterface {
    private static final Logger LOGGER = Logger
            .getLogger(Logic.class.getName());

    private static Logic sInstance;

    private final HashMap<String, CommandHandler> mCommandHandlers;
    private final Stack<ArrayList<Task>> mHistoryStack;
    private final ArrayList<Task> mTasks;

    private UIInterface mUI;
    private StorageInterface mStorage;

    /**
     * Private constructor
     */
    private Logic() {
        mCommandHandlers = new HashMap<String, CommandHandler>();
        mHistoryStack = new Stack<ArrayList<Task>>();
        mTasks = new ArrayList<Task>();
    }

    /**
     * Retrieves the singleton instance of the {@link LogicInterface} component
     * 
     * @return {@link LogicInterface} component
     */
    public synchronized static Logic getInstance() {
        if (sInstance == null) {
            sInstance = new Logic();
        }
        return sInstance;
    }

    @Override
    public void injectDependencies(StorageInterface storage,
            UIInterface textUI) {
        mStorage = storage;
        mUI = textUI;
    }

    @Override
    public Set<String> getReservedKeywords() {
        return mCommandHandlers.keySet();
    }

    @Override
    public HashMap<String, CommandHandler> getCommandHandlers() {
        return mCommandHandlers;
    }

    @Override
    public void registerCommandHandler(CommandHandler handler) {
        List<String> reserved = handler.getReservedKeywords();
        for (String keyword : reserved) {
            if (mCommandHandlers.containsKey(keyword)) {
                throw new Error("Conflicting command handlers for: " + keyword);
            } else {
                mCommandHandlers.put(keyword, handler);
            }
        }
    }

    @Override
    public void executeCommand(String commandString) {
        Command command = Parser.getInstance().parseCommand(this,
                commandString);
        Token reserved = command.getReservedToken();
        if (commandString.isEmpty()) {
            feedback(FeedbackMessage.CLEAR);
            display();
        } else if (reserved.isEmptyToken()) {
            feedback(FeedbackMessage.ERROR_UNRECOGNIZED_COMMAND);
        } else {
            CommandHandler handler = mCommandHandlers.get(reserved.value);
            if (handler != null) {
                handler.execute(this, command);
                mUI.updateFilters(mTasks);
                if (handler.shouldDisplay()) {
                    display();
                }
            }
        }
        writeTasks();
    }

    public void display() {
        mUI.display(mTasks);
    }

    public void display(String s) {
        mUI.display(s);
    }

    public void feedback(FeedbackMessage m) {
        mUI.feedback(m);
    }

    public void addTask(Task task) {
        mTasks.add(task);
        mUI.updateFilters(mTasks);
    }

    public Task getTask(int index) throws TaskNotFoundException {
        try {
            return mTasks.get(mTasks.indexOf(mUI.getTask(index)));
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    public Task removeTask(int index) throws TaskNotFoundException {
        Task task = mUI.getTask(index);
        if (mTasks.remove(task)) {
            return task;
        } else {
            throw new TaskNotFoundException();
        }
    }

    public void pushFilter(Filter filter) {
        mUI.pushFilter(filter);
    }

    public Filter popFilter() {
        return mUI.popFilter();
    }

    public void storeCommandInHistory() {
        ArrayList<Task> rootTaskList = mTasks;
        mHistoryStack.push(rootTaskList);
    }

    public ArrayList<Task> restoreCommandFromHistory() {
        boolean rootHistoryReached = mHistoryStack.size() == 1;
        if (rootHistoryReached) {
            return null;
        } else {
            mHistoryStack.pop();
            mTasks.clear();
            mTasks.addAll(mHistoryStack.peek());
            mUI.updateFilters(mTasks);
            return mTasks;
        }
    }

    public void showChangeDataFilePathDialog() {
        mStorage.showChangeDataFilePathDialog();
    }

    public void readTasks() {
        mTasks.clear();
        mTasks.addAll(mStorage.readTasksFromDataFile());
        mUI.updateFilters(mTasks);
        mHistoryStack.push(mTasks);
    }

    /**
     * Writes the master list of Task objects to the data file
     */
    private void writeTasks() {
        mStorage.writeTasksToDataFile(mTasks);
    }
}