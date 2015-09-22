package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

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
        String[] labels = {"Module", "Priority", "Non-existant"};
        String[] values = {"CS2103", "High"};
        
        // Set the user defined labels
        task.setLabel(labels[0], values[0]);
        task.setLabel(labels[1], values[1]);
        
        // Retrieve and check for correctness
        assertEquals(values[0], task.getLabel(labels[0]));
        assertEquals(values[1], task.getLabel(labels[1]));
        
        // Try to retrieve non-existant label
        assertTrue(task.getLabel(labels[2]) == null);
    }

    @Test
    public void dateRelatedTests() {
        Date now = new Date();
        
        // Date & Time created
        assertTrue(now.compareTo(task.getCreated()) > 0);
        
        // Setting and retrieving deadlines
        assertTrue(task.getDeadline() == null);
        task.setDeadline(now);
        assertTrue(now.compareTo(task.getDeadline()) == 0);
    }
    
}
