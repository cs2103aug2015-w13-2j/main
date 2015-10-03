package sg.edu.cs2103aug2015_w13_2j;

import java.util.Vector;

import javafx.util.Pair;

/**
 * This class assigns the appropriate attributes of a Task 
 * corresponding to the flags in the user's input.
 * 
 * More specifically, the task name and flags/flag-values stated 
 * in the user's input will be detected and assigned to the 
 * correct attributes of the Task object.
 * 
 * @author A0130894B
 */
public class TaskAssembler {
	private Task task;
	private Vector<Pair<Parser.Token, String>> listOfTokens;
	
	/**
	 * Constructor that initializes the listOfTokens to process a 
	 * task
	 * 
	 * @param listOfTokens   List of tokens from user input
	 */
	public TaskAssembler(Vector<Pair<Parser.Token, String>> listOfTokens) {
		this.listOfTokens = listOfTokens;
		task = new Task();
		
		assembleTask();
	}
	
	public Task getAssembledTask() {
		return this.task;
	}
	
	/*************************************************************************
	 * MAIN METHODS - SPECIFYING TASK NAME AND TASK ATTRIBUTES
	 *************************************************************************/
	/**
	 * Sets up the task object with specified task name and optional flags
	 * from the user's input. In this method, the name and flag 
	 * attributes (if any) of the task will be set.
	 */
	private void assembleTask() {
		setTaskName();
		setTaskFlags();
	}
	
	/**
	 * Sets the task's name as specified in the user's input. If no valid 
	 * task name is specified, then an error will be thrown.
	 * 
	 * @throws Error
	 * 			Error when no valid task name is specified in user's input
	 */
	private void setTaskName() throws Error {
		String taskName = findValueOfToken(Parser.Token.NAME, 0);
		
		if (taskName == null) {
			throw new Error("Invalid task name entered");
		} else {
			task.setName(taskName);
		}
	}
	
	/**
	 * Sets up the Task object's optional attributes with flags and their 
	 * corresponding values. This method iterates through all flags specified
	 * in the user's input and attempts to find a corresponding value for it.
	 * 
	 * @throws Error
	 * 			Error when an invalid flag or token is found
	 */
	private void setTaskFlags() throws Error {
		Pair<Parser.Token, String> tokenPair = null;
		Parser.Token tokenType;
		String tokenValue;
		
		for (int i = 0; i < listOfTokens.size(); i++) {
			tokenPair = listOfTokens.get(i);
			tokenType = tokenPair.getKey();
			tokenValue = tokenPair.getValue();
			
			if (tokenType.equals(Parser.Token.FLAG)) {
				setFlagValue(tokenValue, i);
			} else if (tokenType.equals(Parser.Token.FLAG_INVALID)) {
				throw new Error("Invalid flag entered.");
			} else if (tokenType.equals(Parser.Token.ID_INVALID)) {
				throw new Error("Invalid token entered.");
			} 
		}
	}
	
	/**
	 * Sets the Task object's optional attributes with the value of the flag 
	 * specified. 
	 * 
	 * The flag type specified is enumerated to ensure the correct 
	 * attribute is assigned to the task object. As all flags should be 
	 * accompanied by a date, if no date is found for this flagType, an 
	 * Error is thrown.
	 * 
	 * @param flagType
	 * 			Flag to be added to the task object
	 * 
	 * @param indexOfFlag
	 * 			Index of the flag in the list of tokens from user's input
	 * 
	 * @throws Error
	 * 			Error when the date of this flagType is not found
	 * 			Error when the flagType is not found to be of an accepted type (This
	 * 			error acts as an extra safety precaution)
	 */
	private void setFlagValue(String flagType, int indexOfFlag) throws Error {
		int indexOfNextToken = indexOfFlag + 1;
		
		// String representation of first occurrence of date token after this flag
		// TODO: Check for DATE_INVALID tokens and throw appropriate error message 
		String date = findValueOfToken(Parser.Token.DATE, indexOfNextToken);
		
		if (date == null) {
			throw new Error("No date specified for a particular flag. "
							+ "Please input a date right after you have "
							+ "specified a flag.");
		}
		
		switch (flagType) {
			case "s" :
				//TODO: Call task interface method Set start time of task when avail
				break;
			case "e" :
				//TODO: Call task interface method and Set end time of task when avail
				break;
			case "d" :
				//TODO: Call task interface Set deadline time of task when avail
				break;
			default :
				// As an extra safety precaution
				throw new Error("Invalid flag entered.");
		}
	}
	
	
	/*************************************************************************
	 * UTILITY METHODS
	 *************************************************************************/
	/**
	 * Finds the value of the first occurrence of a specified token type from a
	 * starting index. If the specified token is not found, a null token value 
	 * is returned.
	 * 
	 * @param tokenTypeToFind
	 * 			A token type that is to be searched for
	 * 
	 * @param start
	 * 			Starting index of listOfTokens to starting searching from
	 * 
	 * @return The value of the specified token type or null if this value cannot 
	 * 		   be found
	 */
	private String findValueOfToken(Parser.Token tokenTypeToFind, int start) throws Error {
		Pair<Parser.Token, String> tokenPair = null;
		Parser.Token tokenType = null;
		String tokenValue = null;
		
		for (int i = start; i < listOfTokens.size(); i++) {
			tokenPair = listOfTokens.get(i);
			tokenType = tokenPair.getKey();
			
			if (tokenType.equals(tokenTypeToFind)) {
				tokenValue = tokenPair.getValue();
				break;
			}
		}
		
		return tokenValue;
	}
	
}
