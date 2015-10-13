package sg.edu.cs2103aug2015_w13_2j;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.Timer;

//@author A0121410H
/**
 * Text-based snake game that runs within a JTextPane context and using
 * JTextField as the input target.
 * 
 * @author Zhu Chunqi
 */

public class SnakeTXT implements ActionListener, KeyListener {
    private enum DIRECTION {
        UP, DOWN, LEFT, RIGHT;
    }

    private enum BOARD_PIECE {
        BLANK, SNAKE, FOOD
    }

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
    private StringBuilder mBuffer;
    private Timer mTimer;
    private int mScore = 0;
    private int mTime = 0;

    // RNG for food placement
    private Random mRandom = new Random();
    
    private TextUI mTextUI;

    public SnakeTXT(TextUI textUI) {
        mTextUI = textUI;
        
        // Register as the KeyListener for text field
        mTextUI.addTextFieldKeyListener(this);

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
        if (mFoodPosition == null) {
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
        outputln(String.format(HEADER_SCORE, mScore, mTime
                / (1000 / TIME_DELAY)));

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
            output(TextUI.NEWLINE);
        }

        // Update the text pane with the resulting string buffer
        mTextUI.printr(mBuffer.toString());
    }

    private void gameOver() {
        // Deregister as KeyListener and prepare to exit
        mTextUI.removeTextFieldKeyListener(this);
        mTimer.stop();

        // Print game over message
        mTextUI.print(String.format(MESSAGE_GAME_OVER, mScore));
    }

    private void clearOutput() {
        mBuffer = new StringBuilder();
    }

    private void output(String output) {
        mBuffer.append(output);
    }

    private void outputln(String output) {
        mBuffer.append(output);
        mBuffer.append(TextUI.NEWLINE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Increment game time interval step
        mTime++;

        // Update snake head location based on direction
        Point headPos = new Point(mSnakePositions.getLast());
        switch (mDirection) {
        case UP:
            headPos.x--;
            break;
        case DOWN:
            headPos.x++;
            break;
        case LEFT:
            headPos.y--;
            break;
        case RIGHT:
            headPos.y++;
            break;
        }

        // Check if snake head is out of bounds or colliding with body
        if (headPos.x <= 0 || headPos.x >= BOARD_X - 1 || headPos.y <= 0
                || headPos.y >= BOARD_Y - 1
                || mSnakePositions.contains(headPos)) {
            gameOver();
            return;
        }

        // Add a 'new' snake head at the new location
        mSnakePositions.add(headPos);
        if (headPos.equals(mFoodPosition)) {
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
        // System.out.println("Key pressed: " + e.getKeyCode());
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
            if (mDirection != DIRECTION.DOWN) {
                mDirection = DIRECTION.UP;
            }
            break;
        case KeyEvent.VK_DOWN:
            if (mDirection != DIRECTION.UP) {
                mDirection = DIRECTION.DOWN;
            }
            break;
        case KeyEvent.VK_LEFT:
            if (mDirection != DIRECTION.RIGHT) {
                mDirection = DIRECTION.LEFT;
            }
            break;
        case KeyEvent.VK_RIGHT:
            if (mDirection != DIRECTION.LEFT) {
                mDirection = DIRECTION.RIGHT;
            }
            break;
        case KeyEvent.VK_Q:
            gameOver();
        default:
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Empty function
    }
}
