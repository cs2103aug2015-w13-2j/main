package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;

import javax.swing.JFileChooser;

import javafx.stage.FileChooser;

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

    // @@author A0121410H

    /**
     * Creates a {@link JFileChooser} dialog which restricts the user to select
     * {@code .txt} files only. If a file is successfully chosen, the user's
     * preference will be recorded and the selected file will used as the data
     * file. TODO: Use JavaFX {@link FileChooser} instead for native file
     * choosers
     */
    public void showChangeDataFilePathDialog();
}
