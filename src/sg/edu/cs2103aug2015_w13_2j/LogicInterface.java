package sg.edu.cs2103aug2015_w13_2j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;
import sg.edu.cs2103aug2015_w13_2j.storage.StorageInterface;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.UIInterface;

// @@author A0121410H

/**
 * Interface for the logic component. Provides methods to modify the master list
 * of {@link Task} objects as well as to interact with the {@link UIInterface}
 * component and {@link StorageInterface} component via pass-through methods.
 * 
 * @author Zhu Chunqi
 */
public interface LogicInterface {
    /**
     * Injects the dependency on components implementing the
     * {@link StorageInterface} and {@link UIInterface} into this
     * {@link LogicInterface} component.
     * 
     * @param storage
     *            A {@link StorageInterface} component. A handle to this
     *            component will be retained for subsequent calls to
     *            {@link #readTasks()} and {@link #writeTasks()}.
     * @param textUI
     *            An {@link UIInterface} component. A handle to this component
     *            will be retained to be called when a {@link CommandHandler}
     *            provides data to be displayed or provides feedback.
     */
    public void injectDependencies(StorageInterface storage, UIInterface textUI);

    /**
     * Retrieves the set of reserved keyword strings registered by
     * {@link CommandHandler} objects during initialization via
     * {@link #registerCommandHandler(CommandHandler)}.
     * 
     * @return Set of reserved keyword strings.
     */
    public Set<String> getReservedKeywords();

    /**
     * Retrieves a map of the reserved keyword strings to their respective
     * {@link CommandHandler} objects.
     * 
     * @return {@link HashMap} object of the reserved keyword strings to their
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
     * Executes the command entered by the user.
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
     * Pass-through method to {@link UIInterface#display(String)}.
     * 
     * @param s
     *            String to be displayed.
     */
    public void display(String s);

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
     */
    public void showChangeDataFilePathDialog();

    /**
     * Adds the provided {@link Task} object to the master list of {@link Task}
     * objects and updates the {@link FilterChain} in the {@link UIInterface}
     * component.
     * 
     * @param task
     *            {@link Task} object to be added.
     */
    public void addTask(Task task);

    /**
     * Retrieves the {@link Task} object associated with the provided index from
     * the master list of {@link Task} objects. The provided index is specific
     * to the way the {@link UIInterface} component chooses to display the
     * master list of {@link Task} objects. Therefore,
     * {@link UIInterface#getTask(int)} is first called to retrieve the
     * {@link Task} object associated with the provided index,
     * {@link ArrayList#indexOf(Object)} is called to retrieve the corresponding
     * index in the master list of {@link Task} objects and finally the index is
     * used to retrieve the associated {@link Task} object from the master list
     * of {@link Task} objects.
     * 
     * @param index
     *            Integer index of the {@link Task} object to be retrieved.
     * @return {@link Task} object associated with the provided index. An
     *         exception will be thrown if the provided index is out of bounds.
     * @throws TaskNotFoundException
     *             Thrown when the provided index is out of bounds.
     */
    public Task getTask(int index) throws TaskNotFoundException;

    /**
     * Removes the {@link Task} object associated with the provided index from
     * the master list of {@link Task} objects. The provided index is specific
     * to the way the {@link UIInterface} component chooses to display the
     * master list of {@link Task} objects. Therefore,
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
     * via the {@link StorageInterface} component. The current list of
     * {@link Task} objects will be cleared first.
     */
    public void readTasks();

    /**
     * Writes the master list of {@link Task} objects to the FunDUE data file
     * via the {@link StorageInterface} component.
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
     * sets the provided {@link File} object as new the FunDUE data file.
     * 
     * @param newDataFile
     *            {@link File} object to be used as the FunDUE data file.
     */
    public void setDataFile(File newDataFile);

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
     * Pass-through method to {@link UIInterface#popFilter()} which pops the
     * last {@link Filter} object from the filter chain stack and returns it.
     * 
     * @return {@link Filter} object that was popped from the filter chain stack
     *         or {@code null} if only the root {@link Filter} is left.
     */
    public Filter popFilter();
    
    //@@author A0130894B
    
    /**
     * Stores a deep copy of the master {@link Task} list into the undo stack.
     */
    void storeCommandInHistory();

    /**
     * Stores a deep copy of a {@link Task} list into the redo stack.
     */
    void storeCommandInRedoHistory(ArrayList<Task> taskListToRedo);

    /**
     * Clears the redo stack.
     */
    void clearRedoHistory();

    /**
     * Retrieves the most recent user command, if any. The undo stack initializes 
     * with the user's saved master {@link Task} list on its root stack and will 
     * only be restored until that particular entry.
     * 
     * @return List of {@link Task} objects that will be displayed 
     *         after restoring from the undo stack.
     */
    ArrayList<Task> restoreCommandFromHistory();

    /**
     * Obtains the last command the user undid, if any. The redo stack initializes 
     * with no {@link Task} list and will only be restored until that particular 
     * empty entry.
     * 
     * @return List of {@link Task} objects that will be displayed to the user 
     *         after restoring from the redo stack.
     */
    ArrayList<Task> restoreCommandFromRedoHistory();
}
