package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H
public interface ParserInterface {
    /**
     * Parses the command into tokens and calls the corresponding method in the
     * Logic component. Keywords such as add, edit or delete for example are
     * case <b>insensitive</b>. Will ignore extraneous or invalid tokens if they
     * have no effect on the intention of the command
     * 
     * @param command
     *            The command string entered by the user
     */
    public void parseCommand(String command);

    /**
     * Retrieves a string in the format of [token1=value][token2=value]...
     * representing the tokens parsed from the given command
     * 
     * @return The string of tokens parsed
     */
    public String getParsedTokens();
}
