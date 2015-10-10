package sg.edu.cs2103aug2015_w13_2j;

import java.util.ArrayList;

import javafx.util.Pair;
import sg.edu.cs2103aug2015_w13_2j.Parser.Token;

/**
 * This interface contains methods for classes implementing it as well as for
 * other components Parser, Formatter, TextUI, Controller to access it
 * 
 * @author Nguyen Tuong Van
 */

public interface LogicInterface {
	/**
	 * Executes the command represented by the parsed tokens
	 * 
	 * @param tokens
	 *            The parsed tokens
	 */
	public void executeCommand(ArrayList<Pair<Token, String>> tokens);

	/**
	 * Echos back the command entered. For testing purposes and as a stub
	 * 
	 * @param s
	 *            The string to echo back
	 */
	public void echo(String s);
}
