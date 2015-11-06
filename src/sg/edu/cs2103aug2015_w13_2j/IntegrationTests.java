package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.assertEquals;

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
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIStub;

// @@author A0133387B

public class IntegrationTests {
    private static LogicInterface sLogic = Logic.getInstance();
    private static TextUIStub UI = new TextUIStub();
    private static Storage sStorage = Storage.getInstance();

    @BeforeClass
    public static void setup() {
        sLogic.registerCommandHandler(new AddHandler());
        sLogic.registerCommandHandler(new EditHandler());
        sLogic.registerCommandHandler(new FilterHandler());
        sLogic.registerCommandHandler(new PopHandler());
        sLogic.registerCommandHandler(new DeleteHandler());
        sLogic.registerCommandHandler(new MarkImportantHandler());
        sLogic.injectDependencies(sStorage, UI);
        //sLogic.readTasks();
    }

    @Test
    public void testAdd() throws TaskNotFoundException {
        String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        assertEquals(UI.getFeedBackMessage(), "Task added successfully.");
    }

    @Test
    public void testDelete() throws TaskNotFoundException {
    	String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        sLogic.executeCommand("delete 1");
        assertEquals(UI.getFeedBackMessage(), "Task deleted successfully.");
    }
}
