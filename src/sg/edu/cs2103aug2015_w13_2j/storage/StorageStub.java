package sg.edu.cs2103aug2015_w13_2j.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author NOAUTHOR

/**
 * Storage component for testing other components. Does <b>not</b> read from or
 * write to disk, instead stores Task objects in-memory
 */
public class StorageStub implements StorageInterface {
    private ArrayList<Task> mStorage = new ArrayList<Task>();
    private static File mDataFile = new File(System.getProperty("user.dir") + "/FunDUE_test.txt");
    @Override
    public ArrayList<Task> readTasksFromDataFile() {
        return mStorage;
    }

    @Override
    public void writeTasksToDataFile(ArrayList<Task> tasks) {
        mStorage = new ArrayList<Task>(tasks);
    }

    public void clearAllTasks() {
        mStorage = new ArrayList<Task>();
        System.out.println(System.getProperty("user.dir"));
        try {
			writeDataFileContents("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public File getDataFile() {
        return mDataFile;
    }

    @Override
    public void setDataFile(File newDataFile) {
        mDataFile = newDataFile;
    }

	@Override
	public void writeDataFileContents(String content) throws IOException {

	}
}
