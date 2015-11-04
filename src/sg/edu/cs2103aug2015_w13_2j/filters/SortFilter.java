package sg.edu.cs2103aug2015_w13_2j.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author NOAUTHOR

public class SortFilter extends Filter {

    private static final Comparator<Task> END_ASC = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            Date d1 = t1.getEnd();
            Date d2 = t2.getEnd();
            if (d1 == null) {
                return -1;
            } else if (d2 == null) {
                return 1;
            } else {
                return d1.compareTo(d2);
            }
        }
    };
    private static final Comparator<Task> END_DSC = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            Date d1 = t1.getEnd();
            Date d2 = t2.getEnd();
            if (d1 == null) {
                return 1;
            } else if (d2 == null) {
                return -1;
            } else {
                return -d1.compareTo(d2);
            }
        }
    };

    private Comparator<Task> mComparator;

    public class InvalidSortFilterException extends Exception {
        private static final long serialVersionUID = -2791883884133587047L;
    }

    public SortFilter(String sortBy) throws InvalidSortFilterException {
        switch (sortBy) {
        case "end_asc":
            mComparator = END_ASC;
            break;
        case "end_dsc":
            mComparator = END_DSC;
            break;
        default:
            throw new InvalidSortFilterException();
        }
        FILTER_NAME = "sort:" + sortBy;
    }

    @Override
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>(tasks);
        Collections.sort(mTasks, mComparator);
    }
}
