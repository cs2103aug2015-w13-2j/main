package sg.edu.cs2103aug2015_w13_2j;

public interface FunDUEInterface {
	/**
	 * Retrieves the TextUI component of this application instance
	 * 
	 * @return The TextUI component
	 */
	public TextUI getTextUIInstance();

	/**
	 * Retrieves the Parser component of this application instance
	 * 
	 * @return The Parser component
	 */
	public Parser getParserInstance();

	/**
	 * Retrieves the Logic component of this application instance
	 * 
	 * @return The Logic component
	 */
	public Logic getLogicInstance();

	/**
	 * Retrieves the Formatter component of this application instance
	 * 
	 * @return The Formatter component
	 */
	public Formatter getFormatterInstance();

	/**
	 * Retrieves the Storage component of this application instance
	 * 
	 * @return The Storage component
	 */
	public Storage getStorageInstance();
}
