package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

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
	
	// Keeps track of valid options
	private static List<String> listOfValidOptions;
	
	private String commandLine;
	
	/**
	 * Parser constructor 
	 * 
	 * @param commandLine
	 */
	public Parser(String commandLine) {
		this.commandLine = commandLine;
		listOfValidOptions = new ArrayList<String>();
		
		// Groups all the valid options in a single list for ease
		// of keeping track of valid options
		listOfValidOptions.addAll(startTimeOption);
		listOfValidOptions.addAll(endTimeOption);
		listOfValidOptions.addAll(recurTaskOption);
		listOfValidOptions.addAll(deadlineOption);
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
		String taskName = null;
		
		if (hasValidTaskNameWrappers(commandLine)) {
			taskName = getRemainingTaskName(commandLine, "opening");
			
			if (hasValidTaskNameWrappers(taskName)) {
				taskName = getRemainingTaskName(taskName, "closing");
				
			} else {
				// No closing wrapper found
				return null;
			}
			
		} else {
			// No opening wrapper found
			return null;
		}
		
		return taskName;
	}
	
	/**
	 * Checks if a commandLine contains any one of the list of accepted
	 * task name wrappers.
	 * 
	 * @param commandLine   command line entered by the user in the
	 * 						text UI
	 * 
	 * @return   true if the commandLine contains any one of the valid
	 * 			 task name wrappers, false otherwise.
	 */
	public boolean hasValidTaskNameWrappers(String commandLine) {
		boolean containsValidWrapper = false;
		
		for (String wrapper: listOfAcceptedTaskNameWrappers) {
			containsValidWrapper = commandLine.indexOf(wrapper) != -1;
			
			if (containsValidWrapper) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Obtains the task name after a specified opening or closing wrapper. 
	 * If openingOrClosing is "opening", this method removes the first wrapper, 
	 * and returns the task name after that first wrapper.
	 * 
	 * If openingOrClosing is "closing", this method removes the last wrapper,
	 * and returns the task name after that last wrapper.
	 *  
	 * @param taskNameToken      command line entered by the user in the
	 * 						     text UI
	 * @param openingOrClosing   flag that determines if removing the opening
	 * 							 or closing wrapper on a task name
	 * 
	 * @return   Task name remaining after one of its opening or closing wrappers.
	 * 			 Returns null if there are no more wrappers in this Task name or 
	 * 			 task name happens to be null
	 */
	public String getRemainingTaskName(String taskName, String openingOrClosing) {
		String remainingTaskName = null;
		int indexOfWrapper;
		boolean containsThisWrapper = false;
		
		try {
			for (String wrapper: listOfAcceptedTaskNameWrappers) {
				indexOfWrapper = taskName.indexOf(wrapper);
				containsThisWrapper = indexOfWrapper != -1;
				
				if (containsThisWrapper) {
					remainingTaskName = 
							getTextAfterWrapper(taskName, openingOrClosing, 
									remainingTaskName, indexOfWrapper);
					
					break;
				}
			}
		} catch (NullPointerException error) {
			// In the case where taskName is null
			return null;
		}
		
		return remainingTaskName;
	}

	private String getTextAfterWrapper(String taskName, String openingOrClosing, 
									   String remainingTaskName, int indexOfWrapper) {
		switch(openingOrClosing) {
			case "opening" :
				remainingTaskName = 
					getTextAfterOpeningWrapper(taskName, indexOfWrapper);
				
				return remainingTaskName;
				
			case "closing" :
				remainingTaskName = 
					getTextAfterClosingWrapper(taskName, indexOfWrapper);
				
				return remainingTaskName;
		}

		return remainingTaskName;
	}

	private String getTextAfterOpeningWrapper(String taskName, int indexOfWrapper) {
		// Will not include the opening wrapper itself
		return taskName.substring(indexOfWrapper + 1);
	}
	
	private String getTextAfterClosingWrapper(String taskName, int indexOfWrapper) {
		// Will not include the closing wrapper itself
		return taskName.substring(0, indexOfWrapper);
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
	 * 			 one of the <option, option field> pairs entered is incorrect.
	 */
	public String parseAllOptions(String optionsCommandLine) {
		try {
			while (isStillParsingOptions(optionsCommandLine)) {
				optionsCommandLine = parseOption(optionsCommandLine);
	
				boolean optionFieldIsInvalid = optionsCommandLine == null;
	
				if (optionFieldIsInvalid) {
					break;
				}
			}

			return optionsCommandLine;
			
		} catch (NullPointerException error) {
			return null;
		}
	}

	private boolean isStillParsingOptions(String optionsCommandLine) {
		return !optionsCommandLine.equals("");
	}
	
	/**
	 * Parses an option-optionField pair from the options field in commandLine.
	 * Checks if the first token is a valid option and that there is an 
	 * optionField for that option, if it does not, this method returns null.
	 * 
	 * Pre-condition: Check is only viable for the commands that offer this 
	 * 				  format, e.g. 'add' or 'edit'. 
	 * 
	 * @param    optionsCommandLine   
	 * 					command line entered by the user in the text UI
	 * 
	 * @return	 String of remaining options left to parse. If the option is not
	 * 			 valid, or the option is valid but does not have an option 
	 * 			 field, this method returns null
	 */
	public String parseOption(String optionsCommandLine) {
		StringTokenizer tokenizer = new StringTokenizer(optionsCommandLine);
		String option;
		String optionField;
		
		try {
			option = tokenizer.nextToken();
			
			if (isAcceptedOption(option)) {
				optionField = tokenizer.nextToken();
				
				// Check if optionField is valid here 
				// Need to check if is correct date/time format...etc.
				// Add this option to the Task's attributes...etc.
				// -- Work in Progress --
				
				return getOptionsRemaining(optionsCommandLine);
				
			} else {
				return null;
			}
			
		} catch (NoSuchElementException error) {
			// If the optionsCommandLine is the empty String "", or 
			// there is an option but no option field for that option
			return null;
		}
	}
	
	/**
	 * Checks if a token is a valid option.
	 * 
	 * @param token   token from the commandLine
	 * @return   true if token is a valid option, false otherwise.
	 */
	public boolean isAcceptedOption(String token) {
		return listOfValidOptions.contains(token);
	}
	
	/**
	 * Returns a String of the remaining options tokens after
	 * parsing the first 2 valid option tokens.
	 * 
	 * Pre-condition: The first 2 tokens of optionsCommandLine are valid 
	 * 				  options if the number of tokens > 2.
	 * 				  optionsCommandLine must not be null.
	 * 
	 * @param    optionsCommandLine
	 * 				    commandLine with only options and their option fields
	 * 
	 * @return   String of the remaining options tokens without the first
	 * 			 2 valid options tokens.
	 */
	public String getOptionsRemaining(String optionsCommandLine) {
		String[] optionsSplitArray = optionsCommandLine.split(" ", 3);

		/* After splitting the optionsCommandLine into 3 parts, we know that
		 * the remaining String of options will always be the last element of 
		 * the array since the first 2 elements are the option and option 
		 * field respectively 
		 */
		if (optionsSplitArray.length <= 2) {
			return "";
		} else {
			return optionsSplitArray[2];
		}
	}
	
}
