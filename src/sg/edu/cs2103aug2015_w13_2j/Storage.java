package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author A0124007X

/**
 * Storage class that provides methods for file I/O
 * 
 * @author Lu Yang Kenneth
 */
public class Storage implements StorageInterface {
    private static final Logger LOGGER = Logger.getLogger(Storage.class
            .getName());

    protected static final String FILE_THAT_STORES_DATAFILEPATH = "DATA_FILE_PATH";
    protected static final String DEFAULT_DATAFILEPATH = "./FunDUE_DATA_FILE.txt";

    private static Storage sInstance;

    /**
     * Protected constructor
     */
    protected Storage() {
        // Do nothing
    }

    /**
     * Retrieves the singleton instance of the Storage component
     * 
     * @return Storage component
     */
    public static Storage getInstance() {
        if (sInstance == null) {
            sInstance = new Storage();
        }
        return sInstance;
    }

    /*****************************************************************
     * Read/Write list of tasks from the data file
     *****************************************************************/
    
    public ArrayList<Task> readTasksFromDataFile() {
        String dataFilePath = getDataFilePath();

        try {
            ArrayList<Task> tasks = readTasksFromFile(dataFilePath);
            LOGGER.log(Level.INFO, "Number of Tasks read: " + tasks.size());
            return readTasksFromFile(dataFilePath);
        } catch (Exception e) {
            // MISSING DATA FILE: create new file at the path
            try {
                writeStringToFile("", dataFilePath);
            } catch (IOException e2) {
                e2.printStackTrace();
            }

            // Return empty ArrayList
            return new ArrayList<Task>();
        }
    }

    public void writeTasksToDataFile(ArrayList<Task> tasks) {
        String dataFilePath = getDataFilePath();

        try {
            writeTasksToFile(tasks, dataFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*****************************************************************
     * Read from / Write to the file that stores the path of the data file
     *****************************************************************/
    
    public String getDataFilePath() {
        String dataFilePath = "";

        try {
            dataFilePath = readStringFromFile(FILE_THAT_STORES_DATAFILEPATH);
        } catch (Exception e) {
            // MISSING FILE THAT STORES THE PATH OF THE DATA FILE:
        	// reset everything to default
            setDataFilePath(DEFAULT_DATAFILEPATH);

            // Default value
            dataFilePath = DEFAULT_DATAFILEPATH;
        }

        return dataFilePath;
    }

    public void setDataFilePath(String filepath) {
        try {
            writeStringToFile(filepath, FILE_THAT_STORES_DATAFILEPATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*****************************************************************
     * Read/Write list of tasks from any file
     *****************************************************************/
    
    /**
     * Reads the list of tasks from the specified file
     * 
     * @return The list of tasks stored in the specified file
     * @throws Exception
     */
    protected ArrayList<Task> readTasksFromFile(String filepath)
            throws Exception {
        String content = readStringFromFile(filepath);
        String[] taskArray = content.split("\r\r|\n\n");

        ArrayList<Task> tasks = new ArrayList<Task>();
        for (String taskString : taskArray) {
            if (taskString.isEmpty() || taskString.matches("\r+")
                    || taskString.matches("\n+") || taskString.matches(" +")) {
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
    protected void writeTasksToFile(ArrayList<Task> tasks, String filepath)
            throws IOException {
        String content = "";
        for (Task task : tasks) {
            content += task.toString() + "\n";
        }
        writeStringToFile(content, filepath);
    }

    /*****************************************************************
     * Read/Write string from any file
     *****************************************************************/
    
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
    protected void writeStringToFile(String content, String filepath)
            throws IOException {
        Files.write(Paths.get(filepath), content.getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
