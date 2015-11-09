package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkCompletedHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.RedoHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UndoHandler;
import sg.edu.cs2103aug2015_w13_2j.exceptions.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageStub;
import sg.edu.cs2103aug2015_w13_2j.ui.UIStub;

// @@author A0133387B

/**
 * Unit tests for {@link Logic} Component
 */
public class LogicTest {
    private static LogicInterface sLogic = Logic.getInstance();
    private static UIStub sUI = new UIStub();
    private static StorageInterface sStorage = StorageStub.getInstance();

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
        sLogic.registerCommandHandler(new RedoHandler());
        sLogic.injectDependencies(sStorage, sUI);
        sStorage.clearDataFile();
        sUI.getTasksForDisplay();
        sLogic.readTasks();
    }

    // @@author A0130894B

    @After
    public void cleanup() {
        ArrayList<Task> masterTaskList = sLogic.getAllTasks();
        ArrayList<Task> tasksToDelete = getAllTasksToClear(masterTaskList);
        clearMasterTaskList(tasksToDelete);
        sLogic.clearUndoHistory();
        sLogic.clearRedoHistory();
        assert (sLogic.getAllTasks().size() == 0);
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

        // Partition for valid deletion of single Task object
        int expectedSizeOfMasterTaskList = 2;
        sLogic.removeTask(task1);
        assertEquals(expectedSizeOfMasterTaskList, sLogic.getAllTasks().size());
    }
    
    @Test(expected = TaskNotFoundException.class)
    public void deleteShouldThrowTaskNotFoundException() throws TaskNotFoundException {
        // Partition for valid deletion of Task object not found
        String taskName1 = "Test Delete 1 TaskNotFoundException test";
        Task task1 = new Task(taskName1);
        Task unknownTask = new Task();
        
        sLogic.addTask(task1);
        sLogic.removeTask(unknownTask);
    }

    @Test
    public void testEditingTaskAttributes() throws TaskNotFoundException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        testEditTaskName();
        testEditEndDate(dateFormat);
        testEditStartDate(dateFormat);
    }

    /**
     * Test partition for valid editing of single Task object's name for testing
     * the edit command.
     * 
     * @param dateFormat
     *            Date format used in this test case.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    private void testEditTaskName() throws TaskNotFoundException {
        int indexOfTask1 = 1;
        String taskName1 = "'Test Edit 1'";
        String expectedTaskName1Changed = "Test Edit 1 Change Name";

        sLogic.executeCommand("add " + taskName1);
        sLogic.executeCommand("edit " + indexOfTask1 + " '"
                + expectedTaskName1Changed + "'");

        Task actualEditedTask1 = sLogic.getTask(indexOfTask1);
        String actualNameOfEditedTask1 = actualEditedTask1.getName();
        assertEquals(expectedTaskName1Changed, actualNameOfEditedTask1);
    }

    /**
     * Test partition for valid editing of single Task object's start date for
     * testing the edit command.
     * 
     * @param dateFormat
     *            Date format used in this test case.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    private void testEditStartDate(SimpleDateFormat dateFormat)
            throws TaskNotFoundException {
        int indexOfTask3 = 2;
        String taskName3 = "'Test Edit 3'";
        String startDateOfTask3 = "11/01/2015";
        String startDateOfTask3Changed = "12/01/2015";

        sLogic.executeCommand("add " + taskName3 + " -s " + startDateOfTask3);
        sLogic.executeCommand("edit " + indexOfTask3 + " -s "
                + startDateOfTask3Changed);

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

    /**
     * Test partition for valid editing of single Task object's end date for
     * testing the edit command.
     * 
     * @param dateFormat
     *            Date format used in this test case.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    private void testEditEndDate(SimpleDateFormat dateFormat)
            throws TaskNotFoundException {
        int indexOfTask2 = 2;
        String taskName2 = "'Test Edit 2'";
        String endDateOfTask2 = "23/09/2015";
        String endDateOfTask2Changed = "24/09/2015";

        sLogic.executeCommand("add " + taskName2 + " -e " + endDateOfTask2);
        sLogic.executeCommand("edit " + indexOfTask2 + " -e "
                + endDateOfTask2Changed);

        Task actualEditedTask2 = sLogic.getTask(indexOfTask2);
        Date actualEditedTask2Date = actualEditedTask2.getEnd();
        Date expectedTask2StartDate = null;
        try {
            expectedTask2StartDate = dateFormat.parse(endDateOfTask2Changed);
            assertEquals(expectedTask2StartDate, actualEditedTask2Date);
        } catch (ParseException error) {
            error.printStackTrace();
        }
    }

    @Test
    public void testMarkCompleted() throws TaskNotFoundException {
        // Partition for valid marking of single Task object as done
        int indexOfTask1 = 1;
        String taskName1 = "'Test Mark Completed 1'";

        sLogic.executeCommand("add " + taskName1);
        sLogic.executeCommand("done " + indexOfTask1);

        Task actualTask1 = sLogic.getTask(indexOfTask1);
        assertTrue(actualTask1.isCompleted());

        // Partition for valid marking of single Task object as not done
        sLogic.executeCommand("done " + indexOfTask1);
        actualTask1 = sLogic.getTask(indexOfTask1);
        assertFalse(actualTask1.isCompleted());
    }

    @Test
    public void testMarkImportant() throws TaskNotFoundException {
        int indexOfTask1 = 1;
        String taskName1 = "'Test Mark Important 1'";

        // Partition for valid marking of single Task object as important
        sLogic.executeCommand("add " + taskName1);
        sLogic.executeCommand("! " + indexOfTask1);

        Task actualTask1 = sLogic.getTask(indexOfTask1);
        assertTrue(actualTask1.isImportant());

        // Partition for valid marking of single Task object as unimportant
        sLogic.executeCommand("! " + indexOfTask1);
        actualTask1 = sLogic.getTask(indexOfTask1);
        assertFalse(actualTask1.isImportant());
    }

    @Test
    public void testUndo() {
        String undoCommmandString = "undo";
        int expectedNumberOfTasks = 2;
        testUndoNoCommand();
        testUndoAdd(undoCommmandString, expectedNumberOfTasks);
        testUndoEdit(undoCommmandString, expectedNumberOfTasks);
        testUndoMark(undoCommmandString, expectedNumberOfTasks);
    }

    /**
     * Test partition for undoing last command if no command has been given
     */
    private void testUndoNoCommand() {
        assertEquals(null, sLogic.restoreCommandFromHistory());
    }

    /**
     * Test partition for undoing last command for adding {@link Task} function
     * 
     * @param undoCommmandString
     *            The specified undo command keyword.
     * @param expectedNumberOfTasks
     *            Expected number of tasks in the master {@link Task} list.
     */
    private void testUndoAdd(String undoCommmandString,
            int expectedNumberOfTasks) {
        String taskName1 = "Test Undo 1";
        String taskName2 = "Test Undo 2";
        sLogic.executeCommand("add " + "'" + taskName1 + "'");
        sLogic.storeCommandInHistory();
        sLogic.executeCommand("add " + "'" + taskName2 + "'");
        sLogic.storeCommandInHistory();
        sLogic.executeCommand(undoCommmandString);

        assertNumOfTasks(expectedNumberOfTasks);
    }

    /**
     * Test partition for undoing the last command for editing a {@link Task}'s.
     * 
     * @param undoCommmandString
     *            The specified undo command keyword.
     * @param expectedNumberOfTasks
     *            Expected number of tasks in the master {@link Task} list.
     */
    private void testUndoEdit(String undoCommmandString,
            int expectedNumberOfTasks) {
        int indexOfTask1 = 0;
        String taskName1 = "Test Undo 1";
        String task1ChangedName = "Test Undo 1 Changed name";
        sLogic.executeCommand("edit " + indexOfTask1 + " '" + task1ChangedName
                + "'");
        sLogic.storeCommandInHistory();
        sLogic.executeCommand(undoCommmandString);

        assertNumOfTasks(expectedNumberOfTasks);

        ArrayList<Task> masterTaskList = sLogic.getAllTasks();
        Task actualTaskUndone = masterTaskList.get(indexOfTask1);
        String actualTaskName = actualTaskUndone.getName();
        String expectedTaskName = taskName1;
        assertEquals(expectedTaskName, actualTaskName);
    }

    /**
     * Test partition for undoing the last command for marking a {@link Task}'s
     * toggle attribute.
     * 
     * @param undoCommmandString
     *            The specified undo command keyword.
     * @param expectedNumberOfTasks
     *            Expected number of tasks in the master {@link Task} list.
     */
    private void testUndoMark(String undoCommmandString,
            int expectedNumberOfTasks) {
        int indexOfTask1 = 0;
        sLogic.executeCommand("! " + indexOfTask1);
        sLogic.storeCommandInHistory();
        sLogic.executeCommand("! " + indexOfTask1);
        sLogic.storeCommandInHistory();
        sLogic.executeCommand(undoCommmandString);

        ArrayList<Task> masterTaskList = sLogic.getAllTasks();
        Task actualTaskUndone = masterTaskList.get(indexOfTask1);
        assertNumOfTasks(expectedNumberOfTasks);
        assertFalse(actualTaskUndone.isImportant());
    }

    @Test
    public void testRedo() {
        String undoCommandString = "undo";
        String redoCommandString = "redo";

        testRedoNoCommand();

        // Partition for redoing last task addition command once
        int expectedNumOfTasksAfterRedo = 2;

        testRedoAdd(undoCommandString, redoCommandString,
                expectedNumOfTasksAfterRedo);
        testRedoEdit(undoCommandString, redoCommandString,
                expectedNumOfTasksAfterRedo);
        testRedoMark(undoCommandString, redoCommandString,
                expectedNumOfTasksAfterRedo);
    }

    /**
     * Test partition for undoing last command if no command has been given
     */
    private void testRedoNoCommand() {
        assertEquals(null, sLogic.restoreCommandFromRedoHistory());
    }

    /**
     * Test partition for redoing last command for adding {@link Task} function
     * 
     * @param undoCommmandString
     *            The specified undo command keyword.
     * @param redoCommmandString
     *            The specified undo command keyword.
     * @param expectedNumberOfTasks
     *            Expected number of tasks in the master {@link Task} list.
     */
    private void testRedoAdd(String undoCommandString,
            String redoCommandString, int expectedNumOfTasksAfterRedo) {
        String taskName1 = "Test Redo 1";
        String taskName2 = "Test Redo 2";
        sLogic.executeCommand("add " + "'" + taskName1 + "'");
        sLogic.storeCommandInHistory();
        sLogic.executeCommand("add " + "'" + taskName2 + "'");
        sLogic.storeCommandInHistory();
        sLogic.executeCommand(undoCommandString);
        sLogic.executeCommand(redoCommandString);

        assertNumOfTasks(expectedNumOfTasksAfterRedo);
    }

    /**
     * Test partition for redoing last command for editing {@link Task} function
     * 
     * @param undoCommmandString
     *            The specified undo command keyword.
     * @param redoCommmandString
     *            The specified undo command keyword.
     * @param expectedNumberOfTasks
     *            Expected number of tasks in the master {@link Task} list.
     */
    private void testRedoEdit(String undoCommandString,
            String redoCommandString, int expectedNumOfTasksAfterRedo) {
        int indexOfTask1 = 0;
        String taskName1 = "Test Redo 1";
        String task1ChangedName = "Test Redo 1 Changed name";
        sLogic.executeCommand("edit " + indexOfTask1 + " '" + task1ChangedName
                + "'");
        sLogic.storeCommandInHistory();
        sLogic.executeCommand(undoCommandString);
        sLogic.executeCommand(redoCommandString);

        assertNumOfTasks(expectedNumOfTasksAfterRedo);

        ArrayList<Task> masterTaskList = sLogic.getAllTasks();
        Task actualTaskRedone = masterTaskList.get(indexOfTask1);
        String actualTaskName = actualTaskRedone.getName();
        String expectedTaskName = taskName1;

        assertEquals(expectedTaskName, actualTaskName);
    }

    /**
     * Test partition for redoing last command for marking {@link Task} function
     * 
     * @param undoCommmandString
     *            The specified undo command keyword.
     * @param redoCommmandString
     *            The specified redo command keyword.
     * @param expectedNumberOfTasks
     *            Expected number of tasks in the master {@link Task} list.
     */
    private void testRedoMark(String undoCommandString,
            String redoCommandString, int expectedNumOfTasksAfterRedo) {
        int indexOfTask1 = 0;
        sLogic.executeCommand("! " + indexOfTask1);
        sLogic.storeCommandInHistory();
        sLogic.executeCommand("! " + indexOfTask1);
        sLogic.storeCommandInHistory();
        sLogic.executeCommand(undoCommandString);
        sLogic.executeCommand(redoCommandString);

        ArrayList<Task> masterTaskList = sLogic.getAllTasks();
        Task actualTaskRedone = masterTaskList.get(indexOfTask1);

        assertNumOfTasks(expectedNumOfTasksAfterRedo);
        assertFalse(actualTaskRedone.isImportant());
    }

    /**
     * Convenience method for checking if the size of the master list of
     * {@link Task} objects is of the expected number of tasks.
     * 
     * @param expectedNumberOfTasks
     *            Specified number of {@link Task} the master list of
     *            {@link Task}s should contain.
     */
    private void assertNumOfTasks(int expectedNumberOfTasks) {
        ArrayList<Task> masterTaskList = sLogic.getAllTasks();
        int actualMasterTaskListSize = masterTaskList.size();
        assertEquals(expectedNumberOfTasks, actualMasterTaskListSize);
    }

    /**
     * Convenience method for retrieving the list of {@link Task} objects to
     * remove from the master {@link Task} list.
     * 
     * @param masterTaskList
     *            List of {@link Task} objects which contains {@link Task}s
     *            added.
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

    /**
     * Convenience method for removing {@link Task}s specified in tasksToDelete
     * from the master task list.
     * 
     * @param tasksToDelete
     *            List of {@link Task} objects that are to be removed.
     */
    private void clearMasterTaskList(ArrayList<Task> tasksToDelete) {
        for (Task task : tasksToDelete) {
            try {
                sLogic.removeTask(task);
            } catch (TaskNotFoundException error) {
                error.printStackTrace();
            }
        }
    }
}
