package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class StorageTest {
    // Use the same Storage object across the tests
    private Storage storage = new Storage();

    @Test
    public void readAndWriteTest() {
        /* TODO:
         * Create a sample List
         * Write to a test file
         * Read from test file
         * See if sample list and obtained list are the same
         */
    	List<Task> toWrite = new ArrayList<Task>();
    	toWrite.add(new Task("Task one"));
    	toWrite.add(new Task("Task two"));
    	toWrite.add(new Task("Task three"));
    	
    	storage.writeFile(toWrite, "StorageTestTXT.txt");
    	
    	List<Task> toRead = storage.readFile("StorageTestTXT.txt");
    	
    	assertTrue(toWrite.equals(toRead));
    }
}
