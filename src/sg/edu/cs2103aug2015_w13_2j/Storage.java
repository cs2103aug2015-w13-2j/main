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
    public void writeFile(List<Task> tasks, String filename) throws IOException {
    	// TODO
    }

    public List<Task> readFile(String filename) throws IOException {
        List<Task> tasks = new ArrayList<Task>();
        // TODO
        
        return tasks;
    }
    
    public void exportFile(String path) throws IOException {
    	// TODO 
    }
}
