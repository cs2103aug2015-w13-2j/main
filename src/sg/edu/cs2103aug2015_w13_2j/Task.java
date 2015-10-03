package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;
import java.util.HashMap;

//@@author A0121410

/**
 * Task class that encapsulates all the data that represents a task for e.g. the
 * name, deadline etc. Provides methods to manipulate the data
 * 
 * @author Zhu Chunqi
 */
public class Task implements TaskInterface {
    // Maps labels to their values
    private HashMap<Label, String> mLabels = new HashMap<Label, String>();

    /**
     * Zero parameter constructor that creates and initializes a new Task object
     * and records the time of creation
     */
    public Task() {
        Date now = new Date();
        this.setLabel(Label.CREATED, String.valueOf(now.getTime()));
    }

    /**
     * Constructor to create a task with the name as provided. Internally
     * creates a new Task object and calls setName(name)
     * 
     * @param name
     *            The name to be given to the newly created task
     */
    public Task(String name) {
        this();
        setName(name);
    }

    public void setName(String name) {
        setLabel(Label.NAME, name);
    }

    public String getName() {
        return getLabel(Label.NAME);
    }

    public void setLabel(Label label, String value) {
        mLabels.put(label, value);
    }

    public String getLabel(Label label) {
        return mLabels.get(label);
    }

    public Date getCreated() {
        return stringToDate(getLabel(Label.CREATED));
    }

    public void setDeadline(Date deadline) {
        setLabel(Label.DEADLINE, dateToString(deadline));
    }

    public Date getDeadline() {
        return stringToDate(getLabel(Label.DEADLINE));
    }

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
    
    public String toString() {
    	// TODO
    }
    
    public static Task parseTask(String taskString) {
    	// TODO
    }
}
