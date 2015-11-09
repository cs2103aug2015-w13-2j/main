package sg.edu.cs2103aug2015_w13_2j.unused;

// @@author A0130894B-unused
// Was accompanied by the ParserInitialUnused class as a 
// class that contained the various Enum states and Command types.
// However, the design for ParserInitialUnused was deprecated
// and this class was thus redesigned.

/**
 * Class that handles the process of parsing the commandLine. 
 * The traversal of states depends on the command specified in the 
 * commandLine. 
 */
public class ParserStateHandlerUnused {
    /**
     * Enum class representing all available commands supported
     */
    public enum Commands {
        add, delete, edit, mark, archive, retrieve, filter, summarise, 
        export, help, exit
    }
    
    /**
     * Enum class representing all states of parsing a commandLine
     */
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
     * Retrieves and returns a list of available enum type commands 
     * 
     * @return   list of available commands
     */
    public static Commands[] getCommands() {
        return Commands.values();
    }
}
