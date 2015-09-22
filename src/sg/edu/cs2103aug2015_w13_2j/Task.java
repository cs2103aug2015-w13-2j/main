package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;
import java.util.HashMap;

/**
 * Task class that encapsulates all the data that represents a task for e.g.
 * the name, deadline and priority etc. Provides methods to manipulate the data
 * and to create user defined labels
 * @author Zhu Chunqi
 */
public class Task implements TaskInterface {
    // Maps labels to their values
    private HashMap<String, String> mLabels = new HashMap<String, String>();
    
    /**
     * Zero parameter constructor that creates and initializes a new Task object
     */
    public Task() {
        Date now = new Date();
        // TODO: How to extract out all the magic values?
        this.setLabel("CREATED", String.valueOf(now.getTime()));
    }
    
    /**
     * Constructor to create a task with the name as provided. Internally
     * creates a new Task object and calls setName(name)
     * @param name
     *      The name to be given to the newly created task
     */
    public Task(String name) {
        this();
        setName(name);
    }
    
    /**
     * Sets the name of this Task object as a label-value pair
     */
    public void setName(String name) {
        setLabel("NAME", name);
    }
    
    /**
     * Retrieves the name label of this Task object
     * @return
     *      The name of the Task object or null if not set
     */
    public String getName() {
        return getLabel("NAME");
    }
    
    /**
     * Sets a label-value pair of the Task object
     * @param label
     *      The label to be set
     * @param value
     *      The value to set the label to or null if the label was not set
     */
    public void setLabel(String label, String value) {
        mLabels.put(label, value);
    }
    
    /**
     * Retrieves the value of a label of the Task object
     * @return
     *      The value of the label or null if not set
     */
    public String getLabel(String label) {
        return mLabels.get(label);
    }
    
    /**
     * Retrieves the date and time this Task object was created
     * @return
     *      Date object representing the date and time created
     */
    public Date getCreated() {
        return stringToDate(getLabel("CREATED"));
    }
    
    /**
     * Sets the deadline of the Task object
     * @param
     *      Date object representing the deadline
     */
    public void setDeadline(Date deadline) {
        setLabel("DEADLINE", dateToString(deadline));
    }
    
    /**
     * Retrieves the deadline of the Task object
     * @return
     *      Date object representing the deadline or null if not set
     */
    public Date getDeadline() {
        return stringToDate(getLabel("DEADLINE"));
    }
    
    /**
     * Utility method to convert a String millisecond epoch to Date object
     * @param date
     *      String representing the millisecond epoch
     * @return
     *      Date object created from the millisecond epoch or null if date
     *      passed is null
     */
    private Date stringToDate(String date) {
        try {
            return new Date(Long.parseLong(date));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    /**
     * Utility function to convert a Date object to String representing the
     * millisecond epoch
     * @param date
     *      Date object to be converted
     * @return
     *      String object representing the date as the millisecond epoch
     */
    private String dateToString(Date date) {
        return String.valueOf(date.getTime());
    }
}
