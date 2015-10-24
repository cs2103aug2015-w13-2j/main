package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

//@@author A0121410H

public interface TextUIInterface {
    /**
     * Displays the list of Task objects
     * 
     * @param tasks
     *            The list of Task objects to be displayed
     */
    public void display(ArrayList<Task> tasks);

    /**
     * Displays feedback to the user whenever a command entered has side
     * effects. The Message enum encapsulates all required information such as
     * the text to be displayed and the styling
     * 
     * @param m
     *            The Message enum to be displayed
     * @see TextUI.Message
     */
    public void feedback(FeedbackMessage m);

    /**
     * Displays the currently active chain of filters in the UI
     * 
     * @param s
     *            The string representing the currently active filter chain
     */
    public void setFilter(String s);
}
