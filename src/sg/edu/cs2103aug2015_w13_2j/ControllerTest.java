package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {

	private Controller controller;
	
	private void setUpTestController(String commandLine) {
		Parser parser = new Parser();
		parser.parseCommand(commandLine);
		controller = new Controller(parser);
	}
	
	@Test
	public void getCommandTest() {
		// Test for a valid command
		String validCommand = "ADD";
		Controller.Commands expectedValidCommand = Controller.Commands.ADD;
		
		setUpTestController(validCommand);
		assertEquals(expectedValidCommand, controller.getCommand());
	
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
			assertEquals(expectedUnrecognisedCommand, error.getMessage());
		}
	}
	

}
