package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

//@@author A0124007X

/**
* Storage class that provides methods for file I/O
* 
* @author Lu Yang Kenneth
*/
public class Storage implements StorageInterface {
	protected final String FILE_THAT_STORES_FILEPATH = "DATA_FILE_PATH";
	protected final String DEFAULT_DATAFILEPATH = "./FunDUE_DATA_FILE.txt";
	
	private static Storage sInstance;
	private String _datafilepath;
	
	/**
	 * Protected constructor
	 */
	protected Storage() {
		loadDataFilePath();
	}
	
	/**
     * Retrieves the singleton instance of the Storage component
     * 
     * @return Storage component
     */
	public static Storage getInstance() {
	    if(sInstance == null) {
	        sInstance = new Storage();
	    }
	    return sInstance;
	}
	
	public ArrayList<Task> readTasksFromDataFile() {
        try {
			return readTasksFromFile(_datafilepath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Error("File is corrupted.");
		}
    }
    
    public void writeTasksToDataFile(ArrayList<Task> tasks) {
    	try {
			writeTasksToFile(tasks, _datafilepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
    /**
     * Reads the contents of the file
     * that stores the path of the data file
     * 
     * @return A string containing the data file path
     */
	protected String getDataFilePath() throws Exception {
		return _datafilepath;
	}
	
    /**
     * Reads the contents of the file
     * that stores the path of the data file
     * and loads it into a variable for easy access
     */
	private void loadDataFilePath() {
		try {
			_datafilepath = readStringFromFile(FILE_THAT_STORES_FILEPATH);
		} catch (Exception e) {
			try {
				setDataFilePath(DEFAULT_DATAFILEPATH);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
     * Writes the contents of the file
     * that stores the path of the data file
     * 
     * @param filepath
     *            The path of the data file
     * @throws IOException
     */
	protected void setDataFilePath(String filepath) throws IOException {
		_datafilepath = filepath;
		writeStringToFile(_datafilepath, FILE_THAT_STORES_FILEPATH);
	}
	
	/**
     * Reads the list of tasks from the specified file
     * 
     * @return The list of tasks stored in the specified file
     * @throws Exception
     */
    protected ArrayList<Task> readTasksFromFile(String filepath) throws Exception {
    	String content = readStringFromFile(filepath);
    	String[] taskArray = content.split("\r\r|\n\n");
    	
    	ArrayList<Task> tasks = new ArrayList<Task>();
    	for(String taskString : taskArray) {
    		if(taskString.isEmpty() || taskString.equals("\r") || taskString.equals("\n")) {
    			continue;
    		} else {
    			tasks.add(TaskInterface.parseTask(taskString));
    		}
    	}
        return tasks;
    }
    
    /**
     * Writes the provided list of tasks to the specified file
     * 
     * @param tasks
     *            The list of tasks to be written to the specified file
     * @param filepath
     *            The path of the file
     * @throws IOException
     */
    protected void writeTasksToFile(ArrayList<Task> tasks, String filepath) throws IOException {
    	String content = "";
    	for(Task task : tasks) {
    		content += task.toString() + "\n";
    	}
    	writeStringToFile(content, filepath);
    }
    
	/**
     * Reads the contents of the specified text file
     * 
     * @param filepath
     *            The path of the file to be read from
     * @return The contents of the file
     * @throws Exception
     */
	protected String readStringFromFile(String filepath) throws Exception {
    	// Files.readAllBytes() uses UTF-8 character encoding
    	// and ensures that the file is closed after all bytes are read
		String content = new String(Files.readAllBytes(Paths.get(filepath)));
		return content;
	}
	
	/**
     * Writes a string to the specified text file
     * 
     * @param content
     *            The contents to be written to the file
     * @param filepath
     *            The path of the file to write to
     * @throws IOException
     */
	protected void writeStringToFile(String content, String filepath) throws IOException {
    	Files.write(Paths.get(filepath), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
