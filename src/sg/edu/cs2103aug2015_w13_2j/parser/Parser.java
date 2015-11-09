package sg.edu.cs2103aug2015_w13_2j.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.exceptions.IllegalDateFormatException;

// @@author A0121410H

/**
 * Parser class that handles the interpretation of tokens within the user's
 * command input. This class ensures that all tokens of the user command are of
 * a specific {@link Token} type.
 * 
 * @author Zhu Chunqi
 */
public class Parser {
    public static final String FLAG_END = "e";
    public static final String FLAG_START = "s";
    public static final String DATETIME_FORMAT_STRING = "dd_MM_yyyy_HH_mm";
    public static final String[] FLAGS = { FLAG_END, FLAG_START };
    public static final String[] DATETIME_FORMAT_PARTS = { "dd", "MM", "yyyy",
            "0", "0" };
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(
            "dd_MM_yyyy_HH_mm");

    private static final Logger LOGGER = Logger
            .getLogger(Parser.class.getName());

    private enum State {
        GENERAL, ALPHA_NUM, DATE, FLAG, ID, NAME
    }

    private static Parser sInstance;

    private Set<String> mReserved;
    private State mState;
    private String mCommandString;
    private Command mCommand;
    private int mParserPos;

    /**
     * Private constructor.
     */
    private Parser() {
        // Do nothing
    }

    /**
     * Retrieves the singleton instance of this {@link Parser} component.
     * 
     * @return {@link Parser} component.
     */
    public static synchronized Parser getInstance() {
        if (sInstance == null) {
            sInstance = new Parser();
        }
        return sInstance;
    }

    /**
     * Parses the provided command string into a {@link Command} object. Invalid
     * tokens are marked with _INVALID after the {@link Token} type.
     * 
     * @param logic
     *            Dependency injection of a {@link LogicInterface} component
     *            which is used by this {@link Parser} component to determine
     *            the reserved keyword strings.
     * @param command
     *            Command string to parse.
     * @return A {@link Iterable} {@link Command} object containing parsed
     *         {@link Token}s.
     */
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

    /**
     * Retrieves a string in the format of {@code [token1=value][token2=value]}
     * etc. representing the parsed {@link Token}s.
     * 
     * @return String of parsed {@link Token}s.
     */
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

    /**
     * Attempts to parse the provided date string and return a string
     * representation in the format dd_MM_yyyy_HH_mm to be used by
     * {@link SimpleDateFormat} to fill the unspecified parts from a base date.
     * Supported date string formats are any combination of date and time
     * formats as listed below. An exception will be thrown if the provided date
     * string does not conform to any supported format. Bounds checking is
     * <b>not</b> performed for day, month, year, hour or minute values.
     * <ul>
     * <li>dd/mm/yyyy</li>
     * <li>dd/mm</li>
     * <li>dd</li>
     * <li>Thh:mm</li>
     * <li>Thh</li>
     * </ul>
     * 
     * @param datetime
     *            Date string to be parsed.
     * @return String in the format {@code dd_MM_yyyy_HH_mm}. Fields which are
     *         defined in the date string are filled in.
     * @throws IllegalDateFormatException
     *             Thrown when the date string is not of a valid format.
     */
    public static String parseDate(String datetime)
            throws IllegalDateFormatException {
        int[] parts = { -1, -1, -1, -1, -1 };
        String[] stringSplit = datetime.split("T", 2);
        String[] dateSplit = stringSplit[0].split("/", 3);
        try {
            // Parse date components
            if (stringSplit[0].length() > 0) {
                for (int i = 0; i < 3 && i < dateSplit.length; i++) {
                    parts[i] = Integer.parseInt(dateSplit[i]);
                }
            }
            if (stringSplit.length == 2 && stringSplit[1].length() > 0) {
                // Parse time components
                String[] timeSplit = stringSplit[1].split(":", 2);
                for (int i = 0; i < 2 && i < timeSplit.length; i++) {
                    parts[i + 3] = Integer.parseInt(timeSplit[i]);
                }
            }
        } catch (NumberFormatException e) {
            throw new IllegalDateFormatException();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            sb.append((parts[i] == -1 ? DATETIME_FORMAT_PARTS[i]
                    : String.valueOf(parts[i])));
            if (i < parts.length - 1) {
                sb.append('_');
            }
        }
        return sb.toString();
    }

    /**
     * Attempts to update the fields of the provided base {@link Date} to match
     * that in the format string. Throws an exception when the format string
     * contains values which are out of bounds for their corresponding fields.
     * 
     * @param format
     *            String of the format {@code dd_MM_yyyy_HH_mm} as produced by
     *            {@link #parseDate(String)}.
     * @param baseDate
     *            Base {@link Date} object to be updated, does not check for
     *            {@code null}.
     * @return Updated {@link Date} object if there were no parse errors or the
     *         original {@link Date} object otherwise.
     */
    public static Date updateDate(String format, Date baseDate) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        LOGGER.log(Level.FINE, "Base date: " + baseDate);
        String updated = formatter.format(baseDate);
        LOGGER.log(Level.FINE, "Updated: " + updated);
        try {
            return DATETIME_FORMAT.parse(updated);
        } catch (ParseException e) {
            return baseDate;
        }
    }

    /**
     * Main loop of the {@link Parser} state machine.
     */
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
                    String dateString = Parser.parseDate(s);
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
     * @return {@code True} if there are more characters to read, {@code false}
     *         otherwise.
     */
    private boolean hasNext() {
        return mParserPos < mCommandString.length();
    }

    /**
     * Retrieves the next character of the command string without advancing the
     * parser position.
     * 
     * @return Next character of the command string.
     */
    private char peek() {
        return mCommandString.charAt(mParserPos);
    }

    /**
     * Retrieves the next character of the command and advances the parser one
     * position forward.
     * 
     * @return Next character of the command string.
     */
    private char next() {
        return mCommandString.charAt(mParserPos++);
    }

    /**
     * Reads the command string character by character until the provided
     * delimiter or end of command string is encountered. Returns the characters
     * read as a string.
     * 
     * @param delimiter
     *            Character delimiter to stop reading when encountered.
     * @return String read from the current parser position until the delimiter
     *         or end of the command string is encountered.
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
     * returns the value of {@link #hasNext()}.
     * 
     * @return Value of {@link #hasNext()}.
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
     * lower case before the comparison is carried out.
     * 
     * @param s
     *            String to be checked.
     * @return {@code True} if the string is a reserved keyword, {@code false}
     *         otherwise.
     */
    private boolean isReserved(String s) {
        return mReserved.contains(s.toLowerCase());
    }

    /**
     * Checks if the provided string is a valid flag in a case
     * <b>insensitive</b> manner. The provided string is first converted to all
     * lower case before the comparison is carried out.
     * 
     * @param s
     *            String to be checked
     * @return {@code True} if the string is a valid flag, {@code false}
     *         otherwise.
     */
    private boolean isValidFlag(String flag) {
        return Arrays.asList(FLAGS).contains(flag.toLowerCase());
    }
}
