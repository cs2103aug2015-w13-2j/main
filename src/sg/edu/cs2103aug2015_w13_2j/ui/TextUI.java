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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.Task;

//@@author A0121410H

public class TextUI extends JFrame implements TextUIInterface, KeyListener {
    private static final long serialVersionUID = 7758912303888211773L;
    private static final Logger LOGGER = Logger.getLogger(TextUI.class
            .getName());

    // Font constant used by all UI elements
    private static final Font FONT = new Font("consolas", Font.BOLD, 16);

    // The (fixed) size of the window
    private static final Dimension PREFERRED_SIZE = new Dimension(796, 600);

    // Self instance
    private static TextUI sInstance;

    // UI Components
    private JTextField mTextField;
    private TextPane mTextPane;
    private JLabel mFilterLabel;
    private JLabel mFeedbackLabel;

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

    public void setFilter(String s) {
        mFilterLabel.setText(s);
    }
    
    public void display(ArrayList<Task> tasks) {
        mTextPane.display(tasks);
    }

    public void feedback(FeedbackMessage m) {
        mFeedbackLabel.setForeground(m.getType().getColor());
        mFeedbackLabel.setText(m.getMessage());
    }

    public void keyTyped(KeyEvent e) {
        // Empty function
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            LOGGER.log(Level.INFO, "Command: " + mTextField.getText());
            Logic.getInstance().executeCommand(mTextField.getText());
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
     * Initializes and shows the UI Elements. The UI consists a JTextPane which
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

        mFilterLabel = new JLabel();
        mFilterLabel.setFont(FONT);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        contentPane.add(mFilterLabel, constraints);
        
        mTextPane = new TextPane();
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
    }
}
