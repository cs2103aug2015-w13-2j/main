package sg.edu.cs2103aug2015_w13_2j;

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
}
