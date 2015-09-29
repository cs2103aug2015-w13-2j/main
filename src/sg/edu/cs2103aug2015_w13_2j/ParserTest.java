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
		
		assertEquals(null, parser.parseAllOptions(singleToken));
		assertEquals(null, parser.parseAllOptions(invalidSecondOption));
		assertEquals(null, parser.parseAllOptions(invalidSecondOptionField));
		
		// Test cases for having an invalid option
		String singleInvalidToken = "startzzgzz";
		String invalidOptionWithField = "startzxczxc 23/09";
		
		assertEquals(null, parser.parseAllOptions(singleInvalidToken));
		assertEquals(null, parser.parseAllOptions(invalidOptionWithField));

		// Testing for null pointer
		assertEquals(null, parser.parseAllOptions(null));
		
	}
	
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
