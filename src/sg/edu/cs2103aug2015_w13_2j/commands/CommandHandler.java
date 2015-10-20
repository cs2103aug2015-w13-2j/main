package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;

//@@author A0121410H

/**
 * Abstract command class which is inherited by all commands
 * 
 * @author Zhu Chunqi
 */
public abstract class CommandHandler {
    /**
     * Executes the command with additional parameters passed in as parsed
     * tokens
     * 
     * @param tasks
     *            Reference to master list of tasks. Note that any modification
     *            to this reference <b>will</b> affect the master list
     * @param tokens
     *            The command as parsed tokens
     * @return FeebackMessage object containing the relevant feedback to the
     *         user
     */
    public abstract FeedbackMessage execute(
            ArrayList<Pair<Token, String>> tokens);

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
     * @param tokens
     *            The tokens parsed from the command <b>including</b> the
     *            command token itself but <b>excluding</b> any previously used
     *            identifiers
     * @param task
     *            The Task object to be updated
     * @throws InvalidTaskException
     *             Thrown when the Task constructed from the parsed tokens is
     *             invalid
     */
    public void updateTask(ArrayList<Pair<Token, String>> tokens, Task task)
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
                        System.out.println(nextPair.getValue());
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
            task.setType("FLOAT");
         //   LOGGER.info("Set type of task " + task.getName() + " to " + task.getType());
            
        } else {
            if (task.getStart() != null) {
                // if end != null and start != null, event
                task.setType("EVENT"); 
            //    LOGGER.info("Set type of task " + task.getName() + " to " + task.getType());
            } else {
                // if end != null but start == null, deadline
                task.setType("DEADLINE");   
           //     LOGGER.info("Set type of task " + task.getName() + " to " + task.getType());
            }
        }
    }    
}
