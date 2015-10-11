package sg.edu.cs2103aug2015_w13_2j;

import java.awt.event.KeyListener;
import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.TextUI.Message;

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
    public void feedback(Message m);

    /**
     * Registers the object as a KeyListener for the JTextField component to
     * receive key events
     * 
     * @param listener
     *            The object implementing the KeyListener interface
     */
    public void addTextFieldKeyListener(KeyListener listener);

    /**
     * Deregisters the object as a KeyListener
     * 
     * @param listener
     *            The object to be deregistered
     */
    public void removeTextFieldKeyListener(KeyListener listener);
}
