package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.util.List;

//@@author A0121410H
public interface StorageInterface {
	/**
     * Reads the contents of the specified text file
     * 
     * @param filename
     *            The file to be read from
     * @return A string of the contents of the file
     * @throws Exception
     */
	public String readRawFile(String filename) throws Exception;
	
    /**
     * Reads the list of tasks from the specified text file
     * 
     * @param filename
     *            The file to be read from
     * @return The list of tasks stored in the file
     * @throws Exception
     */
    public List<Task> readFile(String filename) throws Exception;
    
    /**
     * Writes a string to a text file
     * 
     * @param content
     *            A string of the content to be written to to the file
     * @param filename
     *            The file to write to
     * @throws IOException
     */
    public void writeRawFile(String content, String filename) throws IOException;
    
    /**
     * Writes the provided list of tasks to a text file
     * 
     * @param tasks
     *            The list of tasks to be written to the file
     * @param filename
     *            The file to write to
     * @throws IOException
     */
    public void writeFile(List<Task> tasks, String filename) throws IOException;
}
