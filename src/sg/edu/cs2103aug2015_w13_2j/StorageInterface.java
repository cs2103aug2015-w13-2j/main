package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.util.List;

//@@author A0124007X
public interface StorageInterface {
	/**
     * Reads the list of tasks from the data file
     * 
     * @return The list of tasks stored in the data file
     * @throws Exception
     */
    public List<Task> readTasksFromDataFile() throws Exception;
    
    /**
     * Writes the provided list of tasks to the data file
     * 
     * @param tasks
     *            The list of tasks to be written to the data file
     * @param filepath
     *            The path of the data file
     * @throws IOException
     */
    public void writeTasksToDataFile(List<Task> tasks) throws IOException;
}
