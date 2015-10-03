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
		String validCommandLine = "add -s 23/09 -e 24/09 'Do Homework'";
		setUpTestTaskAssembler(validCommandLine);
		
		Task validTask = new Task();
		validTask.setName("Do Homework");
		
		String expectedTaskName = "Do Homework";
		assertEquals(expectedTaskName, taskAssembler.getAssembledTask().getName());
	}

}
