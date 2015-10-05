package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.Label;


/**Test cases for the Logic Component
 * 
 * @author Nguyen Tuong Van
 *
 */

public class LogicTest {
    
    private Logic logicComponent;
	@Test
	public void testAdd() {
		logicComponent = new Logic();
        Task newTask = new Task("First"); 
        logicComponent.addTask(newTask);
        assertEquals("First", logicComponent.getTask(0).getName());
	}
	@Test
	public void testDelete(){
		logicComponent = new Logic();
		logicComponent.addTask(new Task("first test task"));
		logicComponent.addTask(new Task("second test task"));
		logicComponent.deleteTask(logicComponent.findTaskByName("first test task"));
		assertEquals(logicComponent.getAllTasks().get(0).getName(), "second test task");
	}
	
	@Test
	public void testDetermineType(){
		logicComponent = new Logic();
		Task newTask = new Task("first test task");
		logicComponent.determineType(newTask);
		assertTrue(newTask.getDeadline() == null);
		assertEquals(newTask.getType(), "FLOAT");
		newTask.setDeadline(new Date());
		logicComponent.determineType(newTask);
		assertEquals(newTask.getType(), "DUE");
		newTask.setStart(new Date());
		logicComponent.determineType(newTask);
		assertEquals(newTask.getType(), "EVENT");
	}
}
