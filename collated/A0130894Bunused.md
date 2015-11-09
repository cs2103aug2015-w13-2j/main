# A0130894Bunused
###### src\sg\edu\cs2103aug2015_w13_2j\unused\ControllerTestUnused.java
``` java
// Was accompanied by the ControllerUnused class as a test bed 
// for it.
// However, the design for ControllerUnused was deprecated
// and this class was also not needed.

/**
 * Controller Test class. 
 * Verifies the workability of all Controller methods.
 */
//public class ControllerTest {
//
//    private Parser parser;
//    private Controller controller;
//    
//    /**
//     * Sets up the test environment for the Controller class methods
//     * 
//     * @param commandLine
//     *          The command line that simulates the input from user
//     */
//    private void setUpTestController(String commandLine) {
//        this.parser = new Parser();
//        this.parser.parseCommand(commandLine);
//        controller = new Controller(parser);
//    }
//    
//    /**
//     * Gets a list of tokens from the parser given a command line
//     * 
//     * Pre-condition:
//     * Test environment for the Controller class has been set up. 
//     * i.e. Parser object has been instantiated
//     * 
//     * @param commandLine
//     *          The command line that simulates the input from user
//     * 
//     * @return list of tokens representing the commandLine
//     */
//    private Vector<Pair<Parser.Token, String>> generateListOfTokens(
//            String commandLine) {
//
//        return this.parser.getListOfTokens();
//    }
//    
//    /**
//     * Tests the getCommand method
//     * Contains a mixture of valid and invalid commands tested
//     */
//    @Test
//    public void getCommandTest() {
//        // Test for a valid command
//        String validCommand = "ADD 'Something'";
//        Controller.Commands expectedValidCommand = Controller.Commands.ADD;
//        getCommandValidTest(validCommand, expectedValidCommand);
//
//        // Test for an invalid command
//        String invalidCommand = "ADZZDSS";
//        String expectedErrorInvalidCommand = "Invalid command entered";
//        getCommandExceptionTest(invalidCommand, expectedErrorInvalidCommand);
//        
//        // Test for empty string command
//        String invalidCommandEmptyString = "";
//        String expectedErrorEmptyString = "Invalid command entered. "
//                + "No user input detected.";
//        getCommandExceptionTest(invalidCommandEmptyString, expectedErrorEmptyString);
//    }
//    
//    /**
//     * Test for valid commands returned by getCommand method
//     * 
//     * @param validCommand 
//     *          The valid command to test
//     * 
//     * @param expectedValidCommand
//     *          The expected valid command to be returned
//     */
//    private void getCommandValidTest(String validCommand, 
//            Controller.Commands expectedValidCommand) {
//        setUpTestController(validCommand);
//        Vector<Pair<Parser.Token, String>> validCommandTokens = 
//                generateListOfTokens(validCommand);
//        
//        assertEquals(expectedValidCommand, controller.getCommand(validCommandTokens));
//    }
//    
//    /**
//     * Test for exceptions thrown by the getCommand method
//     * 
//     * @param invalidCommand 
//     *          The invalid command to test
//     * 
//     * @param expectedErrorMsg 
//     *          The expected error message to be thrown
//     */
//    private void getCommandExceptionTest(String invalidCommand, 
//            String expectedErrorMsg) {
//        setUpTestController(invalidCommand);
//        Vector<Pair<Parser.Token, String>> invalidCommandTokens = 
//                generateListOfTokens(invalidCommand);
//        
//        try {
//            controller.getCommand(invalidCommandTokens);
//            fail("The invalid command " + invalidCommand + " for getCommandExceptionTest "
//                 + "did not throw the exception message '" + expectedErrorMsg + "'");
//        } catch (Error error) {
//            assertEquals(expectedErrorMsg, error.getMessage());
//        }
//    }
//    
//    /**
//     * Tests the startCommandExecution method
//     */
//    @Test
//    public void startCommandExecutionTest() {
//        String invalidCommand = "Addsz";
//        String expectedUnrecognisedCommand = "Invalid command entered";
//        
//        try {
//            setUpTestController(invalidCommand);
//            controller.startCommandExecution();
//            fail("startCommandExecutionTest - invalidCommand exception not thrown");
//        } catch (Error error) {
//            assertEquals(expectedUnrecognisedCommand, error.getMessage());
//        }
//    }
//}
```
###### src\sg\edu\cs2103aug2015_w13_2j\unused\ControllerUnused.java
``` java
// Unused as user input command executed was redesigned and merged into
// the Logic component. The Observer Pattern was subsequently used 
// to reduce coupling in Logic. Hence this class was deprecated.
// Too many classes raises complexity.

/**
 * This class determines the appropriate method of execution from
 * user's input. 
 * 
 * This class scans for the first token of the user input parsed by the 
 * Parser class and verifies it. The appropriate method for that 
 * command is then executed.
 */
//public class ControllerUnused {
//    /**
//     * Enum class representing all available commands supported
//     */
//    public enum Commands {
//        ADD, DELETE, LIST, EDIT, MARK, 
//        ARCHIVE, RETRIEVE, 
//        FILTER, SUMMARISE,
//        EXPORT, HELP, EXIT
//    }
//    
//    private static final Logger log = 
//            Logger.getLogger(Controller.class.getName());
//    private Task task;
//    private TaskAssembler taskAssembler;
//    private Vector<Pair<Parser.Token, String>> listOfTokens;
//    
//    /**
//     * Constructor for CustomController class. 
//     * Takes in a Parser object and initializes a listOfTokens parsed 
//     * by the Parser. The task object is handled upon construction 
//     * by the TaskAssembler.
//     */
//    public Controller(Parser parser) {
//        this.listOfTokens = parser.getListOfTokens();
//        this.taskAssembler = new TaskAssembler(this.listOfTokens);
//    }
//    
//    public Controller(Vector<Pair<Parser.Token, String>> listOfTokens) {
//        this.listOfTokens = listOfTokens;
//        this.taskAssembler = new TaskAssembler(this.listOfTokens);
//    }
//    
//    /**
//     * Retrieves a list of available enum type commands 
//     * 
//     * @return   list of available commands
//     */
//    public static Commands[] getCommandsEnum() {
//        return Commands.values();
//    }
//    
//    /**
//     * Retrieves the task object of this class
//     * 
//     * @return   Task object assembled
//     */
//    public Task getControllerTask() {
//        return this.task;
//    }
//    
//    /**
//     * Executes command as specified by the user input. In the process, it
//     * invokes the corresponding methods from the Logic class.
//     * 
//     * This method is for execution when Parser passes a parser object 
//     * or the list of tokens representing the user input to this Controller 
//     * class.
//     * @throws   Error
//     *              Error when command is not of any accepted type
//     */
//    public void startCommandExecution() {
//        Commands command = getCommand();
//        String taskName;
//        String date;
//        
//        switch (command) {
//            case ADD:
//                log.log(Level.INFO, "Switched to 'add' command");
//                task = taskAssembler.getAssembledTask();
//                taskName = task.getName();
//                FunDUE.sLogic.addTask(task);
//                break;
//            case DELETE:
//                log.log(Level.INFO, "Switched to 'delete' command");
//                taskName = taskAssembler.getTaskNameFromTokens(this.listOfTokens);
//                FunDUE.sLogic.deleteTask(taskName);
//                break;
//            case LIST:
//                log.log(Level.INFO, "Switched to 'list' command");
//                FunDUE.sLogic.list();
//                break;
//            case EDIT:
//                log.log(Level.INFO, "Switched to 'edit' command");
//                task = taskAssembler.getAssembledTask();
//                taskName = task.getName();
//                FunDUE.sLogic.editTask(taskName, task);
//                break;
//            case MARK:
//                log.log(Level.INFO, "Switched to 'mark' command");
//                // TODO: Mark command once done in Logic
//                break;
//            case ARCHIVE:
//                log.log(Level.INFO, "Switched to 'archive' command");
//                taskName = taskAssembler.getTaskNameFromTokens(this.listOfTokens);
//                FunDUE.sLogic.archiveTask(taskName);
//                break;
//            case RETRIEVE:
//                log.log(Level.INFO, "Switched to 'retrieve' command");
//                taskName = taskAssembler.getTaskNameFromTokens(this.listOfTokens);
//                FunDUE.sLogic.retrieveTask(taskName);
//                break;
//            case FILTER:
//                
//            case SUMMARISE:
//                date = taskAssembler.getDateFromTokens(this.listOfTokens);
//                // TODO: Summarise command once done in Logic
//                break;
//            case EXPORT:
//                
//            case HELP:
//                
//            case EXIT:
//                
//            default:
//                throw new Error("Unrecognised command entered");
//                
//        }
//    } 
//    
//    /**
//     * Gets the command of a user input if it is a valid, reserved keyword.
//     * This method only checks the first token of the list of tokens for a 
//     * command, any subsequent commands (even if in a valid format), will be
//     * ignored.
//     * 
//     * @return   A valid command of user input command line
//     * 
//     * @throws   Error
//     *             Error when a token not of the RESERVED Token type is found 
//     *             to represent the command of the user's input
//     */
//    public Commands getCommand(
//            Vector<Pair<Parser.Token, String>> listOfTokens) throws Error {
//        this.listOfTokens = listOfTokens;
//        
//        return this.getCommand();
//    }
//    
//    public Commands getCommand() throws Error {
//        Pair<Parser.Token, String> commandTokenPair;
//        
//        try {
//            commandTokenPair = listOfTokens.get(0);
//            Parser.Token commandType = commandTokenPair.getKey();
//            String commandName = commandTokenPair.getValue();
//            
//            if (commandType.equals(Parser.Token.RESERVED)) {
//                Commands recognisedCommand = 
//                        Commands.valueOf(commandName.toUpperCase());
//                
//                return recognisedCommand;
//            } else {
//                throw new Error("Invalid command entered");
//            }
//        } catch (ArrayIndexOutOfBoundsException error) {
//            throw new Error("Invalid command entered. No user input detected.");
//        }
//    }
//}
```
###### src\sg\edu\cs2103aug2015_w13_2j\unused\ParserInitialUnused.java
``` java
// Unused as the Parser component was redesigned to be a state machine.
// This method was restrictive in the overall design of the app.

/**
 * Parser class that handles all parsed tokens from the Logic component
 * It parses the task's name, flags and options specified in a user command.
 */
public class ParserInitialUnused {
    private static ParserStateHandlerUnused.Commands[] listOfAcceptedCommands;
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
    public ParserInitialUnused(String commandLine) {
        this.commandLine = commandLine;
        listOfValidOptions = new ArrayList<String>();
        listOfAcceptedCommands = ParserStateHandlerUnused.getCommands();
        
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
     *                      text UI
     * 
     * @return   the first token if it is an accepted command
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
     * @return   true if is accepted command, false otherwise.
     */
    public boolean isAcceptedCommand(String token) {
        boolean isAnAcceptedCommand = false;
        
        for (ParserStateHandlerUnused.Commands cmd: listOfAcceptedCommands) {
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
     *                      text UI
     * 
     * @return   valid task name or null if the task name is not surrounded by 
     *           appropriate wrappers.
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
     *                      text UI
     * 
     * @return   true if the commandLine contains any one of the valid
     *           task name wrappers, false otherwise.
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
     *                           text UI
     * @param openingOrClosing   flag that determines if removing the opening
     *                           or closing wrapper on a task name
     * 
     * @return   Task name remaining after one of its opening or closing wrappers.
     *           Returns null if there are no more wrappers in this Task name or 
     *           task name happens to be null
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
     *                  command line entered by the user in the text UI
     * 
     * @return   empty string if successfully parsed the options field 
     *           in the commandLine. Returns null if the format of 
     *           one of the <option, option field> pairs entered is incorrect.
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
     *                format, e.g. 'add' or 'edit'. 
     * 
     * @param    optionsCommandLine   
     *                  command line entered by the user in the text UI
     * 
     * @return   String of remaining options left to parse. If the option is not
     *           valid, or the option is valid but does not have an option 
     *           field, this method returns null
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
     *                options if the number of tokens > 2.
     *                optionsCommandLine must not be null.
     * 
     * @param    optionsCommandLine
     *                  commandLine with only options and their option fields
     * 
     * @return   String of the remaining options tokens without the first
     *           2 valid options tokens.
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
```
###### src\sg\edu\cs2103aug2015_w13_2j\unused\ParserStateHandlerUnused.java
``` java
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
```
###### src\sg\edu\cs2103aug2015_w13_2j\unused\ParserTestUnused.java
``` java
// Was accompanied by the ParserInitialUnused class as a test bed 
// for it.
// However, the design for ParserInitialUnused was deprecated
// and this class was outdated

/**
 * Unit tests for the Parser component
 */
public class ParserTestUnused {
    // Use the same Parser object across all test cases
    private ParserInitialUnused parser = null;
    
    @Before
    public void setUp() {
        String defaultCommandLine = "add -s 23/09 -r 1week 'Task name'";
        parser = new ParserInitialUnused(defaultCommandLine);
    }
    
    @After
    public void cleanUp() {
        parser = null;
    }
    
    @Test
    public void createParserTest() {
        String sampleCommandLine = "add -s 23/09 -e 4pm *@(*#(!&@! 'Task name'";
        parser = new ParserInitialUnused(sampleCommandLine);
        
        assertEquals(sampleCommandLine, parser.getCommandLine());
    }
    
    /*****************************************************************
     * TESTING PARSE STATE TRAVERSAL
     *****************************************************************/
    @Test
    public void parserStateTest() {
        ParserStateHandlerUnused.ParserState state;
        
        // Start from the PARSE_COMMAND_STATE
        state = ParserStateHandlerUnused.ParserState.PARSE_COMMAND_STATE;
        String commandLine = parser.getCommandLine();
        
        // From PARSE_COMMAND_STATE to PARSE_TASK_NAME_STATE
        state = state.nextState(commandLine);
        assertEquals(ParserStateHandlerUnused.ParserState.PARSE_TASK_NAME_STATE, state);
        
        // From PARSE_TASK_NAME_STATE to PARSE_OPTIONS_STATE
        state = state.nextState(commandLine);
        assertEquals(ParserStateHandlerUnused.ParserState.PARSE_OPTIONS_STATE, state);
        
        // From PARSE_OPTIONS_STATE to PARSE_END
        state = state.nextState(commandLine);
        assertEquals(ParserStateHandlerUnused.ParserState.PARSE_END, state);
        
        // Ended state traversal
        state = state.nextState(commandLine);
        assertNull(state);
    }
    
    /*****************************************************************
     * TESTING PARSE COMMAND METHODS
     *****************************************************************/
    @Test
    public void parseCommandTest() {
        // Test cases for valid command formats
        String correctCommand = "add **(#!(*@&#!(@&";
        String singleTokenCommandLine = "add";
        
        assertEquals("add", parser.parseCommand(correctCommand));
        assertEquals("add", parser.parseCommand(singleTokenCommandLine));
        
        // Test cases for invalid command formats or incorrect number of tokens
        String incorrectCommandIdentifier = "addd *(&(!*&@*!*(@&(";
        String singleTokenIncorrectCommand = "adzzd";
        String emptyCommandLine = "";
        
        assertNull(parser.parseCommand(incorrectCommandIdentifier));
        assertNull(parser.parseCommand(singleTokenIncorrectCommand));
        assertNull(parser.parseCommand(emptyCommandLine));
    }
    
    /*****************************************************************
     * TESTING PARSE TASK NAME METHODS
     *****************************************************************/
    @Test
    public void parseTaskNameTest() {
        // Test cases for a task name with enclosing wrappers
        String validTaskName = "'Eat Lunch'";
        String validTaskNameAlternative = "\"Eat Lunch\"";
        
        assertEquals("Eat Lunch", parser.parseTaskName(validTaskName));
        assertEquals("Eat Lunch", parser.parseTaskName(validTaskNameAlternative));
        
        // Test cases for a task name with incomplete wrappers
        String invalidNoWrappers = "Eat Lunch";
        String invalidNoOpeningWrapper = "Eat Lunch'";
        String invalidNoClosingWrapper = "'Eat Lunch";
        
        assertNull(parser.parseTaskName(invalidNoWrappers));
        assertNull(parser.parseTaskName(invalidNoOpeningWrapper));
        assertNull(parser.parseTaskName(invalidNoClosingWrapper));
    }
    
    @Test
    public void getRemainingTaskNameTest() {
        String doubleWrappers = "'Eat Lunch'";
        String singleWrapperAtStart = "'Eat Lunch";
        String singleWrapperAtEnd = "Eat Lunch'";
        String emptyTaskName = "";
        String nullTaskName = null;
        
        // Test case when getting remaining task name without the opening wrapper
        assertEquals("Eat Lunch'", parser.getRemainingTaskName(doubleWrappers, "opening"));
        assertEquals("Eat Lunch", parser.getRemainingTaskName(singleWrapperAtStart, "opening"));
        assertEquals("", parser.getRemainingTaskName(singleWrapperAtEnd, "opening"));
        
        // Test case when getting remaining task name without the closing wrapper
        assertEquals("", parser.getRemainingTaskName(doubleWrappers, "closing"));
        assertEquals("", parser.getRemainingTaskName(singleWrapperAtStart, "closing"));
        assertEquals("Eat Lunch", parser.getRemainingTaskName(singleWrapperAtEnd, "closing"));

        // Test case for empty or null task names
        assertNull(parser.getRemainingTaskName(emptyTaskName, "opening"));
        assertNull(parser.getRemainingTaskName(emptyTaskName, "closing"));
        assertNull(parser.getRemainingTaskName(nullTaskName, "opening"));
        assertNull(parser.getRemainingTaskName(nullTaskName, "closing"));
    }
    
    @Test
    public void hasValidTaskNameWrappers() {
        String validWrappers = "'Do assignment'";
        String validSingleWrapper = "'Do Assignment";
        String validMiddleWrapper = "Do A'ssi'gnm'ent";
        
        assertTrue(parser.hasValidTaskNameWrappers(validWrappers));
        assertTrue(parser.hasValidTaskNameWrappers(validSingleWrapper));
        assertTrue(parser.hasValidTaskNameWrappers(validMiddleWrapper));
        
        String noWrappersFound = "Do assignment";
        String emptyTaskName = "";
        
        assertFalse(parser.hasValidTaskNameWrappers(noWrappersFound));
        assertFalse(parser.hasValidTaskNameWrappers(emptyTaskName));
    }
    
    /*****************************************************************
     * TESTING PARSE OPTIONS IN COMMANDLINE METHODS
     *****************************************************************/
    @Test
    public void parseAllOptionsTest() {
        // Test cases for valid options
        String validOptions = "starting 23/09 end 24/09 -r 1week";
        String singleValidOption = "starting 23/09";
        String emptyToken = "";
        
        assertEquals("", parser.parseAllOptions(validOptions));
        assertEquals("", parser.parseAllOptions(singleValidOption));
        assertEquals("", parser.parseAllOptions(emptyToken));
        
        // Test cases for invalid number of options
        String singleToken = "starting";
        String invalidSecondOption = "starting 23/09 24/09 -r 1week";
        String invalidSecondOptionField = "starting 23/09 end -r 1week";
        
        assertNull(parser.parseAllOptions(singleToken));
        assertNull(parser.parseAllOptions(invalidSecondOption));
        assertNull(parser.parseAllOptions(invalidSecondOptionField));
        
        // Test cases for having an invalid option
        String singleInvalidToken = "startzzgzz";
        String invalidOptionWithField = "startzxczxc 23/09";
        
        assertNull(parser.parseAllOptions(singleInvalidToken));
        assertNull(parser.parseAllOptions(invalidOptionWithField));

        // Testing for null pointer
        assertNull(parser.parseAllOptions(null));
        
    }
    
    @Test
    public void parseOptionTest() {
        // Test cases for valid options
        String validOptionPair = "-s 23/09";
        String multipleValidOptions = "-s 23/09 -e 29/09 -d 4pm";

        assertEquals("", parser.parseOption(validOptionPair));
        assertEquals("-e 29/09 -d 4pm", parser.parseOption(multipleValidOptions));
        
        // Test cases for invalid options or invalid number of option tokens
        String invalidOption = "startzxczxc";
        String invalidOptionWithField = "startzxczxc 23/09";
        String haveOptionNoField = "-s";
        String emptyOptions = "";
        
        assertNull(parser.parseOption(invalidOption));
        assertNull(parser.parseOption(invalidOptionWithField));
        assertNull(parser.parseOption(haveOptionNoField));
        assertNull(parser.parseOption(emptyOptions));
    }
    
    @Test
    public void getOptionsRemainingTest() {
        String validOptionsCommandLine = "starting 23/09 end 24/09 -r 1week";
        String validPairOptions = "starting 23/09";
        String emptyOptions = "";
        
        assertEquals("end 24/09 -r 1week", parser.getOptionsRemaining(validOptionsCommandLine));
        assertEquals("", parser.getOptionsRemaining(validPairOptions));
        assertEquals("", parser.getOptionsRemaining(emptyOptions));
    }
    
    @Test
    public void isValidOptionTest() {
        // Test cases for valid options
        String correctOption = "-s";
        String correctOptionAlternative = "start";
        
        assertTrue(parser.isAcceptedOption(correctOption));
        assertTrue(parser.isAcceptedOption(correctOptionAlternative));
        
        // Test cases for options with incorrect names or more than 1 option token
        String incorrectOptionExtraFlag = "--s";
        String incorrectOptionSpelling = "stzzart";
        String invalidOption = "start today and tonight";
        String emptyToken = "";
        
        assertFalse(parser.isAcceptedOption(incorrectOptionExtraFlag));
        assertFalse(parser.isAcceptedOption(incorrectOptionSpelling));
        assertFalse(parser.isAcceptedOption(invalidOption));
        assertFalse(parser.isAcceptedOption(emptyToken));
    }
}
```
###### src\sg\edu\cs2103aug2015_w13_2j\unused\TaskAssemblerUnused.java
``` java
// Unused as user input interpretation was redesigned and merged into
// the Logic component. The Observer Pattern was subsequently used 
// to reduce coupling in Logic. Hence this class was deprecated.

/**
 * This class interprets the user's input and assigns the task details 
 * specified to the appropriate attributes of a Task object.
 * 
 * More specifically, the task name and flags/flag-values stated 
 * in the user's input will be retrieved and assigned to the 
 * correct attributes of the Task object.
 */
//public class TaskAssemblerUnused {
//    private Task task;
//    private Vector<Pair<Parser.Token, String>> listOfTokens;
//    
//    /**
//     * Constructor that initializes the listOfTokens to process a 
//     * task
//     * 
//     * @param listOfTokens   List of tokens from user input
//     */
//    public TaskAssembler(Vector<Pair<Parser.Token, String>> listOfTokens) {
//        this.listOfTokens = listOfTokens;
//        task = new Task();
//    }
//    
//    /**
//     * Creates a Task object from the list of tokens specified in this 
//     * task assembler and returns that task object.
//     * 
//     * @return A task object containing appropriate task names and 
//     *         flags as specified by the user input.
//     */
//    public Task getAssembledTask() {
//        assembleTask();
//    
//        return this.task;
//    }
//    
//    /*************************************************************************
//     * MAIN METHODS - SPECIFYING TASK NAME AND TASK ATTRIBUTES
//     *************************************************************************/
//    /**
//     * Sets up the task object with specified task name and optional flags
//     * from the user's input. In this method, the name and flag 
//     * attributes (if any) of the task will be set.
//     */
//    private void assembleTask() {
//        setTaskName();
//        setTaskFlags();
//    }
//    
//    /**
//     * Sets the task's name as specified in the user's input. If no valid 
//     * task name is specified, then an error will be thrown.
//     * 
//     * @throws Error
//     *          Error when no valid task name is specified in user's input
//     */
//    private void setTaskName() throws Error {
//        String taskName = getTaskNameFromTokens();
//        
//        if (taskName == null) {
//            throw new Error("Invalid task name entered");
//        } else {
//            task.setName(taskName);
//        }
//    }
//    
//    /**
//     * Sets up the Task object's optional attributes with flags and their 
//     * corresponding values. This method iterates through all flags specified
//     * in the user's input and attempts to find a corresponding value for it.
//     * 
//     * @throws Error
//     *          Error when an invalid flag or token is found
//     */
//    private void setTaskFlags() throws Error {
//        Pair<Parser.Token, String> tokenPair = null;
//        Parser.Token tokenType;
//        String tokenValue;
//        
//        for (int i = 0; i < listOfTokens.size(); i++) {
//            tokenPair = listOfTokens.get(i);
//            tokenType = tokenPair.getKey();
//            tokenValue = tokenPair.getValue();
//            
//            if (tokenType.equals(Parser.Token.FLAG)) {
//                setFlagValue(tokenValue, i);
//            } else if (tokenType.equals(Parser.Token.FLAG_INVALID)) {
//                throw new Error("Invalid flag entered.");
//    } else if (tokenType.equals(Parser.Token.ID_INVALID)) {
//        throw new Error("Invalid token entered.");
//            } 
//        }
//    }
//    
//    /**
//     * Sets the Task object's optional attributes with the value of the flag 
//     * specified. 
//     * 
//     * The flag type specified is enumerated to ensure the correct 
//     * attribute is assigned to the task object. As all flags should be 
//     * accompanied by a date, if no date is found for this flagType, an 
//     * Error is thrown.
//     * 
//     * @param flagType
//     *          Flag to be added to the task object
//     * 
//     * @param indexOfFlag
//     *          Index of the flag in the list of tokens from user's input
//     * 
//     * @throws Error
//     *          Error when the date of this flagType is not found
//     *          Error when the flagType is not found to be of an accepted type (This
//     *          error acts as an extra safety precaution)
//     */
//    private void setFlagValue(String flagType, int indexOfFlag) throws Error {
//        int indexOfNextToken = indexOfFlag + 1;
//        
//        // String representation of first occurrence of date token after this flag
//    // TODO: Check for DATE_INVALID tokens and throw appropriate error message
//    String dateString = findValueOfToken(Parser.Token.DATE, indexOfNextToken);
//    
//    // TODO: For now, this error will not never thrown as invalid task
//    //       name errors will be thrown first due to Parser bug - not done yet.
//    if (dateString == null) {
//        throw new Error("No date specified for a particular flag. "
//                    + "Please input a date right after you have "
//                    + "specified a flag.");
//    }
//    
//    // TODO: Sample date format that is in 'dd/MM' form
//    // This is an overly simple example because a Date format
//    // has not been configured yet. This will be a sample for the
//    // prototype. 
//    // Note: This method does NOT handle date formats. 
//    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
//    Date date = null;
//    
//    try {
//    
//        date = formatter.parse(dateString);
//    
//    } catch (ParseException e) {
//        e.printStackTrace();
//    }
//    
//    // TODO: Note: Cases are switched by String type, but once Command class or
//    //             valid token class is created, then will switch by its enum types.
//    switch (flagType) {
//        case "s" :
//        task.setStart(date);
//        break;
//    case "e" :
//        //TODO: Call task interface method and Set end time of task when avail
//        task.setDeadline(date);
//        break;
//    default :
//        // As an extra safety precaution
//        throw new Error("Invalid flag entered.");
//        }
//    }
//    
//    
//    /*************************************************************************
//     * UTILITY METHODS
//     *************************************************************************/
//    /**
//     * Gets the first occurrence of the task name that is specified in user's 
//     * input. If no valid task name is found in the user's input, then the 
//     * taskName returned will be null.
//     * 
//     * @return Task name or null if task name in user's input is not found
//     */
//    private String getTaskNameFromTokens() {
//        return getTaskNameFromTokens(this.listOfTokens);
//    }
//    
//    public String getTaskNameFromTokens(
//            Vector<Pair<Parser.Token, String>> listOfTokens) {
//        this.listOfTokens = listOfTokens;
//        String taskName = findValueOfToken(Parser.Token.NAME, 0);
//        
//        return taskName;
//    }
//    
//    /**
//     * Gets the first occurrence of the date that is specified in 
//     * user's input. If no valid date is found in the user's input, 
//     * then the date returned will be null.
//     * 
//     * @return The date in String format or null if task name in user's 
//     *         input is not found
//     */
//    private String getDateFromTokens() {
//        return findValueOfToken(Parser.Token.DATE, 0);
//    }
//    
//    public String getDateFromTokens(
//            Vector<Pair<Parser.Token, String>> listOfTokens) {
//        this.listOfTokens = listOfTokens;
//        String date = findValueOfToken(Parser.Token.DATE, 0);
//        
//        return date;
//    }
//    
//    /**
//     * Finds the value of the first occurrence of a specified token type from a
//     * starting index. If the specified token is not found, a null token value 
//     * is returned.
//     * 
//     * @param tokenTypeToFind
//     *          A token type that is to be searched for
//     * 
//     * @param start
//     *          Starting index of listOfTokens to starting searching from
//     * 
//     * @return The value of the specified token type or null if this value cannot 
//     *         be found
//     */
//    private String findValueOfToken(Parser.Token tokenTypeToFind, int start) throws Error {
//        Pair<Parser.Token, String> tokenPair = null;
//        Parser.Token tokenType = null;
//        String tokenValue = null;
//        
//        for (int i = start; i < listOfTokens.size(); i++) {
//            tokenPair = listOfTokens.get(i);
//            tokenType = tokenPair.getKey();
//            
//            if (tokenType.equals(tokenTypeToFind)) {
//                tokenValue = tokenPair.getValue();
//                break;
//            }
//        }
//        
//        return tokenValue;
//    }
//}
```
