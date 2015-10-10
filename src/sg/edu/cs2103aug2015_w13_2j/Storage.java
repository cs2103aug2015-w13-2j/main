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
	public String readRawFile(String filename) throws Exception {
    	// Files.readAllBytes() uses UTF-8 character encoding
    	// and ensures that the file is closed after all bytes are read
		String content = new String(Files.readAllBytes(Paths.get(filename)));
		return content;
	}

    public List<Task> readFile(String filename) throws Exception {
    	String content = readRawFile(filename);
    	String[] taskArray = content.split("\r\r|\n\n");
    	
    	List<Task> tasks = new ArrayList<Task>();
    	for(String taskString : taskArray) {
    		if(taskString.isEmpty() || taskString.equals("\r") || taskString.equals("\n")) {
    			continue;
    		} else {
    			tasks.add(TaskInterface.parseTask(taskString));
    		}
    	}
        return tasks;
    }
	
    public void writeRawFile(String content, String filename) throws IOException {
    	Files.write(Paths.get(filename), content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public void writeFile(List<Task> tasks, String filename) throws IOException {
    	String content = "";
    	for(Task task : tasks) {
    		content += task.toString() + "\n";
    	}
    	writeRawFile(content, filename);
    }
}
