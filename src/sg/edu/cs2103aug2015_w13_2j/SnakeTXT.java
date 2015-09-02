package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * Text-based snake game that runs within a JTextPane context and using
 * JTextField as the input target.
 * @author Zhu Chunqi
 */

public class SnakeTXT implements ActionListener, KeyListener {
    private enum DIRECTION {
        UP, DOWN, LEFT, RIGHT;
    }
    
    private enum BOARD_PIECE {
        BLANK, SNAKE, FOOD
    }
    
    private static final String NEWLINE = System.getProperty("line.separator");
    
    // Dimensions of the playing field. INCLUDES 1 thick borders
    private static final int BOARD_X = 10;
    private static final int BOARD_Y = 25;
    
    // Game settings. Change time delay (ms) for differing difficulty
    private static final int TIME_DELAY = 250;
    private static final int SCORE_PER_FOOD = 10;
    
    // Text headers and messages
    private static final String HEADER_COPYRIGHT = "SnakeTXT ©2015 Zhu Chunqi";
    private static final String HEADER_CONTROLS = "Controls: ↑→↓← Q";
    private static final String HEADER_SCORE = "Score: %1$d\tTime: %2$d";
    private static final String MESSAGE_GAME_OVER = "GAME OVER\nScore: %1$d\n";
    
    // Symbols on the board
    private static final String SYMBOL_SNAKE = "@";
    private static final String SYMBOL_BLANK = " ";
    private static final String SYMBOL_FOOD = "*";
    private static final String SYMBOL_BOARD_SIDES = "|";
    private static final String SYMBOL_BOARD_TOPDOWN = "=";
    
    // Game state
    private DIRECTION mDirection = DIRECTION.RIGHT;
    private BOARD_PIECE[][] mBoard = new BOARD_PIECE[BOARD_X][BOARD_Y];
    private LinkedList<Point> mSnakePositions;
    private Point mFoodPosition;
    private String mBuffer;
    private Timer mTimer;
    private boolean mIsFirstPaint = true;
    private int mDocumentLength = 0;
    private int mBufferSize;
    private int mScore = 0;
    private int mTime = 0;
    
    // References to parent Swing components
    private JTextPane mTextPane;
    private JTextField mTextField;
    private Font mOldFont;
    
    // RNG for food placement
    private Random mRandom = new Random();
    
    public SnakeTXT(JTextPane textPane, JTextField textField) {
        // Store the references to the text pane and text field
        mTextPane = textPane;
        mTextField = textField;
        mOldFont = mTextPane.getFont();
        mTextPane.setFont(new Font("monospaced", Font.PLAIN, 12));
        
        // Register SnakeTXT as key listener for text field input
        mTextField.addKeyListener(this);
        
        // Clear the board and mark initial snake locations
        clearBoard();
        mSnakePositions = new LinkedList<Point>();
        mSnakePositions.add(new Point(5, 5));
        mSnakePositions.add(new Point(5, 6));
        mSnakePositions.add(new Point(5, 7));
        
        // Start the timed interval methods
        mTimer = new javax.swing.Timer(TIME_DELAY, this);
        mTimer.start();
    }
    
    /**
     * Marks the location of the food on the board. If no food location was set,
     * it will generate a new random food location that is not overlapping any
     * snake positions
     */
    private void drawFood() {
        if(mFoodPosition == null) {
            do {
                int posX = mRandom.nextInt(BOARD_X - 2) + 1;
                int posY = mRandom.nextInt(BOARD_Y - 2) + 1;
                mFoodPosition = new Point(posX, posY);
            } while (mSnakePositions.contains(mFoodPosition));
        }
        
        mBoard[mFoodPosition.x][mFoodPosition.y] = BOARD_PIECE.FOOD;
    }
    
    /**
     * Marks the location of snake parts on the board
     */
    private void drawSnake() {
        Iterator<Point> it = mSnakePositions.iterator();
        
        while (it.hasNext()) {
            Point point = it.next();
            mBoard[point.x][point.y] = BOARD_PIECE.SNAKE;
        }
    }
    
    /**
     * Clears the board of any board pieces
     */
    private void clearBoard() {
        for (int x = 0; x < BOARD_X; x++) {
            Arrays.fill(mBoard[x], BOARD_PIECE.BLANK);
        }
    }
    
    /**
     * Renders the board into a string buffer and updates the contents of the
     * text pane with the result
     */
    private void paint() {
        clearOutput();
        // Output the headers
        outputln(HEADER_COPYRIGHT);
        outputln(HEADER_CONTROLS);
        outputln(String.format(HEADER_SCORE, mScore,
                mTime / (1000 / TIME_DELAY)));
        
        // Draw the board with reference to the internal representation
        for (int x = 0; x < BOARD_X; x++) {
            for (int y = 0; y < BOARD_Y; y++) {
                if (x == 0 || x == BOARD_X - 1) {
                    output(SYMBOL_BOARD_TOPDOWN);
                } else if (y == 0 || y == BOARD_Y - 1) {
                    output(SYMBOL_BOARD_SIDES);
                } else if (mBoard[x][y] == BOARD_PIECE.SNAKE) {
                    output(SYMBOL_SNAKE);
                } else if (mBoard[x][y] == BOARD_PIECE.FOOD) {
                    output(SYMBOL_FOOD);
                } else {
                    output(SYMBOL_BLANK);
                }
            }
            output(NEWLINE);
        }
        
        // Update the text pane with the resulting string buffer
        StyledDocument document = mTextPane.getStyledDocument();
        try {
            if(mIsFirstPaint) {
                mIsFirstPaint = false;
                mDocumentLength = document.getLength();
                mBufferSize = mBuffer.length();
            } else {
                document.remove(mDocumentLength, mBufferSize);
            }
            document.insertString(mDocumentLength, mBuffer, null);
            mBufferSize = mBuffer.length();
        } catch (BadLocationException ble) {
            ble.printStackTrace();
            System.exit(1);
        }
    }
    
    private void gameOver() {
        // Restore old settings and prepare to exit
        mTimer.stop();
        mTextPane.setFont(mOldFont);
        mTextField.removeKeyListener(this);
        
        // Print game over message
        StyledDocument document = mTextPane.getStyledDocument();
        try {
            document.insertString(document.getLength(),
                    String.format(MESSAGE_GAME_OVER, mScore), null);
        } catch (BadLocationException ble) {
            ble.printStackTrace();
            System.exit(1);
        }
    }
    
    private void clearOutput() {
        mBuffer = new String();
    }
    
    private void output(String output) {
        mBuffer += output;
    }
    
    private void outputln(String output) {
        mBuffer += output + NEWLINE;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Increment game time interval step
        mTime++;
        
        // Update snake head location based on direction
        Point headPos = new Point(mSnakePositions.getLast());
        switch(mDirection) {
          case UP :
              headPos.x--;
              break;
          case DOWN :
              headPos.x++;
              break;
          case LEFT :
              headPos.y--;
              break;
          case RIGHT :
              headPos.y++;
              break;
        }
        
        // Check if snake head is out of bounds or colliding with body
        if(headPos.x <= 0 || headPos.x >= BOARD_X - 1
                || headPos.y <= 0 || headPos.y >= BOARD_Y - 1
                || mSnakePositions.contains(headPos)) {
            gameOver();
        }
        
        // Add a 'new' snake head at the new location
        mSnakePositions.add(headPos);
        if(headPos.equals(mFoodPosition)) {
            mScore += SCORE_PER_FOOD;
            mFoodPosition = null;
        } else {
            // Snake tail is removed if no food was eaten
            mSnakePositions.remove();
        }
        
        // Render cycle
        clearBoard();
        drawSnake();
        drawFood();
        paint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Empty function
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println("Key pressed: " + e.getKeyCode());
        switch(e.getKeyCode()) {
          case KeyEvent.VK_UP :
            if (mDirection != DIRECTION.DOWN) {
                mDirection = DIRECTION.UP;
            }
            break;
          case KeyEvent.VK_DOWN :
            if (mDirection != DIRECTION.UP) {
                mDirection = DIRECTION.DOWN;
            }
            break;
          case KeyEvent.VK_LEFT :
            if (mDirection != DIRECTION.RIGHT) {
                mDirection = DIRECTION.LEFT;
            }
            break;
          case KeyEvent.VK_RIGHT :
            if (mDirection != DIRECTION.LEFT) {
                mDirection = DIRECTION.RIGHT;
            }
            break;
          case KeyEvent.VK_Q :
              gameOver();
          default :
              break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Empty function
    }
}
