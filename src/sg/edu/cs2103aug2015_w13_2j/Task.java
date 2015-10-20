package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

// @@author A0121410H

/**
 * Task class that encapsulates all the data that represents a task for e.g. the
 * name, deadline etc. Also provides methods to manipulate the data
 * 
 * @author Zhu Chunqi
 */
public class Task implements TaskInterface {
    public enum Type {
        DEADLINE, EVENT, FLOATING
    }

    private static final String TRUE_VALUE = "TRUE";
    private static final String FALSE_VALUE = "FALSE";

    private static final String LABEL_ARCHIVED = "ARCHIVED";
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
     * Constructor to create a task with the name as provided. Internally
     * creates a new Task object and calls {@link Task#setName(String)}
     * 
     * @param name
     *            The name to be given to the newly created task
     */
    public Task(String name) {
        this();
        setName(name);
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

    public void setStart(String startString) {
        setLabel(LABEL_START, startString);
    }

    public Date getStart() {
        return stringToDate(getLabel(LABEL_START));
    }

    public void setEnd(Date end) {
        setLabel(LABEL_END, dateToString(end));
    }

    public void setEnd(String endString) {
        setLabel(LABEL_END, endString);
    }

    public Date getEnd() {
        return stringToDate(getLabel(LABEL_END));
    }

    public void setType(Type type) {
        setLabel(LABEL_TYPE, type.toString());
    }

    public void setCompleted(boolean completed) {
        setBooleanValue(LABEL_COMPLETED, completed);
    }

    public boolean isCompleted() {
        return getBooleanValue(LABEL_COMPLETED);
    }

    public void setArchived(boolean archived) {
        setBooleanValue(LABEL_ARCHIVED, archived);
    }

    public boolean isArchived() {
        return getBooleanValue(LABEL_ARCHIVED);
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
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * The hash code for the Task object is the hash code returned by the long
     * value of the {@link #LABEL_CREATED} label
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

    // @@author A0124007X

    /**
     * Converts the Task object into string format for storage
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
}
