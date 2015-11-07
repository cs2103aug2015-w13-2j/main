package sg.edu.cs2103aug2015_w13_2j;

import java.io.File;
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
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Parser;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.UIInterface;

// @@author A0121410H

/**
 * Implementation of a {@link LogicInterface} component.
 * 
 * @author Zhu Chunqi
 */
public class Logic implements LogicInterface {
    private static final Logger LOGGER = Logger
            .getLogger(Logic.class.getName());

    private static Logic sInstance;

    private final HashMap<String, CommandHandler> mCommandHandlers;
    private final Stack<ArrayList<Task>> mHistoryUndoStack;
    private final Stack<ArrayList<Task>> mHistoryRedoStack;
    private final ArrayList<Task> mTasks;

    // Dependencies
    private UIInterface mUI;
    private StorageInterface mStorage;

    /**
     * Private constructor
     */
    private Logic() {
        mCommandHandlers = new HashMap<String, CommandHandler>();
        mHistoryUndoStack = new Stack<ArrayList<Task>>();
        mHistoryRedoStack = new Stack<ArrayList<Task>>();
        mTasks = new ArrayList<Task>();
    }

    /**
     * Retrieves the singleton instance of this {@link LogicInterface}
     * component.
     * 
     * @return {@link LogicInterface} component
     */
    public synchronized static LogicInterface getInstance() {
        if (sInstance == null) {
            sInstance = new Logic();
        }
        return sInstance;
    }

    @Override
    public void injectDependencies(StorageInterface storage, UIInterface textUI) {
    	assert(storage != null);
    	assert(textUI != null);
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
                LOGGER.log(Level.SEVERE, "Conflicting command handlers for: "
                        + keyword);
                throw new Error("Conflicting command handlers for: " + keyword);
            } else {
                mCommandHandlers.put(keyword, handler);
            }
        }
    }

    @Override
    public void executeCommand(String commandString) {
        Command command = Parser.getInstance()
                .parseCommand(this, commandString);
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
                if (handler.shouldDisplay()) {
                    display();
                }
            }
        }
        writeTasks();
    }

    @Override
    public void display() {
        mUI.display(mTasks);
    }

    @Override
    public void display(String s) {
        mUI.display(s);
    }

    @Override
    public void feedback(FeedbackMessage m) {
        mUI.feedback(m);
    }

    @Override
    public boolean showChangeDataFilePathDialog() {
        return mUI.showChangeDataFilePathDialog();
    }

    @Override
    public void addTask(Task task) {
        mTasks.add(task);
    }

    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }
    
    @Override
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            return mTasks.get(mTasks.indexOf(mUI.getTask(index)));
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }
    
    @Override
    public Task removeTask(Task task) throws TaskNotFoundException {
        if (mTasks.remove(task)) {
            return task;
        } else {
            throw new TaskNotFoundException();
        }
    } 

    @Override
    public Task removeTask(int index) throws TaskNotFoundException {
        Task task = mUI.getTask(index);
        removeTask(task);
        return task;
    }
    
    @Override
    public void readTasks() {
        mTasks.clear();
        mTasks.addAll(mStorage.readTasksFromDataFile());
        storeCommandInHistory();
    }

    @Override
    public void writeTasks() {
        mStorage.writeTasksToDataFile(mTasks);
    }

    @Override
    public File getDataFile() {
        return mStorage.getDataFile();
    }

    @Override
    public void setDataFile(File newDataFile) {
        mStorage.setDataFile(newDataFile);
        readTasks();
    }

    @Override
    public void pushFilter(Filter filter) {
        mUI.pushFilter(filter);
    }

    @Override
    public Filter popFilter() {
        return mUI.popFilter();
    }

    // @@author A0130894B

    /**
     * Utility method that creates a deep copy of the task list specified.
     * 
     * @param taskListToCopy
     *            Task list that is to be copied.
     * @return A list of tasks that has the same contents as taskListToCopy but
     *         has a different object reference, i.e. is a different object.
     */
    public static ArrayList<Task> copyTaskList(ArrayList<Task> taskListToCopy) {
        ArrayList<Task> taskListCopy = new ArrayList<Task>();

        // Creates a deep copy of all tasks in the master task
        // list so that the same reference to the Task object will
        // not be replicated in mHistoryStack.
        for (Task task : taskListToCopy) {
            Task taskCopy = task.newInstance();
            taskListCopy.add(taskCopy);
        }
        return taskListCopy;
    }

    /**
     * Stores a deep copy of the master {@link Task} list into the undo stack.
     */
    public void storeCommandInHistory() {
        ArrayList<Task> rootTaskList = copyTaskList(mTasks);
        mHistoryUndoStack.push(rootTaskList);
    }

    /**
     * Stores a deep copy of a {@link Task} list into the redo stack.
     */
    public void storeCommandInRedoHistory(ArrayList<Task> taskListToRedo) {
        ArrayList<Task> copyOfTaskList = copyTaskList(taskListToRedo);
        mHistoryRedoStack.push(copyOfTaskList);
    }

    /**
     * Clears the undo history stack until the root history is reached.
     */
    public void clearUndoHistory() {
        int rootHistoryReached = 1;
        while(mHistoryUndoStack.size() > rootHistoryReached) {
            mHistoryUndoStack.pop();
        }
    }

    /**
     * Clears the redo stack.
     */
    public void clearRedoHistory() {
        mHistoryRedoStack.clear();
    }

    /**
     * Retrieves the most recent user command, if any. The undo stack
     * initializes with the user's saved master {@link Task} list on its root
     * stack and will only be restored until that particular entry.
     * 
     * @return List of {@link Task} objects that will be displayed after
     *         restoring from the undo stack.
     */
    public ArrayList<Task> restoreCommandFromHistory() {
        boolean rootHistoryReached = mHistoryUndoStack.size() == 1;
        if (rootHistoryReached) {
            return null;
        } else {
            ArrayList<Task> tasksToUndo = mHistoryUndoStack.pop();
            ArrayList<Task> latestTaskListUndone = mHistoryUndoStack.peek();
            storeCommandInRedoHistory(tasksToUndo);
            refreshTaskList(latestTaskListUndone);
            return mTasks;
        }
    }

    /**
     * Obtains the last command the user undid, if any. The redo stack
     * initializes with no {@link Task} list and will only be restored until
     * that particular empty entry.
     * 
     * @return List of {@link Task} objects that will be displayed to the user
     *         after restoring from the redo stack.
     */
    public ArrayList<Task> restoreCommandFromRedoHistory() {
        boolean rootRedoHistoryReached = mHistoryRedoStack.isEmpty();
        if (rootRedoHistoryReached) {
            return null;
        } else {
            ArrayList<Task> latestTaskListRedone = mHistoryRedoStack.pop();
            mHistoryUndoStack.push(copyTaskList(latestTaskListRedone));
            refreshTaskList(latestTaskListRedone);
            return mTasks;
        }
    }

    /**
     * Refreshes the master {@link Task} list to show the latest update to the
     * user.
     * 
     * @param latestTaskListUndone
     *            List of {@link Task} objects the master {@link Task} list will
     *            be updated to.
     */
    private void refreshTaskList(ArrayList<Task> taskList) {
        mTasks.clear();
        mTasks.addAll(copyTaskList(taskList));
    }
}
