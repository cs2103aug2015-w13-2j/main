package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.ArrayList;

import java.util.Date;

import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;


/**Test cases for the Logic Component
 * 
 * @author Nguyen Tuong Van
 *
 */

public class LogicTest{
	private Logic logicTest = Logic.getInstance();	
	private String response = "";
	@Test
	public void testAdd(){
		Task first = new Task("Do 2101 reflection"); 
		first.setEnd(new Date((long)(1254554769)));
		logicTest.addTask(first);
		try {
			assertEquals(logicTest.getTask(0).getName(), "Do 2101 reflection");
		} catch (TaskNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Tests the boundary cases of indices 
	 * using equivalence testing
	 * @param 
	 *         The index to test
	 * @throws TaskNotFoundException
	 */
	

	public void testGetIndex(int index) throws TaskNotFoundException{
		  try {
			    response = logicTest.getTask(index).getName();
		    } catch (TaskNotFoundException e) {
			    response = index + " is out of bound!";
		    }
	}
	
    @Test
    public void testIndex() throws TaskNotFoundException{
        logicTest.executeCommand("add 'Go to CS2103T tutorial -s 21/10T13:00 -e 21/10T14:00'");//event. Index 2
	    logicTest.executeCommand("add 'Do 2101 reflection -e 21/10T23:59'");//deadline, on top. Index 0
	    logicTest.executeCommand("add 'Revise for 2010'");//this is a float task, it's at the bottom of the above 2. Index 3
	    logicTest.executeCommand("add 'Study for CS2105'");//also float, index 4
	    logicTest.executeCommand("add 'Clean room' -e 23/10");//deadline, below Do 2101 reflection.Index 1
	    
	    testGetIndex(0);
	    assertEquals(response, "Do 2101 reflection");//boundary value analysis for the valid range
	    
	    testGetIndex(-1);
	    assertEquals(response, "-1 is out of bound!");//testing using boundary value analysis for equivalence partition
                                                               //below the valid range
	    testGetIndex(5);
	    assertEquals(response, "5 is out of bound!");//testing using boundary value analysis for equivalence partition
                                                             //above the valid range
	//    assertTrue(logicTest.getTask(1) != null);
	//    assertTrue(logicTest.getTask(4) != null);
	//    assertEquals(logicTest.getTask(4).getName(), "Study for CS2105");
    }
    
	@Test
	public void testDelete() throws TaskNotFoundException{
		Task newTask = new Task();
		newTask.setName("test task");
		logicTest.addTask(newTask);
	//	logicTest.removeTask(0);
		testGetIndex(0);
	//	assertEquals(response, "0 is out of bound!");
	}
}
