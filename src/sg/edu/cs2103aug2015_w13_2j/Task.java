package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sg.edu.cs2103aug2015_w13_2j.parser.ParserInterface;

// @@author A0121410H

/**
 * Task class that encapsulates all the data that represents a task for e.g. the
 * name, deadline etc. Also provides getter and setter methods to manipulate
 * named built-in attributes.
 * 
 * @author Zhu Chunqi
 */
public class Task implements TaskInterface, Comparable<Task>, Cloneable {
    public enum Type {
        DEADLINE, EVENT, FLOATING
    }

    private static final String TRUE_VALUE = "TRUE";
    private static final String FALSE_VALUE = "FALSE";
    private static final String LABEL_COMPLETED = "COMPLETED";
    private static final String LABEL_CREATED = "CREATED";
    private static final String LABEL_END = "END";
    private static final String LABEL_IMPORTANT = "IMPORTANT";
    private static final String LABEL_NAME = "NAME";
    private static final String LABEL_START = "START";
    private static final String LABEL_TYPE = "TYPE";

    private HashMap<String, String> mLabels = new HashMap<String, String>();

    /**
     * Zero parameter constructor that creates and initializes a new Task object
     * and records the time of creation
     */
    public Task() {
        setLabel(LABEL_CREATED, String.valueOf(System.currentTimeMillis()));
    }

    /**
     * Constructor to create a new Task object and set the task name to the
     * string provided. Internally creates a new Task object and calls
     * {@link Task#setName(String)}
     * 
     * @param name
     *            String to set the name of the new Task object to
     */
    public Task(String name) {
        this();
        assert (this.getCreated() != null);
        setName(name);
    }

    /**
     * Copy method to create a deep copy of this Task object
     * 
     * @return A new instance of this Task object with all the same attributes
     */
    public Task newInstance() {
        Task newTask = new Task(this.getName());
        Map<String, String> mLabelsMap = this.mLabels;
        for (Map.Entry<String, String> label : mLabelsMap.entrySet()) {
            String labelName = label.getKey();
            String labelValue = label.getValue();
            newTask.setLabel(labelName, labelValue);
        }
        return newTask;
    }

    public void setLabel(String label, String value) {
        mLabels.put(label, value);
    }

    public String getLabel(String label) {
        return mLabels.get(label);
    }

    public void setName(String name) {
        setLabel(LABEL_NAME, name);
    }

    public String getName() {
        return getLabel(LABEL_NAME);
    }

    public Date getCreated() {
        return stringToDate(getLabel(LABEL_CREATED));
    }

    public void setStart(Date start) {
        setLabel(LABEL_START, dateToString(start));
    }

    public void setStart(String format) {
        Date startDate = getStart();
        startDate = updateDate(format, startDate);
        setStart(startDate);
    }

    public Date getStart() {
        return stringToDate(getLabel(LABEL_START));
    }

    public void setEnd(Date end) {
        setLabel(LABEL_END, dateToString(end));
    }

    public void setEnd(String format) {
        Date endDate = getEnd();
        endDate = updateDate(format, endDate);
        setEnd(endDate);
    }

    public Date getEnd() {
        return stringToDate(getLabel(LABEL_END));
    }

    public void setType(Type type) {
        setLabel(LABEL_TYPE, type.toString());
    }

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

    public void setCompleted(boolean completed) {
        setBooleanValue(LABEL_COMPLETED, completed);
    }

    public boolean isCompleted() {
        return getBooleanValue(LABEL_COMPLETED);
    }

    public void setImportant(boolean important) {
        setBooleanValue(LABEL_IMPORTANT, important);
    }

    public boolean isImportant() {
        return getBooleanValue(LABEL_IMPORTANT);
    }

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

    public boolean isValid() throws InvalidTaskException {
        if (getLabel("NAME") == null || getLabel("NAME").length() == 0) {
            throw new InvalidTaskException();
        } else {
            return true;
        }
    }

    /**
     * Checks if two Task objects are equal to each other. Specifically it is
     * assumed that each Task object is created at a different point in time and
     * their CREATED labels are compared for equality
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Task) {
            String c1 = ((Task) o).getLabel(LABEL_CREATED);
            String c2 = this.getLabel(LABEL_CREATED);
            if (c1.equals(c2)) {
                String n1 = ((Task) o).getName();
                String n2 = this.getName();
                return n1.equals(n2);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * The hash code for the Task object is the hash code returned by the long
     * value of the CREATED label
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

    @Override
    public Task clone() {
        Task task = new Task(getName());
        for (String label : mLabels.keySet()) {
            task.setLabel(label, task.getLabel(label));
        }
        return task;
    }

    /**
     * Converts the provided Task object into its string representation
     * 
     * @return String representing the Task object
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
     * Converts the provided list of Task objects into their string
     * representation, concatenates all of them with blank lines as separators
     * and returns the result as a string
     * 
     * @param tasks
     *            List of Task objects to convert
     * @return String representation of the provided list of Task objects
     *         concatenated together with blank lines as separators
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
     * Utility method to convert a String millisecond epoch to a Date object
     * 
     * @param date
     *            String representing the millisecond epoch
     * @return Date object created from the millisecond epoch or null if string
     *         passed is not a valid long or is null
     */
    private Date stringToDate(String date) {
        if (date == null) {
            return null;
        } else {
            try {
                return new Date(Long.parseLong(date));
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /**
     * Utility function to convert a Date object to String representing the
     * millisecond epoch
     * 
     * @param date
     *            Date object to be converted
     * @return String object representing the date as the millisecond epoch or
     *         null if the provided date object is null
     */
    private String dateToString(Date date) {
        if (date == null) {
            return null;
        } else {
            return String.valueOf(date.getTime());
        }
    }

    /**
     * Returns the boolean value of a particular label. The value of the label
     * is assumed to be equal to either {@link Task#TRUE_VALUE} or
     * {@link Task#FALSE_VALUE}
     * 
     * @param label
     *            The label to retrieve the boolean value
     * @return True if the label has been set and the value is equal to
     *         {@link Task#TRUE_VALUE}, false if label is not set or the value
     *         is equal to {@link Task#FALSE_VALUE}
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
     * Sets the boolean value of a particular label. The value of the label is
     * set to either {@link Task#TRUE_VALUE} or {@link Task#FALSE_VALUE}
     * depending on the boolean parameter
     * 
     * @param label
     *            The label to set the boolean value for
     * @param value
     *            The boolean value to set the label to
     */
    private void setBooleanValue(String label, boolean value) {
        if (value) {
            setLabel(label, TRUE_VALUE);
        } else {
            setLabel(label, FALSE_VALUE);
        }
    }

    /**
     * Utility method to update the Date object provided based on the format
     * string by calling {@link ParserInterface#updateDate(String, Date)}
     * 
     * @param format
     *            String format as produced by {@link #parseDate(String)}
     * @param date
     *            Date object to be updated
     * @return Updated Date object
     */
    private Date updateDate(String format, Date date) {
        if (date == null) {
            date = new Date();
        }
        return ParserInterface.updateDate(format, date);
    }
}
