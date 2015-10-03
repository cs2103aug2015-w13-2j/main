package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import org.junit.Rule;
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
		String validCommand = "ADD";
		Controller.Commands expectedValidCommand = Controller.Commands.ADD;
		
		setUpTestController(validCommand);
		assertEquals(expectedValidCommand, controller.getCommand());
	
		String invalidCommand = "ADZZDSS";
		String expectedErrorInvalidCommand = "Invalid command entered";
		
		try {
			setUpTestController(invalidCommand);
			controller.getCommand();
		} catch (Error error) {
			assertEquals(expectedErrorInvalidCommand, error.getMessage());
		}
		
		String invalidCommandEmptyString = "";
		String expectedErrorEmptyString = "Invalid command entered. No user input detected.";
		
		try {
			setUpTestController(invalidCommandEmptyString);
			controller.getCommand();
		} catch (Error error) {
			assertEquals(expectedErrorEmptyString, error.getMessage());
		}
		
	}

}
