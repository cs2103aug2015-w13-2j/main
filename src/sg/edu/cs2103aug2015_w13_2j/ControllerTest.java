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

	private Parser parser;
	private Controller controller;
	
	/**
	 * Sets up the test environment for the Controller class methods
	 * 
	 * @param commandLine
	 * 			The command line that simulates the input from user
	 */
	private void setUpTestController(String commandLine) {
		this.parser = new Parser();
		this.parser.parseCommand(commandLine);
		controller = new Controller(parser);
	}
	
	/**
	 * Gets a list of tokens from the parser given a command line
	 * 
	 * Pre-condition:
	 * Test environment for the Controller class has been set up. 
	 * i.e. Parser object has been instantiated
	 * 
	 * @param commandLine
	 * 			The command line that simulates the input from user
	 * 
	 * @return list of tokens representing the commandLine
	 */
	private Vector<Pair<Parser.Token, String>> generateListOfTokens(
			String commandLine) {

		return this.parser.getListOfTokens();
	}
	
	/**
	 * Tests the getCommand method
	 * Contains a mixture of valid and invalid commands tested
	 */
	@Test
	public void getCommandTest() {
		// Test for a valid command
		String validCommand = "ADD 'Something'";
		Controller.Commands expectedValidCommand = Controller.Commands.ADD;
		getCommandValidTest(validCommand, expectedValidCommand);

		// Test for an invalid command
		String invalidCommand = "ADZZDSS";
		String expectedErrorInvalidCommand = "Invalid command entered";
		getCommandExceptionTest(invalidCommand, expectedErrorInvalidCommand);
		
		// Test for empty string command
		String invalidCommandEmptyString = "";
		String expectedErrorEmptyString = "Invalid command entered. "
				+ "No user input detected.";
		getCommandExceptionTest(invalidCommandEmptyString, expectedErrorEmptyString);
	}
	
	/**
	 * Test for valid commands returned by getCommand method
	 * 
	 * @param validCommand 
	 * 			The valid command to test
	 * 
	 * @param expectedValidCommand
	 * 			The expected valid command to be returned
	 */
	private void getCommandValidTest(String validCommand, 
			Controller.Commands expectedValidCommand) {
		setUpTestController(validCommand);
		Vector<Pair<Parser.Token, String>> validCommandTokens = 
				generateListOfTokens(validCommand);
		
		assertEquals(expectedValidCommand, controller.getCommand(validCommandTokens));
	}
	
	/**
	 * Test for exceptions thrown by the getCommand method
	 * 
	 * @param invalidCommand 
	 * 			The invalid command to test
	 * 
	 * @param expectedErrorMsg 
	 * 			The expected error message to be thrown
	 */
	private void getCommandExceptionTest(String invalidCommand, 
			String expectedErrorMsg) {
		setUpTestController(invalidCommand);
		Vector<Pair<Parser.Token, String>> invalidCommandTokens = 
				generateListOfTokens(invalidCommand);
		
		try {
			controller.getCommand(invalidCommandTokens);
			fail("The invalid command " + invalidCommand + " for getCommandExceptionTest "
				 + "did not throw the exception message '" + expectedErrorMsg + "'");
		} catch (Error error) {
			assertEquals(expectedErrorMsg, error.getMessage());
		}
	}
	
	/**
	 * Tests the startCommandExecution method
	 */
	@Test
	public void startCommandExecutionTest() {
		String invalidCommand = "Addsz";
		String expectedUnrecognisedCommand = "Invalid command entered";
		
		try {
			setUpTestController(invalidCommand);
			controller.startCommandExecution();
			fail("startCommandExecutionTest - invalidCommand exception not thrown");
		} catch (Error error) {
			assertEquals(expectedUnrecognisedCommand, error.getMessage());
		}
	}
	

}
