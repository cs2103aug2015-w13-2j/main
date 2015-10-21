package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class StorageTest {
    // Use the same Storage object across the tests
    private Storage storage = new Storage();
    
    @Test
    public void dataFilePathTest() throws Exception {
    	// Check current execution directory
    	String dir = System.getProperty("user.dir");
    	System.out.println(dir);

    	// Save the original data file path
    	String originalPath = storage.getDataFilePath();
    	
    	String testPath = "StorageTest.txt";
    	storage.setDataFilePath(testPath);
    	String dataFilePath = storage.getDataFilePath();

    	assertTrue(testPath.equals(dataFilePath));
    	
    	// Reset data file path to original
    	storage.setDataFilePath(originalPath);
    }
    
    @Test
    public void readAndWriteStringTest() throws Exception {
    	String testPath = "StorageTest.txt";

    	String stringToWrite = "The quick brown fox jumps over the lazy dog.\nHello world!\n";
    	storage.writeStringToFile(stringToWrite, testPath);
    	String stringWritten = storage.readStringFromFile(testPath);
    	
    	assertTrue(stringToWrite.equals(stringWritten));
    }
    
    @Test
    public void readAndWriteTasksTest() throws Exception {
    	String testPath = "StorageTest.txt";
    	
    	ArrayList<Task> tasksToWrite = new ArrayList<Task>();
    	tasksToWrite.add(new Task("test task one"));
    	tasksToWrite.add(new Task("test task two"));
    	storage.writeTasksToFile(tasksToWrite, testPath);
    	ArrayList<Task> tasksWritten = storage.readTasksFromFile(testPath);
    	
    	assertTrue(tasksToWrite.equals(tasksWritten));
    }
}
