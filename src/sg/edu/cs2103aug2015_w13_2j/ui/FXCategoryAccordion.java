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
            if (task.getEnd() != null) {
                Label endLabel = new Label(
                        "(" + FXUI.PRETTY_TIME.format(task.getEnd()) + ")");
                endLabel.setPadding(FXUI.PADDING_LR_10);
                endLabel.setFont(FXUI.FONT);
                endLabel.setMinWidth(Control.USE_PREF_SIZE);
                // Overdue style is entire row red, otherwise end date is orange
                if (task.isOverdue()) {
                    idLabel.setTextFill(Color.RED);
                    nameLabel.setTextFill(Color.RED);
                    endLabel.setTextFill(Color.RED);
                } else {
                    endLabel.setTextFill(Color.ORANGE);
                }
                row.getChildren().add(endLabel);
            }
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            mContainer.getChildren().add(row);
        }
    }

    public void setName(String name) {
        mName = name;
    }
}
