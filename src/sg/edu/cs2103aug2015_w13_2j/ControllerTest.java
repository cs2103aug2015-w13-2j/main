package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import javafx.util.Pair;

/**
 * Controller Test class. 
 * Verifies the workability of all Controller methods.
 * 
 * @author A0130894B
 */
public class ControllerTest {

	private Controller controller;
	
	private void setUpTestController(String commandLine) {
		Parser parser = new Parser();
		parser.parseCommand(commandLine);
		System.out.println("Parsed TOKENS: " + parser.getListOfTokens());
		controller = new Controller(parser);
	}
	
	private Vector<Pair<Parser.Token, String>> generateListOfTokens(
			String commandLine) {
		Parser parser = new Parser();
		parser.parseCommand(commandLine);
		
		return parser.getListOfTokens();
	}
	
	@Test
	public void getCommandTest() {
		// Test for a valid command
		String validCommand = "ADD 'Something'";
		Vector<Pair<Parser.Token, String>> validCommandTokens = 
				generateListOfTokens(validCommand);
		Controller.Commands expectedValidCommand = Controller.Commands.ADD;
		
		setUpTestController(validCommand);
		assertEquals(expectedValidCommand, controller.getCommand(validCommandTokens));
	
		// Test for a command that is not in accepted list of commands
		String invalidCommand = "ADZZDSS";
		String expectedErrorInvalidCommand = "Invalid command entered";
		
		try {
			setUpTestController(invalidCommand);
			controller.getCommand();
			fail("getCommandTest - invalidCommand exception not thrown");
		} catch (Error error) {
			assertEquals(expectedErrorInvalidCommand, error.getMessage());
		}
		
		// Test for empty string command
		String invalidCommandEmptyString = "";
		String expectedErrorEmptyString = "Invalid command entered. "
				+ "No user input detected.";
		
		try {
			setUpTestController(invalidCommandEmptyString);
			controller.getCommand();
			fail("getCommandTest - invalidCommandEmptyString exception not thrown");
		} catch (Error error) {
			assertEquals(expectedErrorEmptyString, error.getMessage());
		}
		
	}
	
	@Test
	public void startCommandExecutionTest() {
		String invalidCommand = "Addsz";
		String expectedUnrecognisedCommand = "Invalid command entered";
		
		try {
			setUpTestController(invalidCommand);
			controller.startCommandExecution();
			fail("startCommandExecutionTest - invalidCommand exception not thrown");
		} catch (Error error) {
			System.out.println(error.getMessage());
			assertEquals(expectedUnrecognisedCommand, error.getMessage());
		}
	}
	

}
