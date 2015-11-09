package sg.edu.cs2103aug2015_w13_2j;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
import sg.edu.cs2103aug2015_w13_2j.ui.FXUI;
import sg.edu.cs2103aug2015_w13_2j.ui.UIInterface;

// @@author A0121410H

/**
 * Main FunDUE class and JavaFX entry point. Extends the JavaFX
 * {@link Application} class.
 * 
 * @author Zhu Chunqi
 */
public class FunDueFX extends Application {
    // Initial width and height of the FunDUE window
    public static final int START_WIDTH = 700;
    public static final int START_HEIGHT = 600;

    // Minimum width and height that the FunDUE window can be resized to
    public static final int MIN_WIDTH = 550;
    public static final int MIN_HEIGHT = 500;

    // Retrieve singleton instances of application dependencies
    private UIInterface mUI = FXUI.getInstance();
    private LogicInterface mLogic = Logic.getInstance();
    private StorageInterface mStorage = Storage.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Register all the command handlers
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

        // Create the main FunDUE window and style it
        Scene scene = new Scene(mUI.getUI(), START_WIDTH, START_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.getIcons().add(new Image("file:FunDUE Logo.png"));
        primaryStage.setTitle("FunDUE");
        primaryStage.setScene(scene);
        primaryStage.show();

        mUI.injectDependency(mLogic);
        mUI.focusCommandBar();
        mLogic.injectDependencies(mStorage, mUI);
        mLogic.readTasks();
        mLogic.display();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
