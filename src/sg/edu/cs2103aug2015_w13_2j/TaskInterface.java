package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;

import sg.edu.cs2103aug2015_w13_2j.Task.Type;

// @@author A0121410H

public interface TaskInterface extends Comparable<Task> {
    public class InvalidTaskException extends Exception {
        private static final long serialVersionUID = 4591179171294898925L;
    }

    public class TaskNotFoundException extends Exception {
        private static final long serialVersionUID = 1619684577187818793L;
    }

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
     * Sets or updates the start date of the Task object based on the provided
     * format string
     * 
     * @param format
     *            String of the format dd_MM_yyyy_HH_mm as produced by
     *            {@link #parseDate(String)}
     */
    public void setStart(String format);

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
     * Sets or updates the end date of the Task object based on the provided
     * format string
     * 
     * @param format
     *            String of the format dd_MM_yyyy_HH_mm as produced by
     *            {@link #parseDate(String)}
     */
    public void setEnd(String format);

    /**
     * Retrieves the end date of the Task object
     * 
     * @return Date object representing the end date or null if not set
     */
    public Date getEnd();

    /**
     * Sets the type of the task as one of 3 types: an Event, Deadline, or
     * Floating Task
     * 
     * @param type
     *            Enum of the type of tasks
     */
    public void setType(Type type);

    /**
     * Sets true or false for a property of the task
     * 
     * @param propertyString
     *            where property is completed or archived or important
     * 
     *            Gets the flag for a property of the task
     * @return String representing true or false for a property of the task
     */

    public void setCompleted(boolean completed);

    public boolean isCompleted();

    public void setArchived(boolean archived);

    public boolean isArchived();

    public void setImportant(boolean important);

    public boolean isImportant();

    /**
     * Checks if this Task object is overdue. The internal label END of the task
     * is compared with the current date time
     * 
     * @return True if the END label is set and is before the current date time,
     *         false if otherwise or END is not set
     */
    public boolean isOverdue();

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

    public int compareTo(Task task);
}
