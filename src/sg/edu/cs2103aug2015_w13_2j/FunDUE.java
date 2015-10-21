package sg.edu.cs2103aug2015_w13_2j;

import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.ArchiveHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.FilterHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkCompletedHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.RetrieveHandler;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUI;

// @@author A0121410H

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
        Logic.getInstance().registerCommandHandler(new ArchiveHandler());
        Logic.getInstance().registerCommandHandler(new RetrieveHandler());
        Logic.getInstance().registerCommandHandler(new MarkImportantHandler());
        Logic.getInstance().registerCommandHandler(new MarkCompletedHandler());
        Logic.getInstance().registerCommandHandler(new FilterHandler());
        Logic.getInstance().registerCommandHandler(new PopHandler());
        
        TextUI.getInstance();
    }

    public static void main(String[] args) {
        new FunDUE();
    }
}
