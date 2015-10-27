package sg.edu.cs2103aug2015_w13_2j;

import java.util.HashMap;
import java.util.Set;

import sg.edu.cs2103aug2015_w13_2j.commands.CommandHandler;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.TextUIInterface;

//@@author A0121410H

/**
 * This interface contains methods for classes implementing it as well as for
 * other components Parser, Formatter, TextUI, Controller to access it
 */
public interface LogicInterface {
    /**
     * Injects the dependency on components implementing the
     * {@link StorageInterface} and {@link TextUIInterface} into this Logic
     * component
     * 
     * @param storage
     *            A Storage component implementing the {@link StorageInterface}.
     *            A handle to this component will be retained for subsequent
     *            calls to {@link Logic#readTasks()} and writeTasks()
     * @param textUI
     *            An object implementing the {@link TextUIInterface}. A handle
     *            to this object will be retained to be accessed when a
     *            CommandHandler provides data to be displayed, provides
     *            feedback or updates the filter chain
     */
    public void injectDependencies(StorageInterface storage,
            TextUIInterface textUI);

    /**
     * Retrieves the set of reserved keywords registered by CommandHandlers
     * during application initialization
     * 
     * @return Set of reserved keywords
     */
    public Set<String> getReservedKeywords();

    /**
     * Retrieves a map of the reserved keywords to their respective
     * CommandHandlers
     * 
     * @return HashMap of the reserved keywords to their respective
     *         CommandHandlers
     */
    public HashMap<String, CommandHandler> getCommandHandlers();

    /**
     * Registers the provided CommandHandler with this Logic component. Adds all
     * declared reserved keywords retrieved via the
     * {@link CommandHandler#getReservedKeywords()} method to the set of
     * reserved keywords and maps them to this CommandHandler
     * 
     * @param handler
     */
    public void registerCommandHandler(CommandHandler handler);

    /**
     * Executes the command entered by the user
     * 
     * @param command
     *            Command string to execute
     */
    public void executeCommand(String command);

    /**
     * Displays the list of Task objects after being filtered by the filter
     * chain via the {@link FilterChain#getTasksForDisplay()} method in the UI
     * via the {@link TextUIInterface#display(java.util.ArrayList)} method
     */
    public void display();

    /**
     * Passes on the provided string to be displayed in the UI via the
     * {@link TextUIInterface#display(String)} method
     * 
     * @param s
     *            String to be displayed
     */
    public void display(String s);

    /**
     * Passes on the provided FeedbackMessage object to be displayed in the UI
     * via the {@link TextUIInterface#feedback(FeedbackMessage)} method
     * 
     * @param f
     *            FeedbackMessage object to be displayed
     */
    public void feedback(FeedbackMessage f);

    /**
     * Passes on the string representing the currently active filter chain to be
     * displayed in the UI via the {@link TextUIInterface#display(String)}
     * method
     * 
     * @param s
     *            String representing the currently active filter chain
     */
    public void setFilter(String s);
}
