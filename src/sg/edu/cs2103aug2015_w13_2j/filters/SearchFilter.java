package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0121410H

/**
 * {@link Filter} class that filters for tasks containing the provided search
 * terms. A {@link Task} is kept if the name retrieved via
 * {@link Task#getName()} contains <b>any</b> of the needles.<br>
 * <br>
 * The filter predicate <b>for any needle</b> is as follows:<br>
 * 
 * <pre>
 * task.getName().contains(needle)
 * </pre>
 * 
 * @author Zhu Chunqi
 */
public class SearchFilter extends Filter {
    private ArrayList<String> mNeedles;

    public SearchFilter(ArrayList<String> needles) {
        mNeedles = needles;
        // Concatenate the search terms to form the filter name
        mName = "search:" + String.join(",", mNeedles);
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
