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

    /*******************************************************
	 * CONSTRUCTORS
	 *******************************************************/
    
    /**
     * Zero parameter constructor that creates and initializes a new Task object
     * and records the time of creation
     */
    public Task() {
        Date now = new Date();
        setCreated(String.valueOf(now.getTime()));
        setCompleted("FALSE");
        setArchived("FALSE");
        setImportant("FALSE");
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
    
    /*******************************************************
	 * LABEL ACCESSORS
	 * `-> NAME: string
	 * `-> CREATED, START, END: date
	 * `-> TYPE: EVENT / DEADLINE / FLOATING
	 * `-> COMPLETED, ARCHIVED, IMPORTANT: TRUE / FALSE
	 *******************************************************/
    
    public void setLabel(Label label, String value) {
        mLabels.put(label, value);
    }

    public String getLabel(Label label) {
        return mLabels.get(label);
    }
    
    /*******************************************************
	 * (ACCESSORS) NAME: string
	 *******************************************************/

    public void setName(String name) {
        setLabel(Label.NAME, name);
    }

    public String getName() {
        return getLabel(Label.NAME);
    }

    /*******************************************************
   	 * (ACCESSORS) CREATED, START, END: date
   	 *******************************************************/

    public void setCreated(String createdString) {
    	setLabel(Label.CREATED, createdString);
    }

    public Date getCreated() {
        return stringToDate(getLabel(Label.CREATED));
    }

    public void setStart(Date start) {
    	setLabel(Label.START, dateToString(start));
    }
  
    public void setStart(String startString) {
    	setLabel(Label.START, startString);
    }
    
    public Date getStart() {
    	return stringToDate(getLabel(Label.START));
    }

    public void setEnd(Date end) {
        setLabel(Label.END, dateToString(end));
    }

    public void setEnd(String endString) {
    	setLabel(Label.END, endString);
    }
    
    public Date getEnd() {
        return stringToDate(getLabel(Label.END));
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
    
    /*******************************************************
   	 * (ACCESSORS) TYPE: EVENT / DEADLINE / FLOATING
   	 *******************************************************/
    
    public void setType(String typeString) {
    	switch(typeString) {
    		case "EVENT":
    		case "DEADLINE":
    		case "FLOATING":
    			setLabel(Label.TYPE, typeString);
    			break;
    	}
    }

    public String getType() {
        return getLabel(Label.TYPE);
    }

    /*******************************************************
   	 * (ACCESSORS) COMPLETED, ARCHIVED, IMPORTANT: TRUE / FALSE
   	 *******************************************************/
    
    // COMPLETED
    public void setCompleted(String completedString) {
    	switch(completedString) {
    		case "TRUE":
    		case "FALSE":
    			setLabel(Label.COMPLETED, completedString);
    			break;
    	}
    }
    
    public String getCompleted() {
        return getLabel(Label.COMPLETED);
    }
    
    // ARCHIVED
    public void setArchived(String archivedString) {
    	switch(archivedString) {
    		case "TRUE":
    		case "FALSE":
    			setLabel(Label.ARCHIVED, archivedString);
    			break;
    	}
    }
    
    public String getArchived() {
        return getLabel(Label.ARCHIVED);
    }
    
    // IMPORTANT
    public void setImportant(String importantString) {
    	switch(importantString) {
    		case "TRUE":
    		case "FALSE":
    			setLabel(Label.IMPORTANT, importantString);
    			break;
    	}
    }
    
    public String getImportant() {
        return getLabel(Label.IMPORTANT);
    }
    
    /*******************************************************
   	 * STRING <-> TASK CONVERSION METHODS
   	 *******************************************************/
    
    //@@author A0124007X
    public String toString() {
    	String output = "";
    	
        Set<Label> labelSet = mLabels.keySet();
        Iterator<Label> labelIter = labelSet.iterator();
        
        while(labelIter.hasNext()){
            Label label = labelIter.next();
            String value = mLabels.get(label);
            output += label.toString() + ":" + value + "\n";
        }
        
        return output;
    }
    
    public static Task parseTask(String taskString) throws Exception {
    	Task task = new Task();
        
        String[] attributes = taskString.split("\r|\n");
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
