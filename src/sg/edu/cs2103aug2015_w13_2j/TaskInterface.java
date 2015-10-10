package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;

//@@author A0121410H

public interface TaskInterface {
	/**
	 * Checks if this Task object is valid. The only condition is that it
	 * <b>must</b> have a non-zero length name set, all other attributes can be
	 * missing. An invalid Task object should not be used
	 * 
	 * @return True if the Task object is valid, false otherwise
	 * @throws InvalidTaskException
	 *             Thrown when the Task object being checked is invalid
	 */
	public boolean isValid() throws InvalidTaskException;

	/**
	 * Sets a label-value pair of the Task object
	 * 
	 * @param label
	 *            The label to be set
	 * @param value
	 *            The value to set the label to or null to unset the label
	 */
	public void setLabel(String label, String value);

	/**
	 * Retrieves the value of a label of the Task object
	 * 
	 * @return The value of the label or null if not set
	 */
	public String getLabel(String label);

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
	 * Sets the date and time the Task object was created. Used only in
	 * parseTask()
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
	 * (ACCESSORS) TYPE: EVENT / DEADLINE / FLOAT
	 *******************************************************/

	/**
	 * Categorizes a task into one of the 3 types: an Event, Deadline, or
	 * Floating Task
	 * 
	 * @param typeString
	 *            one of 3 types of tasks: Event / Deadline / Float
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
	 * 
	 * @param propertyString
	 *            where property is completed or archived or important
	 * 
	 *            Gets the flag for a property of the task
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

	// @@author A0121410H
	public class InvalidTaskException extends Exception {
		private static final long serialVersionUID = 4591179171294898925L;
	}
	
	public class TaskNotFoundException extends Exception {
		private static final long serialVersionUID = 1619684577187818793L;
	}

	// @@author A0124007X
	/**
	 * Converts a string into its corresponding Task object
	 * 
	 * @param taskString
	 *            The string representing a Task object
	 * @return The constructed Task object
	 */
	public static Task parseTask(String taskString) {
		Task task = new Task();
		String[] attributes = taskString.split("\r|\n");
		for (String pair : attributes) {
			if (!pair.isEmpty()) {
				// Only splits by the first colon
				String[] pairTokens = pair.split(":", 2);
				if (pairTokens.length == 2) {
					String label = pairTokens[0].toUpperCase();
					String value = pairTokens[1];
					task.setLabel(label, value);
				}
			}
		}
		return task;
	}
}
