package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.Logger;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;

public class Logic implements LogicInterface {
    private static Logic sInstance;
    private static final Logger LOGGER = Logger
            .getLogger(Logic.class.getName());
    private ArrayList<Task> mTasks = new ArrayList<Task>();

    /**
     * Protected constructor
     */
    protected Logic() {
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

    public void executeCommand(String command) {
        ArrayList<Pair<Token, String>> tokens = Parser.getInstance()
                .parseCommand(command);
        executeCommand(tokens);
    }

    private void executeCommand(ArrayList<Pair<Token, String>> tokens) {
        // Remove whitespaces first
        Iterator<Pair<Token, String>> iter = tokens.iterator();
        while (iter.hasNext()) {
            Pair<Token, String> pair = iter.next();
            if (pair.getKey() == Token.WHITESPACE) {
                iter.remove();
            }
        }

        for (Pair<Token, String> pair : tokens) {
            if (pair.getKey() == Token.RESERVED) {
                try {
                    switch (pair.getValue()) {
                    case "add":
                        addTask(tokens);
                        TextUI.getInstance().feedback(FeedbackMessage.LOGIC_ADDED);
                        break;
                    case "edit":
                        editTask(tokens);
                        TextUI.getInstance().feedback(FeedbackMessage.LOGIC_EDITED);
                        break;
                    case "list":
                        TextUI.getInstance().feedback(FeedbackMessage.CLEAR);
                        break;
                    case "delete":
                        deleteTask(tokens);
                        TextUI.getInstance().feedback(FeedbackMessage.LOGIC_DELETED);
                        break;
                    case "search":
                        searchTask(tokens);
                        break;
                    case "archive":
                        archiveTask(tokens);
                        TextUI.getInstance().feedback(FeedbackMessage.LOGIC_ARCHIVED);
                        break;
                    case "retrieve":
                        retrieveTask(tokens);
                        TextUI.getInstance().feedback(FeedbackMessage.LOGIC_RETRIEVED);
                        break;
                    default:
                        System.err.println("[Logic] Unimplemented command: "
                                + pair.getValue());
                        TextUI.getInstance().feedback(
                                FeedbackMessage.ERROR_COMMAND_NOT_IMPLEMENTED);
                        break;
                    }
                } catch (InvalidTaskException e) {
                    System.err.println("[Logic] Invalid Task");
                    TextUI.getInstance().feedback(FeedbackMessage.ERROR_INVALID_TASK);
                } catch (TaskNotFoundException e) {
                    System.err.println("[Logic] Task not found");
                    TextUI.getInstance().feedback(FeedbackMessage.ERROR_TASK_NOT_FOUND);
                }
                TextUI.getInstance().display(mTasks);
                return;
            }
        }
        TextUI.getInstance().feedback(FeedbackMessage.ERROR_COMMAND_NOT_RECOGNIZED);
    }

    public void echo(String s) {
        // TextUI.getInstance().print(s);
    }

    /**
     * Reads the tasks from data file to mTasks
     */
    private void readFile() {
        try {
            mTasks = (ArrayList) Storage.getInstance().readTasksFromDataFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the list of tasks in mTasks to the data file
     */
    private void writeFile() {
        try {
            Storage.getInstance().writeTasksToDataFile(mTasks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the passed in Task object based on the parsed tokens
     * 
     * @param tokens
     *            The tokens parsed from the command <b>including</b> the
     *            command token itself but <b>excluding</b> any previously used
     *            identifiers
     * @throws InvalidTaskException
     *             Thrown when the Task constructed from the parsed tokens is
     *             invalid
     */
    private void updateTask(ArrayList<Pair<Token, String>> tokens, Task task)
            throws InvalidTaskException {
        Iterator<Pair<Token, String>> iter = tokens.iterator();

        while (iter.hasNext()) {
            Pair<Token, String> pair = iter.next();
            switch (pair.getKey()) {
            case FLAG:
                String flag = pair.getValue();
                switch (flag) {
                // Flags which expect the next token to be a date
                case Parser.FLAG_END:
                case Parser.FLAG_START:
                    System.out.println("[Logic] Flag encountered: " + flag);
                    if (iter.hasNext()) {
                        Pair<Token, String> nextPair = iter.next();
                        assert (nextPair.getKey() == Token.DATE || nextPair
                                .getKey() == Token.DATE_INVALID);
                        // Only set valid dates
                        if (nextPair.getKey() == Token.DATE) {
                            if (flag.compareTo(Parser.FLAG_END) == 0) {
                                task.setEnd(nextPair.getValue());
                            } else if (flag.compareTo(Parser.FLAG_START) == 0) {
                                task.setStart(nextPair.getValue());
                            }
                        }
                    }
                    break;
                }
                break;
            case NAME:
                task.setName(pair.getValue());
                break;
            case ALPHA_NUM:
            case DATE:
            case DATE_INVALID:
            case FLAG_INVALID:
            case ID:
            case ID_INVALID:
            case RESERVED:
            case WHITESPACE:
                // Do nothing
                break;
            }
        }

        // Check if the constructed Task is valid
        task.isValid();
    }

    private Task addTask(ArrayList<Pair<Token, String>> tokens)
            throws InvalidTaskException {
        Task task = new Task();
        updateTask(tokens, task);
        // expects task to be valid first before adding
        assert (task.isValid());
        mTasks.add(task);
        determineType(task);
        sortByDeadline();
        System.out.println("[Logic] Added task");
        System.out.print(task);
        return task;
    }

    private void editTask(ArrayList<Pair<Token, String>> tokens)
            throws TaskNotFoundException, InvalidTaskException {
        Iterator<Pair<Token, String>> iter = tokens.iterator();
        Task task = null;

        // First iteration to retrieve the corresponding Task object
        while (iter.hasNext() && task == null) {
            Pair<Token, String> pair = iter.next();
            switch (pair.getKey()) {
            case NAME:
            case ID:
                int index = pair.getKey() == Token.ID ? Integer.parseInt(pair
                        .getValue()) : getTaskIndexByName(pair.getValue());
                task = getTask(index);
                // Remove the first identifying name or ID such that future ones
                // will update the Task's name
                iter.remove();
                break;
            default:
                break;
            }
        }

        System.out.println("[Logic] Editing task");
        System.out.println("[Logic] Before");
        System.out.print(task);
        updateTask(tokens, task);
        determineType(task);
        sortByDeadline();
        System.out.println("[Logic] After");
        System.out.print(task);

    }

    /**
     * Deletes the task with the specified ID or name (ID has greater precedence
     * over name) from the master task list
     *
     * @param tokens
     *            The tokens parsed from the command <b>including</b> the
     *            command token itself
     * @throws TaskNotFoundException
     *             Thrown when the provided task name could not be found or ID
     *             is out of bounds
     */
    private void deleteTask(ArrayList<Pair<Token, String>> tokens)
            throws TaskNotFoundException {
        Iterator<Pair<Token, String>> iter = tokens.iterator();

        while (iter.hasNext()) {
            Pair<Token, String> pair = iter.next();
            switch (pair.getKey()) {
            case ALPHA_NUM:
            case DATE:
            case DATE_INVALID:
            case FLAG:
            case FLAG_INVALID:
                // Do nothing
                break;
            case ID_INVALID:
                // Do nothing
                break;
            case ID:
            case NAME:
                int index = pair.getKey() == Token.ID ? Integer.parseInt(pair
                        .getValue()) : getTaskIndexByName(pair.getValue());
                System.out.println("[Logic] Removing task");
                Task task = removeTask(index);
                System.out.print(task);
                return;
            case RESERVED:
            case WHITESPACE:
                // Do nothing
                break;
            }
        }

    }

    // @@author A0130894B
    /**
     * Searches for a task with the specified name from the master task list
     *
     * @param tokens
     *            The tokens parsed from the command <b>including</b> the
     *            command token itself
     */
    private void searchTask(ArrayList<Pair<Token, String>> tokens) {
        Iterator<Pair<Token, String>> iter = tokens.iterator();

        while (iter.hasNext()) {
            Pair<Token, String> pair = iter.next();
            switch (pair.getKey()) {
            case ALPHA_NUM:
                TextUI.getInstance()
                        .feedback(FeedbackMessage.ERROR_INVALID_SEARCH_TERM);
                break;
            case DATE:
            case DATE_INVALID:
            case FLAG:
            case FLAG_INVALID:
            case ID_INVALID:
            case ID:
                // Do nothing
                break;
            case NAME:
                // To demonstrate use of assert & logger during tut 14 Oct
                ArrayList<Task> tasksFound = search(pair.getValue());
                assert (tasksFound != null);

                String tasksFoundNames = "";
                for (Task task : tasksFound) {
                    tasksFoundNames += task.getName() + " | ";
                }

                LOGGER.info("[Logic] All searched tasks: " + tasksFoundNames);

                if (!tasksFound.isEmpty()) {
                    // TODO:
                    // No display view for tasks found in user interface yet.
                    // Do nothing first.
                    TextUI.getInstance().feedback(FeedbackMessage.LOGIC_SEARCH_FOUND);
                } else {
                    TextUI.getInstance().feedback(
                            FeedbackMessage.LOGIC_SEARCH_NOT_FOUND);
                }
                break;
            case RESERVED:
            case WHITESPACE:
                // Do nothing
                break;
            }
        }

    }

    /**
     * Archives the task with the specified ID from the master task list
     *
     * @param tokens
     *            The tokens parsed from the command <b>including</b> the
     *            command token itself
     * @throws TaskNotFoundException
     *             Thrown when the provided task name of the ID could not be
     *             found or ID is out of bounds
     */
    private void archiveTask(ArrayList<Pair<Token, String>> tokens)
            throws TaskNotFoundException {
        Iterator<Pair<Token, String>> iter = tokens.iterator();

        while (iter.hasNext()) {
            Pair<Token, String> pair = iter.next();
            switch (pair.getKey()) {
            case ALPHA_NUM:
            case DATE:
            case DATE_INVALID:
            case FLAG:
            case FLAG_INVALID:
            case ID_INVALID:
                // Do nothing
                break;
            case ID:
            case NAME:
                // TODO:
                // No display view for tasks found in user interface yet.
                // Do nothing first.
                int index = pair.getKey() == Token.ID ? Integer.parseInt(pair
                        .getValue()) : getTaskIndexByName(pair.getValue());
                System.out.println("[Logic] Archiving task");
                Task task = archive(index);
                System.out.print(task);
            case RESERVED:
            case WHITESPACE:
                // Do nothing
                break;
            }
        }

    }

    /**
     * Retrieves the task with the specified ID from the master task list
     *
     * @param tokens
     *            The tokens parsed from the command <b>including</b> the
     *            command token itself
     * @throws TaskNotFoundException
     *             Thrown when the provided task name of the ID could not be
     *             found or ID is out of bounds
     */
    private void retrieveTask(ArrayList<Pair<Token, String>> tokens)
            throws TaskNotFoundException {
        Iterator<Pair<Token, String>> iter = tokens.iterator();

        while (iter.hasNext()) {
            Pair<Token, String> pair = iter.next();
            switch (pair.getKey()) {
            case ALPHA_NUM:
            case DATE:
            case DATE_INVALID:
            case FLAG:
            case FLAG_INVALID:
            case ID_INVALID:
                // Do nothing
                break;
            case ID:
            case NAME:
                int index = pair.getKey() == Token.ID ? Integer.parseInt(pair
                        .getValue()) : getTaskIndexByName(pair.getValue());
                System.out.println("[Logic] Retrieving task");
                Task task = retrieve(index);
                System.out.print(task);
            case RESERVED:
            case WHITESPACE:
                // Do nothing
                break;
            }
        }

    }

    /**
     * Retrieves the index of the first occurrence of a Task object with the
     * name provided
     * 
     * @param name
     *            The name of the Task object to find
     * @return The index of the Task object or -1 if none is found
     */
    protected int getTaskIndexByName(String name) { // changed to protected to
                                                    // aid testing
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getName().compareTo(name) == 0) {
                return i;
            }
        }
        return -1;
    }

    // @@author A0121410H
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
    private Task getTask(int index) throws TaskNotFoundException {
        if (index >= 0 && index < mTasks.size()) {
            return mTasks.get(index);
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Convenience method to remove a task with an index specified by
     * non-sanitized user input or to be chained with the return value of
     * {@link Logic#getTaskIndexByName(String)}. Throws an exception if the
     * index is out of bounds
     * 
     * @param index
     *            The index of the Task object to be removed
     * @return The Task object that was removed
     * @throws TaskNotFoundException
     *             Thrown when the index specified is out of bounds
     */
    private Task removeTask(int index) throws TaskNotFoundException {
        if (index >= 0 && index < mTasks.size()) {
            Task toRemove = mTasks.remove(index);
            sortByDeadline();
            return toRemove;
        } else {
            throw new TaskNotFoundException();
        }
    }

    // @@author A0133387B

    private Task archive(int index) throws TaskNotFoundException {
        Task archivedTask = new Task();
        if (index >= 0 && index < mTasks.size()) {
            archivedTask = mTasks.get(index);
            // when calling this method, the user is expected to be in the main
            // view and thus,
            // the task must have the ARCHIVE view set to FALSE
            assert (archivedTask.getArchived().equals("FALSE"));
            archivedTask.setArchived("TRUE");
            return archivedTask;
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Mark a task as important
     * 
     * @param index
     *            the ID of the task in current list
     * @return the task that was marked important
     * @throws TaskNotFoundException
     */

    private Task markImportant(int index) throws TaskNotFoundException {
        Task importantTask = new Task();
        if (index >= 0 && index < mTasks.size()) {
            importantTask = mTasks.get(index);
            if (importantTask.getImportant().equals("FALSE")) {
                importantTask.setImportant("TRUE");
            }
            return importantTask;
        } else {
            throw new TaskNotFoundException();
        }
    }

    private Task retrieve(int index) throws TaskNotFoundException {
        Task retrievedTask = new Task();
        if (index >= 0 && index < mTasks.size()) {
            retrievedTask = mTasks.get(index);
            // when calling this method, the user is expected to be in the
            // archive view and thus,
            // the task must have the ARCHIVE view set to TRUE
            assert (retrievedTask.getArchived().equals("TRUE"));
            retrievedTask.setArchived("FALSE");
            return retrievedTask;
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
     */
    private ArrayList<Task> sortByDeadline() {
        Collections.sort(mTasks, new Comparator<Task>() {
            public int compare(Task task1, Task task2) {
                // Each task always has a type before invoking this method
                assert (task1.getType() != null && task1.getType() != null);

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

    /**
     * Get the list of overdue tasks TODO: If no overdue tasks, print FeedbackMessage
     * saying no overdue?
     */
    private ArrayList<Task> viewOverdue() {
        ArrayList<Task> overdueTasks = new ArrayList<Task>();
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).isOverdue())
                overdueTasks.add(mTasks.get(i));
        }

        return overdueTasks;
    }

    /**
     * Get the list of ongoing tasks (meaning not archived)
     */

    private ArrayList<Task> viewOngoing() {
        ArrayList<Task> ongoingTasks = new ArrayList<Task>();
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getArchived().equals("FALSE"))
                ongoingTasks.add(mTasks.get(i));
        }

        return ongoingTasks;
    }

    /**
     * Get the list of archived tasks TODO: Same thing, a FeedbackMessage if there is no
     * archived task?
     */

    private ArrayList<Task> viewArchived() {
        ArrayList<Task> archivedTasks = new ArrayList<Task>();
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getArchived().equals("TRUE"))
                archivedTasks.add(mTasks.get(i));
        }

        return archivedTasks;
    }

    /**
     * Determine the type of a task based on its start (if any) and end (if any)
     * times
     * 
     * @param task
     *            the new task to be categorized
     */
    private void determineType(Task task) {
        assert (task != null);

        if (task.getEnd() == null) {
            // if end == null, float
            task.setType("FLOAT");
            LOGGER.info("[Logic]: Set type of task " + task.getName() + " to "
                    + task.getType());

        } else {
            if (task.getStart() != null) {
                // if end != null and start != null, event
                task.setType("EVENT");
                LOGGER.info("[Logic]: Set type of task " + task.getName()
                        + " to " + task.getType());
            } else {
                // if end != null but start == null, deadline
                task.setType("DEADLINE");
                LOGGER.info("[Logic]: Set type of task " + task.getName()
                        + " to " + task.getType());
            }
        }
    }

}
