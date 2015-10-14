package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

//@@author A0121410H

public class TextUI extends JFrame implements TextUIInterface, KeyListener {
    /**
     * Enumeration of user feedback messages to the user. As this is implemented
     * as an enum, the messages do not contain dynamic data. Each enum element
     * is self contained with the message text {@link Message#getMessage()} and
     * styling {@link Message#getType()} to be shown to the user
     * 
     * @author Zhu Chunqi
     *
     */
    public enum Message {
        // Used to clear the feedback label
        CLEAR("", FeedbackType.INFO),

        // Errors
        ERROR_INVALID_TASK("Invalid task. Did you remember to include quotes around the task name?", FeedbackType.ERROR),
        ERROR_TASK_NOT_FOUND("Task not found. Did you enter the index correctly?", FeedbackType.ERROR),
        ERROR_INVALID_SEARCH_TERM("Invalid search term. Did you enter it with double quotes?", FeedbackType.ERROR),
        ERROR_COMMAND_NOT_IMPLEMENTED("The command that you have entered has not been implemented.", FeedbackType.ERROR),
        ERROR_COMMAND_NOT_RECOGNIZED("Command not recognized.", FeedbackType.ERROR),

        // Informative
        LOGIC_ADDED("Task added successfully.", FeedbackType.INFO),
        LOGIC_EDITED("Task edited successfully.", FeedbackType.INFO),
        LOGIC_DELETED("Task deleted successfully.", FeedbackType.INFO),
        LOGIC_ARCHIVED("Task archived successfully.", FeedbackType.INFO),
        LOGIC_RETRIEVED("Task retrieved successfully.", FeedbackType.INFO),
        LOGIC_SEARCH_FOUND("Task(s) found.", FeedbackType.INFO),
        LOGIC_SEARCH_NOT_FOUND("Task(s) not found.", FeedbackType.INFO);

        private String mMsg;
        private FeedbackType mType;

        private Message(String s, FeedbackType type) {
            mMsg = s;
            mType = type;
        }

        /**
         * Retrieves the message text
         * 
         * @return The message text
         */
        public String getMessage() {
            return mMsg;
        }

        /**
         * Retrieves the FeedbackType enum containing the styling information
         * for this message
         * 
         * @return FeedbackType enum for this message
         * @see FeedbackType
         */
        public FeedbackType getType() {
            return mType;
        }
    }

    /**
     * Enumeration of the styling for the different types of user feedback
     * messages. The color of font to be used for each feedback type can be
     * retrieved via {@link FeedbackType#getColor()}
     * 
     * @author Zhu Chunqi
     *
     */
    public enum FeedbackType {
        INFO(Color.BLACK), ERROR(Color.RED);

        private Color mColor;

        private FeedbackType(Color color) {
            mColor = color;
        }

        /**
         * Retrieves the color that should be used to style this type of
         * feedback
         * 
         * @return Color object to be used for this type of feedback
         */
        public Color getColor() {
            return mColor;
        }
    }

    // For portability across Unix and Windows systems
    public static final String NEWLINE = System.getProperty("line.separator");

    // Internally used constants for formatting the UI
    private static final Font FONT = new Font("consolas", Font.BOLD, 16);
    private static final int WIDTH_ID = 4;
    private static final int WIDTH_NAME = 47;
    private static final int WIDTH_DATE = 16;
    private static final int WIDTH_TOTAL = WIDTH_ID + 1 + WIDTH_NAME + 1 + WIDTH_DATE + 1 + WIDTH_DATE;
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
    private static final Color COLOR_OVERDUE = Color.RED;
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yy'T'HH:mm");

    // Serialization ID
    private static final long serialVersionUID = 7758912303888211773L;

    // The (fixed) size of the window
    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    // Self instance
    private static TextUI sInstance;
    
    // UI Components
    private JTextField mTextField;
    private JTextPane mTextPane;
    private JLabel mLabel;
    private int mPrevLen = 0;

    /**
     * Protected constructor
     */
    protected TextUI() {
        createUI();
    }

    /**
     * Retrieves the singleton instance of the TextUI component
     * 
     * @return TextUI component
     */
    public static TextUI getInstance() {
        if (sInstance == null) {
            sInstance = new TextUI();
        }
        return sInstance;
    }

    public void display(ArrayList<Task> tasks) {
        clear();
        writeSeparator();
        writeHeader();
        writeSeparator();
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                writeTask(tasks.get(i), i);
            }
        } else {
            print(SEPARATOR_VERTICAL);
            writeCentered(HEADER_NO_TASKS, WIDTH_TOTAL);
            print(SEPARATOR_VERTICAL);
            print(NEWLINE);
        }
        writeSeparator();
    }

    public void feedback(Message m) {
        mLabel.setForeground(m.getType().getColor());
        mLabel.setText(m.getMessage());
    }

    public void print(String s) {
        print(s, 0);
    }

    public void printr(String s) {
        print(s, mPrevLen);
    }

    public void addTextFieldKeyListener(KeyListener listener) {
        mTextField.addKeyListener(listener);
    }

    public void removeTextFieldKeyListener(KeyListener listener) {
        mTextField.removeKeyListener(listener);
    }

    public void keyTyped(KeyEvent e) {
        // Empty function
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            // Hijack the snake command
            if (mTextField.getText().equalsIgnoreCase("snake")) {
                new SnakeTXT(this);
            } else {
                System.out.println("[TextUI] Command: " + mTextField.getText());
                Logic.getInstance().executeCommand(mTextField.getText());
            }
            mTextField.setText(null);
            break;
        case KeyEvent.VK_UP:
            // TODO: Pass to parser
            break;
        case KeyEvent.VK_DOWN:
            // TODO: Pass to parser
            break;
        case KeyEvent.VK_ESCAPE:
            System.exit(0);
            break;
        default:
            break;
        }
    }

    public void keyReleased(KeyEvent e) {
        // Empty function
    }

    /**
     * Initialize and show the UI Elements. The UI consists a JTextPane which
     * displays styled text to the user, a JLabel which displays styled text
     * feedback from executing user commands, and a JTextField which accepts
     * user input
     */
    private void createUI() {
        // Create and set up window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setPreferredSize(PREFERRED_SIZE);

        // Will be reused for each UI element
        GridBagConstraints constraints = null;

        mTextPane = new JTextPane();
        mTextPane.setEditable(false);
        mTextPane.setFont(FONT);
        mTextPane.setBackground(Color.BLACK);
        mTextPane.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(mTextPane);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        contentPane.add(scrollPane, constraints);

        mLabel = new JLabel();
        mLabel.setFont(FONT);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        contentPane.add(mLabel, constraints);

        mTextField = new JTextField();
        mTextField.addKeyListener(this);
        mTextField.setFont(FONT);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        contentPane.add(mTextField, constraints);

        // Display the window
        this.setLocation(100, 100);
        this.pack();
        this.setVisible(true);
        mTextField.requestFocusInWindow();
    }

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
        StyledDocument document = mTextPane.getStyledDocument();

        print(SEPARATOR_VERTICAL);
        int startLoc = document.getLength();
        writeID(id);
        print(SEPARATOR_VERTICAL);
        writeTaskName(t.getName());
        print(SEPARATOR_VERTICAL);
        writeDate(t.getStart());
        print(SEPARATOR_VERTICAL);
        writeDate(t.getEnd());
        int endLoc = document.getLength();
        print(SEPARATOR_VERTICAL);
        print(NEWLINE);

        if (t.isOverdue()) {
            StyleContext styleContext = StyleContext.getDefaultStyleContext();
            AttributeSet attributeSet = styleContext.addAttribute(
                    SimpleAttributeSet.EMPTY, StyleConstants.Background,
                    COLOR_OVERDUE);
            document.setCharacterAttributes(startLoc, endLoc - startLoc,
                    attributeSet, false);
        }
    }

    private void writeCentered(String header, int width) {
        assert (header.length() < width);
        int start = (width - header.length()) / 2;
        writeRepeat(SEPARATOR_BLANK, start);
        print(header);
        writeRepeat(SEPARATOR_BLANK, width - start - header.length());
    }

    private void writeID(int id) {
        assert (id >= 0 && id <= 9999);
        // TODO: Assumed width to be 4
        if (id < 10) {
            print("  " + id + " ");
        } else if (id < 100) {
            print(" " + id + " ");
        } else if (id < 1000) {
            print(String.valueOf(id));
        }
    }

    private void writeTaskName(String taskName) {
        assert (taskName != null && taskName.length() > 0);
        if (taskName.length() < WIDTH_NAME) {
            print(taskName);
            writeRepeat(SEPARATOR_BLANK, WIDTH_NAME - taskName.length());
        } else {
            print(taskName.substring(0, WIDTH_NAME - 3) + "...");
        }
    }

    private void writeDate(Date date) {
        if (date == null) {
            writeCentered("---", WIDTH_DATE);
        } else {
            writeCentered(FORMAT_DATE.format(date), WIDTH_DATE);
        }
    }

    private void writeRepeat(String s, int n) {
        for (int i = 0; i < n; i++) {
            print(s);
        }
    }

    /**
     * Prints the string to the text pane with a newline. Internally calls print
     * with newline appended to the provided string
     * 
     * @param s
     *            The string to be printed
     */
    private void println(String s) {
        print(s + NEWLINE);
    }

    /**
     * Prints the string to replace the previously printed string with a
     * newline. Internally calls printr with newline appended to provided string
     * 
     * @param s
     *            The string the be printed in replacement of previous string
     */
    private void printlnr(String s) {
        printr(s + NEWLINE);
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
    private void print(String s, int offset) {
        StyledDocument document = mTextPane.getStyledDocument();
        try {
            mPrevLen = s.length();
            // Remove string of length offset from end of document
            if (offset > 0) {
                document.remove(document.getLength() - offset, offset);
            }
            document.insertString(document.getLength(), s, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility function to clear the text pane at the start of each display call
     */
    private void clear() {
        StyledDocument document = mTextPane.getStyledDocument();
        try {
            document.remove(0, document.getLength());
            mPrevLen = 0;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
