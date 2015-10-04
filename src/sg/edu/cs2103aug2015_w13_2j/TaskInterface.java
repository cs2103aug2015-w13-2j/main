package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;

//@@author A0121410H

/**
 * Public interface of the Task class
 * 
 * @author Zhu Chunqi
 */
public interface TaskInterface {
    // Enumerated labels
    public enum Label {
        CREATED, DEADLINE, NAME, TYPE, STATUS
    }
    
    //@author Nguyen Tuong Van
  
    public enum Type {
        EVENT, DUE, FLOAT
    }
    
    public enum Status {
        ONGOING, COMPLETED, OVERDUE, ARCHIVED, DELETED
    }
    
    
    /**
     * Sets a label-value pair of the Task object
     * 
     * @param label
     *            The label to be set
     * @param value
     *            The value to set the label to or null to unset the label
     */
    public void setLabel(Label label, String value);

    /**
     * Retrieves the value of a label of the Task object
     * 
     * @return The value of the label or null if not set
     */
    public String getLabel(Label label);
    
    /**
     * Sets the name of this Task object as a label-value pair
     * 
     * @param name
     *            The string to set the task's name to
     */
    public void setName(String name);

    /**
     * Retrieves the name label of this Task object
     * 
     * @return The name of the Task object or null if not set
     */
    public String getName();

    /**
     * Sets the date and time the Task object was created.
     * Used only in parseTask()
     * 
     * @param createdString
     *            String representing the millisecond epoch
     */
    public void setCreated(String createdString);
    
    /**
     * Convenience method to retrieve the date and time this Task object was
     * created in a Date object
     * 
     * @return Date object representing the date and time created
     */
    public Date getCreated();

    /**
     * Sets the deadline of the Task object
     * 
     * @param deadline
     *            Date object representing the deadline or null to unset
     */
    public void setDeadline(Date deadline);
    
    /**
     * Sets the deadline of the Task object
     * Used only in parseTask()
     * 
     * @param deadlineString
     *            String representing the millisecond epoch
     */
    public void setDeadline(String deadlineString);

    /**
     * Retrieves the deadline of the Task object
     * 
     * @return Date object representing the deadline or null if not set
     */
    public Date getDeadline();
    
    /**
     * Converts Task object into string format for storage in the data file
     * 
     * @return String representing the Task and its attributes
     */
    public String toString();
    
    /**
     * Converts a string into its corresponding Task object
     * Note: Exception must be thrown in all methods that call parseTask()
     * 
     * @param taskString
     *            A Task in its string format
     * @return The corresponding Task object after conversion
     */
    public static Task parseTask(String taskString) throws Exception {
    	Task task = new Task();
    	// To be overridden in Task.java
        return task;
    }
}
