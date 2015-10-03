package sg.edu.cs2103aug2015_w13_2j;

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
		add, delete, edit, mark, archive, retrieve, filter, summarise, 
		export, help, exit
	}
	
	/**
	 * 
	 */
	public enum ControllerState {
		PARSE_COMMAND_STATE {
			public ControllerState nextState(String commandLine) {
				return PARSE_TASK_NAME_STATE;
			}
			// Pass commandLine without command token (first token) to next state
		}, 
		
		PARSE_TASK_NAME_STATE {
			public ControllerState nextState(String commandLine) {
				return PARSE_OPTIONS_STATE;
			}
			// Pass commandLine without task name token (last token) to next state
		}, 
		
		PARSE_OPTIONS_STATE {
			public ControllerState nextState(String commandLine) {
				return PARSE_END;
			}
			// Pass something to ending state, doesn't really matter what
		},
		
		PARSE_END {
			public ControllerState nextState(String commandLine) {
				return null;
			}
		};
		
		public abstract ControllerState nextState(String commandLine);
		
	}
	
	/**
	 * Retrieves and returns a list of available enum type commands 
	 * 
	 * @return	 list of available commands
	 */
	public static Commands[] getCommands() {
		return Commands.values();
	}
}
