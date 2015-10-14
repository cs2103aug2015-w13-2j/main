package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H

/**
 * This interface contains methods for classes implementing it as well as for
 * other components Parser, Formatter, TextUI, Controller to access it
 */
public interface LogicInterface {
    /**
     * Executes the command entered by the user
     * 
     * @param command
     *            The entered command
     */
    public void executeCommand(String command);

    /**
     * Echos back the command entered. For testing purposes and as a stub
     * 
     * @param s
     *            The string to echo back
     */
    public void echo(String s);
}
