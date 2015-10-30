package sg.edu.cs2103aug2015_w13_2j.storage;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

/**
 * Storage component for testing other components. Does <b>not</b> read from or
 * write to disk, instead stores Task objects in-memory
 */
public class StorageStub implements StorageInterface {
    private ArrayList<Task> mStorage = new ArrayList<Task>();

    @Override
    public void showChangeDataFilePathDialog() {
        // Do nothing
    }

    @Override
    public ArrayList<Task> readTasksFromDataFile() {
        return mStorage;
    }

    @Override
    public void writeTasksToDataFile(ArrayList<Task> tasks) {
        mStorage = new ArrayList<Task>(tasks);
    }
}