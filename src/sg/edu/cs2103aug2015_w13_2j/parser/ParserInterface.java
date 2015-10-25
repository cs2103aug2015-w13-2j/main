package sg.edu.cs2103aug2015_w13_2j.parser;

import java.util.Calendar;
import java.util.GregorianCalendar;

import sg.edu.cs2103aug2015_w13_2j.Logic;

// @@author A0121410H

public interface ParserInterface {
    /**
     * Parses the command into tokens. Keywords such as add, edit or delete for
     * example are case <b>insensitive</b>. Invalid tokens are marked with
     * _INVALID after the token type
     * 
     * @param logic
     *            Dependency injection of the Logic component which is used by
     *            the Parser component to determine which are reserved keywords
     * @param command
     *            The command string entered by the user
     * @return Iterable Command object containing all parsed Tokens
     */
    public Command parseCommand(Logic logic, String command);

    /**
     * Retrieves a string in the format of [token1=value][token2=value]...
     * representing the tokens parsed from the given command
     * 
     * @return The string of tokens parsed
     */
    public String getParsedTokens();

    /**
     * Attempts to parse the given date string into a string representation of
     * the millisecond epoch. The valid formats are any combination of date and
     * time formats including omission of either as listed below:
     * <ul>
     * <li>dd/mm/yyyy</li>
     * <li>dd/mm</li>
     * <li>dd</li>
     * <li>Thh:mm</li>
     * <li>Thh</li>
     * </ul>
     * 
     * @param dateString
     *            The date string to parse
     * @return The string representing the millisecond epoch
     * @throws IllegalDateFormatException
     *             Thrown when the date string cannot be determined to be any
     *             valid date format
     */
    public static String parseDate(String dateString)
            throws IllegalDateFormatException {
        Calendar date = new GregorianCalendar();
        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);
        int hour = 0;
        int min = 0;

        String[] tokenSplit = dateString.split("T", 2);
        String[] dateSplit = tokenSplit[0].split("/", 3);

        // Parse the date components
        for (int i = 0; i < dateSplit.length; i++) {
            try {
                if (i == 0) {
                    day = Integer.parseInt(dateSplit[i]);
                } else if (i == 1) {
                    month = Integer.parseInt(dateSplit[i]);
                } else if (i == 2) {
                    year = Integer.parseInt(dateSplit[i]);
                }
            } catch (NumberFormatException e) {
                throw new IllegalDateFormatException();
            }
        }

        // Parse the time components if any
        if (tokenSplit.length == 2) {
            String[] timeSplit = tokenSplit[1].split(":", 2);
            for (int i = 0; i < timeSplit.length; i++) {
                try {
                    if (i == 0) {
                        hour = Integer.parseInt(timeSplit[i]);
                    } else if (i == 1) {
                        min = Integer.parseInt(timeSplit[i]);
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalDateFormatException();
                }
            }
        }

        // Create the new parsed date and throw when any arguments are illegal
        try {
            date = new GregorianCalendar(year, month - 1, day, hour, min, 0);
            return String.valueOf(date.getTimeInMillis());
        } catch (IllegalArgumentException e) {
            throw new IllegalDateFormatException();
        }
    }

    public class IllegalDateFormatException extends Exception {
        private static final long serialVersionUID = -3295451690918110371L;
    }
}