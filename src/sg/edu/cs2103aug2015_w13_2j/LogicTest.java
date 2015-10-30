package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageStub;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIStub;

/**
 * Test cases for the Logic Component
 * 
 * @author Nguyen Tuong Van
 */

public class LogicTest {
    private static StorageStub sStorage = new StorageStub();
    private static TextUIStub sTextUI = new TextUIStub();
    private static Logic sLogic = Logic.getInstance();
    private String response = "";

    @BeforeClass
    public static void setup() {
        sLogic.registerCommandHandler(new AddHandler());
        sLogic.injectDependencies(sStorage, sTextUI);
        sLogic.readTasks();
    }

    @Test
    public void testAdd() throws TaskNotFoundException {
        Task task = new Task("Do 2101 reflection");
        task.setEnd(new Date(1254554769));
        sLogic.addTask(task);
        try {
            assertEquals(sLogic.getTask(0).getName(), "Do 2101 reflection");
        } catch (TaskNotFoundException e) {
            e.printStackTrace();
        }
        sLogic.removeTask(0);
    }

    /**
     * Tests the boundary cases of indices using equivalence testing
     * 
     * @param The
     *            index to test
     * @throws TaskNotFoundException
     */
    public void testGetIndex(int index) throws TaskNotFoundException {
        try {
            response = sLogic.getTask(index).getName();
            System.out.println(sLogic.getTask(index).getName());
        } catch (TaskNotFoundException e) {
            response = index + " is out of bounds!";
        }
    }

    @Test
    public void testIndex() throws TaskNotFoundException {
        sLogic.executeCommand("add 'Go to CS2103T tutorial' -s 21/10T13:00 -e 21/10T14:00");
        sLogic.executeCommand("add 'Do 2101 reflection' -e 21/10T23:59");
        sLogic.executeCommand("add 'Revise for 2010'");
        sLogic.executeCommand("add 'Study for CS2105'");
        sLogic.executeCommand("add 'Clean room' -e 23/10");

        testGetIndex(0);
        assertEquals(response, "Go to CS2103T tutorial");
        // boundary value analysis for valid range

        testGetIndex(-1);
        assertEquals(response, "-1 is out of bounds!");
        // testing using boundary value analysis for equivalence partition below
        // the valid range

        testGetIndex(5);
        assertEquals(response, "5 is out of bounds!");
        // testing using boundary value analysis for equivalence partition above
        // the valid range

        assertTrue(sLogic.getTask(1) != null);
        assertTrue(sLogic.getTask(4) != null);
        assertEquals(sLogic.getTask(4).getName(), "Clean room");
    }

    @Test
    public void testDelete() throws TaskNotFoundException {
        // change this to executeCommand for integration test
        sLogic.removeTask(0);
        sLogic.removeTask(0);
        sLogic.removeTask(0);
        sLogic.removeTask(0);
        sLogic.removeTask(0);

        Task task = new Task();
        task.setName("test task");
        sLogic.addTask(task);
        sLogic.removeTask(0);
        testGetIndex(0);
        assertEquals(response, "0 is out of bounds!");
    }
}
