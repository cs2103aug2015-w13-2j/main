package sg.edu.cs2103aug2015_w13_2j;

import java.util.Arrays;
import java.util.List;

/**
 * Sample Add command format
 * add -s <date/time> -e <date/time> -r <frequency/> -d <date/time> "Task name"
 */
public class Parser implements ParserInterface {
	
	public enum ParserState {
		PARSE_COMMAND_STATE {
			public ParserState nextState(String commandLine) {
				return PARSE_TASK_NAME_STATE;
			}
			// Pass commandLine without command token (first token) to next state
		}, 
		
		PARSE_TASK_NAME_STATE {
			public ParserState nextState(String commandLine) {
				return PARSE_OPTIONS_STATE;
			}
			// Pass commandLine without task name token (last token) to next state
		}, 
		
		PARSE_OPTIONS_STATE {
			public ParserState nextState(String commandLine) {
				return PARSE_END;
			}
			// Pass something to ending state, doesn't really matter what
		},
		
		PARSE_END {
			public ParserState nextState(String commandLine) {
				return null;
			}
		};
		
		public abstract ParserState nextState(String commandLine);
		
	}
	
	/**
	 * Enum class representing all available commands supported
	 * 
	 * @author Natasha Koh Sze Sze
	 *
	 */
	public enum Commands {
		add, delete, edit, mark, archive, retrieve, filter, summarise, 
		export, help, exit
	}

	private static final Commands[] 
			listOfAcceptedCommands = Commands.values();
	
	private static final List<String> startTimeOption = 
		    Arrays.asList("-s", "starting", "start", "starts");
	
	private static final List<String> endTimeOption = 
		    Arrays.asList("-e", "ending", "end", "ends");
	
	private static final List<String> recurTaskOption = 
		    Arrays.asList("-r", "recur");
	
	private static final List<String> deadlineOption = 
		    Arrays.asList("-d", "deadline");
	
	private static final List<String> listOfAcceptedTaskNameWrappers = 
			Arrays.asList("'", "\"", "-");
	
	private String commandLine;
	
	/**
	 * Parser constructor 
	 * 
	 * @param commandLine
	 */
	public Parser(String commandLine) {
		this.commandLine = commandLine;
	}
	
	public String getCommandLine() {
		return this.commandLine;
	}
	
	/*****************************************************************
	 * PARSING COMMAND METHODS
	 *****************************************************************/
	/**
	 * Parses the first token of the commandLine to identify
	 * the command. If the first token is not a valid command, 
	 * this method returns null, if not, it will return that token.
	 * 
	 * @param commandLine   command line entered by the user in the
	 * 						text UI
	 * 
	 * @return 	 the first token if it is an accepted command
	 */
	public String parseCommand(String commandLine) {
		String[] commandLineTokens = commandLine.split(" ", 2);
		String firstToken = commandLineTokens[0];
		
		if (isAcceptedCommand(firstToken)) {
			return firstToken;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns true if a token is an accepted user 
	 * command
	 * 
	 * @param token   a token from the command line 
	 * 
	 * @return	 true if is accepted command, false otherwise.
	 */
	public boolean isAcceptedCommand(String token) {
		boolean isAnAcceptedCommand = false;
		
		for (Commands cmd: listOfAcceptedCommands) {
			if (cmd.toString().equals(token)) {
				isAnAcceptedCommand = true;
				break;
			}
		}
		
		return isAnAcceptedCommand;
	}
	
	/*****************************************************************
	 * PARSING TASK NAME METHODS
	 *****************************************************************/
	/**
	 * Parses the last token of the commandLine which is expected to be
	 * some task name specified by the user surrounded by a pair of wrappers.
	 * E.g. 'Do homework' or "Do homework", etc. Checks if that last token has
	 * a valid wrapper surrounding it. If it does, then the String in between those
	 * wrappers will be the task name.
	 * 
	 * @param commandLine   command line entered by the user in the
	 * 						text UI
	 * 
	 * @return   valid task name or null if the task name is not surrounded by 
	 * 			 appropriate wrappers.
	 */
	public String parseTaskName(String commandLine) {
		return null;
	}
	
	
	
	/*****************************************************************
	 * PARSING OPTIONS FIELD IN COMMAND LINE METHODS
	 *****************************************************************/
	/**
	 * Parses all options in the commandLine. Checks if the options or 
	 * option fields are valid or if they are in a valid 
	 * <option, option field> pair.
	 * 
	 * @param    optionsCommandLine
	 * 				 	command line entered by the user in the text UI
	 * 
	 * @return   empty string if successfully parsed the options field 
	 * 			 in the commandLine. Returns null if the format of 
	 * 			 one of the options entered is incorrect.
	 */
	public String parseAllOptions(String optionsCommandLine) {
		return null;
	}
	
	/**
	 * Parses an option from the commandLine, only for the commands
	 * that offer this format, e.g. 'add' or 'edit'. Checks is the first
	 * token is a valid option and that there is a optionField for that
	 * option, if it does not, this method returns null.
	 * 
	 * @param    optionsCommandLine   
	 * 					command line entered by the user in the text UI
	 * 
	 * @return	 remaining options left to parse. If the option is not
	 * 			 valid, or the option is valid but does not have an option 
	 * 			 field, this method returns null
	 */
	public String parseOption(String optionsCommandLine) {
		return null;
	}
	
}
