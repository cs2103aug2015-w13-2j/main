package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H
public class FunDUE {
    public static TextUI sTextUI;
    public static Parser sParser;
    public static Formatter sFormatter;
    public static Logic sLogic;
    public static Storage sStorage;

    /**
     * Initializes all the components and injects the dependencies to each
     * component
     */
    public FunDUE() {
        sTextUI = new TextUI();
        sParser = new Parser();
        sFormatter = new Formatter();
        sLogic = new Logic();
        sStorage = new Storage();
    }

    public static void main(String[] args) {
        new FunDUE();
    }
}
