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
		
	}

}
