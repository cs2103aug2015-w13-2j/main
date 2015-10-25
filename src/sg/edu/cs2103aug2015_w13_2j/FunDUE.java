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
     * Initialization of components. Components are <b>not</b> allowed to
     * reference other components during initialization. Additional
     * initialization should be called from the {@link #run()} method instead
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
    }

    public void run() {
        TextUI.getInstance();
        Logic.getInstance().readTasks();
        Logic.getInstance().display();
    }

    public static void main(String[] args) {
        new FunDUE().run();
    }
}
