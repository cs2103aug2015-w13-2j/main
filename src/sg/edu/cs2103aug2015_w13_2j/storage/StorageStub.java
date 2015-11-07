package sg.edu.cs2103aug2015_w13_2j.storage;

import java.io.File;
import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0121410H

/**
 * {@link StorageInterface} component for testing purposes. Stores {@link Task}
 * objects in memory and does <b>not</b> read from or write to disk.
 */
public class StorageStub implements StorageInterface {
    private final ArrayList<Task> mStorage = new ArrayList<Task>();

    private static StorageInterface sInstance;

    /**
     * Private constructor.
     */
    private StorageStub() {
        // Do nothing
    }

    /**
     * Retrieves the single instance of this {@link StorageInterface} component.
     * 
     * @return {@link StorageInterface} component.
     */
    public static synchronized StorageInterface getInstance() {
        if (sInstance == null) {
            sInstance = new StorageStub();
        }
        return sInstance;
    }

    @Override
    public ArrayList<Task> readTasksFromDataFile() {
        return mStorage;
    }

    @Override
    public void writeTasksToDataFile(ArrayList<Task> tasks) {
        mStorage.clear();
        mStorage.addAll(tasks);
    }

    @Override
    public File getDataFile() {
        return null;
    }

    @Override
    public void setDataFile(File newDataFile) {
        // Do nothing
    }

    @Override
    public void clearDataFile() {
        mStorage.clear();
    }
}
