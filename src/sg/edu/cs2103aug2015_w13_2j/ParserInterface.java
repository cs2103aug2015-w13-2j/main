package sg.edu.cs2103aug2015_w13_2j;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javafx.util.Pair;

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
	 * Passes the parsed tokens to the Logic component to be executed
	 */
	public void executeCommand();

	/**
	 * Convenience method to parse the command and executes it
	 * 
	 * @param command
	 *            The command string entered by the user
	 * @see ParserInterface#parseCommand
	 * @see ParserInterface#executeCommand
	 */
	public void parseAndExecuteCommand(String command);

	/**
	 * Retrieves a string in the format of [token1=value][token2=value]...
	 * representing the tokens parsed from the given command
	 * 
	 * @return The string of tokens parsed
	 */
	public String getParsedTokens();

	/**
	 * Retrieves the list of tokens parsed and their values.
	 * 
	 * @return A Vector of <Token-Token name> pairs
	 */
	public Vector<Pair<Parser.Token, String>> getListOfTokens();

	/**
	 * Attempts to parse the given date string into a string representation of
	 * the millisecond epoch. The valid formats are any combination of date and
	 * time formats including ommission of either as listed below:
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
	public static String parseDate(String dateString) throws IllegalDateFormatException {
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
