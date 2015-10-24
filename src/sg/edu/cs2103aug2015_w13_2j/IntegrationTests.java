package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIStub;

public class IntegrationTests {
    @Test
    public void testLogicComponent() throws TaskNotFoundException {
        TextUIStub uiStub = new TextUIStub();
        StorageStub storageStub = new StorageStub();
        // Note: LogicWrapper accepts any classes which implements
        // TextUIInterface and StorageInterface respectively
        LogicWrapper<TextUIStub, StorageStub> logicWrapper = new LogicWrapper<>(
                uiStub, storageStub);
        logicWrapper.registerCommandHandler(new AddHandler());

        String taskName = "My first integration test!";
        logicWrapper.executeCommand("add '" + taskName + "'");
        // Task should have been sent to Storage component to be saved
        ArrayList<Task> storageTasks = storageStub.readTasksFromDataFile();
        assertEquals(1, storageTasks.size());
        assertEquals(taskName, storageTasks.get(0).getName());
        // Task should also reside in FilterChain within Logic component
        Task logicTaskZero = logicWrapper.getTask(0);
        assertEquals(taskName, logicTaskZero.getName());
        // Task should be sent to TextUI for display
        ArrayList<Task> tasksForDisplay = uiStub.getTasksForDisplay();
        assertEquals(1, tasksForDisplay.size());
        assertEquals(taskName, tasksForDisplay.get(0).getName());
        // FilterChain should still be /all/
        assertEquals("/all/", uiStub.getFilterChain());
    }

}
