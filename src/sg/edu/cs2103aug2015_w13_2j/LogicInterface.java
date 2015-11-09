package sg.edu.cs2103aug2015_w13_2j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.exceptions.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.UIInterface;

// @@author A0121410H

/**
 * Interface for a FunDUE logic component. Provides methods to modify the master
 * list of {@link Task} objects as well as methods to interact with the
 * {@link UIInterface} and {@link StorageInterface} components via pass-through
 * methods.
 * 
 * @author Zhu Chunqi
 */
public interface LogicInterface {
    /**
     * Injects the dependency on {@link StorageInterface} and
     * {@link UIInterface} components into this {@link LogicInterface}
     * component.
     * 
     * @param storage
     *            {@link StorageInterface} component to be used. A handle to
     *            this component will be retained for subsequent I/O method
     *            calls.
     * @param textUI
     *            {@link UIInterface} component to be used. A handle to this
     *            component will be retained for when a {@link CommandHandler}
     *            requires data to be displayed or {@link FeedbackMessage} to be
     *            shown.
     */
    public void injectDependencies(StorageInterface storage, UIInterface textUI);

    /**
     * Retrieves the {@link Set} of reserved keyword strings registered by
     * {@link CommandHandler} objects during FunDUE initialization.
     * 
     * @return {@link Set} of reserved keyword strings.
     * @see #registerCommandHandler(CommandHandler)
     */
    public Set<String> getReservedKeywords();

    /**
     * Retrieves the {@link HashMap} of reserved keyword strings to their
     * respective {@link CommandHandler} objects.
     * 
     * @return {@link HashMap} mapping reserved keyword strings to their
     *         respective {@link CommandHandler} objects.
     */
    public HashMap<String, CommandHandler> getCommandHandlers();

    /**
     * Registers the provided {@link CommandHandler} object with this
     * {@link LogicInterface} component. Adds all reserved keyword strings
     * retrieved via {@link CommandHandler#getReservedKeywords()} to the
     * complete set of reserved keywords and maps them to this
     * {@link CommandHandler} object.
     * 
     * @param handler
     *            {@link CommandHandler} object to be registered.
     */
    public void registerCommandHandler(CommandHandler handler);

    /**
     * Executes the command string entered by the user.
     * 
     * @param command
     *            Command string to execute.
     */
    public void executeCommand(String command);

    /**
     * Sends the master list of {@link Task} objects to the {@link UIInterface}
     * component to be displayed.
     */
    public void display();

    /**
     * Pass-through method to {@link UIInterface#feedback(FeedbackMessage)}
     * which displays the provided {@link FeedbackMessage} object to the user.
     * 
     * @param feedback
     *            {@link FeedbackMessage} object to be displayed.
     */
    public void feedback(FeedbackMessage feedback);

    /**
     * Pass-through method to {@link UIInterface#showChangeDataFilePathDialog()}
     * which shows a file picker dialog to let the user choose a new FunDUE data
     * file.
     * 
     * @return {@code True} if a file was chosen, {@code false} otherwise.
     */
    public boolean showChangeDataFilePathDialog();

    /**
     * Pass-through method to {@link UIInterface#showHelpPage()} which shows the
     * FunDUE Help Page window.
     * 
     * @return {@code True} if the FunDUE Help Page was shown, {@code false} if
     *         the window was already showing.
     */
    public boolean showHelpPage();

    /**
     * Adds the provided {@link Task} object to the master list of {@link Task}
     * objects.
     * 
     * @param task
     *            {@link Task} object to be added.
     */
    public void addTask(Task task);

    /**
     * Retrieves the master list of ({@link Task} objects existing locally in
     * the {@link Logic} component.
     * 
     * @return Master list of {@link Task} objects
     */
    public ArrayList<Task> getAllTasks();

    /**
     * Retrieves the {@link Task} object associated with the provided index from
     * the master list of {@link Task} objects. The provided index is specific
     * to the way the {@link UIInterface} component chooses to display and index
     * the master list of {@link Task} objects. Therefore,
     * {@link UIInterface#getTask(int)} is first called to retrieve the
     * {@link Task} object from the {@link UIInterface} component,
     * {@link ArrayList#indexOf(Object)} is called to retrieve the corresponding
     * index in the master list of {@link Task} objects, and finally that index
     * is used to retrieve the associated {@link Task} object from the master
     * list of {@link Task} objects.
     * 
     * @param index
     *            Index of the {@link Task} object to be retrieved.
     * @return {@link Task} object associated with the provided index.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    public Task getTask(int index) throws TaskNotFoundException;

    /**
     * Removes the {@link Task} object from the master list of {@link Task}
     * objects. {@link ArrayList#remove(Object)} is called to remove the
     * {@link Task} object from the master list of {@link Task} objects.
     * 
     * @param task
     *            {@link Task} object to be removed.
     * @return {@link Task} object specified. An exception will be thrown if any
     *         provided index is out of bounds.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    public Task removeTask(Task task) throws TaskNotFoundException;

    /**
     * Removes the {@link Task} object associated with the provided index from
     * the master list of {@link Task} objects. The provided index is specific
     * to the way the {@link UIInterface} component chooses to display and index
     * the master list of {@link Task} objects. Therefore,
     * {@link UIInterface#getTask(int)} is first called to retrieve the
     * {@link Task} object associated with the provided index, then
     * {@link ArrayList#remove(Object)} is called to remove the {@link Task}
     * object from the master list of {@link Task} objects.
     * 
     * @param index
     *            Integer index of the {@link Task} object to be removed.
     * @return {@link Task} object associated with the provided index. An
     *         exception will be thrown if the provided index is out of bounds.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    public Task removeTask(int index) throws TaskNotFoundException;

    /**
     * Reads the master list of {@link Task} objects from the FunDUE data file
     * using the {@link StorageInterface} component. The current master list of
     * {@link Task} objects will be cleared first.
     */
    public void readTasks();

    /**
     * Writes the master list of {@link Task} objects to the FunDUE data file
     * using the {@link StorageInterface} component.
     */
    public void writeTasks();

    /**
     * Pass-through method to {@link StorageInterface#getDataFile()} which
     * retrieves the {@link File} object representing the current FunDUE data
     * file.
     * 
     * @return {@link File} object representing the current FunDUE data file.
     */
    public File getDataFile();

    /**
     * Pass-through method to {@link StorageInterface#setDataFile(File)} which
     * sets the provided {@link File} object as the new FunDUE data file.
     * 
     * @param dataFile
     *            {@link File} object to be used as the FunDUE data file.
     */
    public void setDataFile(File dataFile);

    /**
     * Pass-through method to {@link UIInterface#pushFilter(Filter)} which
     * pushes the provided {@link Filter} object onto the filter chain stack.
     * 
     * @param filter
     *            {@link Filter} object to be pushed onto the filter chain
     *            stack.
     */
    public void pushFilter(Filter filter);

    /**
     * Pass-through method to {@link UIInterface#popFilter()} which pops the top
     * most {@link Filter} object from the filter chain stack and returns it.
     * 
     * @return {@link Filter} object that was popped from the filter chain stack
     *         or {@code null} if only the root {@link Filter} is left.
     */
    public Filter popFilter();

    // @@author A0130894B

    /**
     * Stores a deep copy of the master list of {@link Task} objects into the
     * undo stack.
     */
    public void storeCommandInHistory();

    /**
     * Stores a deep copy of a {@link Task} list into the redo stack.
     * 
     * @param taskListToRedo
     *            List of {@link Task} objects to be stored in redo history.
     * 
     */
    public void storeCommandInRedoHistory(ArrayList<Task> taskListToRedo);

    /**
     * Clears the undo history stack until the root history is reached.
     */
    public void clearUndoHistory();

    /**
     * Clears the redo stack.
     */
    public void clearRedoHistory();

    /**
     * Retrieves the most recent user command, if any. The undo stack
     * initializes with the user's saved master {@link Task} list on its root
     * stack and will only be restored until that particular entry.
     * 
     * @return List of {@link Task} objects that will be displayed after
     *         restoring from the undo stack.
     */
    public ArrayList<Task> restoreCommandFromHistory();

    /**
     * Obtains the last command the user undid, if any. The redo stack
     * initializes with no {@link Task} list and will only be restored until
     * that particular empty entry.
     * 
     * @return List of {@link Task} objects that will be displayed to the user
     *         after restoring from the redo stack.
     */
    public ArrayList<Task> restoreCommandFromRedoHistory();
}
