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

}
