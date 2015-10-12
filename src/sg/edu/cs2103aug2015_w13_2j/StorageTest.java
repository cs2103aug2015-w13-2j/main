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

    	String stringToWrite = "NAME:Task one\nCOMPLETED:FALSE\nARCHIVED:FALSE\nCREATED:1443958836657\nIMPORTANT:FALSE\n\n";
    	stringToWrite += "NAME:Task two\nCOMPLETED:FALSE\nARCHIVED:FALSE\nCREATED:1443958836657\nIMPORTANT:FALSE\n\n";
    	stringToWrite += "NAME:Task three\nCOMPLETED:FALSE\nARCHIVED:FALSE\nCREATED:1443958836657\nIMPORTANT:FALSE\n\n";
    	storage.writeStringToFile(stringToWrite, filename);
    	List<Task> listWritten = storage.readTasksFromFile(filename);
    	storage.writeTasksToFile(listWritten, filename);
    	String stringWritten = storage.readStringFromFile(filename);
    	
    	assertTrue(stringToWrite.equals(stringWritten));
    }
    
    @Test
    public void dataFilePathTest() throws Exception {
    	// current execution directory
    	String dir = System.getProperty("user.dir");
    	System.out.println(dir);
    	
    	// create (empty) data file
    	String dataFilePath = storage.readStringFromFile("DATA_FILE_PATH");
    	System.out.println(dataFilePath);
    	storage.writeStringToFile("", dataFilePath);
    }
    
    @Test
    public void stringEscapeTest() throws Exception {
    	/* StringEscapeTestInput.txt looks like this:
    	 * > NAME:Task one" has s'trange\ncharacters
    	 * > COMPLETED:FALSE
    	 * > ARCHIVED:FALSE
    	 * > CREATED:1443958836657
    	 * > IMPORTANT:FALSE
    	 */
    	List<Task> list = storage.readTasksFromFile("StringEscapeTestInput.txt");
    	storage.writeTasksToFile(list, "StringEscapeTestOutput.txt");
    	
    	String inputContent = storage.readStringFromFile("StringEscapeTestInput.txt");
    	String outputContent = storage.readStringFromFile("StringEscapeTestOutput.txt");
    	
    	assertTrue(inputContent.equals(outputContent));
    }
}
