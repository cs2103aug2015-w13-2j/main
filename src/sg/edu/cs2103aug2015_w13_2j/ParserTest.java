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
	public void isValidOptionTest() {
		String correctOption = "-s";
		String correctOptionAlternative = "start";
		String incorrectOptionExtraFlag = "--s";
		String incorrectOptionSpelling = "stzzart";
		
		assertTrue(parser.isAcceptedOption(correctOption));
		assertTrue(parser.isAcceptedOption(correctOptionAlternative));
		assertFalse(parser.isAcceptedOption(incorrectOptionExtraFlag));
		assertFalse(parser.isAcceptedOption(incorrectOptionSpelling));
	}

}
