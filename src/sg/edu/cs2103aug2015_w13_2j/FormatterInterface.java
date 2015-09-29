package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H
public interface FormatterInterface {
    // Enumeration of the various formats for displaying
    public enum Format {
        LIST, CALENDAR
    }

    /**
     * Formats the details of a task for output into TextUI
     * 
     * @param t
     *            The Task object to be formatted for display
     * @param f
     *            The enumerated format option to display the Task object
     */
    public void format(Task t, Format f);

    /**
     * Formats the list of tasks for output into TextUI
     * 
     * @param tasks
     *            The array of Task objects to be formatted display
     * @param f
     *            The enumerated format option to display the Task objects
     */
    public void format(Task[] tasks, Format f);
}
