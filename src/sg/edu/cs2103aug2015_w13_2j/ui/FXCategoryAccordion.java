package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.List;

import javafx.scene.control.Accordion;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sg.edu.cs2103aug2015_w13_2j.Task;

public class FXCategoryAccordion extends Accordion {
    private String mName;
    private final VBox mContainer;
    private final TitledPane mTitledPane;

    public FXCategoryAccordion(String name) {
        super();
        mName = name;
        mContainer = new VBox();
        mTitledPane = new TitledPane("", mContainer);
        mTitledPane.setFont(FXUI.FONT);
        mTitledPane.setAnimated(false);
        this.setExpandedPane(mTitledPane);
        getPanes().add(mTitledPane);
    }

    /**
     * Creates the UI elements to display styled Task objects within this
     * {@link Accordion} object
     * 
     * @param tasks
     *            List of Task objects to be displayed in the specified category
     *            accordion
     * @param offset
     *            Zero-based integer offset to start indexing the Task objects
     *            from
     */
    public void update(List<Task> tasks, int offset) {
        // Update category count and clear container
        mTitledPane.setText(mName + " (" + tasks.size() + ")");
        mContainer.getChildren().clear();

        // Display Task objects in category
        // Note: ID, importance and time labels will always take up their required
        // space, while name label will grow horizontally to fill in available space
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            HBox row = new HBox();
            mContainer.getChildren().add(row);
            
            Label idLabel = new Label((int) (i + offset + 1) + ".");
            idLabel.setPadding(FXUI.PADDING_LR);
            idLabel.setFont(FXUI.FONT);
            idLabel.setMinWidth(FXUI.ID_MIN_WIDTH);
            row.getChildren().add(idLabel);
            
            Label importanceLabel = new Label("!");
            importanceLabel.setPadding(FXUI.PADDING_LR);
            importanceLabel.setFont(FXUI.FONT);
            importanceLabel.setMinWidth(Control.USE_PREF_SIZE);
            importanceLabel.setTextFill(Color.RED);
            if (task.isImportant()) {
                row.getChildren().add(importanceLabel);
            }
            
            Label nameLabel = new Label(task.getName());
            nameLabel.setPadding(FXUI.PADDING_LR);
            nameLabel.setFont(FXUI.FONT);
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            row.getChildren().add(nameLabel);
            nameLabel.getStyleClass().add("task");
            
            Label timeLabel;
            if (task.getStart() != null && task.getEnd() != null) { // EVENT
                timeLabel = new Label(
                        "(from " + FXUI.PRETTY_TIME.format(task.getStart()) + " to "
                                + FXUI.PRETTY_TIME.format(task.getEnd()) + ")");
                row.getChildren().add(timeLabel);
            } else if (task.getEnd() != null) { // DEADLINE
                timeLabel = new Label(
                        "(due " + FXUI.PRETTY_TIME.format(task.getEnd()) + ")");
                row.getChildren().add(timeLabel);
            } else if (task.getStart() != null) { // FLOAT with start date
                timeLabel = new Label(
                        "(starts/started " + FXUI.PRETTY_TIME.format(task.getStart()) + ")");
                row.getChildren().add(timeLabel);
            } else {
                timeLabel = new Label();
            }
            timeLabel.setPadding(FXUI.PADDING_LR);
            timeLabel.setFont(FXUI.FONT);
            timeLabel.setMinWidth(Control.USE_PREF_SIZE);
            
            // Completed and overdue events all grey, overdue all red, otherwise orange
            if (task.isCompleted()) {
                idLabel.setTextFill(Color.GREY);
                nameLabel.setTextFill(Color.GREY);
                timeLabel.setTextFill(Color.GREY);
                nameLabel.getStyleClass().add("completed");
            } else if (task.isOverdue() && task.getStart() != null
                    && task.getEnd() != null) {
                idLabel.setTextFill(Color.GREY);
                nameLabel.setTextFill(Color.GREY);
                timeLabel.setTextFill(Color.GREY);
            } else if (task.isOverdue()) {
                idLabel.setTextFill(Color.RED);
                nameLabel.setTextFill(Color.RED);
                timeLabel.setTextFill(Color.RED);
            } else {
                timeLabel.setTextFill(Color.ORANGE);
            }
        }
    }

    public void setName(String name) {
        mName = name;
    }
}
