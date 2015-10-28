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
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UnarchiveHandler;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
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
        sLogic.registerCommandHandler(new PopHandler());
        sLogic.registerCommandHandler(new DeleteHandler());
        sLogic.registerCommandHandler(new MarkImportantHandler());
        sLogic.injectDependencies(sStorage, sTextUI);
        sLogic.readTasks();
    }

    @Test
    public void testAddFirstTask() throws TaskNotFoundException {
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
        assertEquals(tasksForDisplay.size(), 1);
        assertEquals(taskName, tasksForDisplay.get(0).getName());

        // FilterChain should still be /all/
        assertEquals("/all/", sTextUI.getFilterChain());
    }

    @Test
    public void testTaskModification() throws TaskNotFoundException {
        String taskName = "My first integration test!";
        sLogic.executeCommand("add '" + taskName + "'");
        
        //Testing various methods that modify the task to ensure Logic works well with Storage
        assertEquals(sLogic.getTask(0).isArchived(), false);
        int indexToArchive = 0;
        sLogic.executeCommand("archive " + indexToArchive);
        // Task modification reflected in Storage component
        assertEquals(sStorage.readTasksFromDataFile().get(0).isArchived(), true);
        // Task modification reflected in TextUI component
        assertEquals(sTextUI.getTasksForDisplay().get(0).isArchived(), true);
        
        sLogic.executeCommand("unarchive " + indexToArchive);
        assertEquals(sStorage.readTasksFromDataFile().get(0).isArchived(), false); 
        assertEquals(sTextUI.getTasksForDisplay().get(0).isArchived(), false);
        
        sLogic.executeCommand("edit " + indexToArchive + " -e 27/10");
        String taskType = "DEADLINE";
        assertEquals(sStorage.readTasksFromDataFile().get(0).isOverdue(), true); 
        assertEquals(sStorage.readTasksFromDataFile().get(0).getType(), taskType); 
        assertEquals(sTextUI.getTasksForDisplay().get(0).isOverdue(), true);
        assertEquals(sTextUI.getTasksForDisplay().get(0).getType(), taskType);
        
        sLogic.executeCommand("! " + indexToArchive);
        assertEquals(sStorage.readTasksFromDataFile().get(0).isImportant(), true);
        assertEquals(sTextUI.getTasksForDisplay().get(0).isImportant(), true);
        
        sLogic.executeCommand("del " + indexToArchive);
        assertEquals(sStorage.readTasksFromDataFile().size(), 0);
        assertEquals(sTextUI.getTasksForDisplay().size(), 0);
    }
    
    @Test
    public void testFilter(){
    	sLogic.executeCommand("add 'first task'");
    	sLogic.executeCommand("! 0");
    	sLogic.executeCommand("filter is:important");
        assertEquals(sTextUI.getFilterChain(), "/all/is:important/");
        assertEquals(sStorage.readTasksFromDataFile().get(0).isImportant(), true);
        
        sLogic.executeCommand("ar 0");
        sLogic.executeCommand("filter is:archived");
        assertEquals(sTextUI.getFilterChain(), "/all/is:important/is:archived/");
        assertEquals(sStorage.readTasksFromDataFile().get(0).isArchived(), true);
        
        sLogic.executeCommand("pop");
        sLogic.executeCommand("pop");
        sLogic.executeCommand("add 'second task'");
        sLogic.executeCommand("add 'third'");
        sLogic.executeCommand("filter search:task");
        assertEquals(sTextUI.getFilterChain(), "/all/search:task/");
        assertEquals(sTextUI.getTasksForDisplay().size(), 2);
        assertEquals(sTextUI.getTasksForDisplay().get(0).getName(), "first task");
        assertEquals(sTextUI.getTasksForDisplay().get(1).getName(), "second task");
        
        // Tests for invalid filters
        sLogic.executeCommand("pop");
        sLogic.executeCommand("filter is:asdf");
        assertEquals(sTextUI.getFeedbackMessage(), FeedbackMessage.ERROR_INVALID_FILTER);
        sLogic.executeCommand("filter searc:task");
        assertEquals(sTextUI.getFeedbackMessage(), FeedbackMessage.ERROR_INVALID_FILTER);
    }
}
