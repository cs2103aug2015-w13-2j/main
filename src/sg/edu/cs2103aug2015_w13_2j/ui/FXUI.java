package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.ocpsoft.prettytime.PrettyTime;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
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
    public static final Insets PADDING_LR_10 = new Insets(0, 10, 0, 10);
    public static final Font FONT = new Font(24);

    private static final int START_WIDTH = 800;
    private static final int START_HEIGHT = 600;
    private static final int MIN_WIDTH = 500;
    private static final int MIN_HEIGHT = 600;

    private static FXUI sInstance;

    private final Label mFeedbackLabel;
    private final TextField mTextField;
    private final List<Task> mOrderedTasks;
    private final FilterChain mFilterChain;
    private final FXCategoryAccordion mFilteredCategory;
    private final FXCategoryAccordion mUpcomingCategory;
    private final FXCategoryAccordion mFloatingCategory;
    private final VBox mCenterVBox;

    private LogicInterface mLogic;

    /**
     * Private constructor
     */
    private FXUI() {
        mFeedbackLabel = new Label("Welcome to FunDUE!");
        mFeedbackLabel.setPadding(PADDING_LR_10);
        mFeedbackLabel.setFont(FONT);
        mTextField = new TextField();
        mTextField.setFont(FONT);
        mTextField.setOnKeyPressed(this);
        mTextField.setMaxWidth(Double.MAX_VALUE);
        mOrderedTasks = new ArrayList<Task>();
        mFilterChain = new FilterChain();
        mFilteredCategory = new FXCategoryAccordion("");
        mFilteredCategory.managedProperty()
                .bind(mFilteredCategory.visibleProperty());
        mFloatingCategory = new FXCategoryAccordion("Someday");
        mUpcomingCategory = new FXCategoryAccordion("Upcoming");
        //mAllCategory = new FXCategoryAccordion("All Tasks");
        mCenterVBox = new VBox(mFilteredCategory, mFloatingCategory,
                mUpcomingCategory);
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
    }

    @Override
    public void display(ArrayList<Task> tasks) {
        // Clear the ordered task list and the container VBox
        mOrderedTasks.clear();
        Collections.sort(tasks);

        if (mFilterChain.size() > 1) {
            List<Task> filteredTasks = mFilterChain.getTasksForDisplay();
            Collections.sort(filteredTasks);
            mFilteredCategory.setName(mFilterChain.getFilterChain());
            mFilteredCategory.update(filteredTasks, mOrderedTasks.size());
            mOrderedTasks.addAll(filteredTasks);

            mFilteredCategory.setVisible(true);
            mFloatingCategory.setVisible(false);
            mUpcomingCategory.setVisible(false);
        } else {
            mFilteredCategory.setVisible(false);
            mFloatingCategory.setVisible(true);
            mUpcomingCategory.setVisible(true);
        }

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

        //mAllCategory.update(tasks, mOrderedTasks.size());
        //mOrderedTasks.addAll(tasks);
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
    public void updateFilters(ArrayList<Task> tasks) {
        mFilterChain.updateFilters(tasks);
    }

    public void createUI(Stage primaryStage) {
        ScrollPane displayScrollPane = new ScrollPane(mCenterVBox);
        displayScrollPane.setFitToWidth(true);
        displayScrollPane.setFitToHeight(true);
        displayScrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);

        VBox bottomVBox = new VBox(mFeedbackLabel, mTextField);

        BorderPane container = new BorderPane();
        container.setCenter(displayScrollPane);
        container.setBottom(bottomVBox);

        Scene scene = new Scene(container, START_WIDTH, START_HEIGHT);

        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.getIcons().add(new Image("file:FunDUE Logo.png"));
        primaryStage.setTitle("FunDUE");
        primaryStage.setScene(scene);
        primaryStage.show();
        mTextField.requestFocus();
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
        case ENTER:
            mLogic.executeCommand(mTextField.getText());
            mTextField.setText("");
            break;
        case ESCAPE:
            Platform.exit();
            break;
        default:
            // Do nothing
        }
    }
}
