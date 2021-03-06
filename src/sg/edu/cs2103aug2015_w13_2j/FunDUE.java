package sg.edu.cs2103aug2015_w13_2j;

import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.FilterHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.HelpHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.LoadHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkCompletedHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.RedoHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.SearchHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UndoHandler;
import sg.edu.cs2103aug2015_w13_2j.storage.Storage;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUI;
import sg.edu.cs2103aug2015_w13_2j.ui.UIInterface;

// @@author A0121410H-unused
// Reason: Old AWT-based UI initialization procedure, replaced with JavaFX
// version

public class FunDUE {
    private UIInterface mTextUI;
    private StorageInterface mStorage = Storage.getInstance();
    private LogicInterface mLogic = Logic.getInstance();

    /**
     * Initialization of components. Components are <b>not</b> allowed to
     * reference other components during initialization. Additional
     * initialization should be called from the {@link #run()} method instead
     */
    public FunDUE() {
        mLogic.registerCommandHandler(new AddHandler());
        mLogic.registerCommandHandler(new EditHandler());
        mLogic.registerCommandHandler(new DeleteHandler());
        mLogic.registerCommandHandler(new MarkImportantHandler());
        mLogic.registerCommandHandler(new MarkCompletedHandler());
        mLogic.registerCommandHandler(new UndoHandler());
        mLogic.registerCommandHandler(new RedoHandler());
        mLogic.registerCommandHandler(new FilterHandler());
        mLogic.registerCommandHandler(new PopHandler());
        mLogic.registerCommandHandler(new HelpHandler());
        mLogic.registerCommandHandler(new LoadHandler());
        mLogic.registerCommandHandler(new SearchHandler());
    }

    public void run() {
        mTextUI = TextUI.getInstance();
        mTextUI.injectDependency(mLogic);
        mLogic.injectDependencies(mStorage, mTextUI);
        mLogic.readTasks();
        mLogic.display();
    }

    public static void main(String[] args) {
        new FunDUE().run();
    }
}
