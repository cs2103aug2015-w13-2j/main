package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.Task.Type;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Parser;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;

// @@author A0121410H

/**
 * Base abstract class inherited by all command handlers. Provides default
 * methods to set and retrieve the name, syntax, flags, options and reserved
 * keyword strings of the command handlers. Also provides a method to update
 * {@link Task} objects from information contained within a {@link Command}
 * object.
 * 
 * @author Zhu Chunqi
 */
public abstract class CommandHandler {
    protected static final String FLAG_START = "-s: Specifies the start date/time of the Task.";
    protected static final String FLAG_END = "-e: Specifies the end date/time of the Task.";

    protected static final String OPTION_DATETIME = "DATETIME: Any valid combination of the supported date and time formats.";
    protected static final String OPTION_FILTER_NAME = "FILTER_NAME: A valid filter name.";
    protected static final String OPTION_TASK_ID = "TASK_ID: The index of the Task as displayed.";
    protected static final String OPTION_TASK_NAME = "TASK_NAME: Name to be given to this Task. Must be surrounded by either single (') or double quotes (\").";
    protected static final String OPTION_SEARCH_TERM = "SEARCH_TERM: Any word or quoted phrase.";

    private String mName;
    private String mSyntax;
    private String[] mFlags;
    private String[] mOptions;
    private String[] mReserved;

    /**
     * Constructor for all {@link CommandHandler} objects. Stores the name,
     * syntax, supported flags, supported options and reserved keyword strings
     * of the {@link CommandHandler}. Each inheriting {@link CommandHandler}
     * <b>must</b> provide valid parameters as the values are <b>not</b>
     * initialized by default.
     * 
     * @param name
     *            Name of this {@link CommandHandler}.
     * @param syntax
     *            Syntax required by this {@link CommandHandler}.
     * @param flags
     *            String array of flags supported and their descriptions.
     * @param options
     *            String array of options supported and their descriptions.
     * @param reserved
     *            Array of reserved keyword strings handled by this
     *            {@link CommandHandler}.
     */
    public CommandHandler(String name, String syntax, String[] flags,
            String[] options, String[] reserved) {
        mName = name;
        mSyntax = syntax;
        mFlags = flags;
        mOptions = options;
        mReserved = reserved;
    }

    /**
     * Executes the functionality of this {@link CommandHandler} based on the
     * provided {@link Command} object.
     * 
     * @param logic
     *            Dependency injection of {@link LogicInterface} component to
     *            allow this {@link CommandHandler} to function.
     * @param command
     *            {@link Command} object containing parsed {@link Token} objects
     *            from user input.
     */
    public abstract void execute(LogicInterface logic, Command command);

    /**
     * Retrieves the name of this {@link CommandHandler}.
     * 
     * @return Name of this {@link CommandHandler}.
     */
    public String getName() {
        return mName;
    }

    /**
     * Retrieves the syntax required by this {@link CommandHandler}.
     * 
     * @return Syntax required by this {@link CommandHandler}.
     */
    public String getSyntax() {
        return mSyntax;
    }

    /**
     * Retrieves a <b>sorted</b> list of flags and their descriptions supported
     * by this {@link CommandHandler}.
     * 
     * @return Sorted list of flags and their descriptions.
     */
    public List<String> getFlags() {
        return getSortedList(mFlags);
    }

    /**
     * Retrieves a <b>sorted</b> list of options and their descriptions that are
     * supported by this {@link CommandHandler}.
     * 
     * @return Sorted list of options and their descriptions.
     */
    public List<String> getOptions() {
        return getSortedList(mOptions);
    }

    /**
     * Retrieves a <b>sorted</b> list of reserved keyword strings handled by
     * this {@link CommandHandler}. The list may include simple aliases or
     * keywords with completely different functionality.
     * 
     * @return Sorted list of reserved keyword strings handled by this
     *         {@link CommandHandler}.
     */
    public List<String> getReservedKeywords() {
        return getSortedList(mReserved);
    }

    /**
     * Checks if this {@link CommandHandler} requires a display refresh after
     * execution. Defaults to {@code true} for all {@link CommandHandler}s
     * unless {@link #shouldDisplay()} is explicitly overridden to return
     * {@code false}.
     * 
     * @return {@code True} if this {@link CommandHandler} requires a display
     *         refresh after execution, {@code false} otherwise.
     */
    public boolean shouldDisplay() {
        return true;
    }

    /**
     * Updates the provided {@link Task} object based on the provided
     * {@link Command} object.
     * 
     * @param command
     *            {@link Command} object created from user input.
     * @param task
     *            {@link Task} object to be updated.
     * @throws InvalidTaskException
     *             Thrown when the {@link Task} object modified by the
     *             {@link Command} object becomes invalid.
     */
    public void updateTask(Command command, Task task)
            throws InvalidTaskException {
        Iterator<Token> iter = command.iterator();
        while (iter.hasNext()) {
            Token token = iter.next();
            switch (token.type) {
            case FLAG :
                // Flags which expect the next token to be a date
                String flag = token.value;
                switch (flag) {
                case Parser.FLAG_END :
                    // Falls through
                case Parser.FLAG_START :
                    if (iter.hasNext()) {
                        Token nextToken = iter.next();
                        if (nextToken.type == Token.Type.DATE) {
                            if (flag.compareTo(Parser.FLAG_END) == 0) {
                                task.setEnd(nextToken.value);
                            } else if (flag.compareTo(Parser.FLAG_START) == 0) {
                                task.setStart(nextToken.value);
                            }
                        }
                    }
                    break;
                }
                break;
            case NAME :
                if (token.value.length() == 0) {
                    throw new InvalidTaskException();
                } else {
                    task.setName(token.value);
                    break;
                }
            default :
                // Do nothing
                break;
            }
        }
        task.isValid();
        determineType(task);
    }

    // @@author A0133387B

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
            task.setType(Type.FLOATING);
            // LOGGER.info("Set type of task " + task.getName() + " to " +
            // task.getType());

        } else {
            if (task.getStart() != null) {
                // if end != null and start != null, event
                task.setType(Type.EVENT);
                // LOGGER.info("Set type of task " + task.getName() + " to " +
                // task.getType());
            } else {
                // if end != null but start == null, deadline
                task.setType(Type.DEADLINE);
                // LOGGER.info("Set type of task " + task.getName() + " to " +
                // task.getType());
            }
        }
    }

    // @@author A0121410H

    /**
     * Converts an array of strings into a sorted list of strings.
     * 
     * @param array
     *            Array of strings to be sorted.
     * @return Sorted list of strings.
     */
    private List<String> getSortedList(String[] array) {
        List<String> list = Arrays.asList(array);
        Collections.sort(list);
        return list;
    }
}
