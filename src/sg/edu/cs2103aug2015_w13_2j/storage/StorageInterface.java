package sg.edu.cs2103aug2015_w13_2j.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0124007X

public interface StorageInterface {
    /**
     * Reads the data file and returns a list of parsed Task objects
     * 
     * @return List of Task objects
     */
    public ArrayList<Task> readTasksFromDataFile();

    /**
     * Writes the provided list of tasks to the data file
     * 
     * @param tasks
     *            List of tasks to be written to the data file
     */
    public void writeTasksToDataFile(ArrayList<Task> tasks);

    /**
     * Retrieves the {@link File} object representing the current FunDUE data
     * file.
     * 
     * @return {@link File} object representing the current FunDUE data file.
     */
    public File getDataFile();

    /**
     * Sets the FunDUE data file to the provided {@link File} object and updates
     * the user's data file path preference. If the {@link File} object points
     * to a non-existent file, the file will be created.
     * 
     * @param newDataFile
     *            {@link File} object to be used as the new FunDUE data file. If
     *            {@code null} then this method does nothing.
     */
    public void setDataFile(File newDataFile);
    
    public void writeDataFileContents(String content) throws IOException;
}
