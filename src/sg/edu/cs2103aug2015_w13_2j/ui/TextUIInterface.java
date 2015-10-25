package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0121410H

public interface TextUIInterface {
    /**
     * Injects the dependency on an object implementing the
     * {@link LogicInterface} into this TextUI component
     * 
     * @param logic
     *            An object implementing the {@link LogicInterface}. A handle to
     *            this object will be retained so that
     *            {@link LogicInterface#executeCommand(String)} can be passed
     *            commands entered
     */
    public void injectDependency(LogicInterface logic);

    /**
     * Displays the provided list of Task objects
     * 
     * @param tasks
     *            List of Task objects to be displayed
     */
    public void display(ArrayList<Task> tasks);

    /**
     * Displays the provided string directly on the TextPane via
     * {@link TextPane#print(String)}
     * 
     * @param s
     *            String to be displayed
     */
    public void display(String s);

    /**
     * Displays the provided FeedbackMessage object to the user. The
     * FeedbackMessage object encapsulates the feedback message text as well as
     * the styling with which to display the message
     * 
     * @param f
     *            FeedbackMessage object to be displayed
     * @see FeedbackMessage
     */
    public void feedback(FeedbackMessage f);

    /**
     * Displays the string representing the currently active filter chain
     * 
     * @param s
     *            String representing the currently active filter chain
     */
    public void setFilter(String s);
}
