package sg.edu.cs2103aug2015_w13_2j;

import javafx.application.Application;
import javafx.stage.Stage;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.ArchiveHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.ExitHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.FilterHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.HelpHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.LoadHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkCompletedHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.SearchHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UnarchiveHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UndoHandler;
import sg.edu.cs2103aug2015_w13_2j.storage.Storage;
import sg.edu.cs2103aug2015_w13_2j.ui.FXUI;

public class FunDueFX extends Application {
    private FXUI mUI = FXUI.getInstance();
    private Logic mLogic = Logic.getInstance();
    private Storage mStorage = Storage.getInstance();
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        mLogic.registerCommandHandler(new AddHandler());
        mLogic.registerCommandHandler(new EditHandler());
        mLogic.registerCommandHandler(new DeleteHandler());
        mLogic.registerCommandHandler(new ArchiveHandler());
        mLogic.registerCommandHandler(new UnarchiveHandler());
        mLogic.registerCommandHandler(new MarkImportantHandler());
        mLogic.registerCommandHandler(new MarkCompletedHandler());
        mLogic.registerCommandHandler(new UndoHandler());
        mLogic.registerCommandHandler(new FilterHandler());
        mLogic.registerCommandHandler(new PopHandler());
        mLogic.registerCommandHandler(new HelpHandler());
        mLogic.registerCommandHandler(new ExitHandler());
        mLogic.registerCommandHandler(new LoadHandler());
        mLogic.registerCommandHandler(new SearchHandler());
        mUI.createUI(primaryStage);
        mUI.injectDependency(mLogic);
        mLogic.injectDependencies(mStorage, mUI);
        mLogic.readTasks();
        mLogic.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
