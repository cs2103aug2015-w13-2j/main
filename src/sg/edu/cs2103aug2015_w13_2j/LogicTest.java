package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkCompletedHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UndoHandler;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageStub;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIStub;

// @@author A0133387B

/**
 * Unit test for {@link Logic} Component
 */
public class LogicTest {
    private static final Logger LOGGER = Logger
            .getLogger(LogicTest.class.getName());
    private static StorageStub sStorage = new StorageStub();
    private static TextUIStub sTextUI = new TextUIStub();
    private static LogicInterface sLogic = Logic.getInstance();

    /**
     * Initializes all components involved in testing the {@link Logic}.
     */
    @BeforeClass
    public static void setup() {
        sLogic.registerCommandHandler(new AddHandler());
        sLogic.registerCommandHandler(new DeleteHandler());
        sLogic.registerCommandHandler(new EditHandler());
        sLogic.registerCommandHandler(new MarkCompletedHandler());
        sLogic.registerCommandHandler(new MarkImportantHandler());
        sLogic.registerCommandHandler(new UndoHandler());
        sLogic.injectDependencies(sStorage, sTextUI);
        sStorage.clearAllTasks();
        sLogic.readTasks();
    }
    
    @After
    public void cleanup() {
        ArrayList<Task> masterTaskList = sLogic.getAllTasks();
        ArrayList<Task> tasksToDelete = new ArrayList<Task>();
        tasksToDelete = getAllTasksToClear(masterTaskList);
        clearMasterTaskList(tasksToDelete);
    }

    @Test
    public void testAdd() throws TaskNotFoundException {
        String taskName1 = "Test Add 1";
        String taskName2 = "Test Add 2";
        Task task1 = new Task(taskName1);
        Task task2 = new Task(taskName2);
        Task taskWithNoName = new Task();
        
        // Test for adding a single task
        sLogic.addTask(task1);
        Task actualTaskAdded1 = sLogic.getAllTasks().get(0);
        assertEquals(task1, actualTaskAdded1);
        
        // Test for adding multiple tasks
        sLogic.addTask(task2);
        Task actualTaskAdded2 = sLogic.getAllTasks().get(1);
        assertEquals(task2, actualTaskAdded2);
        
        // Test for adding a taskWithNoName
        sLogic.addTask(taskWithNoName);
        Task actualTaskAdded3 = sLogic.getAllTasks().get(2);
        assertEquals(taskWithNoName, actualTaskAdded3);
        
        // Test for adding a null Task object
        sLogic.addTask(null);
        Task actualTaskAdded4 = sLogic.getAllTasks().get(3);
        assertEquals(null, actualTaskAdded4);
    }

    @Test
    public void testDelete() throws TaskNotFoundException {
        String taskName1 = "Test Delete 1";
        String taskName2 = "Test Delete 2";
        String taskName3 = "Test Delete 3";
        Task task1 = new Task(taskName1);
        Task task2 = new Task(taskName2);
        Task task3 = new Task(taskName3);
        
        sLogic.addTask(task1);
        sLogic.addTask(task2);
        sLogic.addTask(task3);
        
        // Test deleting a single item
        int expectedSizeOfMasterTaskList = 2;
        sLogic.removeTask(task1);
        assertEquals(expectedSizeOfMasterTaskList, sLogic.getAllTasks().size());
        
        // Test deleting an item not in the task list
        Task unknownTask = new Task();
        String expectedErrorMsgForUnknownTask = 
                FeedbackMessage.ERROR_TASK_NOT_FOUND.getMessage();
        
        try {
            sLogic.removeTask(unknownTask);
        } catch (TaskNotFoundException error) {
            assertEquals(expectedErrorMsgForUnknownTask, error.getMessage());
        }
    }
    
    @Test
    public void testEditingTaskAttributes() throws TaskNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Test for editing the name of a task
        int indexOfTask1 = 0;
        String taskName1 = "'Test Edit 1'";
        String expectedTaskName1Changed = "Test Edit 1 Change Name";
        
        sLogic.executeCommand("add " + taskName1);
        sLogic.executeCommand("edit " + indexOfTask1 + " '" 
                + expectedTaskName1Changed + "'");
        
        Task actualEditedTask1 = sLogic.getTask(indexOfTask1);
        String actualNameOfEditedTask1 = actualEditedTask1.getName();
        assertEquals(expectedTaskName1Changed, actualNameOfEditedTask1);
        
        // Test for editing the end date of a task
        int indexOfTask2 = 1;
        String taskName2 = "'Test Edit 2'";
        String endDateOfTask2 = "23/09/2015";
        String endDateOfTask2Changed = "24/09/2015";
        
        sLogic.executeCommand("add " + taskName2 + " -e " + endDateOfTask2);
        sLogic.executeCommand("edit " + indexOfTask2 + " -e " + endDateOfTask2Changed);
        
        Task actualEditedTask2 = sLogic.getTask(indexOfTask2);
        Date actualEditedTask2Date = actualEditedTask2.getEnd();
        Date expectedTask2StartDate = null;
        try {
            expectedTask2StartDate = dateFormat.parse(endDateOfTask2Changed);
        } catch (ParseException error) {
            error.printStackTrace();
        }
        assertEquals(expectedTask2StartDate, actualEditedTask2Date);
        
        // Test for editing the end date of a task
        int indexOfTask3 = 2;
        String taskName3 = "'Test Edit 3'";
        String startDateOfTask3 = "11/01/2015";
        String startDateOfTask3Changed = "12/01/2015";
        
        sLogic.executeCommand("add " + taskName3 + " -s " + startDateOfTask3);
        sLogic.executeCommand("edit " + indexOfTask3 + " -s " + startDateOfTask3Changed);
        
        Task actualEditedTask3 = sLogic.getTask(indexOfTask3);
        Date actualEditedTask3Date = actualEditedTask3.getStart();
        Date expectedTask3StartDate = null;
        try {
            expectedTask3StartDate = dateFormat.parse(startDateOfTask3Changed);
        } catch (ParseException error) {
            error.printStackTrace();
        }
        assertEquals(expectedTask3StartDate, actualEditedTask3Date);
    }
    
    @Test
    public void testMarkCompleted() throws TaskNotFoundException {
        // Test for marking a task as completed
        int indexOfTask1 = 0;
        String taskName1 = "'Test Mark Completed 1'";
        
        sLogic.executeCommand("add " + taskName1);
        sLogic.executeCommand("done " + indexOfTask1);
        
        Task actualTask1 = sLogic.getTask(indexOfTask1);
        assertTrue(actualTask1.isCompleted());
        
        // Test for marking a task as uncompleted
        sLogic.executeCommand("done " + indexOfTask1);
        actualTask1 = sLogic.getTask(indexOfTask1);
        assertFalse(actualTask1.isCompleted());
    }

    @Test
    public void testMarkImportant() throws TaskNotFoundException {
        // Test for marking a task as important
        int indexOfTask1 = 0;
        String taskName1 = "'Test Mark Important 1'";
        
        sLogic.executeCommand("add " + taskName1);
        sLogic.executeCommand("! " + indexOfTask1);
        
        Task actualTask1 = sLogic.getTask(indexOfTask1);
        assertTrue(actualTask1.isImportant());
        
        // Test for marking a task as unimportant
        sLogic.executeCommand("! " + indexOfTask1);
        actualTask1 = sLogic.getTask(indexOfTask1);
        assertFalse(actualTask1.isImportant());
    }
    
    /**
     * Convenience method for removing {@link Task}s specified in tasksToDelete
     * from the master task list.
     * 
     * @param tasksToDelete
     *          List of {@link Task} objects that are to be removed.
     */
    private void clearMasterTaskList(ArrayList<Task> tasksToDelete) {
        for (Task task: tasksToDelete) {
            try {
                sLogic.removeTask(task);
            } catch (TaskNotFoundException error) {
                error.printStackTrace();
            }
        }
    }
    
    /**
     * Convenience method for retrieving the list of {@link Task} objects to
     * remove from the master {@link Task} list.
     * 
     * @param masterTaskList
     *          List of {@link Task} objects which contains {@link Task}s
     *          added.
     * @return List of {@link Task} objects that are to be removed.
     */
    private ArrayList<Task> getAllTasksToClear(ArrayList<Task> masterTaskList) {
        ArrayList<Task> tasksToDelete = new ArrayList<Task>();
        int masterTaskListSize = masterTaskList.size();
        
        for (int index = 0; index < masterTaskListSize; index++) {
            tasksToDelete.add(masterTaskList.get(index));
        }
        return tasksToDelete;
    }
}
