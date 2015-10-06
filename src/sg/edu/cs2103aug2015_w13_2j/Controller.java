package sg.edu.cs2103aug2015_w13_2j;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Pair;

/**
 * This class determines the appropriate method of execution from
 * user's input. 
 * 
 * This class scans for the first token of the user input parsed by the 
 * Parser class and verifies it. The appropriate method for that 
 * command is then executed.
 * 
 * @author A0130894B
 *
 */
public class Controller {
	/**
	 * Enum class representing all available commands supported
	 */
	public enum Commands {
		ADD, DELETE, LIST, EDIT, MARK, 
		ARCHIVE, RETRIEVE, 
		FILTER, SUMMARISE,
		EXPORT, HELP, EXIT
	}
	
	private static final Logger log = 
			Logger.getLogger(Controller.class.getName());
	private Task task;
	private TaskAssemblerInterface taskAssembler;
	private Vector<Pair<Parser.Token, String>> listOfTokens;
	
	/**
	 * Constructor for CustomController class. 
	 * Takes in a Parser object and initializes a listOfTokens parsed 
	 * by the Parser. The task object is handled upon construction 
	 * by the TaskAssembler.
	 */
	public Controller(Parser parser) {
		this.listOfTokens = parser.getListOfTokens();
		this.taskAssembler = new TaskAssembler(this.listOfTokens);
	}
	
	public Controller(Vector<Pair<Parser.Token, String>> listOfTokens) {
		this.listOfTokens = listOfTokens;
		this.taskAssembler = new TaskAssembler(this.listOfTokens);
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
	 * Executes command as specified by the user input. In the process, it
	 * invokes the corresponding methods from the Logic class.
	 * 
	 * This method is for execution when Parser passes a parser object 
	 * or the list of tokens representing the user input to this Controller 
	 * class.

	 * @throws   Error
	 * 				Error when command is not of any accepted type
	 */
	public void startCommandExecution() {
		Commands command = getCommand();
		String taskName;
		String date;

		switch (command) {
			case ADD:
				log.log(Level.INFO, "Switched to 'add' command");
				task = taskAssembler.getAssembledTask();
				taskName = task.getName();
				FunDUE.sLogic.addTask(task);
				break;
			case DELETE:
				log.log(Level.INFO, "Switched to 'delete' command");
				taskName = taskAssembler.getTaskNameFromTokens(this.listOfTokens);
				FunDUE.sLogic.deleteTask(taskName);
				break;
			case LIST:
				log.log(Level.INFO, "Switched to 'list' command");
				FunDUE.sLogic.list();
				break;
			case EDIT:
				log.log(Level.INFO, "Switched to 'edit' command");
				task = taskAssembler.getAssembledTask();
				taskName = task.getName();
				FunDUE.sLogic.editTask(taskName, task);
				break;
			case MARK:
				log.log(Level.INFO, "Switched to 'mark' command");
				// TODO: Mark command once done in Logic
				break;
			case ARCHIVE:
				log.log(Level.INFO, "Switched to 'archive' command");
				taskName = taskAssembler.getTaskNameFromTokens(this.listOfTokens);
				FunDUE.sLogic.archiveTask(taskName);
				break;
			case RETRIEVE:
				log.log(Level.INFO, "Switched to 'retrieve' command");
				taskName = taskAssembler.getTaskNameFromTokens(this.listOfTokens);
				FunDUE.sLogic.retrieveTask(taskName);
				break;
			case FILTER:
				
			case SUMMARISE:
				date = taskAssembler.getDateFromTokens(this.listOfTokens);
				// TODO: Summarise command once done in Logic
				break;
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
	public Commands getCommand(
			Vector<Pair<Parser.Token, String>> listOfTokens) throws Error {
		this.listOfTokens = listOfTokens;
		
		return this.getCommand();
	}
	
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
