package sg.edu.cs2103aug2015_w13_2j.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;

// @@author A0124007X

/**
 * Storage component which provides methods to store and retrieve a list of Task
 * objects to and from disk. Stores the user's preference for location of data
 * file and provides file picker dialog to change the preferred location
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
        loadDataFile();
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
    public void showChangeDataFilePathDialog() {
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "FunDue Data File (.txt)", "txt");
        fc.setDialogTitle("Select FunDue Data File");
        fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setSelectedFile(mDataFile);
        int returnValue = fc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            mDataFile = fc.getSelectedFile();
            PREFERENCES.put(sPrefKey, fc.getSelectedFile().getAbsolutePath());
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

    // @@author A0124007X

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
    private void writeDataFileContents(String content) throws IOException {
        Files.write(mDataFile.toPath(), content.getBytes());
    }
}
