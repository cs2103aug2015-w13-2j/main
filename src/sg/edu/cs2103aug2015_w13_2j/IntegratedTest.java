package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.ui.TextUI;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIStub;

public class IntegratedTest {
	@Test
	public void integratedTest() {
		TextUIStub uiStub = new TextUIStub();
		StorageStub storageStub = new StorageStub();
		LogicStub logicStub = new LogicStub();//do not change this method or use getInstance()
		//Testing if the stubs are indeed separate from the main classes
		assertEquals(uiStub.equals(TextUI.getInstance()), false);
		assertEquals(storageStub.equals(Storage.getInstance()), false);
		assertEquals(logicStub.equals(Logic.getInstance()), false);
		System.out.println("Test file: " + storageStub.getDataFilePath());
		logicStub.executeCommand("add 'My first integration test!'", uiStub, storageStub);
		ArrayList<Task> storageTaskArray = storageStub.readTasksFromDataFile();
		System.out.println("Test storage: size = " + storageTaskArray.size());
		for(int i = 0; i < storageTaskArray.size(); i++){
			System.out.println("Printing task");
			System.out.println(storageTaskArray.get(i));
		}
		ArrayList<Task> logicTaskArray = new ArrayList<Task>();
		Task dummyTask = new Task("My first integration test!");
		logicTaskArray.add(dummyTask);
		assertEquals(uiStub.display(logicTaskArray).get(0).getName(), dummyTask.getName()); 
	}

}

