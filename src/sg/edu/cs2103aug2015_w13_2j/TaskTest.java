package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Date;

public class TaskTest {
    // Use the same task object across the tests
    private Task task;

    @Test
    public void createTaskWithNameTest() {
        String testName = "CS2103 V0.2";
        task = new Task(testName);
        assertEquals(testName, task.getName());
    }
    
    @Test
    public void userDefinedLabelsTest() {
        task = new Task();
        String[] labels = {"NAME", "CREATED", "END"};
        String[] values = {"CS2103", "12345678"};
        
        // Set the labels
        task.setLabel(labels[0], values[0]);
        task.setLabel(labels[1], values[1]);
        
        // Retrieve and check for correctness
        assertEquals(values[0], task.getLabel(labels[0]));
        assertEquals(values[1], task.getLabel(labels[1]));
        
        // Try to retrieve non-existent label
        assertTrue(task.getLabel(labels[2]) == null);
    }

    @Test
    public void dateRelatedTests() {
        task = new Task();
        Date now = new Date();
        
        // Date & Time created
        assertTrue(now.compareTo(task.getCreated()) >= 0);
        
        // Setting and retrieving deadlines
        assertTrue(task.getEnd() == null);
        task.setEnd(now);
        assertTrue(now.compareTo(task.getEnd()) == 0);
    }
    
    @Test
    public void conversionTest() {
    	String taskString = "NAME:test task name\nCREATED:1443886630393\nDEADLINE:1443886630410\n";
    	task = TaskInterface.parseTask(taskString);
    	
    	assertEquals(taskString, task.toString());
    }
}
