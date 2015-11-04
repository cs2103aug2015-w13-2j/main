package sg.edu.cs2103aug2015_w13_2j.parser;

import java.util.Arrays;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;

// @@author A0121410H

/**
 * Parser class that handles the interpretation of all tokens in the user's
 * command input. This class ensures that all tokens of the user command are of
 * a specific Token type.
 * 
 * @author Zhu Chunqi
 */
public class Parser implements ParserInterface {
    private static final Logger LOGGER = Logger
            .getLogger(Parser.class.getName());

    private enum State {
        GENERAL, ALPHA_NUM, DATE, FLAG, ID, NAME
    }

    public static final String FLAG_END = "e";
    public static final String FLAG_START = "s";
    public static final String[] FLAGS = { FLAG_END, FLAG_START };

    private static Parser sInstance;
    private Set<String> mReserved;
    private State mState;
    private String mCommandString;
    private Command mCommand;
    private int mParserPos;

    /**
     * Protected constructor
     */
    protected Parser() {
        // Do nothing
    }

    /**
     * Retrieves the singleton instance of the Parser component
     * 
     * @return Parser component
     */
    public static Parser getInstance() {
        if (sInstance == null) {
            sInstance = new Parser();
        }
        return sInstance;
    }

    public Command parseCommand(Logic logic, String command) {
        mReserved = logic.getReservedKeywords();
        mState = State.GENERAL;
        mParserPos = 0;
        mCommand = new Command();
        mCommandString = command;
        startParserLoop();
        LOGGER.log(Level.INFO, getParsedTokens());
        return mCommand;
    }

    public String getParsedTokens() {
        StringBuilder sb = new StringBuilder();
        for (Token token : mCommand) {
            sb.append('[');
            sb.append(token.type);
            if (token.value != null) {
                sb.append('=');
                sb.append(token.value);
            }
            sb.append(']');
        }
        return sb.toString();
    }

    private void startParserLoop() {
        // Buffer to store parsed tokens
        String s;
        char openingQuote = '"';

        while (hasNext()) {
            if (!trim()) {
                break;
            }

            switch (mState) {
              case ALPHA_NUM :
                s = nextDelimiter(' ');
                if (isReserved(s)) {
                    mCommand.addToken(Token.Type.RESERVED, s);
                } else {
                    mCommand.addToken(Token.Type.ALPHA_NUM, s);
                }
                mState = State.GENERAL;
                break;
              case DATE :
                s = nextDelimiter(' ');
                try {
                    String dateString = ParserInterface.parseDate(s);
                    mCommand.addToken(Token.Type.DATE, dateString);
                } catch (IllegalDateFormatException e) {
                    mCommand.addToken(Token.Type.DATE_INVALID, s);
                }
                mState = State.GENERAL;
                break;
              case FLAG :
                // Consume dash character
                next();
                s = nextDelimiter(' ');

                // Check if flag is valid and add token
                if (isValidFlag(s)) {
                    mCommand.addToken(Token.Type.FLAG, s);
                    // TODO: May not always transition to date state
                    mState = State.DATE;
                } else {
                    mCommand.addToken(Token.Type.FLAG_INVALID, s);
                    mState = State.GENERAL;
                }
                break;
              case GENERAL :
                if (peek() == ' ') {
                    // Skip over all intervening whitespace
                    while (hasNext() && peek() == ' ') {
                        next();
                    }
                } else if (peek() == '"' || peek() == '\'') {
                    openingQuote = next();
                    mState = State.NAME;
                } else if (peek() == '-') {
                    mState = State.FLAG;
                } else if (peek() >= '0' && peek() <= '9') {
                    mState = State.ID;
                } else {
                    mState = State.ALPHA_NUM;
                }
                break;
              case ID :
                s = nextDelimiter(' ');
                try {
                    Integer.parseInt(s);
                    mCommand.addToken(Token.Type.ID, s);
                } catch (NumberFormatException e) {
                    // e.printStackTrace();
                    mCommand.addToken(Token.Type.ID_INVALID, s);
                }
                mState = State.GENERAL;
                break;
              case NAME :
                s = nextDelimiter(openingQuote);
                mCommand.addToken(Token.Type.NAME, s);
                // Consume closing quote if not end of command
                if (hasNext()) {
                    next();
                }
                mState = State.GENERAL;
                break;
              default :
                throw new Error("Invalid parser state: " + mState);
            }
        }
    }

    /**
     * Checks if the end of the command string has been reached
     * 
     * @return True if there are more characters to read, false otherwise
     */
    private boolean hasNext() {
        return mParserPos < mCommandString.length();
    }

    /**
     * Retrieves the next character of the command string without advancing the
     * parser position
     * 
     * @return Next character of the command string
     */
    private char peek() {
        return mCommandString.charAt(mParserPos);
    }

    /**
     * Retrieves the next character of the command and advances the parser one
     * position forwards
     * 
     * @return Next character of the command string
     */
    private char next() {
        return mCommandString.charAt(mParserPos++);
    }

    /**
     * Reads the command string character by character until the provided
     * delimiter or end of the command string is encountered. Returns the
     * characters read as a string
     * 
     * @param delimiter
     *            Character delimiter to stop reading when encountered
     * @return String read from the current parser position until the delimiter
     *         or end of the command string is encountered
     */
    private String nextDelimiter(char delimiter) {
        StringBuilder sb = new StringBuilder();
        while (hasNext() && peek() != delimiter) {
            sb.append(next());
        }
        return sb.toString();
    }

    /**
     * Advances the parser position to the next non-whitespace character and
     * returns the value of {@link #hasNext()}
     * 
     * @return Value of {@link #hasNext()}
     */
    private boolean trim() {
        if (hasNext() && peek() == ' ') {
            while (hasNext() && peek() == ' ') {
                next();
            }
        }
        return hasNext();
    }

    /**
     * Checks if the provided string is a reserved keyword in a case
     * <b>insensitive</b> manner. The provided string is first converted to all
     * lower case before the comparison is carried out
     * 
     * @param s
     *            String to be checked
     * @return True if the string is a reserved keyword, false otherwise
     */
    private boolean isReserved(String s) {
        return mReserved.contains(s.toLowerCase());
    }

    /**
     * Checks if the provided string is a valid flag in a case
     * <b>insensitive</b> manner The provided string is first converted to all
     * lower case before the comparison is carried out
     * 
     * @param s
     *            String to be checked
     * @return True if the string is a valid flag, false otherwise
     */
    private boolean isValidFlag(String flag) {
        return Arrays.asList(FLAGS).contains(flag.toLowerCase());
    }
}
