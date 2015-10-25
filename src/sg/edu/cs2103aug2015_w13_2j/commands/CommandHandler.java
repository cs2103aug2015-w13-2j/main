package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.Task.Type;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Parser;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;

// @@author A0121410H

/**
 * Abstract command handler class which is inherited by all commands
 * 
 * @author Zhu Chunqi
 */
public abstract class CommandHandler {
    protected static final String FLAG_START = "-s: Specifies the start date/time of the Task";
    protected static final String FLAG_END = "-e: Specifies the end date/time of the Task";

    protected static final String OPTION_TASK_ID = "TASK_ID: The index of the Task as shown in the ID column.";
    protected static final String OPTION_TASK_NAME = "TASK_NAME: Any short phrase to identify this particular Task. Must be surrounded by either single or double quotes.";
    protected static final String OPTION_DATETIME = "DATETIME: Any valid combination of the supported date and time formats.";
    protected static final String OPTION_FILTER_NAME = "FILTER_NAME: A valid filter name and argument if and argumer is required.";
    protected static final String OPTION_COMMAND_NAME = "COMMAND_NAME: A valid command name.";

    private String mName;
    private String mSyntax;
    private String[] mFlags;
    private String[] mOptions;
    private String[] mReserved;

    public CommandHandler(String name, String syntax, String[] flags,
            String[] options, String[] reserved) {
        mName = name;
        mSyntax = syntax;
        mFlags = flags;
        mOptions = options;
        mReserved = reserved;
    }

    /**
     * Executes the command with additional parameters passed in as parsed
     * tokens
     * 
     * @param logic
     *            Dependency injection of the Logic component for the command
     *            handler to act upon
     * @param command
     *            Command object containing parsed Tokens of the command
     * @param tasks
     *            Reference to master list of tasks. Note that any modification
     *            to this reference <b>will</b> affect the master list
     */
    public abstract void execute(Logic logic, Command command);

    /**
     * Retrieves the name of this CommandHandler
     * 
     * @return Name of this CommandHandler
     */
    public String getName() {
        return mName;
    }

    /**
     * Retrieves the syntax required by this CommandHandler
     * 
     * @return Syntax required by this CommandHandler
     */
    public String getSyntax() {
        return mSyntax;
    }

    /**
     * Retrieves the list of flags with their descriptions that are supported by
     * this CommandHandler
     * 
     * @return List of flags and their descriptions
     */
    public List<String> getFlags() {
        return getSortedList(mFlags);
    }

    /**
     * Retrieves the list of options with their descriptions that are supported
     * by this CommandHandler
     * 
     * @return List of options and their descriptions
     */
    public List<String> getOptions() {
        return getSortedList(mOptions);
    }

    /**
     * Retrieves the list of reserved keywords recognized by this
     * CommandHandler. The list may include simple aliases or keywords with
     * completely different functionality. Note that only one CommandHandler may
     * be attached to a single keyword
     * 
     * @return A list of keywords recognized by this CommandHandler
     */
    public List<String> getReservedKeywords() {
        return getSortedList(mReserved);
    }

    /**
     * Checks if this CommandHandler requires a display refresh after execution.
     * Defaults to true for all CommandHandlers unless {@link #shouldDisplay()}
     * is explicitly overridden and returning false. CommandHandlers which
     * display directly to the TextPane with the {@link Logic#display(String)}
     * method should override and return false
     * 
     * @return True if this CommandHandler requires a display refresh after
     *         execution, false otherwise
     */
    public boolean shouldDisplay() {
        return true;
    }

    /**
     * Updates the passed in Task object based on the parsed tokens
     * 
     * @param command
     *            Command object containing parsed Tokens of the command
     * @param task
     *            The Task object to be updated
     * @throws InvalidTaskException
     *             Thrown when the Task constructed from the parsed tokens is
     *             invalid
     */
    public void updateTask(Command command, Task task)
            throws InvalidTaskException {
        Iterator<Token> iter = command.iterator();
        while (iter.hasNext()) {
            Token token = iter.next();
            switch (token.type) {
            case FLAG:
                String flag = token.value;
                switch (flag) {
                // Flags which expect the next token to be a date
                case Parser.FLAG_END:
                case Parser.FLAG_START:
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
            case NAME:
                task.setName(token.value);
                break;
            default:
                // Do nothing
                break;
            }
        }
        task.isValid();
        determineType(task);
    }

    /**
     * Determine the type of a task based on its start (if any) and end (if any)
     * times
     * 
     * @param task
     *            the new task to be categorized
     * @@author A0133387B
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

    private List<String> getSortedList(String[] array) {
        List<String> list = Arrays.asList(array);
        Collections.sort(list);
        return list;
    }
}
