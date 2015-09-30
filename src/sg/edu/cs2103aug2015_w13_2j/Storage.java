package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@@author Kenneth

/**
* Storage class that provides methods for file I/O
* 
* @author Lu Yang Kenneth
*/
public class Storage implements StorageInterface {
	public Storage() {
        // Empty constructor
    }
	
    public void writeFile(List<Task> tasks, String filename) throws IOException {
    	/* TODO:
    	 * 1. Convert List to string
    	 * 2. Save string to file
    	 */
    }

    public List<Task> readFile(String filename) throws IOException {
        List<Task> tasks = new ArrayList<Task>();
        /* TODO:
         * 1. Get string content of file
         * 2. Split string by \n
         * 3. Save to List
        */
        
        return tasks;
    }
}
