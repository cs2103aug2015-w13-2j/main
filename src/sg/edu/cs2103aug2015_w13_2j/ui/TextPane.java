package sg.edu.cs2103aug2015_w13_2j.ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;

// @@author A0121410H-unused
// Reason: Original text-based UI sub-component

/**
 * This class extends the JTextPane swing component with methods specific to
 * displaying and formatting Task objects and other views such as a help page
 * 
 * @author Zhu Chunqi
 * @see JTextPane
 */
public class TextPane extends JTextPane {
    private static final long serialVersionUID = -2906035118426407209L;

    private static final int WIDTH_ID = 4;
    private static final int WIDTH_NAME = 47;
    private static final int WIDTH_DATE = 16;
    private static final int WIDTH_TOTAL = WIDTH_ID + 1 + WIDTH_NAME + 1
            + WIDTH_DATE + 1 + WIDTH_DATE;

    public static final String NEWLINE = System.getProperty("line.separator");
    private static final String HEADER_ID = "ID";
    private static final String HEADER_NAME = "TASK NAME";
    private static final String HEADER_START = "START";
    private static final String HEADER_END = "END";
    private static final String HEADER_NO_TASKS = "NO TASKS TO DISPLAY";
    private static final String HEADER_NO_OVERDUE_TASKS = "YOU HAVE NO OVERDUE TASKS";
    private static final String SEPARATOR_VERTICAL = "|";
    private static final String SEPARATOR_HORIZONTAL = "-";
    private static final String SEPARATOR_CROSS = "+";
    private static final String SEPARATOR_BLANK = " ";

    private static final Color COLOR_NORMAL = Color.BLACK;
    private static final Color COLOR_HIGHLIGHT = Color.RED;

    private static final AttributeSet STYLE_DEFAULT = getStyle(COLOR_NORMAL);
    private static final AttributeSet STYLE_HIGHLIGHT = getStyle(COLOR_HIGHLIGHT);

    /**
     * SimpleDateFormatter object which formats date objects using
     * <code>dd/MM/yy'T'HH:mm</code> format
     */
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(
            "dd/MM/yy'T'HH:mm");

    public TextPane() {
        super();
    }

    /**
     * Displays the list of Task objects in the document with the appropriate
     * styling
     * 
     * @param tasks
     *            The list of Task objects to be displayed
     */
    public void display(ArrayList<Task> tasks) {
        clear();
        writeSeparator();
        writeHeader();
        writeSeparator();
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    if (tasks.get(i).isValid()) {
                        writeTask(tasks.get(i), i);
                    }
                } catch (InvalidTaskException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            print(SEPARATOR_VERTICAL);
            writeCentered(HEADER_NO_TASKS, WIDTH_TOTAL);
            print(SEPARATOR_VERTICAL);
            print(NEWLINE);
        }
        writeSeparator();
    }

    /**
     * Writes the headers of the table with vertical separators between columns
     * as shown compacted below<br>
     * <code>| ID | TASK NAME | START | END |</code>
     */
    private void writeHeader() {
        print(SEPARATOR_VERTICAL);
        writeCentered(HEADER_ID, WIDTH_ID);
        print(SEPARATOR_VERTICAL);
        writeCentered(HEADER_NAME, WIDTH_NAME);
        print(SEPARATOR_VERTICAL);
        writeCentered(HEADER_START, WIDTH_DATE);
        print(SEPARATOR_VERTICAL);
        writeCentered(HEADER_END, WIDTH_DATE);
        print(SEPARATOR_VERTICAL);
        print(NEWLINE);
    }

    /**
     * Writes a separator row with crosses at each column end point to the
     * document as shown compacted below<br>
     * <code>+----+-----------+-------+-----+</code>
     */
    private void writeSeparator() {
        print(SEPARATOR_CROSS);
        writeRepeat(SEPARATOR_HORIZONTAL, WIDTH_ID);
        print(SEPARATOR_CROSS);
        writeRepeat(SEPARATOR_HORIZONTAL, WIDTH_NAME);
        print(SEPARATOR_CROSS);
        writeRepeat(SEPARATOR_HORIZONTAL, WIDTH_DATE);
        print(SEPARATOR_CROSS);
        writeRepeat(SEPARATOR_HORIZONTAL, WIDTH_DATE);
        print(SEPARATOR_CROSS);
        print(NEWLINE);
    }

    private void writeTask(Task t, int id) {
        print(SEPARATOR_VERTICAL);
        writeID(id);
        print(SEPARATOR_VERTICAL);
        print(SEPARATOR_BLANK);
        writeStatus(t);
        print(SEPARATOR_BLANK);
        writeTruncate(t.getName(), WIDTH_NAME - 5);
        print(SEPARATOR_VERTICAL);
        writeDates(t);
        print(SEPARATOR_VERTICAL);
        print(NEWLINE);
    }

    private void writeStatus(Task t) {
        if (t.isCompleted()) {
            print("[D]");
        } else if (t.isImportant()) {
            print("[!]", STYLE_HIGHLIGHT);
        } else {
            print("---");
        }
    }

    /**
     * Writes the provided string in a centered position within the provided
     * width, with the rest of the space padded by blanks
     * 
     * @param s
     *            The string to be written
     * @param width
     *            The width in which to center the string
     */
    private void writeCentered(String s, int width) {
        assert (s.length() < width);
        int start = (width - s.length()) / 2;
        writeRepeat(SEPARATOR_BLANK, start);
        print(s);
        writeRepeat(SEPARATOR_BLANK, width - start - s.length());
    }

    /**
     * Writes the index of the Task object. Internally calls writeCentered with
     * the string value of the index and a width of 4
     * 
     * @param id
     *            The index of the Task object
     */
    private void writeID(int id) {
        assert (id >= 0 && id <= 9999);
        writeCentered(String.valueOf(id), WIDTH_ID);
    }

    /**
     * Writes the string provided with provisions for truncating excess over
     * width and appending with ellipsis
     * 
     * @param s
     *            The string to be written
     * @param width
     *            The width in which to constrain the string
     */
    private void writeTruncate(String s, int width) {
        assert (s != null && s.length() > 0);
        assert (width > 3);
        if (s.length() < width) {
            print(s);
            writeRepeat(SEPARATOR_BLANK, width - s.length());
        } else {
            print(s.substring(0, width - 3) + "...");
        }
    }

    /**
     * Writes the date provided using the {@link TextPane#FORMAT_DATE} constant
     * or a centered <code>---</code> if the date is null
     * 
     * @param date
     *            The date to be written
     */
    private void writeDates(Task t) {
        Date start = t.getStart();
        Date end = t.getEnd();
        if (start == null) {
            writeCentered("---", WIDTH_DATE);
        } else {
            writeCentered(FORMAT_DATE.format(start), WIDTH_DATE);
        }
        print(SEPARATOR_VERTICAL);
        if (end == null) {
            writeCentered("---", WIDTH_DATE);
        } else {
            print(SEPARATOR_BLANK);
            if (t.isOverdue()) {
                print(FORMAT_DATE.format(end), STYLE_HIGHLIGHT);
            } else {
                print(FORMAT_DATE.format(end));
            }
            print(SEPARATOR_BLANK);
        }
    }

    /**
     * Utility method to repeatedly write the provided string for the specified
     * number of times
     * 
     * @param s
     *            The string to be repeated
     * @param n
     *            The number of times to repeat
     */
    private void writeRepeat(String s, int n) {
        for (int i = 0; i < n; i++) {
            print(s);
        }
    }

    public void print(String s) {
        print(s, STYLE_DEFAULT);
    }

    /**
     * Internally used method to print the string to the text pane at the
     * specified position from the end of the document. If provided offset is
     * not 0, the method will first remove string of length offset from the end
     * of document and then prints the provided string
     * 
     * @param s
     *            The string to be printed
     * @param offset
     *            Positive integer representing offset from end of document
     */
    private void print(String s, AttributeSet a) {
        StyledDocument document = getStyledDocument();
        try {
            document.insertString(document.getLength(), s, a);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the text pane by removing all content within the document
     */
    public void clear() {
        StyledDocument document = getStyledDocument();
        try {
            document.remove(0, document.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private static AttributeSet getStyle(Color c) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        return styleContext.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Background, c);
    }
}
