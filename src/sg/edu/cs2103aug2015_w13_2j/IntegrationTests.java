package sg.edu.cs2103aug2015_w13_2j;

//@@author A0133387B

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.BeforeClass;
import org.junit.Test;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.ArchiveHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.FilterHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UnarchiveHandler;
import sg.edu.cs2103aug2015_w13_2j.storage.Storage;
import sg.edu.cs2103aug2015_w13_2j.ui.FXUITest;

public class IntegrationTests {
    private static Logic sLogic = Logic.getInstance();
	private static FXUITest sFXUI= new FXUITest();
    private static Storage sStorage = Storage.getInstance();
    @BeforeClass
    public static void setup() {
        sLogic.registerCommandHandler(new AddHandler());
        sLogic.registerCommandHandler(new ArchiveHandler());
        sLogic.registerCommandHandler(new UnarchiveHandler());
        sLogic.registerCommandHandler(new EditHandler());
        sLogic.registerCommandHandler(new FilterHandler());
        sLogic.registerCommandHandler(new PopHandler());
        sLogic.registerCommandHandler(new DeleteHandler());
        sLogic.registerCommandHandler(new MarkImportantHandler());
		sLogic.injectDependencies(sStorage, sFXUI);
        sLogic.readTasks();
    }

    @Test
    public void testAdd() throws TaskNotFoundException {
        String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        assertEquals(sFXUI.getFeedBackMessage(), "Task added successfully.");
    }
    
   
    public void testDelete() throws TaskNotFoundException {
        sLogic.executeCommand("delete 1");
        assertEquals(sFXUI.getFeedBackMessage(), "Task deleted successfully."); 
    }
}
