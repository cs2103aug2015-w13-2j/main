package sg.edu.cs2103aug2015_w13_2j;

public class FunDUE {
    private TextUI mTextUI;
    private Parser mParser;

    /**
     * Initializes all the components and injects the dependencies to each
     * component
     */
    public FunDUE() {
        mTextUI = new TextUI(mParser);
    }

    public static void main(String[] args) {
        new FunDUE();
    }
}
