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
		assertEquals(null, state);
	}
	
	@Test
	public void parseCommandTest() {
		String correctCommand = "add **(#!(*@&#!(@&";
		String incorrectCommandIdentifier = "addd *(&(!*&@*!*(@&(";
		String singleTokenCommandLine = "add";
		String singleTokenIncorrectCommand = "adzzd";
		String emptyCommandLine = "";
		
		assertEquals("add", parser.parseCommand(correctCommand));
		assertEquals(null, parser.parseCommand(incorrectCommandIdentifier));
		assertEquals("add", parser.parseCommand(singleTokenCommandLine));
		assertEquals(null, parser.parseCommand(singleTokenIncorrectCommand));
		assertEquals(null, parser.parseCommand(emptyCommandLine));
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
	@Test
	public void parseOptionTest() {
		String validOptionPair = "-s 23/09";
		String multipleValidOptions = "-s 23/09 -e 29/09 -d 4pm";

		String invalidOption = "startzxczxc";
		String invalidOptionWithField = "startzxczxc 23/09";
		String haveOptionNoField = "-s";
		String emptyOptions = "";
		
		assertEquals("", parser.parseOption(validOptionPair));
		assertEquals("-e 29/09 -d 4pm", parser.parseOption(multipleValidOptions));
		
		assertEquals(null, parser.parseOption(invalidOption));
		assertEquals(null, parser.parseOption(invalidOptionWithField));
		assertEquals(null, parser.parseOption(haveOptionNoField));
		assertEquals(null, parser.parseOption(emptyOptions));
	}
	
	@Test
	public void getOptionsRemainingTest() {
		String optionsCommandLine = "starting 23/09 end 24/09 -r 1week";
		String optionsCommandLineOnePair = "starting 23/09";
		String emptyOptions = "";
		
		assertEquals("end 24/09 -r 1week", parser.getOptionsRemaining(optionsCommandLine));
		assertEquals("", parser.getOptionsRemaining(optionsCommandLineOnePair));
		assertEquals("", parser.getOptionsRemaining(emptyOptions));
	}
	
	@Test
	public void isValidOptionTest() {
		String correctOption = "-s";
		String correctOptionAlternative = "start";
		String incorrectOptionExtraFlag = "--s";
		String incorrectOptionSpelling = "stzzart";
		String invalidOption = "start today and tonight";
		String emptyToken = "";
		
		assertTrue(parser.isAcceptedOption(correctOption));
		assertTrue(parser.isAcceptedOption(correctOptionAlternative));
		
		assertFalse(parser.isAcceptedOption(incorrectOptionExtraFlag));
		assertFalse(parser.isAcceptedOption(incorrectOptionSpelling));
		assertFalse(parser.isAcceptedOption(invalidOption));
		assertFalse(parser.isAcceptedOption(emptyToken));
	}
	
}
