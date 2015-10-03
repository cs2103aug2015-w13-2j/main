package sg.edu.cs2103aug2015_w13_2j;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;

/**
 * Class that determines 
 * 
 * @@author A0130894B
 *
 */
public class Controller {
	/**
	 * Enum class representing all available commands supported
	 */
	public enum Commands {
		ADD, DELETE, EDIT, MARK, ARCHIVE, RETRIEVE, FILTER, SUMMARISE,
		EXPORT, HELP, EXIT
	}
	
	private static final Logger log = 
			Logger.getLogger(Controller.class.getName());
	private Task task;
	private Vector<Pair<Parser.Token, String>> listOfTokens;
	
	/**
	 * Constructor for CustomController class. 
	 * Takes in a Parser object and initializes a listOfTokens.
	 */
	public Controller(Parser parser) {
		this.listOfTokens = parser.getListOfTokens();
		TaskAssembler taskAssembler = new TaskAssembler(this.listOfTokens);
		task = taskAssembler.getAssembledTask();
		
		startCommandExecution();
	}
	
	/**
	 * Retrieves and returns a list of available enum type commands 
	 * 
	 * @return	 list of available commands
	 */
	public static Commands[] getCommands() {
		return Commands.values();
	}
	
	public Task getControllerTask() {
		return this.task;
	}
	
	/**
	 * Executes command as specified by the user input
	 * 
	 * @throws   Error
	 * 				Error when command is not of any accepted type
	 * 
	 */
	private void startCommandExecution() {
		
	} 
	
	/**
	 * Gets the command of a user input if it is a valid, reserved keyword.
	 * This method only checks the first token of the list of tokens for a 
	 * command, any subsequent commands (even if in a valid format), will be
	 * ignored.
	 * 
	 * @return   A valid command of user input command line
	 * 
	 * @throws Error
	 * 			Error when a token not of the RESERVED Token type is found 
	 * 			to represent the command of the user's input
	 */
	private Commands getCommand() throws Error {
		return null;
	}
	
}
