package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class StorageStub extends Storage{
	private String TEST_FILE_PATH = "TEST_FILE_PATH";
	private String TEST_FILE_NAME = "./FunDUE.test.txt";
    public StorageStub(){
	    super();
	    this.setDataFilePath(TEST_FILE_NAME);
	    System.out.println("stub = " + this.getDataFilePath());
	    try {
			this.writeStringToFile("", TEST_FILE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void eraseFileContent(){
    	try {
			Files.write(Paths.get(TEST_FILE_NAME), new byte[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String getDataFilePath(){
    	return TEST_FILE_NAME;
    }
    
    public void writeTasksToDataFile(ArrayList<Task> tasks) {
    	String dataFilePath = getDataFilePath();
    	
    	try {
			writeTasksToFile(tasks, TEST_FILE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	protected void setDataFilePath(String filepath) {
		try {
			writeStringToFile(filepath, TEST_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
