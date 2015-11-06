package sg.edu.cs2103aug2015_w13_2j.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sg.edu.cs2103aug2015_w13_2j.FunDueFX;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
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

// @@author A0133387B

public class FXUITest extends Application {
    private UIInterface mUI = FXUI.getInstance();
    private LogicInterface mLogic = Logic.getInstance();
    private StorageInterface mStorage = Storage.getInstance();

    public UIInterface getUI(){
    	return mUI;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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

        Scene scene = new Scene(mUI.getUI(), FunDueFX.START_WIDTH,
                FunDueFX.START_HEIGHT);
        primaryStage.setMinWidth(FunDueFX.MIN_WIDTH);
        primaryStage.setMinHeight(FunDueFX.MIN_HEIGHT);
        primaryStage.getIcons().add(new Image("file:FunDUE Logo.png"));
        primaryStage.setTitle("FunDUE");
        primaryStage.setScene(scene);
        primaryStage.show();

        mUI.injectDependency(mLogic);
        mLogic.injectDependencies(mStorage, mUI);
        mLogic.readTasks();
        mLogic.display();
    }
}
