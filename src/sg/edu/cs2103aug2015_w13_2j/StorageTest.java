package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class StorageTest {
    // Use the same Storage object across the tests
    private Storage storage = new Storage();

    @Test
    public void readAndWriteTest() throws Exception {
    	String filename = "StorageTest.txt";
    	
    	String stringToWrite = "NAME:Task one|CREATED:1443958836657|\nNAME:Task two|CREATED:1443958836657|\nNAME:Task three|CREATED:1443958836657|\n";
    	storage.writeRawFile(stringToWrite, filename);
    	List<Task> listWritten = storage.readFile(filename);
    	storage.writeFile(listWritten, filename);
    	String stringWritten = storage.readRawFile(filename);
    	
    	assertTrue(stringToWrite.equals(stringWritten));
    }
    
    @Test
    public void dataFilePathTest() throws Exception {
        /* Not performed by storage eventually,
         * but this is to test if Java's interaction
         * with file directories work as intended.
         */
    	
    	// current execution directory
    	String dir = System.getProperty("user.dir");
    	System.out.println(dir);
    	
    	// create (empty) data file
    	String dataFilePath = storage.readRawFile("DATA_FILE_PATH");
    	System.out.println(dataFilePath);
    	storage.writeRawFile("", dataFilePath);
    }
}
