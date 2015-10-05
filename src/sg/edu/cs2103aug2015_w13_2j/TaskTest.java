package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Date;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.Label;

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
        Label[] labels = {Label.NAME, Label.CREATED, Label.DEADLINE};
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
        assertTrue(task.getDeadline() == null);
        task.setDeadline(now);
        assertTrue(now.compareTo(task.getDeadline()) == 0);
    }
    
    @Test
    public void conversionTest() throws Exception {
    	String taskString = "NAME:test task name\nCREATED:1443886630393\nDEADLINE:1443886630410\n";
    	task = Task.parseTask(taskString);
    	String compareString = task.toString();
    	
    	assertTrue(taskString.equals(compareString));
    }
}
