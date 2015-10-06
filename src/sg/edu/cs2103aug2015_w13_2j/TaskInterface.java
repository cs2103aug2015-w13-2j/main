package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;

//@@author A0121410H

/**
 * Public interface of the Task class
 * 
 * @author Zhu Chunqi
 */
public interface TaskInterface {
	/*******************************************************
	 * ENUMS
	 *******************************************************/
	
    // Enumerated labels
    public enum Label {
        NAME, CREATED, START, END, TYPE, COMPLETED, ARCHIVED, IMPORTANT
    }
    
    //@@author A0133387B
    public enum Type {
    	// EVENT: START + END
    	// DEADLINE: END
    	// FLOATING: N/A
        EVENT, DEADLINE, FLOATING
    }
    
    //@@author A0124007X
    public enum Completed {
    	TRUE, FALSE
    }
    public enum Archived {
    	TRUE, FALSE
    }
    
    public enum Important {
    	TRUE, FALSE
    }
    
    /*******************************************************
	 * LABEL ACCESSORS
	 * `-> NAME: string
	 * `-> CREATED, START, END: date
	 * `-> TYPE: EVENT / DEADLINE / FLOATING
	 * `-> COMPLETED, ARCHIVED, IMPORTANT: TRUE / FALSE
	 *******************************************************/
    
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
    
    /*******************************************************
	 * (ACCESSORS) NAME: string
	 *******************************************************/
    
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
    
    /*******************************************************
   	 * (ACCESSORS) CREATED, START, END: date
   	 *******************************************************/
    
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
     * Sets the start date of the Task object
     * 
     * @param start
     *            Date object representing the start date or null to unset
     */
    public void setStart(Date start);
    
    /**
     * Sets the start date of the Task object
     * 
     * @param startString
     *            String representing the millisecond epoch
     */
    public void setStart(String startString);

    /**
     * Retrieves the start date of the Task object
     * 
     * @return Date object representing the start date or null if not set
     */
    public Date getStart();
    
    /**
     * Sets the end date of the Task object
     * 
     * @param end
     *            Date object representing the end date or null to unset
     */
    public void setEnd(Date end);
    
    /**
     * Sets the end date of the Task object
     * 
     * @param endString
     *            String representing the millisecond epoch
     */
    public void setEnd(String endString);

    /**
     * Retrieves the end date of the Task object
     * 
     * @return Date object representing the end date or null if not set
     */
    public Date getEnd();
    
    /*******************************************************
   	 * (ACCESSORS) TYPE: EVENT / DEADLINE / FLOATING
   	 *******************************************************/
    
    /**
     * Categorizes a task into one of the 3 types: an Event, Deadline, or Floating Task
     * @param typeString
     *            one of 3 types of tasks: Event / Deadline / Floating
     * @author Nguyen Tuong Van
     * 
    */
    public void setType(String typeString);
    
    public String getType();
    
    /*******************************************************
   	 * (ACCESSORS) COMPLETED, ARCHIVED, IMPORTANT: TRUE / FALSE
   	 *******************************************************/
    
    /**
     * Sets true or false for a property of the task
     * @param propertyString
     *            where property is completed or archived or important
     * 
     * Gets the flag for a property of the task
     * @return String representing true or false for a property of the task
    */
    
    // COMPLETED
    public void setCompleted(String completedString);
    
    public String getCompleted();
    
    // ARCHIVED
    public void setArchived(String archivedString);
    
    public String getArchived();
    
    // IMPORTANT
    public void setImportant(String importantString);
    
    public String getImportant();
    
    /*******************************************************
   	 * STRING <-> TASK CONVERSION METHODS
   	 *******************************************************/
    
    //@@author A0124007X
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
