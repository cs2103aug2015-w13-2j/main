package sg.edu.cs2103aug2015_w13_2j;

//@@author A0133387B
/**
 * contains tests for testing all components to ensure they work together to 
 * produce expected output
 * @author Nguyen Tuong Van
 */

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

public class IntegrationTests {
	private static LogicInterface sLogic = Logic.getInstance();
	private static UIStub UI;
	private static StorageTest sStorage = new StorageTest();
    private static final Logger LOGGER = Logger
            .getLogger(Storage.class.getName());
    
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
        UI.injectDependency(sStorage);
       
    }
    @Before
    public void beforeAll(){
    	sStorage.clearTestFileContents();
    	UI.refreshTaskList();
    	assert(UI.getTasksForDisplay().size() == 0);
        sLogic.readTasks();
    }

    
    @After
    public void afterAll(){
    	LOGGER.log(Level.INFO, "End of test check : ");
    	for(int i = 0; i < UI.getTasksForDisplay().size(); i++){
			System.out.println(UI.getTasksForDisplay().get(i).toString());

        }
    }

    @Test
    public void testAdd() throws TaskNotFoundException {
    	assertEquals(UI.getTasksForDisplay().size(), 0);
        String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        assertEquals(UI.getFeedBackMessage(), AddHandler.ADD_SUCCESS);
     
        sLogic.executeCommand("add '");
        assertEquals(UI.getFeedBackMessage(), FeedbackMessage.ERROR_INVALID_TASK.getMessage() );
        System.out.println("test add");
    }

    @Test
    public void testDel() throws TaskNotFoundException {
    	System.out.println("enter test del");
    	assertEquals(UI.getTasksForDisplay().size(), 0);
    	String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        assertEquals(UI.getTasksForDisplay().size(), 1);
        sLogic.executeCommand("delete 1");
        System.out.println("Deleted index 1");
        assertEquals(UI.getFeedBackMessage(), DeleteHandler.DELETE_SUCCESS);
        sLogic.executeCommand("delete 1");
        assertEquals(UI.getFeedBackMessage(), FeedbackMessage.ERROR_TASK_NOT_FOUND.getMessage());
        sLogic.executeCommand("delet");
        assertEquals(UI.getFeedBackMessage(), FeedbackMessage.ERROR_UNRECOGNIZED_COMMAND.getMessage());
        System.out.println("exit test del");
    }
    
    @Test
    public void testFilterImportant() throws TaskNotFoundException {
    	assertEquals(UI.getTasksForDisplay().size(), 0);
    	String first = "My first integration test!";
        sLogic.executeCommand("add '" + first + "'");
        String sec = "My sec integration test!";
        sLogic.executeCommand("add '" + sec + "'");
        String third = "My third integration test!";
        sLogic.executeCommand("add '" + third + "'");
        assertEquals(UI.getTasksForDisplay().size(), 3);
        sLogic.executeCommand("! 1");
        sLogic.executeCommand("filter important");
        LOGGER.log(Level.INFO, "FILTER IMPORTANT");
        assertEquals(UI.getTasksForDisplay().size(), 1);
    } 
    
    @Test
    public void testFilterActive() throws TaskNotFoundException {
    	assertEquals(UI.getTasksForDisplay().size(), 0);
    	String first = "My first integration test!";
        sLogic.executeCommand("add '" + first + "'");
        String sec = "My sec integration test!";
        sLogic.executeCommand("add '" + sec + "'");
        String third = "My third integration test!";
        sLogic.executeCommand("add '" + third + "'");
        assertEquals(UI.getTasksForDisplay().size(), 3);
        sLogic.executeCommand("done 1");
        sLogic.executeCommand("filter active");
        LOGGER.log(Level.INFO, "FILTER ACTIVE");
        assertEquals(UI.getTasksForDisplay().size(), 2);
    } 
    @Test
    public void testUndo() throws TaskNotFoundException {
    	assertEquals(UI.getTasksForDisplay().size(), 0);
    	String first = "My first integration test!";
        sLogic.executeCommand("add '" + first + "'");
        String sec = "My sec integration test!";
        sLogic.executeCommand("add '" + sec + "'");
        String third = "My third integration test!";
        sLogic.executeCommand("add '" + third + "'");
        assertEquals(UI.getTasksForDisplay().size(), 3);
        sLogic.executeCommand("del 1");
        assertEquals(UI.getFeedBackMessage(), DeleteHandler.DELETE_SUCCESS);
        assertEquals(UI.getTasksForDisplay().size(), 2);
        sLogic.executeCommand("undo");
        LOGGER.log(Level.INFO, "UNDO DELETE");
        assertEquals(UI.getTasksForDisplay().size(), 3);
    } 
}
