package sg.edu.cs2103aug2015_w13_2j.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javafx.scene.Parent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.DefaultCaret;

import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;

// @@author A0121410H

public class TextUI extends JFrame implements UIInterface, KeyListener {
    private static final long serialVersionUID = 7758912303888211773L;
    private static final Font FONT = new Font("consolas", Font.BOLD, 16);
    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    private static TextUI sInstance;

    private LogicInterface mLogic;
    private JTextField mTextField;
    private TextPane mTextPane;
    private JLabel mFilterLabel;
    private JLabel mFeedbackLabel;
    private ArrayList<Task> mTasks;

    /**
     * Protected constructor
     */
    protected TextUI() {

    }

    /**
     * Retrieves the singleton instance of the TextUI component
     * 
     * @param logic
     *            Dependency injection of the Logic component depended upon by
     *            the TextUI component
     * @return TextUI component singleton instance
     */
    public static TextUI getInstance() {
        if (sInstance == null) {
            sInstance = new TextUI();
        }
        return sInstance;
    }

    public void injectDependency(LogicInterface logic) {
        mLogic = logic;
    }

    public void display(ArrayList<Task> tasks) {
        mTasks = tasks;
        mTextPane.display(tasks);
    }

    public void display(String s) {
        mTextPane.clear();
        mTextPane.print(s);
    }

    public Task getTask(int index) throws TaskNotFoundException {
        try {
            return mTasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }

    public void feedback(FeedbackMessage m) {
        mFeedbackLabel.setForeground(m.getType().getAWTColor());
        mFeedbackLabel.setText(m.getMessage());
    }

    public void setFilter(String s) {
        mFilterLabel.setText(s);
    }

    public void keyTyped(KeyEvent e) {
        // Empty function
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            mLogic.executeCommand(mTextField.getText());
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
     * Initializes and shows the UI Elements. The UI consists a JLabel which
     * displays the currently active filter chain, a JTextPane which displays
     * the tasks to the user, another JLabel which displays text feedback from
     * executing user commands, and a JTextField from which to accept user input
     */
    public Parent getUI() {
        // Create and set up window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        // Will be reused for each UI element
        GridBagConstraints constraints = null;

        mFilterLabel = new JLabel();
        mFilterLabel.setFont(FONT);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        contentPane.add(mFilterLabel, constraints);

        mTextPane = new TextPane();
        DefaultCaret caret = (DefaultCaret) mTextPane.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        mTextPane.setPreferredSize(PREFERRED_SIZE);
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
        constraints.gridy = 1;
        contentPane.add(scrollPane, constraints);

        mFeedbackLabel = new JLabel();
        mFeedbackLabel.setFont(FONT);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        contentPane.add(mFeedbackLabel, constraints);

        mTextField = new JTextField();
        mTextField.addKeyListener(this);
        mTextField.setFont(FONT);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 3;
        contentPane.add(mTextField, constraints);

        // Display the window
        this.setLocation(100, 100);
        this.pack();
        this.setVisible(true);
        mTextField.requestFocusInWindow();

        return null;
    }

    @Override
    public void pushFilter(Filter filter) {
        // TODO Auto-generated method stub

    }

    @Override
    public Filter popFilter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFeedBackMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean showChangeDataFilePathDialog() {
        // TODO Auto-generated method stub
        return false;
    }
}
