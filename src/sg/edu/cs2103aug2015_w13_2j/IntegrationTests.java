package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.FilterHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.HelpHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.LoadHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkCompletedHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.RedoHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.SearchHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UndoHandler;
import sg.edu.cs2103aug2015_w13_2j.storage.Storage;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageTest;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.UIStub;

// @@author A0133387B

/**
 * Contains tests for testing all components to ensure they work together to
 * produce expected output
 * 
 * Includes tests for the synchronization of the components Tests 4 components
 * Logic, UI, Parser and Storage as a whole. Mimics a user's action of adding a
 * command into the application by calling executeCommand() in Logic and
 * examines the output produced by the User Interface Stub after the whole
 * operation. Before any tests are carried out, the file will be cleared so that
 * individual tests do not affect each other
 * 
 * @author Nguyen Tuong Van
 */
public class IntegrationTests {
    private static LogicInterface sLogic = Logic.getInstance();
    private static UIStub UI;
    private static StorageTest sStorage = new StorageTest();
    private static final Logger LOGGER = Logger.getLogger(Storage.class
            .getName());

    @BeforeClass
    public static void setup() {
        UI = new UIStub();
        sLogic.registerCommandHandler(new AddHandler());
        sLogic.registerCommandHandler(new EditHandler());
        sLogic.registerCommandHandler(new DeleteHandler());
        sLogic.registerCommandHandler(new MarkImportantHandler());
        sLogic.registerCommandHandler(new MarkCompletedHandler());
        sLogic.registerCommandHandler(new UndoHandler());
        sLogic.registerCommandHandler(new RedoHandler());
        sLogic.registerCommandHandler(new FilterHandler());
        sLogic.registerCommandHandler(new PopHandler());
        sLogic.registerCommandHandler(new HelpHandler());
        sLogic.registerCommandHandler(new LoadHandler());
        sLogic.registerCommandHandler(new SearchHandler());
        sLogic.injectDependencies(sStorage, UI);
    }

    @Before
    public void beforeAll() {
        UI.refreshFilter();
        sStorage.clearDataFile();
        sLogic.readTasks();
    }

    @After
    public void afterAll() {
        LOGGER.log(Level.INFO, "End of test check : ");
        for (int i = 0; i < UI.getTasksForDisplay().size(); i++) {
            System.out.println(UI.getTasksForDisplay().get(i).toString());
        }
        sStorage.clearDataFile();
    }

    /**
     * Some convenience methods to add tasks to tests
     */
    public void addThreeTasks() {
        String first = "My first integration test!";
        sLogic.executeCommand("add '" + first + "'");
        String sec = "My sec integration test!";
        sLogic.executeCommand("add '" + sec + "'");
        String third = "My third integration test!";
        sLogic.executeCommand("add '" + third + "'");
        assertEquals(UI.getTasksForDisplay().size(), 3);
    }

    public void addOneTask() {
        String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        assertEquals(UI.getTasksForDisplay().size(), 1);
        assertEquals(UI.getFeedbackMessageString(), AddHandler.ADD_SUCCESS);
    }

    @Test
    public void addShortForm() {
        String taskName = "My first integration test!";
        sLogic.executeCommand("a '" + taskName + "'");
        assertEquals(UI.getTasksForDisplay().size(), 1);
        assertEquals(UI.getFeedbackMessageString(), AddHandler.ADD_SUCCESS);
    }

    @Test
    public void testAddInvalid() throws TaskNotFoundException {
        sLogic.executeCommand("add '");
        assertEquals(UI.getFeedbackMessageString(),
                FeedbackMessage.ERROR_INVALID_TASK.getMessage());
    }

    @Test
    public void testEditNameOneTask() throws TaskNotFoundException {
        addOneTask();
        String newName = "ZZZZZ";
        sLogic.executeCommand("edit 1 " + "'" + newName + "'");
        assertEquals(UI.getFeedbackMessageString(), EditHandler.EDIT_SUCCESS);
        assertEquals(UI.getTasksForDisplay().get(0).getName(), newName);
    }

    @Test
    public void testEditShortForm() throws TaskNotFoundException {
        addOneTask();
        String newName = "ZZZZZ";
        sLogic.executeCommand("e 1 " + "'" + newName + "'");
        assertEquals(UI.getFeedbackMessageString(), EditHandler.EDIT_SUCCESS);
        assertEquals(UI.getTasksForDisplay().get(0).getName(), newName);
    }

    @Test
    public void testEditNameOneTaskInvalid() throws TaskNotFoundException {
        addOneTask();
        sLogic.executeCommand("edit 2");
        assertEquals(UI.getFeedbackMessageString(),
                FeedbackMessage.ERROR_TASK_NOT_FOUND.getMessage());
    }

    @Test
    public void testEditNameThreeTasks() throws TaskNotFoundException {
        addThreeTasks();
        String newName = "ZZZZZ";
        sLogic.executeCommand("edit 1 " + "'" + newName + "'"); // <-- since the
                                                                // list is
                                                                // sorted, this
                                                                // task now
                                                                // sinks to the
                                                                // bottom of the
                                                                // list
        assertEquals(UI.getFeedbackMessageString(), EditHandler.EDIT_SUCCESS);
        assertEquals(UI.getTasksForDisplay().get(2).getName(), newName); // <--
                                                                         // checking
                                                                         // the
                                                                         // last
                                                                         // task
    }

    @Test
    public void testEditTime() throws TaskNotFoundException {
        addThreeTasks();
        String name = UI.getTasksForDisplay().get(0).getName();
        sLogic.executeCommand("edit 1 " + "-s 11/11 -e 12/11"); // <-- since
                                                                // this task
                                                                // becomes event
        // it sinks to the bottom of the list
        assertEquals(UI.getFeedbackMessageString(), EditHandler.EDIT_SUCCESS);
        assertEquals(UI.getTasksForDisplay().get(2).getName(), name);
    }

    @Test
    public void testDelete() throws TaskNotFoundException {
        System.out.println("enter test del");
        addOneTask();
        sLogic.executeCommand("delete 1");
        System.out.println("Deleted index 1");
        assertEquals(UI.getFeedbackMessageString(),
                DeleteHandler.DELETE_SUCCESS);
        sLogic.executeCommand("delete 1"); // <-- out of bound, as task list is
                                           // empty
        assertEquals(UI.getFeedbackMessageString(),
                FeedbackMessage.ERROR_TASK_NOT_FOUND.getMessage());
        sLogic.executeCommand("delet"); // <-- invalid command
        assertEquals(UI.getFeedbackMessageString(),
                FeedbackMessage.ERROR_UNRECOGNIZED_COMMAND.getMessage());
        System.out.println("exit test del");
    }

    @Test
    public void testDeleteShortForm() throws TaskNotFoundException {
        addOneTask();
        sLogic.executeCommand("del 1");
        assertEquals(UI.getFeedbackMessageString(),
                DeleteHandler.DELETE_SUCCESS);
        assertEquals(UI.getTasksForDisplay().size(), 0);
    }

    @Test
    public void testFilterImportant() throws TaskNotFoundException {
        addThreeTasks();
        sLogic.executeCommand("! 1");
        sLogic.executeCommand("filter important");
        assertEquals(UI.getFeedbackMessageString(),
                FilterHandler.FILTER_SUCCESS);
        LOGGER.log(Level.INFO, "FILTER IMPORTANT");
        assertEquals(UI.getTasksForDisplay().size(), 1);
    }

    @Test
    public void testFilterActive() throws TaskNotFoundException {
        addThreeTasks();
        sLogic.executeCommand("done 1");
        sLogic.executeCommand("filter active");
        assertEquals(UI.getFeedbackMessageString(),
                FilterHandler.FILTER_SUCCESS);
        LOGGER.log(Level.INFO, "FILTER ACTIVE");
        assertEquals(UI.getTasksForDisplay().size(), 2);
    }

    @Test
    public void testFilterInvalid() throws TaskNotFoundException {
        addThreeTasks();
        sLogic.executeCommand("filter 111");
        assertEquals(UI.getFeedbackMessageString(),
                FeedbackMessage.ERROR_INVALID_FILTER.getMessage());
    }

    @Test
    public void testFilterChain() throws TaskNotFoundException {
        sLogic.executeCommand("back");
        assertEquals(UI.getFeedbackMessageString(), PopHandler.POP_FAIL);
        addThreeTasks();
        sLogic.executeCommand("done 1");
        assertEquals(UI.getFeedbackMessageString(),
                MarkCompletedHandler.SET_COMPLETED_SUCCESS);
        sLogic.executeCommand("! 3");
        assertEquals(UI.getFeedbackMessageString(),
                MarkImportantHandler.SET_IMPORTANT_SUCCESS);
        sLogic.executeCommand("filter active");
        assertEquals(UI.getFeedbackMessageString(),
                FilterHandler.FILTER_SUCCESS);
        assertEquals(UI.getTasksForDisplay().size(), 2);
        sLogic.executeCommand("filter important");
        assertEquals(UI.getTasksForDisplay().size(), 1);
        sLogic.executeCommand("back");
        assertEquals(UI.getTasksForDisplay().size(), 2);
        sLogic.executeCommand("back");
        assertEquals(UI.getTasksForDisplay().size(), 3);
    }

    @Test
    public void testSearch() throws TaskNotFoundException {
        addThreeTasks();
        sLogic.executeCommand("search first");
        assertEquals(UI.getFeedbackMessageString(),
                SearchHandler.SEARCH_SUCCESS);
        assertEquals(UI.getTasksForDisplay().size(), 1);
        sLogic.executeCommand("back");
        assertEquals(UI.getFeedbackMessageString(), PopHandler.POP_SUCCESS);
        assertEquals(UI.getTasksForDisplay().size(), 3);
    }

    @Test
    public void testUndoRedo() throws TaskNotFoundException {
        addThreeTasks();
        sLogic.executeCommand("del 1");
        assertEquals(UI.getFeedbackMessageString(),
                DeleteHandler.DELETE_SUCCESS);
        assertEquals(UI.getTasksForDisplay().size(), 2);
        sLogic.executeCommand("undo");
        LOGGER.log(Level.INFO, "UNDO DELETE");
        assertEquals(UI.getTasksForDisplay().size(), 3);
        sLogic.executeCommand("redo");
        assertEquals(UI.getTasksForDisplay().size(), 2);
    }

    @Test
    public void testSortByName() throws TaskNotFoundException {
        String first = "jumps over the lazy dog";
        sLogic.executeCommand("add '" + first + "'");
        String sec = "brown fox";
        sLogic.executeCommand("add '" + sec + "'");
        String third = "The quick";
        sLogic.executeCommand("add '" + third + "'");
        assertEquals(UI.getTasksForDisplay().get(0).getName(), third);
        assertEquals(UI.getTasksForDisplay().get(1).getName(), sec);
        assertEquals(UI.getTasksForDisplay().get(2).getName(), first);
    }

    @Test
    public void testSortChronological() throws TaskNotFoundException {
        String first = "over the lazy dog";
        sLogic.executeCommand("add '" + first + "'");
        String sec = "brown fox jumps ";
        sLogic.executeCommand("add '" + sec + "'");
        String third = "the quick";
        sLogic.executeCommand("add '" + third + "'");
        assertEquals(UI.getTasksForDisplay().get(0).getName(), sec);//< now brown fox is on top,
                                                                    //< then over, and the quick
        assertEquals(UI.getTasksForDisplay().get(1).getName(), first);
        assertEquals(UI.getTasksForDisplay().get(2).getName(), third);
        // change two of them to deadline task
        sLogic.executeCommand("edit 1 -e 8/11"); // < this task (brown fox ...) becomes a
                                                 // deadline task and sinks to
                                                 // the bottom. on top now is over, 
                                                 // then the quick
        assertEquals(UI.getTasksForDisplay().get(2).getName(), sec);
        assertEquals(UI.getTasksForDisplay().get(0).getName(), first);
        assertEquals(UI.getTasksForDisplay().get(1).getName(), third);
        // now change the task named brown fox
        sLogic.executeCommand("edit 1 -e 9/11");//< now over... sinks below the quick
        assertEquals(UI.getTasksForDisplay().get(0).getName(), third);
        assertEquals(UI.getTasksForDisplay().get(1).getName(), sec);
        assertEquals(UI.getTasksForDisplay().get(2).getName(), first);
    }
}
