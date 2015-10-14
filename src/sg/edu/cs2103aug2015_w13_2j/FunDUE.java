package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H

public class FunDUE {
    /**
     * Initializes all the components via dependency chaining. Each component
     * which has a dependency on another component will call the corresponding
     * getInstance method to retrieve a handle to the component
     */
    public FunDUE() {
        TextUI.getInstance();
    }

    public static void main(String[] args) {
        new FunDUE();
    }
}
