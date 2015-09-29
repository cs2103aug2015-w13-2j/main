package sg.edu.cs2103aug2015_w13_2j;

import java.awt.event.KeyListener;

//@@author A0121410H
public interface TextUIInterface {
    /**
     * Prints the string to the text pane
     * 
     * @param s
     *            The string to be printed
     */
    public void print(String s);

    /**
     * Prints the string to replace the previously printed string
     * 
     * @param s
     *            The string to be printed in replacement of previous string
     */
    public void printr(String s);

    /**
     * Prints the string to the text pane with a newline. Internally calls print
     * with newline appended to the provided string
     * 
     * @param s
     *            The string to be printed
     */
    public void println(String s);

    /**
     * Prints the string to replace the previously printed string with a
     * newline. Internally calls printr with newline appended to provided string
     * 
     * @param s
     *            The string the be printed in replacement of previous string
     */
    public void printlnr(String s);

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
