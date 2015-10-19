package sg.edu.cs2103aug2015_w13_2j;

import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUI;

//@@author A0121410H

public class FunDUE {
    /**
     * Initializes all the components via dependency chaining. Each component
     * which has a dependency on another component will call the corresponding
     * getInstance method to retrieve a handle to the component
     */
    public FunDUE() {
        Logic.getInstance().registerCommandHandler(new AddHandler());
        Logic.getInstance().registerCommandHandler(new EditHandler());
        Logic.getInstance().registerCommandHandler(new DeleteHandler());
        
        TextUI.getInstance();
    }

    public static void main(String[] args) {
        new FunDUE();
    }
}
