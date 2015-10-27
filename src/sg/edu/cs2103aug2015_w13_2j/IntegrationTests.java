package sg.edu.cs2103aug2015_w13_2j;

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
import sg.edu.cs2103aug2015_w13_2j.commands.UnarchiveHandler;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIStub;

public class IntegrationTests {
    private static TextUIStub sTextUI = new TextUIStub();
    private static StorageStub sStorage = new StorageStub();
    private static Logic sLogic = Logic.getInstance();

    @BeforeClass
    public static void setup() {
        sLogic.registerCommandHandler(new AddHandler());
        sLogic.registerCommandHandler(new ArchiveHandler());
        sLogic.registerCommandHandler(new UnarchiveHandler());
        sLogic.registerCommandHandler(new EditHandler());
        sLogic.registerCommandHandler(new FilterHandler());
        sLogic.registerCommandHandler(new DeleteHandler());
        sLogic.registerCommandHandler(new MarkImportantHandler());
        sLogic.injectDependencies(sStorage, sTextUI);
        sLogic.readTasks();
    }

    @Test
    public void testLogicComponent() throws TaskNotFoundException {
        String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");

        // Task should have been sent to Storage component to be saved
        ArrayList<Task> storageTasks = sStorage.readTasksFromDataFile();
        assertEquals(1, storageTasks.size());
        assertEquals(taskName, storageTasks.get(0).getName());
     
        // Task should also reside in FilterChain within Logic component
        Task logicTaskZero = sLogic.getTask(0);
        assertEquals(taskName, logicTaskZero.getName());

        // Task should be sent to TextUI for display
        ArrayList<Task> tasksForDisplay = sTextUI.getTasksForDisplay();
        assertEquals(1, tasksForDisplay.size());
        assertEquals(taskName, tasksForDisplay.get(0).getName());

        // FilterChain should still be /all/
        assertEquals("/all/", sTextUI.getFilterChain());
        
        //Testing various methods that modify the task to ensure Logic works well with Storage
        assertEquals(sLogic.getTask(0).isArchived(), false);
        int indexToArchive = 0;
        sLogic.executeCommand("archive " + indexToArchive);
        // Task should have been modified in Storage component
        assertEquals(sStorage.readTasksFromDataFile().get(0).isArchived(), true); 
        
        sLogic.executeCommand("unarchive " + indexToArchive);
        assertEquals(sStorage.readTasksFromDataFile().get(0).isArchived(), false); 
        
        sLogic.executeCommand("edit " + indexToArchive + " -e 27/10");
        assertEquals(sStorage.readTasksFromDataFile().get(0).isOverdue(), true); 
        assertEquals(sStorage.readTasksFromDataFile().get(0).getType(), "DEADLINE"); 
        
        sLogic.executeCommand("! " + indexToArchive);
        assertEquals(sStorage.readTasksFromDataFile().get(0).isImportant(), true);
        
        sLogic.executeCommand("del " + indexToArchive);
        assertEquals(sStorage.readTasksFromDataFile().size(), 0);
        //Test again to make sure Logic works well with TextUI
        assertEquals(0, sTextUI.getTasksForDisplay().size());
        
    }
    
    @Test
    public void testFilter(){
    	sLogic.executeCommand("add '" + "first task" + "'");
    	sLogic.executeCommand("! 0");
    	sLogic.executeCommand("filter is:important");
        assertEquals(sTextUI.getFilterChain(), "/all/is:important/");
        assertEquals(sStorage.readTasksFromDataFile().get(0).isImportant(), true); 
    }
}
