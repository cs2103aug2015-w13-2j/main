package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import javafx.scene.Parent;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.exceptions.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;

// @@author A0121410H

public interface UIInterface {
    /**
     * Injects the dependency on a {@link LogicInterface} component into this
     * {@link UIInterface} component.
     * 
     * @param logic
     *            A {@link LogicInterface} component. A handle to this object
     *            will be retained to access the {@link LogicInterface} methods.
     */
    public void injectDependency(LogicInterface logic);

    /**
     * Displays the provided list of {@link Task} objects.
     * 
     * @param tasks
     *            List of {@link Task} objects to be displayed.
     */
    public void display(ArrayList<Task> tasks);

    /**
     * Retrieves the Task object associated with the index provided. The general
     * contract is that the display will associate an unique integer index to
     * each Task object whenever {@link #display(ArrayList)} is called
     * 
     * @param index
     *            Integer index associated with a displayed Task object
     * @return Task object associated with the provided index
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds
     */
    public Task getTask(int index) throws TaskNotFoundException;

    /**
     * Displays the provided FeedbackMessage object to the user. The
     * FeedbackMessage object encapsulates the feedback message text as well as
     * the styling with which to display the message
     * 
     * @param f
     *            FeedbackMessage object to be displayed
     * @see FeedbackMessage
     */
    public void feedback(FeedbackMessage f);

    /**
     * Retrieves the string message content of the last {@link FeedbackMessage}
     * object that was displayed.
     * 
     * @return String message content of last displayed {@link FeedbackMessage}
     *         object.
     */
    public String getFeedbackMessageString();

    /**
     * Adds the provided {@link Filter} object to the filter chain. The filtered
     * list of Task objects will then be displayed
     * 
     * @param filter
     *            Filter object to add to the filter chain
     */
    public void pushFilter(Filter filter);

    /**
     * Removes the final {@link Filter} object in the filter chain and returns
     * it. If only the root filter is remaining, this method does nothing and
     * returns {@code null}
     * 
     * @return Popped {@link Filter} object or {@code null} if only the root
     *         {@link Filter} remains
     */
    public Filter popFilter();

    /**
     * Shows a file picker dialog which prompts the user to select a FunDUE data
     * file. Returns {@code true} if a file is successfully chosen, the user's
     * preference will be updated and the selected file will used as the FunDUE
     * data file.
     * 
     * @return {@code True} if a file was selected, {@code false} otherwise.
     */
    public boolean showChangeDataFilePathDialog();

    /**
     * Shows the window containing the FunDUE Help Page. Return value indicates
     * whether the window was opened by the method.
     * 
     * @return {@code True} if the FunDUE Help Page was shown, {@code false} if
     *         the window was already showing.
     */
    public boolean showHelpPage();

    /**
     * Attempts to place focus on the command bar text field input.
     */
    public void focusCommandBar();

    /**
     * Retrieves the UI elements to be displayed in a {@link Parent} container.
     * 
     * @return {@link Parent} object to be displayed.
     */
    public Parent getUI();
}
