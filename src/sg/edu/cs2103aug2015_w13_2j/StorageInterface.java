package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.util.List;

//@@author A0121410H
public interface StorageInterface {
    /**
     * Writes the provided list of tasks to a text file
     * 
     * @param tasks
     *            The list of tasks to be written to disk
     * @param filename
     *            The filename to write to
     * @throws IOException
     */
    public void writeFile(List<Task> tasks, String filename) throws IOException;

    /**
     * Reads from the specified file the list of tasks stored within
     * 
     * @param filename
     *            The file to be read from
     * @return The list of tasks stored in the file
     * @throws IOException
     */
    public List<Task> readFile(String filename) throws IOException;
    
    //@@author Kenneth
    /**
     * Exports a backup of the data file to a user-defined location
     * 
     * @param path
     *            The directory to export the file to
     * @throws IOException
     */
    public void exportFile(String path) throws IOException;
}