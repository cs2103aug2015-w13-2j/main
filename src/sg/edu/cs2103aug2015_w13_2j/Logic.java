package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUI;

public class Logic {
    private static Logic sInstance;
    private static final Logger LOGGER = Logger
            .getLogger(Logic.class.getName());
    private HashMap<String, CommandHandler> mCommandHandlers = new HashMap<String, CommandHandler>();
    private ArrayList<Task> mTasks = new ArrayList<Task>();

    /**
     * Protected constructor
     */
    protected Logic() {
        // Do nothing
        TextUI.getInstance().display(mTasks);
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

    public void executeCommand(String command) {
        ArrayList<Pair<Token, String>> tokens = Parser.getInstance()
                .parseCommand(command);
        FeedbackMessage feedback = new FeedbackMessage(
                "Command not recognized.", FeedbackType.ERROR);
        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.RESERVED) {
                CommandHandler handler = mCommandHandlers.get(pair.getValue());
                if (handler != null) {
                    feedback = handler.execute(tokens);
                }
                break;
            }
        }
        sortByDeadline();
        TextUI.getInstance().feedback(feedback);
        TextUI.getInstance().display(mTasks);
    }

    public void addTask(Task task) {
        mTasks.add(task);
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
        if (index >= 0 && index < mTasks.size()) {
            return mTasks.get(index);
        } else {
            throw new TaskNotFoundException();
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
        if (index >= 0 && index < mTasks.size()) {
            return mTasks.remove(index);
        } else {
            throw new TaskNotFoundException();
        }
    }
    
    /**
     * This method sorts a list of tasks according to their deadlines(if any)
     * The tasks with deadlines takes priority, followed by events sorted
     * according to start time and floats to be added last, sorted by their
     * names
     * 
     * 
     * @@author A0133387B
     * 
     */
    private ArrayList<Task> sortByDeadline() {
        Collections.sort(mTasks, new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
            	//Each task always has a type before invoking this method 
            	assert(task1.getType() != null && task1.getType() != null);
            	
                if (task1.getType().equals(task2.getType())) {
                    if (task1.getType().equals("DEADLINE")) {
                        return task1.getEnd().compareTo(task2.getEnd());
                    } else if (task1.getType().equals("EVENT")) {
                        return task1.getStart().compareTo(task2.getStart());
                    } else {
                        return task1.getName().compareTo(task2.getName());
                    }
                } else {
                    return task1.getType().compareTo(task2.getType());
                }
            }
        });

        return mTasks;
    }
    
    /**
     * This method sorts a list of tasks according to their deadlines(if any)
     * The tasks with deadlines takes priority, followed by events sorted
     * according to start time and floats to be added last, sorted by their
     * names
     * 
     */
    
    private ArrayList<Task> search(String keyword) {
        ArrayList<Task> tasksWithKeyword = new ArrayList<Task>();
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getName().toLowerCase()
                    .contains(keyword.toLowerCase())) {
            	tasksWithKeyword.add(mTasks.get(i));
            }
        }

        return tasksWithKeyword;
    }
}