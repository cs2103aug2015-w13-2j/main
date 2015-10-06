package sg.edu.cs2103aug2015_w13_2j;

import java.util.Vector;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;

public interface TaskAssemblerInterface {

	/**
	 * Creates a Task object from the list of tokens specified in this 
	 * task assembler and returns that task object.
	 * 
	 * @return A task object containing appropriate task names and 
	 * 		   flags as specified by the user input.
	 */
	Task getAssembledTask();

	/**
	 * Gets the first occurrence of the task name that is specified in user's 
	 * input. If no valid task name is found in the user's input, then the 
	 * taskName returned will be null.
	 * 
	 * @return Task name or null if task name in user's input is not found
	 */
	String getTaskNameFromTokens(
			Vector<Pair<Parser.Token, String>> listOfTokens);

	/**
	 * Gets the first occurrence of the date that is specified in 
	 * user's input. If no valid date is found in the user's input, 
	 * then the date returned will be null.
	 * 
	 * @return The date in String format or null if task name in user's 
	 * 		   input is not found
	 */
	String getDateFromTokens(
			Vector<Pair<Parser.Token, String>> listOfTokens);

}