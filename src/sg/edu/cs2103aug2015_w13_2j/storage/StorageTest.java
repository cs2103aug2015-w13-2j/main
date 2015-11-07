package sg.edu.cs2103aug2015_w13_2j.storage;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0124007X

/**
 * {@link StorageInterface} component that contains test cases for the
 * {@link Storage} class. Can be also be used as a stand-in for the actual
 * {@link Storage} class in integration tests as the user's data file preference
 * and defaults have been properly overridden.
 * 
 * @author Lu Yang Kenneth
 */
public class StorageTest extends Storage {
    private static final String PREFKEY_TESTFILE_PATH = "FUNDUE_TESTFILE_PATH";
    private static final String DEFAULT_TESTFILE_PATH = "./FunDUE_test.txt";
    private static final Logger LOGGER = Logger
            .getLogger(Storage.class.getName());

    public StorageTest() {
        sPrefKey = PREFKEY_TESTFILE_PATH;
        sDefaultPath = DEFAULT_TESTFILE_PATH;
        loadDataFile();
    }

    @Test
    public void readAndWriteTasksTest() throws Exception {
        ArrayList<Task> tasksToWrite = new ArrayList<Task>();
        tasksToWrite.add(new Task("test task one"));
        tasksToWrite.add(new Task("test task two"));
        writeTasksToDataFile(tasksToWrite);
        ArrayList<Task> tasksWritten = readTasksFromDataFile();
        assertTrue(tasksToWrite.equals(tasksWritten));
    }
    
    //@@author A0133387B
    /**to facilitate integration testing
     * This method is used to clear the test file specified in the IntegrationTest JUnit class
    **/
    public void clearTestFileContents(){
    	PrintWriter writer = null;
    	File testFile = getDataFile();
		try {
			writer = new PrintWriter(testFile);
			System.out.println("IN STORAGE TEST");
			LOGGER.log(Level.INFO, testFile.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	writer.print("");
    	writer.close();
    }
}
