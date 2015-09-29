package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

//@@author A0121410H
public class TextUI extends JFrame implements TextUIInterface, KeyListener {
    // For portability across Unix and Windows systems
    public static final String NEWLINE = System.getProperty("line.separator");
    
    // Serialization ID
    private static final long serialVersionUID = 7758912303888211773L;

    // The (fixed) size of the window
    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    // UI components that need to be accessed and updated
    private static JTextField mTextField;
    private static JTextPane mTextPane;

    private int mPrevLen = 0;

    public TextUI() {
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
        mTextPane.getCaret().setVisible(true);
        mTextPane.setFont(new Font("monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(mTextPane);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        constraints.weightx = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        contentPane.add(scrollPane, constraints);

        mTextField = new JTextField();
        mTextField.addKeyListener(this);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        contentPane.add(mTextField, constraints);

        // Display the window
        this.setLocation(100, 100);
        this.pack();
        this.setVisible(true);
        mTextField.requestFocusInWindow();
    }

    public void print(String s) {
        print(s, 0);
    }

    public void printr(String s) {
        print(s, mPrevLen);
    }

    public void println(String s) {
        print(s + NEWLINE);
    }

    public void printlnr(String s) {
        printr(s + NEWLINE);
    }

    /**
     * Internally used method to print the string to the text pane at the
     * specified position from the end of the document. If provided offset
     * is not 0, the method will first remove string of length offset from
     * the end of document and then prints the provided string
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
    
    public void addTextFieldKeyListener(KeyListener listener) {
        mTextField.addKeyListener(listener);
    }
    
    public void removeTextFieldKeyListener(KeyListener listener) {
        mTextField.removeKeyListener(listener);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Empty function
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            // Hijack the snake command
            if(mTextField.getText().equalsIgnoreCase("snake")) {
                new SnakeTXT();
            } else {
                FunDUE.sParser.parseCommand(mTextField.getText() + NEWLINE);
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

    @Override
    public void keyReleased(KeyEvent e) {
        // Empty function
    }
}
