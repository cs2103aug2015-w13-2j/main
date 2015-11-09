package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Date;

import sg.edu.cs2103aug2015_w13_2j.Task.Type;

// @@author A0121410H

/**
 * Interface for the {@link Task} class. Provides methods to modify a 
 * {@link Task} object as well as methods to retrieve that {@link Task} 
 * object's attributes.
 */
public interface TaskInterface extends Comparable<Task> {
    public class InvalidTaskException extends Exception {
        private static final long serialVersionUID = 4591179171294898925L;
    }

    public class TaskNotFoundException extends Exception {
        private static final long serialVersionUID = 1619684577187818793L;
    }

    /**
     * Sets a label-value pair of the {@link Task} object.
     * 
     * @param label
     *            The label to be set.
     * @param value
     *            The value to set the label to or null to unset the label.
     */
    public void setLabel(String label, String value);

    /**
     * Retrieves the value of a label of the {@link Task} object.
     * 
     * @param label
     *            The label to be retrieved.
     * @return The value of the label or null if not set.
     */
    public String getLabel(String label);

    /**
     * Sets the name of this {@link Task} object as a label-value pair.
     * 
     * @param name
     *            The string to set the task's name to.
     */
    public void setName(String name);

    /**
     * Retrieves the name label of this {@link Task} object.
     * 
     * @return The name of the Task object or null if not set.
     */
    public String getName();

    /**
     * Convenience method to retrieve the date and time this {@link Task} 
     * object was created in a Date object.
     * 
     * @return Date object representing the date and time created.
     */
    public Date getCreated();

    /**
     * Sets the start date of the {@link Task} object.
     * 
     * @param start
     *            Date object representing the start date or null to unset.
     */
    public void setStart(Date start);

    /**
     * Sets or updates the start date of the {@link Task} object based on 
     * the provided format string.
     * 
     * @param format
     *            String of the format dd_MM_yyyy_HH_mm as produced by
     *            parsing the Date of the {@link Task}
     */
    public void setStart(String format);

    /**
     * Retrieves the start date of the {@link Task} object.
     * 
     * @return Date object representing the start date or null if not set.
     */
    public Date getStart();

    /**
     * Sets the end date of the {@link Task} object.
     * 
     * @param end
     *            Date object representing the end date or null to unset.
     */
    public void setEnd(Date end);

    /**
     * Sets or updates the end date of the {@link Task} object based on 
     * the provided format string.
     * 
     * @param format
     *            String of the format dd_MM_yyyy_HH_mm as produced by
     *            parsing the Date of the {@link Task}
     */
    public void setEnd(String format);

    /**
     * Retrieves the end date of the {@link Task} object.
     * 
     * @return Date object representing the end date or null if not set.
     */
    public Date getEnd();

    /**
     * Sets the type of the task as one of 3 types: an Event, Deadline, or
     * Floating Task.
     * 
     * @param type
     *            Enum of the type of tasks.
     */
    public void setType(Type type);

    /**
     * Sets the COMPLETED label of a task as True or False.
     * 
     * @param completed
     *          Specified True or False value to be set.
     */
    public void setCompleted(boolean completed);

    /**
     * Checks if this {@link Task} object is marked as completed. The internal 
     * label COMPLETED of the task is checked.
     * 
     * @return True if the COMPLETED label is set, and false if it is not set.
     */
    public boolean isCompleted();

    /**
     * Sets the IMPORTANT label of a task as True or False.
     * 
     * @param important
     *          Specified True or False value to be set.
     */
    public void setImportant(boolean important);

    /**
     * Checks if this {@link Task} object is marked as important. The internal 
     * label IMPORTANT of the task is checked.
     * 
     * @return True if the IMPORTANT label is set, and false if it is not set.
     */
    public boolean isImportant();

    /**
     * Checks if this {@link Task}object is overdue. The internal label END 
     * of the task is compared with the current date time.
     * 
     * @return True if the END label is set and is before the current date time,
     *         false if otherwise or END is not set.
     */
    public boolean isOverdue();

    /**
     * Checks if this {@link Task} object is valid. The only condition is that it
     * <b>must</b> have a non-zero length name set, all other attributes can be
     * missing. An invalid Task object should not be used.
     * 
     * @return True if the Task object is valid, false otherwise.
     * @throws InvalidTaskException
     *          Thrown when the Task object being checked is invalid.
     */
    public boolean isValid() throws InvalidTaskException;

    // @@author A0124007X

    /**
     * Converts a string into its corresponding {@link Task} object.
     * 
     * @param taskString
     *            The string representing a {@link Task} object.
     * @return The constructed Task object.
     */
    public static Task parseTask(String taskString) {
        Task task = new Task();
        String[] attributes = taskString.split("\n");
        for (String pair : attributes) {
            // Line cannot be empty
            if (!pair.isEmpty()) {
                // Only splits by the first colon
                String[] pairTokens = pair.split(":", 2);

                // Colon must exist
                if (pairTokens.length == 2) {
                    String label = pairTokens[0].toUpperCase();
                    String value = pairTokens[1];
                    task.setLabel(label, value);
                }
            }
        }
        return task;
    }
    
    /**
     * Converts the text in a FunDUE data file to a list of {@link Task}
     * objects.
     * 
     * @param s
     *         The String representing text in FunDUE data file.
     * @return ArrayList of valid Tasks that have been read from the 
     *         FunDUE data file.
     * @throws InvalidTaskException
     *          Thrown when the Task object being checked is invalid.
     */
    public static ArrayList<Task> parseTasks(String s)
            throws InvalidTaskException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String[] taskStrings = s.split("\n\n");
        for (String taskString : taskStrings) {
            // Check that the task string is not empty nor just whitespace
            // Note: refer to issue #124
            if (!taskString.isEmpty() && !taskString.matches("\\s+")) {
                Task task = TaskInterface.parseTask(taskString);
                if (task.isValid()) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }
}
