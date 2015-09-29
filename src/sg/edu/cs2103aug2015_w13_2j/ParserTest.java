package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

public class ParserTest {
	// Use the same Parser object across all test cases
	private Parser parser = null;
	
	@Before
	public void setUp() {
		String defaultCommandLine = "add -s 23/09 -r 1week 'Task name'";
		parser = new Parser(defaultCommandLine);
	}
	
	@After
	public void cleanUp() {
		parser = null;
	}
	
	@Test
	public void createParserTest() {
		String sampleCommandLine = "add -s 23/09 -e 4pm *@(*#(!&@! 'Task name'";
		parser = new Parser(sampleCommandLine);
		
		assertEquals(sampleCommandLine, parser.getCommandLine());
	}
	
	/*****************************************************************
	 * TESTING PARSE STATE TRAVERSAL
	 *****************************************************************/
	@Test
	public void parserStateTest() {
		// Start from the PARSE_COMMAND_STATE
		Parser.ParserState state = Parser.ParserState.PARSE_COMMAND_STATE;
		String commandLine = parser.getCommandLine();
		
		// From PARSE_COMMAND_STATE to PARSE_TASK_NAME_STATE
		state = state.nextState(commandLine);
		assertEquals(Parser.ParserState.PARSE_TASK_NAME_STATE, state);
		
		// From PARSE_TASK_NAME_STATE to PARSE_OPTIONS_STATE
		state = state.nextState(commandLine);
		assertEquals(Parser.ParserState.PARSE_OPTIONS_STATE, state);
		
		// From PARSE_OPTIONS_STATE to PARSE_END
		state = state.nextState(commandLine);
		assertEquals(Parser.ParserState.PARSE_END, state);
		
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
		
	}
	
	@Test
	public void getRemainingTaskNameTest() {
		
	}
	
	@Test
	public void hasValidTaskNameWrappers() {
	
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
