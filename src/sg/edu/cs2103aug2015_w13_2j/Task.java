package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

// @@author A0121410H

/**
 * Task class that encapsulates all the data that represents a task for e.g. the
 * name, deadline etc. Provides methods to manipulate the data
 * 
 * @author Zhu Chunqi
 */
public class Task implements TaskInterface {
	private HashMap<String, String> mLabels = new HashMap<String, String>();

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
	 * creates a new Task object and calls {@link Task#setName(String)}
	 * 
	 * @param name
	 *            The name to be given to the newly created task
	 */
	public Task(String name) {
		this();
		setName(name);
	}

	/*******************************************************
	 * LABEL ACCESSORS `-> NAME: string `-> CREATED, START, END: date `-> TYPE:
	 * EVENT / DEADLINE / FLOAT `-> COMPLETED, ARCHIVED, IMPORTANT: TRUE / FALSE
	 *******************************************************/

	public void setLabel(String label, String value) {
		mLabels.put(label, value);
	}

	public String getLabel(String label) {
		return mLabels.get(label);
	}

	/*******************************************************
	 * (ACCESSORS) NAME: string
	 *******************************************************/

	public void setName(String name) {
		setLabel("NAME", name);
	}

	public String getName() {
		return getLabel("NAME");
	}

	/*******************************************************
	 * (ACCESSORS) CREATED, START, END: date
	 *******************************************************/

	public void setCreated(String createdString) {
		setLabel("CREATED", createdString);
	}

	public Date getCreated() {
		return stringToDate(getLabel("CREATED"));
	}

	public void setStart(Date start) {
		setLabel("START", dateToString(start));
	}

	public void setStart(String startString) {
		setLabel("START", startString);
	}

	public Date getStart() {
		return stringToDate(getLabel("START"));
	}

	public void setEnd(Date end) {
		setLabel("END", dateToString(end));
	}

	public void setEnd(String endString) {
		setLabel("END", endString);
	}

	public Date getEnd() {
		return stringToDate(getLabel("END"));
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
	 * (ACCESSORS) TYPE: EVENT / DEADLINE / FLOAT
	 *******************************************************/

	public void setType(String typeString) {
		switch (typeString) {
		case "EVENT":
		case "DEADLINE":
		case "FLOAT":
			setLabel("TYPE", typeString);
			break;
		}
	}

	public String getType() {
		return getLabel("TYPE");
	}

	/*******************************************************
	 * (ACCESSORS) COMPLETED, ARCHIVED, IMPORTANT: TRUE / FALSE
	 *******************************************************/

	// COMPLETED
	public void setCompleted(String completedString) {
		switch (completedString) {
		case "TRUE":
		case "FALSE":
			setLabel("COMPLETED", completedString);
			break;
		}
	}

	public String getCompleted() {
		return getLabel("COMPLETED");
	}

	// ARCHIVED
	public void setArchived(String archivedString) {
		switch (archivedString) {
		case "TRUE":
		case "FALSE":
			setLabel("ARCHIVED", archivedString);
			break;
		}
	}

	public String getArchived() {
		return getLabel("ARCHIVED");
	}

	// IMPORTANT
	public void setImportant(String importantString) {
		switch (importantString) {
		case "TRUE":
		case "FALSE":
			setLabel("IMPORTANT", importantString);
			break;
		}
	}

	public String getImportant() {
		return getLabel("IMPORTANT");
	}

	public boolean isValid() throws InvalidTaskException {
		if (getLabel("NAME") == null || getLabel("NAME").length() == 0) {
			throw new InvalidTaskException();
		} else {
			return true;
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
		Iterator<Entry<String, String>> iter = mLabels.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			sb.append(entry.getKey());
			sb.append(":");
			sb.append(entry.getValue());
			sb.append("\n");
		}
		return sb.toString();
	}
}
