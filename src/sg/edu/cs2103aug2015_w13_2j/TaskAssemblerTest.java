package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskAssemblerTest {

	private TaskAssembler taskAssembler;
	
	private void setUpTestTaskAssembler(String commandLine) {
		Parser parser = new Parser();
		parser.parseCommand(commandLine);
		taskAssembler = new TaskAssembler(parser.getListOfTokens());
	}
	
	@Test
	public void setTaskNameTest() {
		// Valid Task Name added to Task object
		String validCommandLine = "add -s 23/09 -e 24/09 'Do Homework'";
		setUpTestTaskAssembler(validCommandLine);
		
		Task validTask = new Task();
		validTask.setName("Do Homework");
		
		String expectedTaskName = "Do Homework";
		assertEquals(expectedTaskName, taskAssembler.getAssembledTask().getName());
		
		// Valid Task Name added to Task object even without last inverted comma
		validCommandLine = "add -s 23/09 -e 24/09 'Do Homework";
		setUpTestTaskAssembler(validCommandLine);
		
		validTask = new Task();
		validTask.setName("Do Homework");
		
		assertEquals(expectedTaskName, taskAssembler.getAssembledTask().getName());
		
		// Invalid task name found, throw Error
		String invalidTaskNameCommandLine = "add -s 23/09 -e 24/09 Do Homework'";
		String expectedErrorInvalidTaskName = "Invalid task name entered";
		
		try {
			setUpTestTaskAssembler(invalidTaskNameCommandLine);
			fail("setTaskNameTest - invalidTaskNameEntered exception not thrown");
		} catch (Error error) {
			assertEquals(expectedErrorInvalidTaskName, error.getMessage());
		}
		
	}
	
	@Test
	public void setTaskFlags() {
		// Invalid token found, throw Error
		String invalidTokenCommandLine = "add -s 23/09 24/09 'Do Homework'";
		String expectedErrorInvalidToken = "Invalid token entered.";
		
		try {
			setUpTestTaskAssembler(invalidTokenCommandLine);
			fail("setTaskFlags - invalidToken exception not thrown");
		} catch (Error error) {
			assertEquals(expectedErrorInvalidToken, error.getMessage());
		}
		
		String invalidMultipleTokensCommandLine = "add 23/09 24/09 'Do Homework'";
		String expectedErrorInvalidMultipleTokens = "Invalid token entered.";
		
		try {
			setUpTestTaskAssembler(invalidMultipleTokensCommandLine);
			fail("setTaskFlags - invalidMultipleToken exception not thrown");
		} catch (Error error) {
			assertEquals(expectedErrorInvalidMultipleTokens, error.getMessage());
		}
		
		/* TODO: To be edited and tested again
		// Invalid date found, throw Error
		String dateNotFoundCommandLine = "add -s 'Do Homework'";
		String expectedErrordateNotFound = 
				"No date specified for a particular flag. "
				+ "Please input a date right after you have "
				+ "specified a flag.";
		
		try {
			setUpTestTaskAssembler(dateNotFoundCommandLine);
			fail("setTaskFlags - dateNotFound exception not thrown");
		} catch (Error error) {
			assertEquals(expectedErrordateNotFound, error.getMessage());
		}
		*/
	}

}
