package sg.edu.cs2103aug2015_w13_2j;

/**
 * Sample Add command format
 * add -s <date/time> -e <date/time> -r <frequency/> -d <date/time> "Task name"
 */
public class Parser implements ParserInterface {
	
	public enum ParserState {
		PARSE_COMMAND_STATE {
			public ParserState nextState(String commandLine) {
				System.out.println("In PARSE_COMMAND_STATE");
				return PARSE_TASK_NAME_STATE;
			}
			// Pass commandLine without command token (first token) to next state
		}, 
		
		PARSE_TASK_NAME_STATE {
			public ParserState nextState(String commandLine) {
				System.out.println("In PARSE_TASK_NAME_STATE");
				return PARSE_OPTIONS_STATE;
			}
			// Pass commandLine without task name token (last token) to next state
		}, 
		
		PARSE_OPTIONS_STATE {
			public ParserState nextState(String commandLine) {
				System.out.println("In PARSE_OPTIONS_STATE");
				return PARSE_END;
			}
			// Pass something to ending state, doesn't really matter what
		},
		
		PARSE_END {
			public ParserState nextState(String commandLine) {
				System.out.println("In PARSE_END");
				return null;
			}
		};
		
		public abstract ParserState nextState(String commandLine);
		
	}
	
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
	
	
}
