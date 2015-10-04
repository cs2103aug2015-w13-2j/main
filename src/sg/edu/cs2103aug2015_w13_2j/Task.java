package sg.edu.cs2103aug2015_w13_2j;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;

//@@author A0121410H

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
        //@@author A0133387B
        setStatus(Status.ONGOING);
    }
    
    public void setLabel(Label label, String value) {
        mLabels.put(label, value);
    }

    public String getLabel(Label label) {
        return mLabels.get(label);
    }

    public void setName(String name) {
        setLabel(Label.NAME, name);
    }

    public String getName() {
        return getLabel(Label.NAME);
    }

    public void setCreated(String createdString) {
    	setLabel(Label.CREATED, createdString);
    }

    public Date getCreated() {
        return stringToDate(getLabel(Label.CREATED));
    }

    public void setDeadline(Date deadline) {
        setLabel(Label.DEADLINE, dateToString(deadline));
    }

    public void setDeadline(String deadlineString) {
    	setLabel(Label.DEADLINE, deadlineString);
    }
    
    public Date getDeadline() {
        return stringToDate(getLabel(Label.DEADLINE));
    }

    
    /**Categorizes a task into one of the 3 types: an Event, Deadline, or Float
     * @param type
     *            one of 3 types of tasks: due (with deadline), event, or float
     * @author Nguyen Tuong Van
     * 
    */
    public void setType(Type type) {
        mLabels.put(Label.TYPE, type.toString());
    }

    public String getType() {
        return getLabel(Label.TYPE);
    }
    
    /**Categorizes the status of a task: ongoing, completed, overdue, archived, deleted
     * @param status
     *            the status to be set. Default for new task is ongoing
     * @author Nguyen Tuong Van
     * 
    */
    public void setStatus(Status status) {
        mLabels.put(Label.STATUS, status.toString());
    }
    
    public void markDeleted() {
    	this.setStatus(Status.DELETED);
    }

    public void markCompleted() {
        this.setStatus(Status.COMPLETED);
    }
    
    public void markArchived() {
        this.setStatus(Status.ARCHIVED);
    }
    
    public void markOverdue() {
        this.setStatus(Status.OVERDUE);
    }
    
    public String getStatus() {
        return getLabel(Label.STATUS);
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
    
    //@@author Kenneth
    public String toString() {
    	String output = "";
    	
        Set<Label> labelSet = mLabels.keySet();
        Iterator<Label> labelIter = labelSet.iterator();
        
        while(labelIter.hasNext()){
            Label label = labelIter.next();
            String value = mLabels.get(label);
            output += label.toString() + ":" + value + "|";
        }
        
        return output;
    }
    
    public static Task parseTask(String taskString) throws Exception {
    	Task task = new Task();
        
        String[] attributes = taskString.split("\\|");
        for(String pair : attributes) {
            if(pair.isEmpty()) {
                continue;
            } else {
                // Only splits by the first colon
                String[] pairArray = pair.split(":", 2);
                String labelName = pairArray[0].toLowerCase();
                labelName = labelName.substring(0,1).toUpperCase() + labelName.substring(1);
                String value = pairArray[1];

                Method m = Task.class.getMethod("set" + labelName, String.class);
                m.invoke(task, value);
            }
        }
        
        return task;
    }
}
