package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    
    protected String getDataFilePath() {
		String dataFilePath = "";
		
		try {
			dataFilePath = readStringFromFile(TEST_FILE_PATH);
		} catch (Exception e) {
			// MISSING FILE THAT STORES THE PATH OF THE DATA FILE: reset everything to default
			setDataFilePath(TEST_FILE_NAME);
			
			// Default value
			dataFilePath = TEST_FILE_NAME;
		}
		
		return dataFilePath;
	}
    
    public void writeTasksToDataFile(ArrayList<Task> tasks) {
    	String dataFilePath = getDataFilePath();
    	
    	try {
			writeTasksToFile(tasks, dataFilePath);
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
