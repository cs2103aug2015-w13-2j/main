package sg.edu.cs2103aug2015_w13_2j.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;

// @@author A0121410H

public interface ParserInterface {
    public static final Logger LOGGER = Logger
            .getLogger(ParserInterface.class.getName());

    public class IllegalDateFormatException extends Exception {
        private static final long serialVersionUID = -3295451690918110371L;
    }

    public static final String[] DATETIME_FORMAT_PARTS = { "dd", "MM", "yyyy",
            "0", "0" };
    public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat(
            "dd_MM_yyyy_HH_mm");

    /**
     * Parses the provided command string into {@link Token}s. Invalid tokens
     * are marked with _INVALID after the token type
     * 
     * @param logic
     *            Dependency injection of Logic component implementing the
     *            {@link LogicInterface} which is used by this Parser component
     *            to determine which are reserved keywords
     * @param command
     *            Command string to parse
     * @return {@link Iterable} {@link Command} object containing all the parsed
     *         {@link Token}s
     */
    public Command parseCommand(Logic logic, String command);

    /**
     * Retrieves a string in the format of {@code [token1=value][token2=value]}
     * etc. representing the {@link Token}s parsed from the command string
     * 
     * @return String of parsed {@link Token}s
     */
    public String getParsedTokens();

    /**
     * Attempts to parse the provided date string and return a string
     * representation in the format dd_MM_yyyy_HH_mm to be used by
     * {@link SimpleDateFormat} to fill the defaults from a base date. Supported
     * date string formats are any combination of date and time formats as
     * listed below. An exception will be thrown if the provided date string
     * does not conform to any supported format. Bounds checking is <b>not</b>
     * performed for day, month, year, hour or minute values
     * <ul>
     * <li>dd/mm/yyyy</li>
     * <li>dd/mm</li>
     * <li>dd</li>
     * <li>Thh:mm</li>
     * <li>Thh</li>
     * </ul>
     * 
     * @param datetime
     *            Date string to be parsed
     * @return String in the format dd_MM_yyyy_HH_mm. Fields which are defined
     *         in the date string are filled in.
     * @throws IllegalDateFormatException
     *            Error thrown when a date is not a valid numerical format.
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
     * Attempts to update the fields of the provided base date to match that in
     * the format string provided. Throws an exception when the format string
     * contains values which are out of bounds for their respective fields
     * 
     * @param format
     *            String of the format dd_MM_yyyy_HH_mm as produced by
     *            {@link #parseDate(String)}
     * @param baseDate
     *            Date object to update, does not check for null
     * @return Updated Date object if there were no parse errors, the original
     *         Date object otherwise
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
}
