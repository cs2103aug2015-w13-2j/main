package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIInterface;

public class LogicWrapper<T extends TextUIInterface, S extends StorageInterface>
        extends Logic {
    private T mTextUI;
    private S mStorage;

    public LogicWrapper(T textUI, S storage) {
        mTextUI = textUI;
        mStorage = storage;
        mFilterChain = new FilterChain(new ArrayList<Task>());
        mFeedback = new FeedbackMessage("", FeedbackType.INFO);
    }

    @Override
    protected void display() {
        mTextUI.display(mFilterChain.getTasks());
        mTextUI.display(mFilterChain.getTasksForDisplay());
        mTextUI.setFilter(mFilterChain.getFilterChain());
    }

    @Override
    protected void writeTasks() {
        mStorage.writeTasksToDataFile(mFilterChain.getTasks());
    }
}
