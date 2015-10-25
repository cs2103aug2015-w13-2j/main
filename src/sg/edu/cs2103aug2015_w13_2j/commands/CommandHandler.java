package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.Iterator;
import java.util.List;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.Task.Type;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.parser.Parser;
import sg.edu.cs2103aug2015_w13_2j.parser.Token;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;

// @@author A0121410H

/**
 * Abstract command handler class which is inherited by all commands
 * 
 * @author Zhu Chunqi
 */
public abstract class CommandHandler {
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
     * 
     * @return FeebackMessage object containing the relevant feedback to the
     *         user
     */
    public abstract FeedbackMessage execute(Logic logic, Command command);

    /**
     * Retrieves a list of reserved keywords which are handled by this command.
     * They can include simple aliases or completely different functionality.
     * Note that no more than a single command handler may be attached to a
     * single keyword, the later registrant will be ignored and an exception
     * will be thrown
     * 
     * @return A list of keywords handled by this command handler
     */
    public abstract List<String> getReservedKeywords();

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
        assert(task != null);

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
}
