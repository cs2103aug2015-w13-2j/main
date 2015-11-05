package sg.edu.cs2103aug2015_w13_2j;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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

// @@author A0121410H-unused
// Reason: Used exclusively for our CS2101 OP2 Software Demonstration.
// Additional banner atop the application UI to show our banner slides.

public class FunDueDemo extends Application implements EventHandler<KeyEvent> {
    private static final Logger LOGGER = Logger.getLogger(FunDueDemo.class
            .getName());
    private static final String SLIDE_PATH = "file:resources/demo/Slide%d.JPG";
    private static final int SLIDE_START = 1;
    private static final int SLIDE_END = 9;

    private final UIInterface mUI = FXUI.getInstance();
    private final LogicInterface mLogic = Logic.getInstance();
    private final StorageInterface mStorage = Storage.getInstance();
    private final ImageView mImageView = new ImageView();
    private final Parent mFunDue = mUI.getUI();

    private int mSlide = SLIDE_START;

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

        mImageView.setPreserveRatio(true);

        VBox container = new VBox(mImageView, mFunDue);
        VBox.setVgrow(mFunDue, Priority.ALWAYS);

        Scene scene = new Scene(container, 800, 800);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this);
        mImageView.fitWidthProperty().bind(scene.widthProperty());

        loadSlide();

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(800);
        primaryStage.getIcons().add(new Image("file:FunDUE Logo.png"));
        primaryStage.setTitle("FunDUE Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        mUI.injectDependency(mLogic);
        mLogic.injectDependencies(mStorage, mUI);
        mLogic.readTasks();
        mLogic.display();
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
        case UP :
            LOGGER.log(Level.INFO, "UP " + mSlide);
            if (mSlide > SLIDE_START) {
                mSlide--;
                loadSlide();
            }
            break;
        case DOWN :
            LOGGER.log(Level.INFO, "DOWN" + mSlide);
            if (mSlide < SLIDE_END) {
                mSlide++;
                loadSlide();
            }
            break;
        default :
            // Do nothing
        }
    }

    private void loadSlide() {
        if (mSlide >= SLIDE_START && mSlide <= SLIDE_END) {
            String slidePath = String.format(SLIDE_PATH, mSlide);
            LOGGER.log(Level.INFO, "Loading slide: " + slidePath);
            mImageView.setImage(new Image(slidePath));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
