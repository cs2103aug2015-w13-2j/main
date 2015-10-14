package sg.edu.cs2103aug2015_w13_2j;

import sg.edu.cs2103aug2015_w13_2j.FeedbackType;

//@@author A0121410H

/**
 * Enumeration of user feedback messages to the user. As this is implemented as
 * an enum, the messages do not contain dynamic data. Each enum element is self
 * contained with the message text {@link Message#getMessage()} and styling
 * {@link Message#getType()} to be shown to the user
 * 
 * @author Zhu Chunqi
 */
public enum FeedbackMessage {
    // Used to clear the feedback label
    CLEAR("", FeedbackType.INFO),

    // Errors
    ERROR_INVALID_TASK("Invalid task. Did you remember to include quotes around the task name?", FeedbackType.ERROR),
    ERROR_TASK_NOT_FOUND("Task not found. Did you enter the index correctly?", FeedbackType.ERROR),
    ERROR_INVALID_SEARCH_TERM("Invalid search term. Did you enter it with double quotes?", FeedbackType.ERROR),
    ERROR_COMMAND_NOT_IMPLEMENTED("The command that you have entered has not been implemented.", FeedbackType.ERROR),
    ERROR_COMMAND_NOT_RECOGNIZED("Command not recognized.", FeedbackType.ERROR),

    // Informative
    LOGIC_ADDED("Task added successfully.", FeedbackType.INFO),
    LOGIC_EDITED("Task edited successfully.", FeedbackType.INFO),
    LOGIC_DELETED("Task deleted successfully.", FeedbackType.INFO),
    LOGIC_ARCHIVED("Task archived successfully.", FeedbackType.INFO),
    LOGIC_RETRIEVED("Task retrieved successfully.", FeedbackType.INFO),
    LOGIC_SEARCH_FOUND("Task(s) found.", FeedbackType.INFO),
    LOGIC_SEARCH_NOT_FOUND("Task(s) not found.", FeedbackType.INFO);

    private String mMsg;
    private FeedbackType mType;

    private FeedbackMessage(String s, FeedbackType type) {
        mMsg = s;
        mType = type;
    }

    /**
     * Retrieves the message text
     * 
     * @return The message text
     */
    public String getMessage() {
        return mMsg;
    }

    /**
     * Retrieves the FeedbackType enum containing the styling information for
     * this message
     * 
     * @return FeedbackType enum for this message
     * @see FeedbackType
     */
    public FeedbackType getType() {
        return mType;
    }
}