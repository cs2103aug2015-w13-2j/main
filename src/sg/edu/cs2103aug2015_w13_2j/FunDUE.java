package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H
public class FunDUE implements FunDUEInterface {
	private TextUI mTextUI;
	private Parser mParser;
	private Logic mLogic;
	private Storage mStorage;

	/**
	 * Initializes all the components. The general contract of all components is
	 * that its constructor may not reference any other component. All API calls
	 * <b>must</b> be prefixed with the corresponding getInstance call.
	 */
	public FunDUE() {
		mTextUI = new TextUI(this);
		mParser = new Parser(this);
		mLogic = new Logic(this);
		mStorage = new Storage();
	}

	public TextUI getTextUIInstance() {
		return mTextUI;
	}

	public Parser getParserInstance() {
		return mParser;
	}

	public Logic getLogicInstance() {
		return mLogic;
	}

	public Storage getStorageInstance() {
		return mStorage;
	}

	public static void main(String[] args) {
		new FunDUE();
	}
}
