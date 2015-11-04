package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author NOAUTHOR

public class SearchFilter extends Filter {
    private ArrayList<String> mNeedles;

    public SearchFilter(ArrayList<String> needles) {
        FILTER_NAME = "search:";
        for (int i = 0; i < needles.size(); i++) {
            FILTER_NAME += needles.get(i);
            if (i < needles.size() - 1) {
                FILTER_NAME += ",";
            }
        }
        mNeedles = needles;
    }

    @Override
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            for (String needle : mNeedles) {
                if (task.getName().contains(needle)) {
                    mTasks.add(task);
                    break;
                }
            }
        }
    }

}
