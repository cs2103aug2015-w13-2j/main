package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import sg.edu.cs2103aug2015_w13_2j.exceptions.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.parser.Parser;

// @@author A0121410H

/**
 * The Task class encapsulates all the data that represents a task: for example
 * the name, deadline etc. Also provides getter and setter methods to manipulate
 * named built-in attributes.
 * 
 * @author Zhu Chunqi
 */
public class Task implements Comparable<Task>, Cloneable {
    public enum Type {
        DEADLINE, EVENT, FLOATING
    }

    public static final String TRUE_VALUE = "TRUE";
    public static final String FALSE_VALUE = "FALSE";
    public static final String LABEL_COMPLETED = "COMPLETED";
    public static final String LABEL_CREATED = "CREATED";
    public static final String LABEL_END = "END";
    public static final String LABEL_IMPORTANT = "IMPORTANT";
    public static final String LABEL_NAME = "NAME";
    public static final String LABEL_START = "START";
    public static final String LABEL_TYPE = "TYPE";

    private final HashMap<String, String> mLabels = new HashMap<String, String>();

    /**
     * Zero parameter constructor that creates and initializes a new
     * {@link Task} object and records the time of creation. Note that the
     * {@link Task} object is <b>invalid</b> as no name has been set.
     */
    public Task() {
        setLabel(LABEL_CREATED, String.valueOf(System.currentTimeMillis()));
    }

    /**
     * Constructor to create a new {@link Task} object and set the name as the
     * string provided. Internally creates a new {@link Task} object and calls
     * {@link Task#setName(String)}.
     * 
     * @param name
     *            String name to give this {@link Task} object.
     */
    public Task(String name) {
        this();
        assert (this.getCreated() != null);
        setName(name);
    }

    /**
     * Sets the string value of the specified label of this {@link Task} object.
     * 
     * @param label
     *            Label value to set.
     * @param value
     *            String value to set the label or {@code null} to unset the
     *            label.
     */
    public void setLabel(String label, String value) {
        mLabels.put(label, value);
    }

    /**
     * Retrieves the string value of the specified label of this {@link Task}
     * object.
     * 
     * @param label
     *            Label value to retrieve.
     * @return String value of the specified label or {@code null} if the label
     *         was not previously set.
     */
    public String getLabel(String label) {
        return mLabels.get(label);
    }

    /**
     * Sets the string value of the {@value #LABEL_NAME} label of this
     * {@link Task} object.
     * 
     * @param name
     *            String value to set the {@value #LABEL_NAME} label.
     */
    public void setName(String name) {
        setLabel(LABEL_NAME, name);
    }

    /**
     * Retrieves the string value of the {@value #LABEL_NAME} label of this
     * {@link Task} object.
     * 
     * @return String value of the {@value #LABEL_NAME} label or {@code null} if
     *         not previously set.
     */
    public String getName() {
        return getLabel(LABEL_NAME);
    }

    /**
     * Retrieves a {@link Date} representation the {@value #LABEL_CREATED} label
     * of this {@link Task} object.
     * 
     * @return {@link Date} representation of the {@value #LABEL_CREATED} label.
     */
    public Date getCreated() {
        return stringToDate(getLabel(LABEL_CREATED));
    }

    /**
     * Sets the value of the {@value #LABEL_START} label of this {@link Task}
     * object by converting the provided {@link Date} object into the string
     * millisecond epoch.
     * 
     * @param start
     *            {@link Date} value to set the {@value #LABEL_START} or
     *            {@code null} to unset the label.
     */
    public void setStart(Date start) {
        setLabel(LABEL_START, dateToString(start));
    }

    /**
     * Sets the value of the {@value #LABEL_START} label of this {@link Task}
     * object based on the current value of the {@value #LABEL_START} label as
     * well as the provided format string. If the {@value #LABEL_START} label
     * has been set previously, its value will be used as the base date from
     * which the unspecified parts of the format string are derived. Otherwise,
     * the current date and time will be used as the base date instead.
     * 
     * @param format
     *            Format string of the form {@code dd_MM_yyyy_HH_mm} where one
     *            or more parts may be specified by user input.
     */
    public void setStart(String format) {
        Date startDate = getStart();
        startDate = updateDate(format, startDate);
        setStart(startDate);
    }

    /**
     * Retrieves a {@link Date} representation of the {@value #LABEL_START}
     * label of this {@link Task} object.
     * 
     * @return {@link Date} representation of the {@value #LABEL_START} label.
     */
    public Date getStart() {
        return stringToDate(getLabel(LABEL_START));
    }

    /**
     * Sets the value of the {@value #LABEL_END} label of this {@link Task}
     * object by converting the provided {@link Date} object into the string
     * millisecond epoch.
     * 
     * @param start
     *            {@link Date} value to set the {@value #LABEL_END} or
     *            {@code null} to unset the label.
     */
    public void setEnd(Date end) {
        setLabel(LABEL_END, dateToString(end));
    }

    /**
     * Sets the value of the {@value #LABEL_END} label of this {@link Task}
     * object based on the current value of the {@value #LABEL_END} label as
     * well as the provided format string. If the {@value #LABEL_END} label has
     * been set previously, its value will be used as the base date from which
     * the unspecified parts of the format string are derived. Otherwise, the
     * current date and time will be used as the base date instead.
     * 
     * @param format
     *            Format string of the form {@code dd_MM_yyyy_HH_mm} where one
     *            or more parts may be specified by user input.
     */
    public void setEnd(String format) {
        Date endDate = getEnd();
        endDate = updateDate(format, endDate);
        setEnd(endDate);
    }

    /**
     * Retrieves a {@link Date} representation of the {@value #LABEL_END} label
     * of this {@link Task} object.
     * 
     * @return {@link Date} representation of the {@value #LABEL_END} label.
     */
    public Date getEnd() {
        return stringToDate(getLabel(LABEL_END));
    }

    /**
     * Sets the enumerated {@link Type} of this {@link Task} object.
     * 
     * @param type
     *            Enumerated {@link Task} {@link Type}.
     */
    public void setType(Type type) {
        setLabel(LABEL_TYPE, type.toString());
    }

    /**
     * Retrieves the enumerated {@link Type} of this {@link Task} object.
     * 
     * @return Enumerated {@link Task} {@link Type}.
     */
    public String getType() {
        if (this.getEnd() == null) {
            return "FLOAT";
        } else {
            if (this.getStart() != null) {
                return "EVENT";
            } else {
                return "DEADLINE";
            }
        }
    }

    /**
     * Sets the boolean value of the {@value #LABEL_COMPLETED} label of this
     * {@link Task} object.
     * 
     * @param completed
     *            Boolean value to set the {@value #LABEL_COMPLETED} label.
     */
    public void setCompleted(boolean completed) {
        setBooleanValue(LABEL_COMPLETED, completed);
    }

    /**
     * Retrieves the boolean value of the {@value #LABEL_COMPLETED} label of
     * this {@link Task} object.
     * 
     * @return Boolean value of the {@value #LABEL_COMPLETED} label or
     *         {@code false} if not previously set.
     */
    public boolean isCompleted() {
        return getBooleanValue(LABEL_COMPLETED);
    }

    /**
     * Sets the boolean value of the {@value #LABEL_IMPORTANT} label of this
     * {@link Task} object.
     * 
     * @param important
     *            Boolean value to set the {@value #LABEL_IMPORTANT} label.
     */
    public void setImportant(boolean important) {
        setBooleanValue(LABEL_IMPORTANT, important);
    }

    /**
     * Retrieves the boolean value of the {@value #LABEL_IMPORTANT} label of
     * this {@link Task} object.
     * 
     * @return Boolean value of the {@value #LABEL_IMPORTANT} label or
     *         {@code false} if not previously set.
     */
    public boolean isImportant() {
        return getBooleanValue(LABEL_IMPORTANT);
    }

    /**
     * Checks if the {@link Date} value of the {@value #LABEL_END} label of this
     * {@link Task} object has passed.
     * 
     * @return {@code True} if the {@value #LABEL_END} label {@link Date} has
     *         passed, {@code false} otherwise.
     */
    public boolean isOverdue() {
        Date deadline = getEnd();
        if (deadline == null) {
            return false;
        } else {
            if (deadline.compareTo(new Date()) < 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Checks if this {@link Task} object is valid, i.e. its
     * {@value #LABEL_NAME} label has been set with a string of non-zero length.
     * Otherwise, an exception will be thrown.
     * 
     * @return {@code True} if this {@link Task} object is valid, {@code false}
     *         otherwise.
     * @throws InvalidTaskException
     *             Thrown when this {@link Task} object is invalid, i.e. it does
     *             not have a non-zero length value set for its
     *             {@value #LABEL_NAME} label.
     */
    public boolean isValid() throws InvalidTaskException {
        if (getName() == null || getName().length() == 0) {
            throw new InvalidTaskException();
        } else {
            return true;
        }
    }

    /**
     * Checks if two {@link Task} objects are equal to each other. Specifically
     * it is assumed that each {@link Task} object is created at a different
     * point in time and their {@value #LABEL_CREATED} labels are compared for
     * equality. If their {@value #LABEL_CREATED} labels the same then their
     * {@value #LABEL_NAME} labels will be compared for equality.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Task) {
            String c1 = ((Task) o).getLabel(LABEL_CREATED);
            String c2 = this.getLabel(LABEL_CREATED);
            if (c1 == null && c2 == null) {
                return false;
            } else if (c1 == null && c2 != null || c1 != null && c2 == null) {
                return false;
            } else if (c1.equals(c2)) {
                String n1 = ((Task) o).getName();
                String n2 = this.getName();
                if (n1 == null && n2 == null) {
                    return true;
                } else if (n1 == null && n2 != null || n1 != null && n2 == null) {
                    return false;
                } else {
                    return n1.equals(n2);
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * The hash code for this {@link Task} object is the hash code of the long
     * value of the {@value #LABEL_CREATED} label.
     * 
     * @return Hash code of this {@link Task} object.
     */
    @Override
    public int hashCode() {
        try {
            Long created = Long.parseLong(getLabel(LABEL_CREATED));
            return created.hashCode();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Creates and returns a deep copy of this {@link Task} object.
     */
    @Override
    public Task clone() {
        Task task = new Task(getName());
        for (String label : mLabels.keySet()) {
            task.setLabel(label, task.getLabel(label));
        }
        return task;
    }

    /**
     * Converts the provided {@link Task} object into its string representation.
     * 
     * @return String representation of this {@link Task} object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : mLabels.entrySet()) {
            sb.append(entry.getKey());
            sb.append(":");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Converts and concatenates the provided list of {@link Task} objects into
     * their string representation with blank lines as separator.
     * 
     * @param tasks
     *            List of {@link Task} objects.
     * @return String representation of the provided list of {@link Task}
     *         objects.
     */
    public static String toString(ArrayList<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    // @@author A0124007X
    
    /**
     * Comparison method used in {@link Task} sorting
     */
    @Override
    public int compareTo(Task task) {
        if (this.isEvent() && task.isEvent()) {
            // BOTH EVENT
            if (!this.getStart().equals(task.getStart())) {
                return this.getStart().compareTo(task.getStart());
            } else if (!this.getEnd().equals(task.getEnd())) {
                return this.getEnd().compareTo(task.getEnd());
            } else {
                return this.getName().compareTo(task.getName());
            }
        } else if (this.isDeadline() && task.isDeadline()) {
            // BOTH DEADLINE
            if (!this.getEnd().equals(task.getEnd())) {
                return this.getEnd().compareTo(task.getEnd());
            } else {
                return this.getName().compareTo(task.getName());
            }
        } else if (this.isFloat() && task.isFloat()) {
            // BOTH FLOAT
            if (this.getStart() != null && task.getStart() != null) {
                // BOTH HAVE START DATE
                if (!this.getStart().equals(task.getStart())) {
                    return this.getStart().compareTo(task.getStart());
                } else {
                    return this.getName().compareTo(task.getName());
                }
            } else if (this.getStart() != null) {
                return -1;
            } else if (task.getStart() != null) {
                return 1;
            } else {
                return this.getName().compareTo(task.getName());
            }
        } else if (this.isDeadline() && task.isEvent()) {
            // ONE DEADLINE, ONE EVENT
            return this.getEnd().compareTo(task.getStart());
        } else if (this.isEvent() && task.isDeadline()) {
            // ONE EVENT, ONE DEADLINE
            return this.getStart().compareTo(task.getEnd());
        } else if ((this.isDeadline() || this.isEvent()) && task.isFloat()) {
            // ONE DEADLINE/EVENT, ONE FLOAT
            return -1;
        } else if (this.isFloat() && (task.isDeadline() || task.isEvent())) {
            // ONE FLOAT, ONE DEADLINE/EVENT
            return 1;
        } else {
            return this.getName().compareTo(task.getName());
        }
    }

    private boolean isFloat() {
        return getType().equals("FLOAT");
    }

    private boolean isEvent() {
        return getType().equals("EVENT");
    }

    private boolean isDeadline() {
        return getType().equals("DEADLINE");
    }

    // @@author A0121410H

    /**
     * Convert the string representation of the millisecond epoch into a
     * {@link Date} object.
     * 
     * @param dateString
     *            String representation of the millisecond epoch.
     * @return {@link Date} object representation of the string millisecond
     *         epoch or {@code null} if the string passed is not a valid long or
     *         is {@code null}.
     */
    private Date stringToDate(String dateString) {
        if (dateString == null) {
            return null;
        } else {
            try {
                return new Date(Long.parseLong(dateString));
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /**
     * Converts the {@link Date} object into a string representing the
     * millisecond epoch.
     * 
     * @param date
     *            {@link Date} object to be converted.
     * @return String representation of the millisecond epoch or {@code null} if
     *         the provided {@link Date} object is {@code null}.
     */
    private String dateToString(Date date) {
        if (date == null) {
            return null;
        } else {
            return String.valueOf(date.getTime());
        }
    }

    /**
     * Returns the boolean value of the specified label. The value of the
     * specified label is assumed to be equal to either {@value #TRUE_VALUE} or
     * {@value #FALSE_VALUE}.
     * 
     * @param label
     *            Label to retrieve the boolean value.
     * @return {@code True} if the label has been set and the value is equal to
     *         {@value #TRUE_VALUE}, {@code false} if the label was not
     *         previously set or the value is equal to {@value #FALSE_VALUE}.
     */
    private boolean getBooleanValue(String label) {
        String value = getLabel(label);
        if (value == null || value.compareTo(FALSE_VALUE) == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sets the boolean value of the specified label. The value of the label is
     * set to either {@value #TRUE_VALUE} or {@value #FALSE_VALUE} corresponding
     * to the boolean parameter.
     * 
     * @param label
     *            Label to set the boolean value.
     * @param value
     *            Boolean value to set the specified label.
     */
    private void setBooleanValue(String label, boolean value) {
        if (value) {
            setLabel(label, TRUE_VALUE);
        } else {
            setLabel(label, FALSE_VALUE);
        }
    }

    /**
     * Pass-through method to {@link ParserInterface#updateDate(String, Date)}
     * which updates the {@link Date} provided based on the format string.
     * 
     * @param format
     *            Format string of the form {@code dd_MM_yyyy_HH_mm} where one
     *            or more parts may be specified by user input.
     * @param date
     *            Base {@link Date} from which the unspecified parts of the
     *            format string are derived. If {@code null}, the current date
     *            time will be used instead.
     * @return Updated {@link Date} object.
     */
    private Date updateDate(String format, Date date) {
        if (date == null) {
            date = new Date();
        }
        return Parser.updateDate(format, date);
    }

    // @@author A0124007X

    /**
     * Parses the provided string and attempts to reconstruct the original
     * {@link Task} object.
     * 
     * @param taskString
     *            String representation of a {@link Task} object.
     * @return Reconstructed {@link Task} object.
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
     * Parses a string containing multiple {@link Task} objects separated by
     * empty lines into a list of {@link Task} objects.
     * 
     * @param tasksString
     *            String representation of multiple {@link Task} objects.
     * @return List of reconstructed {@link Task} objects.
     * @throws InvalidTaskException
     *             Thrown when a reconstructed {@link Task} object is invalid,
     *             i.e. does not have a task name.
     */
    public static ArrayList<Task> parseTasks(String tasksString)
            throws InvalidTaskException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String[] taskStrings = tasksString.split("\n\n");
        for (String taskString : taskStrings) {
            // Check that the task string is not empty nor just whitespace
            // Note: refer to issue #124
            if (!taskString.isEmpty() && !taskString.matches("\\s+")) {
                Task task = Task.parseTask(taskString);
                if (task.isValid()) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }
}
