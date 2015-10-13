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
import javax.swing.text.BadLocationException;
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
    private static final char SEPARATOR_VERTICAL = '|';
    private static final char SEPARATOR_HORIZONTAL = '-';
    private static final char SEPARATOR_CROSS = '+';
    private static final char SEPARATOR_BLANK = ' ';
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd/MM/yy'T'HH:mm");

    // Serialization ID
    private static final long serialVersionUID = 7758912303888211773L;

    // The (fixed) size of the window
    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    private FunDUE mAppInstance;
    private JTextField mTextField;
    private JTextPane mTextPane;
    private JLabel mLabel;
    private int mPrevLen = 0;

    public TextUI(FunDUE appInstance) {
        mAppInstance = appInstance;

        // Create and set up window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setPreferredSize(PREFERRED_SIZE);

        // Will be reused for each GUI component
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

        mLabel = new JLabel("Test");
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

    public void display(ArrayList<Task> tasks) {
        clear();
        StringBuilder sb = new StringBuilder();
        writeHeader(sb);
        writeSeparator(sb);
        if(tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                writeTask(sb, tasks.get(i), i);
            }
        } else {
            sb.append(SEPARATOR_VERTICAL);
            writeCentered(sb, HEADER_NO_TASKS, WIDTH_TOTAL);
            sb.append(SEPARATOR_VERTICAL);
            sb.append(NEWLINE);
        }
        writeSeparator(sb);
        printr(sb.toString());
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
                mAppInstance.getParserInstance().parseAndExecuteCommand(
                        mTextField.getText());
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
    
    private void writeHeader(StringBuilder sb) {
        sb.append(SEPARATOR_VERTICAL);
        writeCentered(sb, HEADER_ID, WIDTH_ID);
        sb.append(SEPARATOR_VERTICAL);
        writeCentered(sb, HEADER_NAME, WIDTH_NAME);
        sb.append(SEPARATOR_VERTICAL);
        writeCentered(sb, HEADER_START, WIDTH_DATE);
        sb.append(SEPARATOR_VERTICAL);
        writeCentered(sb, HEADER_END, WIDTH_DATE);
        sb.append(SEPARATOR_VERTICAL);
        sb.append(NEWLINE);
    }
    
    private void writeSeparator(StringBuilder sb) {
        sb.append(SEPARATOR_CROSS);
        writeRepeat(sb, SEPARATOR_HORIZONTAL, WIDTH_ID);
        sb.append(SEPARATOR_CROSS);
        writeRepeat(sb, SEPARATOR_HORIZONTAL, WIDTH_NAME);
        sb.append(SEPARATOR_CROSS);
        writeRepeat(sb, SEPARATOR_HORIZONTAL, WIDTH_DATE);
        sb.append(SEPARATOR_CROSS);
        writeRepeat(sb, SEPARATOR_HORIZONTAL, WIDTH_DATE);
        sb.append(SEPARATOR_CROSS);
        sb.append(NEWLINE);
    }
    
    private void writeTask(StringBuilder sb, Task t, int id) {
        sb.append(SEPARATOR_VERTICAL);
        writeID(sb, id);
        sb.append(SEPARATOR_VERTICAL);
        writeTaskName(sb, t.getName());
        sb.append(SEPARATOR_VERTICAL);
        writeDate(sb, t.getStart());
        sb.append(SEPARATOR_VERTICAL);
        writeDate(sb, t.getEnd());
        sb.append(SEPARATOR_VERTICAL);
        sb.append(NEWLINE);
    }
    
    private void writeCentered(StringBuilder sb, String header, int width) {
        assert(header.length() < width);
        int start = (width - header.length()) / 2;
        writeRepeat(sb, SEPARATOR_BLANK, start);
        sb.append(header);
        writeRepeat(sb, SEPARATOR_BLANK, width - start- header.length());
    }
    
    private void writeID(StringBuilder sb, int id) {
        assert(id >= 0 && id <= 9999);
        // TODO: Assumed width to be 4
        if(id < 10) {
            sb.append("  " + id + " ");
        } else if(id < 100) {
            sb.append(" " + id + " ");
        } else if(id < 1000) {
            sb.append(id);
        }
    }
    
    private void writeTaskName(StringBuilder sb, String taskName) {
        assert(taskName != null && taskName.length() > 0);
        if(taskName.length() < WIDTH_NAME) {
            sb.append(taskName);
            writeRepeat(sb, SEPARATOR_BLANK, WIDTH_NAME - taskName.length());
        } else {
            sb.append(taskName.substring(0, WIDTH_NAME - 3) + "...");
        }
    }
    
    private void writeDate(StringBuilder sb, Date date) {
        if(date == null) {
            writeCentered(sb, "---", WIDTH_DATE);
        } else {
            writeCentered(sb, FORMAT_DATE.format(date), WIDTH_DATE);
        }
    }
    
    private void writeRepeat(StringBuilder sb, char c, int n) {
        for(int i = 0; i < n; i++) {
            sb.append(c);
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
