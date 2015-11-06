package sg.edu.cs2103aug2015_w13_2j.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;

// @@author A0124007X

/**
 * Storage component which provides methods to store and retrieve a list of Task
 * objects to and from disk. Stores the user's preference for location of data
 * file with the {@value #PREFKEY_DATAFILE_PATH} preference key.
 * 
 * @author Lu Yang Kenneth
 */
public class Storage implements StorageInterface {
    public static final String DEFAULT_DATAFILE_PATH = "./FunDUE.txt";
    private static final Logger LOGGER = Logger
            .getLogger(Storage.class.getName());
    private static final Preferences PREFERENCES = Preferences
            .userNodeForPackage(Storage.class);
    private static final String PREFKEY_DATAFILE_PATH = "FUNDUE_DATAFILE_PATH";

    private static Storage sInstance;
    private static File mDataFile;
    private static String backupFileForTesting = DEFAULT_DATAFILE_PATH;
    
    protected static String sPrefKey = PREFKEY_DATAFILE_PATH;
    protected static String sDefaultPath = DEFAULT_DATAFILE_PATH;
    
    /**
     * Protected constructor
     */
    protected Storage() {
        LOGGER.log(Level.WARNING,
                "Warning for preferences initialization on Windows machines is"
                        + " alright and does not have any ill effects,"
                        + " preferences are still stored");
        switchTodataFile(); //in case the file has been changed before especially for testing
        //loadDataFile();
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

    @Override
    public ArrayList<Task> readTasksFromDataFile() {
        try {
            String data = readDataFileContents();
            ArrayList<Task> tasks = TaskInterface.parseTasks(data);
            LOGGER.log(Level.INFO, "Number of Tasks read: " + tasks.size());
            return tasks;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to read tasks from data file", e);
        } catch (InvalidTaskException e) {
            LOGGER.log(Level.WARNING, "Invalid task encountered", e);
        }
        return new ArrayList<Task>();
    }

    @Override
    public void writeTasksToDataFile(ArrayList<Task> tasks) {
        String data = Task.toString(tasks);
        try {
            writeDataFileContents(data);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to write tasks to data file", e);
        }
    }

    @Override
    public File getDataFile() {
        return mDataFile;
    }

    @Override
    public void setDataFile(File newDataFile) {
        if (newDataFile != null) {
            PREFERENCES.put(sPrefKey, newDataFile.getAbsolutePath());
            loadDataFile();
        }
    }
    
   
    /**
     * Loads the file specified as per user preference to be used as the data
     * file. If the user had not specified a preferred file path, the default
     * path of {@value #DEFAULT_DATAFILE_PATH} will be used instead. The data
     * file will be created if it does not exist
     */
    protected void loadDataFile() {
        String path = PREFERENCES.get(sPrefKey, sDefaultPath);
        LOGGER.log(Level.INFO, "Loading data file: " + path);
        mDataFile = new File(path);
        if (!mDataFile.exists()) {
            try {
                mDataFile.createNewFile();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Unable to create data file at <"
                        + path + ">, possibly lacking premissions");
            }
        }
    }

    /**
     * Reads the contents of the data file
     * 
     * @return String of the data file contents
     * @throws IOException
     *             Thrown when an I/O error occurs when reading from the data
     *             file
     */
    private String readDataFileContents() throws IOException {
        // Files.readAllBytes() uses UTF-8 character encoding and ensures that
        // the file is closed after all bytes are read
        return new String(Files.readAllBytes(mDataFile.toPath()));
    }

    /**
     * Writes the provided string contents to the data file
     * 
     * @param contents
     *            String contents to be written to the data file
     * @throws IOException
     *             Thrown when an I/O error occurs when writing to the data file
     */
    public void writeDataFileContents(String content) throws IOException {
        Files.write(mDataFile.toPath(), content.getBytes());
    }
    
    //@@author A0133387B
    /**to facilitate testing
     * This method is used to clear the test file specified in the IntegrationTest JUnit class
    **/
    public void clearTestFileContents(){
    	PrintWriter writer = null;
		try {
			writer = new PrintWriter(mDataFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	writer.print("");
    	writer.close();
    }
    
    /**
     * For testing purpose of the actual working component.
     * This method switches to use the test file in place of the current file
     * After testing, switch back to the current working file
     * see switchToDataFile below
     * @param newTestFile
     */
    public void switchToTestFile(File newTestFile) {
    	backupFileForTesting = mDataFile.getAbsolutePath();
        if (newTestFile != null) {
            PREFERENCES.put(sPrefKey, newTestFile.getAbsolutePath());
            loadDataFile();
        }
    }
    
    public void switchTodataFile() {
        PREFERENCES.put(sPrefKey, backupFileForTesting);
            loadDataFile();
        
    }

}
