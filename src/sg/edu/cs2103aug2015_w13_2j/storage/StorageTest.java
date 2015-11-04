package sg.edu.cs2103aug2015_w13_2j.storage;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0124007X

public class StorageTest extends Storage {
    private static final String PREFKEY_TESTFILE_PATH = "FUNDUE_TESTFILE_PATH";
    private static final String DEFAULT_TESTFILE_PATH = "./FunDUE_test.txt";

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
}
