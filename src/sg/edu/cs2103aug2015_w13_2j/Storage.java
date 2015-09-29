package sg.edu.cs2103aug2015_w13_2j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Storage implements StorageInterface {
    public void writeFile(List<Task> tasks, String filename) throws IOException {
    }

    public List<Task> readFile(String filename) throws IOException {
        List<Task> tasks = new ArrayList<Task>();
        return tasks;
    }

}
