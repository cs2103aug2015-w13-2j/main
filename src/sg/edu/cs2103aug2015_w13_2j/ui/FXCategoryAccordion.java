package sg.edu.cs2103aug2015_w13_2j.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sg.edu.cs2103aug2015_w13_2j.Task;

// @@author A0124007X

public class FXCategoryAccordion extends Accordion {
    private String mName;
    private final VBox mContainer;
    private final TitledPane mTitledPane;

    public FXCategoryAccordion(String name) {
        super();
        mName = name;
        mContainer = new VBox();
        mTitledPane = new TitledPane("", mContainer);
        mTitledPane.getStyleClass().add("normalFont");
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

        // No tasks to display
        if (tasks.size() == 0) {
            HBox row = new HBox();
            row.setMinHeight(35);
            row.setAlignment(Pos.CENTER);
            mContainer.getChildren().add(row);

            Label idLabel = new Label();
            idLabel.getStyleClass().add("labelPadding");
            idLabel.getStyleClass().add("smallerFont");
            idLabel.setMinWidth(FXUI.ID_MIN_WIDTH);
            row.getChildren().add(idLabel);

            Label nameLabel = new Label("No tasks in this category.");
            nameLabel.getStyleClass().add("labelPadding");
            nameLabel.getStyleClass().add("normalFont");
            nameLabel.setTextFill(Color.GREY);
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            row.getChildren().add(nameLabel);

            Region spacer = new Region();
            row.getChildren().add(spacer);
            HBox.setHgrow(spacer, Priority.ALWAYS);
        }

        // Display Task objects in category
        // Note: ID, importance and time labels will always take up their
        // required space, while name label will grow horizontally to fill
        // in available space
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            HBox row = new HBox();
            row.setMinHeight(35);
            row.setAlignment(Pos.CENTER);
            mContainer.getChildren().add(row);

            Label idLabel = new Label((int) (i + offset + 1) + "");
            idLabel.getStyleClass().add("labelPadding");
            idLabel.getStyleClass().add("smallerFont");
            idLabel.setMinWidth(FXUI.ID_MIN_WIDTH);
            idLabel.setTextFill(Color.GREY);
            row.getChildren().add(idLabel);

            Label importanceLabel = new Label("!");
            importanceLabel.getStyleClass().add("labelPadding");
            importanceLabel.getStyleClass().add("normalFont");
            importanceLabel.setMinWidth(Control.USE_PREF_SIZE);
            importanceLabel.setTextFill(Color.RED);
            if (task.isImportant()) {
                row.getChildren().add(importanceLabel);
            }

            Label nameLabel = new Label(task.getName());
            nameLabel.getStyleClass().add("labelPadding");
            nameLabel.getStyleClass().add("normalFont");
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            row.getChildren().add(nameLabel);

            Region spacer = new Region();
            row.getChildren().add(spacer);
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label timeLabel;
            final SimpleDateFormat DATE = new SimpleDateFormat("d/M/yy HH:mm");
            final SimpleDateFormat DAY = new SimpleDateFormat("d/M/yy");
            final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm");
            if (task.getStart() != null && task.getEnd() != null) { // EVENT
                Date start = task.getStart();
                Date end = task.getEnd();

                // Shortens the description if start and end are the same day
                String timeString = "(" + DATE.format(start) + " - ";
                if (DAY.format(start).equals(DAY.format(end))) {
                    timeString += TIME.format(end);

                } else {
                    timeString += DATE.format(end);
                }
                timeString += ")";
                timeLabel = new Label(timeString);

                row.getChildren().add(timeLabel);
            } else if (task.getEnd() != null) { // DEADLINE
                Date end = task.getEnd();
                timeLabel = new Label("(due " + DATE.format(end) + ")");
                row.getChildren().add(timeLabel);
            } else if (task.getStart() != null) { // FLOAT with start date
                Date start = task.getStart();

                if (start.before(new Date())) {
                    timeLabel = new Label(
                            "(started " + DATE.format(start) + ")");
                } else {
                    timeLabel = new Label(
                            "(starts " + DATE.format(start) + ")");
                }

                row.getChildren().add(timeLabel);
            } else {
                timeLabel = new Label();
            }
            timeLabel.getStyleClass().add("labelPadding");
            timeLabel.getStyleClass().add("smallerFont");
            timeLabel.setMinWidth(Control.USE_PREF_SIZE);

            // Completed and overdue events all grey, overdue all red, otherwise
            // orange
            if (task.isCompleted()) {
                nameLabel.setTextFill(Color.GREY);
                timeLabel.setTextFill(Color.GREY);
                nameLabel.getStyleClass().add("completed");
            } else if (task.isOverdue() && task.getStart() != null
                    && task.getEnd() != null) {
                nameLabel.setTextFill(Color.GREY);
                timeLabel.setTextFill(Color.GREY);
            } else if (task.isOverdue()) {
                nameLabel.setTextFill(Color.RED);
                timeLabel.setTextFill(Color.RED);
            } else {
                timeLabel.setTextFill(Color.DARKORANGE);
            }
        }
    }

    public void setName(String name) {
        mName = name;
    }
}
