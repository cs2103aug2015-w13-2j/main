package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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

	private FunDUE mAppInstance;
	private JTextField mTextField;
	private JTextPane mTextPane;
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
	
	public void display(ArrayList<Task> tasks) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < tasks.size(); i++) {
			sb.append(i);
			sb.append(": ");
			sb.append(tasks.get(i).getName());
			sb.append('\n');
		}
		printr(sb.toString());
	}

	public void keyTyped(KeyEvent e) {
		// Empty function
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ENTER:
			// Hijack the snake command
			if (mTextField.getText().equalsIgnoreCase("snake")) {
				new SnakeTXT();
			} else {
				System.out.println("[TextUI] Command: " + mTextField.getText());
				mAppInstance.getParserInstance().parseAndExecuteCommand(mTextField.getText());
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

	public void addTextFieldKeyListener(KeyListener listener) {
		mTextField.addKeyListener(listener);
	}

	public void removeTextFieldKeyListener(KeyListener listener) {
		mTextField.removeKeyListener(listener);
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
}
