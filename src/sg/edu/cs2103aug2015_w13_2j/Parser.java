package sg.edu.cs2103aug2015_w13_2j;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Vector;

import javafx.util.Pair;

/**
 * 
 */
public class Parser implements ParserInterface {
    private enum State {
        GENERAL, ALPHA_NUM, DATE, FLAG, ID, NAME
    }

    private enum Token {
        RESERVED("RESERVED"), DATE("DATE"), DATE_INVALID("DATE_INVALID"), FLAG(
                "FLAG"), FLAG_INVALID("FLAG_INVALID"), ID("ID"), ID_INVALID(
                "ID_INVALID"), NAME("NAME"), WHITESPACE("WHITESPACE"),
                ALPHA_NUM("ALPHA_NUM");

        private String mValue;

        Token(String value) {
            mValue = value;
        }

        public String getValue() {
            return mValue;
        }
    }

    public static final String[] RESERVED = { "add", "delete", "edit", "list" };
    public static final String[] FLAGS = { "e", "s" };

    private State mState = State.GENERAL;
    private String mCommand;
    private int mParserPos;
    private Vector<Pair<Token, String>> mTokens = new Vector<Pair<Token, String>>();

    public Parser() {
        // Empty constructor
    }

    public void parseCommand(String command) {
        mState = State.GENERAL;
        mCommand = command;
        mParserPos = 0;
        mTokens.clear();
        startParserLoop();
    }

    public String getParsedTokens() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mTokens.size(); i++) {
            sb.append('[');
            sb.append(mTokens.get(i).getKey().getValue());
            if (mTokens.get(i).getValue() != null) {
                sb.append('=');
                sb.append(mTokens.get(i).getValue());
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
            // Call trim to remove any whitespace between tokens
            trim();

            switch (mState) {

            case ALPHA_NUM:
                s = nextDelimiter(' ');
                if (isReserved(s)) {
                    addToken(Token.RESERVED, s);
                } else {
                    addToken(Token.ALPHA_NUM, s);
                }
                mState = State.GENERAL;
                break;
            case DATE:
                s = nextDelimiter(' ');
                addToken(Token.DATE, s);
                mState = State.GENERAL;
                break;
            case FLAG:
                // Consume dash character
                next();

                // Check if flag is valid and add token
                String flag = String.valueOf(next());
                if (isValidFlag(flag)) {
                    addToken(Token.FLAG, String.valueOf(flag));
                    // TODO: May not always transition to date state
                    mState = State.DATE;
                } else {
                    addToken(Token.FLAG_INVALID, String.valueOf(flag));
                    mState = State.GENERAL;
                }
                break;
            case GENERAL:
                if (peek() == '"' || peek() == '\'') {
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
            case ID:
                s = nextDelimiter(' ');
                try {
                    Integer.parseInt(s);
                    addToken(Token.ID, s);
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                    addToken(Token.ID_INVALID, s);
                }
                mState = State.GENERAL;
                break;
            case NAME:
                s = nextDelimiter(openingQuote);
                addToken(Token.NAME, s);
                // Consume closing quote if not end of command
                if (hasNext()) {
                    next();
                }
                mState = State.GENERAL;
                break;
            default:
                throw new Error("Invalid parser state: " + mState);
            }
        }
    }

    /**
     * Check if the end of the command has been reached
     * 
     * @return True if there is more characters to be read, false otherwise
     */
    private boolean hasNext() {
        return mParserPos < mCommand.length();
    }

    /**
     * Retrieves the next character of the command without advancing the parser
     * position
     * 
     * @return The next character of the command
     */
    private char peek() {
        return mCommand.charAt(mParserPos);
    }

    /**
     * Retrieves the next character of the command and advances the parser
     * position forward
     * 
     * @return The next character of the command
     */
    private char next() {
        return mCommand.charAt(mParserPos++);
    }

    /**
     * Trims the command of whitespace from the current parser position to the
     * next non-whitespace character. Adds a whitespace token to the list of
     * parsed tokens
     */
    private void trim() {
        if (hasNext() && peek() == ' ') {
            addToken(Token.WHITESPACE, null);
            while (hasNext() && peek() == ' ') {
                next();
            }
        }
    }

    /**
     * Utility method to read the command until the delimiter is reached and
     * returns the characters read as a string
     * 
     * @param delimiter
     *            The character delimiter to stop at
     * @return The string read from the current parser position until the
     *         delimiter is reached
     */
    private String nextDelimiter(char delimiter) {
        StringBuilder sb = new StringBuilder();

        while (hasNext() && peek() != delimiter) {
            sb.append(next());
        }

        return sb.toString();
    }

    /**
     * Convenience method to add a token to the list of parsed tokens
     * 
     * @param token
     *            The enumerated token
     * @param value
     *            The value of the token parsed
     */
    private void addToken(Token token, String value) {
        mTokens.add(new Pair<Token, String>(token, value));
    }

    /**
     * Checks if the provided token is a reserved keyword in a case
     * <b>insensitive</b> manner
     * 
     * @param s
     *            The string token to be checked
     * @return True if token is a reserved keyword, false otherwise
     */
    private boolean isReserved(String s) {
        return Arrays.asList(RESERVED).contains(s.toLowerCase());
    }

    /**
     * Checks if the provided flag is valid in a case <b>insensitive</b> manner
     * 
     * @param flag
     *            The flag to be checked
     * @return True if flag is valid, false otherwise
     */
    private boolean isValidFlag(String flag) {
        return Arrays.asList(FLAGS).contains(flag.toLowerCase());
    }
}
