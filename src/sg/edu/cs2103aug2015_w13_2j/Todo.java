package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class Todo extends JFrame implements KeyListener {
    // Recommended for serializable classes
    private static final long serialVersionUID = 7758912303888211773L;

    // For portability across Unix and Windows systems
    private static final String NEWLINE = System.getProperty("line.separator");
    
    // The (fixed) size of the window
    private static final Dimension PREFERRED_SIZE = new Dimension(500, 600);
    
    // Components that need to be accessed and updated
    private static JTextField mTextField;
    private static JTextPane mTextPane;
    
    /**
     * Creates the GUI window and adds all the GUI components on initialization
     */
    public Todo() {
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
        
        JButton button = new JButton("Test");
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 2;
        contentPane.add(button, constraints);
        
        // Display the window
        this.setLocation(100, 100);
        this.pack();
        this.setVisible(true);
        mTextField.requestFocusInWindow();
    }
    
    public static void main(String[] args) {
        Todo todo = new Todo();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Empty function
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
          case KeyEvent.VK_ENTER :
            StyledDocument document = mTextPane.getStyledDocument();
            String input = mTextField.getText() + NEWLINE;
            mTextField.setText(null);
            try {
                document.insertString(document.getLength(), input, null);
                mTextPane.setCaretPosition(document.getLength());
            } catch (BadLocationException ble) {
                System.exit(1);
            }
            break;
          default :
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Empty function
    }
}
