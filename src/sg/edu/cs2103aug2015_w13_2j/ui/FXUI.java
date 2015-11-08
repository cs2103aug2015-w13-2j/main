package sg.edu.cs2103aug2015_w13_2j.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import org.ocpsoft.prettytime.PrettyTime;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;

// @@author A0121410H

/**
 * {@link UIInterface} component built using JavaFX.
 * 
 * @author Zhu Chunqi
 */
public class FXUI implements UIInterface, EventHandler<KeyEvent> {
    public static final PrettyTime PRETTY_TIME = new PrettyTime();
    public static final Insets PADDING_LR = new Insets(0, 3, 0, 3);
    public static final Insets PADDING_FEEDBACK = new Insets(5, 10, 5, 10);
    public static final Font FONT = new Font(18);
    public static final Font FONT_SMALLER = new Font(15);
    public static final double ID_MIN_WIDTH = 35;
    public static final double HELP_MIN_WIDTH = 400;
    public static final double HELP_MIN_HEIGHT = 400;

    private static final Logger LOGGER = Logger.getLogger(FXUI.class.getName());

    private static FXUI sInstance;

    private final Label mFeedbackLabel;
    private final TextField mTextField;
    private final List<Task> mOrderedTasks;
    private final FilterChain mFilterChain;
    private final FXCategoryAccordion mFilteredCategory;
    private final FXCategoryAccordion mFloatingCategory;
    private final FXCategoryAccordion mUpcomingCategory;
    private final VBox mCenterVBox;
    private final BorderPane mContainer;
    private final Stage mHelpWindow;

    private LogicInterface mLogic;

    /**
     * Private constructor
     */
    private FXUI() {
        mFeedbackLabel = new Label("Welcome to FunDUE!");
        mFeedbackLabel.setPadding(PADDING_FEEDBACK);
        mFeedbackLabel.setFont(FONT_SMALLER);
        mFeedbackLabel.setId("mFeedbackLabel");

        mTextField = new TextField();
        mTextField.setFont(FONT);
        mTextField.setOnKeyPressed(this);
        mTextField.setMaxWidth(Double.MAX_VALUE);
        mTextField.setId("mTextField");
        DropShadow borderGlow = new DropShadow(BlurType.GAUSSIAN, Color.ORANGE,
                15.0, 0.0, 0.0, 0.0);
        mTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov,
                    Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    mTextField.setEffect(borderGlow);
                } else {
                    mTextField.setEffect(null);
                }
            }
        });

        mOrderedTasks = new ArrayList<Task>();

        mFilterChain = new FilterChain();
        mFilteredCategory = new FXCategoryAccordion("");
        // NOTE: Binding the managed property to the visible property to
        // properly hide the UI element when visible is set to false. Otherwise
        // the element will still take up space in layout as it is still managed
        mFilteredCategory.managedProperty().bind(
                mFilteredCategory.visibleProperty());

        mFloatingCategory = new FXCategoryAccordion("Someday");
        mFloatingCategory.managedProperty().bind(
                mFloatingCategory.visibleProperty());

        mUpcomingCategory = new FXCategoryAccordion("Events / Deadlines");
        mUpcomingCategory.managedProperty().bind(
                mUpcomingCategory.visibleProperty());

        mCenterVBox = new VBox(mFilteredCategory, mFloatingCategory,
                mUpcomingCategory);
        mCenterVBox.setId("mCenterVBox");

        ScrollPane displayScrollPane = new ScrollPane(mCenterVBox) {
            // Override to prevent blue focus highlights from appearing
            @Override
            public void requestFocus() {
                // Do nothing
            }
        };
        displayScrollPane.setFitToWidth(true);
        displayScrollPane.setFitToHeight(true);
        displayScrollPane.getStyleClass().add("scrollpane");

        VBox bottomVBox = new VBox(mFeedbackLabel, mTextField);
        bottomVBox.setId("bottomVBox");

        mContainer = new BorderPane();
        mContainer.setId("container");
        mContainer.setCenter(displayScrollPane);
        mContainer.setBottom(bottomVBox);
        mContainer.getStylesheets().add(
                getClass().getResource("styleFX.css").toExternalForm());

        mHelpWindow = new Stage();
        mHelpWindow.setTitle("FunDUE Help Page");
        mHelpWindow.setMinWidth(400);
        mHelpWindow.setMinHeight(400);
    }

    /**
     * Retrieves the singleton instance of this {@link UIInterface} component
     * 
     * @return {@link UIInterface} component singleton instance
     */
    public synchronized static FXUI getInstance() {
        if (sInstance == null) {
            sInstance = new FXUI();
        }
        return sInstance;
    }

    @Override
    public void injectDependency(LogicInterface logic) {
        mLogic = logic;
        createHelpPage(mLogic);
    }

    @Override
    public void display(ArrayList<Task> tasks) {
        // Re-seed the filter chain
        mFilterChain.updateFilters(tasks);

        // Clear the ordered task list
        mOrderedTasks.clear();
        Collections.sort(tasks);

        if (mFilterChain.size() > 1) {
            List<Task> filteredTasks = mFilterChain.getTasksForDisplay();
            mFilteredCategory.setName(mFilterChain.getFilterChain());
            mFilteredCategory.update(filteredTasks, mOrderedTasks.size());
            mOrderedTasks.addAll(filteredTasks);

            mFilteredCategory.setVisible(true);
            mFloatingCategory.setVisible(false);
            mUpcomingCategory.setVisible(false);
        } else {
            // Someday
            List<Task> floatingTasks = tasks.stream()
                    .filter((Task t) -> t.getEnd() == null)
                    .collect(Collectors.toList());
            mFloatingCategory.update(floatingTasks, mOrderedTasks.size());
            mOrderedTasks.addAll(floatingTasks);

            // Upcoming
            List<Task> upcomingTasks = tasks.stream()
                    .filter((Task t) -> t.getEnd() != null)
                    .collect(Collectors.toList());
            mUpcomingCategory.update(upcomingTasks, mOrderedTasks.size());
            mOrderedTasks.addAll(upcomingTasks);

            mFilteredCategory.setVisible(false);
            mFloatingCategory.setVisible(true);
            mUpcomingCategory.setVisible(true);
        }
    }

    @Override
    public void display(String s) {
        // TODO Auto-generated method stub
    }

    @Override
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            // NOTE: list is zero indexed whereas display is 1 indexed
            return mOrderedTasks.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    @Override
    public void feedback(FeedbackMessage f) {
        mFeedbackLabel.setTextFill(f.getType().getFXColor());
        mFeedbackLabel.setText(f.getMessage());
    }

    @Override
    public void pushFilter(Filter filter) {
        mFilterChain.pushFilter(filter);
    }

    @Override
    public Filter popFilter() {
        return mFilterChain.popFilter();
    }

    @Override
    public boolean showChangeDataFilePathDialog() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select FunDUE Data File");
        fc.setInitialDirectory(mLogic.getDataFile().getParentFile());
        fc.setInitialFileName(mLogic.getDataFile().getName());
        fc.getExtensionFilters().add(
                new ExtensionFilter("FunDUE Data Files", "*.txt"));
        File selectedFile = fc.showSaveDialog(null);
        if (selectedFile == null) {
            return false;
        } else {
            LOGGER.log(Level.INFO,
                    "Selected data file: " + selectedFile.getAbsolutePath());
            mLogic.setDataFile(selectedFile);
            return true;
        }
    }

    @Override
    public boolean showHelpPage() {
        if (mHelpWindow.isShowing()) {
            mHelpWindow.requestFocus();
            return false;
        } else {
            mHelpWindow.show();
            mHelpWindow.requestFocus();
            return true;
        }
    }

    @Override
    public Parent getUI() {
        return mContainer;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
        case ENTER :
            mLogic.executeCommand(mTextField.getText());
            mTextField.setText("");
            break;
        case ESCAPE :
            Platform.exit();
            break;
        default :
            // Do nothing
        }
    }

    @Override
    public String getFeedBackMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Creates the UI elements necessary to display the help page using
     * information provided by the loaded {@link CommandHandler} objects.
     * 
     * @param logic
     *            {@link LogicInterface} component from which to obtain the
     *            loaded {@link CommandHandler} objects.
     */
    private void createHelpPage(LogicInterface logic) {
        // Create a styled container and scroll pane
        VBox container = new VBox();
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        Scene scene = new Scene(scrollPane, HELP_MIN_WIDTH, HELP_MIN_HEIGHT);
        scene.getStylesheets().add(
                getClass().getResource("styleFX.css").toExternalForm());

        // Retrieve all distinct CommandHandlers and iterate
        List<CommandHandler> handlers = logic.getCommandHandlers().values()
                .stream().distinct().collect(Collectors.toList());
        for (CommandHandler handler : handlers) {
            // Create a row container for each CommandHandler
            VBox row = new VBox();
            row.getStyleClass().addAll("helpCategoryRow");

            // CommandHandler name
            Label handlerName = new Label(handler.getName());
            handlerName.getStyleClass().addAll("helpCategoryLabel");

            // CommandHandler syntax
            Label handlerUsage = new Label("Usage: "
                    + handler.getReservedKeywords() + " " + handler.getSyntax());
            handlerUsage.getStyleClass().add("wrappingText");

            // CommandHandler flags
            VBox flagsContainer = new VBox();
            for (String flag : handler.getFlags()) {
                Label flagLabel = new Label(flag);
                flagLabel.getStyleClass().addAll("wrappingText",
                        "helpTabbedLabel");
                flagsContainer.getChildren().add(flagLabel);
            }

            // CommandHandler options
            VBox optionsContainer = new VBox();
            for (String option : handler.getOptions()) {
                Label optionLabel = new Label(option);
                optionLabel.getStyleClass().addAll("wrappingText",
                        "helpTabbedLabel");
                optionsContainer.getChildren().add(optionLabel);
            }

            row.getChildren().addAll(handlerName, handlerUsage, flagsContainer,
                    optionsContainer);
            container.getChildren().add(row);
        }
        mHelpWindow.setScene(scene);
    }
}
