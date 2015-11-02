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
        // Note: ID and end date labels will always take up their required
        // space, name label can grow horizontally to fill in available space
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            HBox row = new HBox();
            
            Label idLabel = new Label((int) (i + offset + 1) + ".");
            idLabel.setPadding(FXUI.PADDING_LR_10);
            idLabel.setFont(FXUI.FONT);
            idLabel.setMinWidth(Control.USE_PREF_SIZE);
            
            Label nameLabel = new Label(task.getName());
            nameLabel.setPadding(FXUI.PADDING_LR_10);
            nameLabel.setFont(FXUI.FONT);
            row.getChildren().addAll(idLabel, nameLabel);
            
            if (task.getStart() != null && task.getEnd() != null) { // EVENT
                Label durationLabel = new Label(
                        "(from " + FXUI.PRETTY_TIME.format(task.getStart()) + " to "
                                + FXUI.PRETTY_TIME.format(task.getEnd()) + ")");
                durationLabel.setPadding(FXUI.PADDING_LR_10);
                durationLabel.setFont(FXUI.FONT);
                durationLabel.setMinWidth(Control.USE_PREF_SIZE);
                
                // Overdue style is entire row red, otherwise end date is orange
                if (task.isCompleted()) {
                    idLabel.setTextFill(Color.GREY);
                    nameLabel.setTextFill(Color.GREY);
                    durationLabel.setTextFill(Color.GREY);
                } else if (task.isOverdue()) {
                    idLabel.setTextFill(Color.RED);
                    nameLabel.setTextFill(Color.RED);
                    durationLabel.setTextFill(Color.RED);
                } else {
                    durationLabel.setTextFill(Color.ORANGE);
                }
                
                row.getChildren().add(durationLabel);
            } else if (task.getEnd() != null) { // DEADLINE
                Label endLabel = new Label(
                        "(due " + FXUI.PRETTY_TIME.format(task.getEnd()) + ")");
                endLabel.setPadding(FXUI.PADDING_LR_10);
                endLabel.setFont(FXUI.FONT);
                endLabel.setMinWidth(Control.USE_PREF_SIZE);
                
                // Overdue style is entire row red, otherwise end date is orange
                if (task.isCompleted()) {
                    idLabel.setTextFill(Color.GREY);
                    nameLabel.setTextFill(Color.GREY);
                    endLabel.setTextFill(Color.GREY);
                } else if (task.isOverdue()) {
                    idLabel.setTextFill(Color.RED);
                    nameLabel.setTextFill(Color.RED);
                    endLabel.setTextFill(Color.RED);
                } else {
                    endLabel.setTextFill(Color.ORANGE);
                }
                
                row.getChildren().add(endLabel);
            } else if (task.getStart() != null) { // FLOAT with start date
                Label startLabel = new Label(
                        "(starts/started " + FXUI.PRETTY_TIME.format(task.getStart()) + ")");
                startLabel.setPadding(FXUI.PADDING_LR_10);
                startLabel.setFont(FXUI.FONT);
                startLabel.setMinWidth(Control.USE_PREF_SIZE);
                
                if (task.isCompleted()) {
                    idLabel.setTextFill(Color.GREY);
                    nameLabel.setTextFill(Color.GREY);
                    startLabel.setTextFill(Color.GREY);
                }

                row.getChildren().add(startLabel);
            } else if (task.isCompleted()) { // FLOAT without anything
                idLabel.setTextFill(Color.GREY);
                nameLabel.setTextFill(Color.GREY);
            }
            
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            mContainer.getChildren().add(row);
        }
    }

    public void setName(String name) {
        mName = name;
    }
}
