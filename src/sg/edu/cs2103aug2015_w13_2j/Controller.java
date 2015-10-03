package sg.edu.cs2103aug2015_w13_2j;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;

/**
 * This class determines the appropriate method of execution from
 * the Logic Interface. 
 * 
 * It scans for the first token of the user input parsed by the 
 * Parser class and verifies it. The appropriate method for that 
 * command is then executed.
 * 
 * The Task object passed to the Logic Interface methods is handled
 * by the TaskAssembler class and not this class.
 * 
 * @author A0130894B
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
	 * Takes in a Parser object and initializes a listOfTokens parsed 
	 * by the Parser. The task object is handled upon construction 
	 * by the TaskAssembler.
	 */
	public Controller(Parser parser) {
		this.listOfTokens = parser.getListOfTokens();
		TaskAssembler taskAssembler = new TaskAssembler(this.listOfTokens);
		task = taskAssembler.getAssembledTask();
		
		startCommandExecution();
	}
	
	/**
	 * Retrieves a list of available enum type commands 
	 * 
	 * @return	 list of available commands
	 */
	public static Commands[] getCommandsEnum() {
		return Commands.values();
	}
	
	/**
	 * Retrieves the task object of this class
	 * 
	 * @return   Task object assembled
	 */
	public Task getControllerTask() {
		return this.task;
	}
	
	/**
	 * Executes command as specified by the user input
	 * 
	 * @throws   Error
	 * 				Error when command is not of any accepted type
	 */
	public void startCommandExecution() {
		Commands command = getCommand();
		
		switch (command) {
			case ADD:
				log.log(Level.INFO, "Switched to 'add' command");
				//TODO: Call LOGIC INTERFACE method
				break;
			case DELETE:
				log.log(Level.INFO, "Switched to 'delete' command");
				//TODO: Call LOGIC INTERFACE method
				break;
			case EDIT:
				log.log(Level.INFO, "Switched to 'edit' command");
				//TODO: Call LOGIC INTERFACE method
				break;
			case MARK:
				log.log(Level.INFO, "Switched to 'mark' command");
				break;
			case ARCHIVE:
			
			case RETRIEVE:
				
			case FILTER:
				
			case SUMMARISE:
				
			case EXPORT:
				
			case HELP:
				
			case EXIT:
				
			default:
				throw new Error("Unrecognised command entered");
				
		}
	} 
	
	/**
	 * Gets the command of a user input if it is a valid, reserved keyword.
	 * This method only checks the first token of the list of tokens for a 
	 * command, any subsequent commands (even if in a valid format), will be
	 * ignored.
	 * 
	 * @return   A valid command of user input command line
	 * 
	 * @throws   Error
	 * 			   Error when a token not of the RESERVED Token type is found 
	 * 			   to represent the command of the user's input
	 */
	public Commands getCommand() throws Error {
		Pair<Parser.Token, String> commandTokenPair;
		
		try {
			commandTokenPair = listOfTokens.get(0);
			Parser.Token commandType = commandTokenPair.getKey();
			String commandName = commandTokenPair.getValue();
			
			if (commandType.equals(Parser.Token.RESERVED)) {
				Commands recognisedCommand = 
						Commands.valueOf(commandName.toUpperCase());
				
				return recognisedCommand;
			} else {
				throw new Error("Invalid command entered");
			}
		} catch (ArrayIndexOutOfBoundsException error) {
			throw new Error("Invalid command entered. No user input detected.");
		}
	}
	
}
