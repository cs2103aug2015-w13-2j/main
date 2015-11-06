package sg.edu.cs2103aug2015_w13_2j;

//@@author A0133387B
/**
 * contains tests for testing all components to ensure they work together to 
 * produce expected output
 * @author Nguyen Tuong Van
 */

import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.FilterHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.storage.Storage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIStub;

public class IntegrationTests {
    private static LogicInterface sLogic = Logic.getInstance();
    private static TextUIStub UI = new TextUIStub();
    private static Storage sStorage = Storage.getInstance();
    private static final Logger LOGGER = Logger
            .getLogger(Storage.class.getName());
    
    @BeforeClass
    public static void setup() {
        sLogic.registerCommandHandler(new AddHandler());
        sLogic.registerCommandHandler(new EditHandler());
        sLogic.registerCommandHandler(new FilterHandler());
        sLogic.registerCommandHandler(new PopHandler());
        sLogic.registerCommandHandler(new DeleteHandler());
        sLogic.registerCommandHandler(new MarkImportantHandler());
        sLogic.injectDependencies(sStorage, UI);
        sStorage.switchToTestFile(new File("FunDUE_test.txt"));
    }
    @Before
    public void beforeAll(){
    	sStorage.clearTestFileContents();
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
        assertEquals(UI.getFeedBackMessage(), "Task added successfully.");
     
        sLogic.executeCommand("add '");
        assertEquals(UI.getFeedBackMessage(), FeedbackMessage.ERROR_INVALID_TASK.getMessage() );
        System.out.println("test add");
    }

    @Test
    public void testDel() throws TaskNotFoundException {
    	System.out.println("enter test del");
    	//sStorage.clearTestFileContents();
        //sLogic.readTasks();
    	assertEquals(UI.getTasksForDisplay().size(), 0);
    	String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        sLogic.executeCommand("delete 1");
        assertEquals(UI.getFeedBackMessage(), "Task deleted successfully.");
        System.out.println("exit test del");
    }
    
    @Test
    public void testSearch() throws TaskNotFoundException {
    	//sStorage.clearTestFileContents();
        //sLogic.readTasks();
    	assertEquals(UI.getTasksForDisplay().size(), 0);
    	ArrayList<Task> tasks = new ArrayList<Task>();
    	String first = "My first integration test!";
        sLogic.executeCommand("add '" + first + "'");
        tasks.add(new Task(first));
        String sec = "My sec integration test!";
        tasks.add(new Task(sec));
        sLogic.executeCommand("add '" + sec + "'");
        String third = "My third integration test!";
        sLogic.executeCommand("add '" + third + "'");
        tasks.add(new Task(third));
        sLogic.executeCommand("search:test");
        assertEquals(UI.getTasksForDisplay().size(), 3);
        System.out.println("test search");
    }
    
    @AfterClass
    public static void switchStoragedataFile(){
    	sStorage.switchTodataFile();
    }
}
