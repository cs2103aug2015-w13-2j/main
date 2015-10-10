package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

//@@author A0121410H
public class TextUI extends JFrame implements TextUIInterface, KeyListener {
	public enum Message {
		CLEAR("", FeedbackType.INFO),
		
		// Errors
		ERROR_INVALID_TASK("Invalid task. Did you remember to include quotes around the task name?", FeedbackType.ERROR),
		ERROR_TASK_NOT_FOUND("Task not found. Did you enter the index correctly?", FeedbackType.ERROR),
		ERROR_COMMAND_NOT_IMPLEMENTED("The command that you have entered has not been implemented.", FeedbackType.ERROR),
		ERROR_COMMAND_NOT_RECOGNIZED("Command not recognized.", FeedbackType.ERROR),
		
		// Infomative
		LOGIC_ADDED("Task added successfully.", FeedbackType.INFO),
		LOGIC_EDITED("Task edited successfully.", FeedbackType.INFO),
		LOGIC_DELETED("Task deleted successfully.", FeedbackType.INFO);

		private String mMsg;
		private FeedbackType mType;

		private Message(String s, FeedbackType type) {
			mMsg = s;
			mType = type;
		}

		public String getMessage() {
			return mMsg;
		}

		public FeedbackType getType() {
			return mType;
		}
	}

	public enum FeedbackType {
		INFO(Color.BLACK), ERROR(Color.RED);

		private Color mColor;

		private FeedbackType(Color color) {
			mColor = color;
		}

		public Color getColor() {
			return mColor;
		}
	}

	// For portability across Unix and Windows systems
	public static final String NEWLINE = System.getProperty("line.separator");

	private static final Font FONT = new Font("consolas", Font.BOLD, 16);

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
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tasks.size(); i++) {
			sb.append(i);
			sb.append(": ");
			sb.append(tasks.get(i).getName());
			sb.append('\n');
		}
		printr(sb.toString());
	}

	public void feedback(Message m) {
		mLabel.setForeground(m.getType().getColor());
		mLabel.setText(m.getMessage());
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

	/**
	 * Prints the string to the text pane
	 * 
	 * @param s
	 *            The string to be printed
	 */
	private void print(String s) {
		print(s, 0);
	}

	/**
	 * Prints the string to replace the previously printed string
	 * 
	 * @param s
	 *            The string to be printed in replacement of previous string
	 */
	private void printr(String s) {
		print(s, mPrevLen);
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
}
