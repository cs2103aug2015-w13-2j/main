# A0121410Hunused
###### src\sg\edu\cs2103aug2015_w13_2j\filters\SortFilter.java
``` java
// Reason: Tasks are returned sorted by default. Task object also implements
// Comparable.

public class SortFilter extends Filter {

    private static final Comparator<Task> END_ASC = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            Date d1 = t1.getEnd();
            Date d2 = t2.getEnd();
            if (d1 == null) {
                return -1;
            } else if (d2 == null) {
                return 1;
            } else {
                return d1.compareTo(d2);
            }
        }
    };
    private static final Comparator<Task> END_DSC = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            Date d1 = t1.getEnd();
            Date d2 = t2.getEnd();
            if (d1 == null) {
                return 1;
            } else if (d2 == null) {
                return -1;
            } else {
                return -d1.compareTo(d2);
            }
        }
    };

    private Comparator<Task> mComparator;

    public class InvalidSortFilterException extends Exception {
        private static final long serialVersionUID = -2791883884133587047L;
    }

    public SortFilter(String sortBy) throws InvalidSortFilterException {
        switch (sortBy) {
          case "end_asc" :
            mComparator = END_ASC;
            break;
          case "end_dsc" :
            mComparator = END_DSC;
            break;
          default :
            throw new InvalidSortFilterException();
        }
        mName = "sort:" + sortBy;
    }

    @Override
    public void applyFilter(ArrayList<Task> tasks) {
        mTasks = new ArrayList<Task>(tasks);
        Collections.sort(mTasks, mComparator);
    }
}
```
###### src\sg\edu\cs2103aug2015_w13_2j\FunDUE.java
``` java
// Reason: Old AWT-based UI initialization procedure, replaced with JavaFX
// version

public class FunDUE {
    private UIInterface mTextUI;
    private StorageInterface mStorage = Storage.getInstance();
    private LogicInterface mLogic = Logic.getInstance();

    /**
     * Initialization of components. Components are <b>not</b> allowed to
     * reference other components during initialization. Additional
     * initialization should be called from the {@link #run()} method instead
     */
    public FunDUE() {
        mLogic.registerCommandHandler(new AddHandler());
        mLogic.registerCommandHandler(new EditHandler());
        mLogic.registerCommandHandler(new DeleteHandler());
        mLogic.registerCommandHandler(new MarkImportantHandler());
        mLogic.registerCommandHandler(new MarkCompletedHandler());
        mLogic.registerCommandHandler(new UndoHandler());
        mLogic.registerCommandHandler(new RedoHandler());
        mLogic.registerCommandHandler(new FilterHandler());
        mLogic.registerCommandHandler(new PopHandler());
        mLogic.registerCommandHandler(new HelpHandler());
        mLogic.registerCommandHandler(new LoadHandler());
        mLogic.registerCommandHandler(new SearchHandler());
    }

    public void run() {
        mTextUI = TextUI.getInstance();
        mTextUI.injectDependency(mLogic);
        mLogic.injectDependencies(mStorage, mTextUI);
        mLogic.readTasks();
        mLogic.display();
    }

    public static void main(String[] args) {
        new FunDUE().run();
    }
}
```
###### src\sg\edu\cs2103aug2015_w13_2j\FunDueDemo.java
``` java
// Reason: Used exclusively for our CS2101 OP2 Software Demonstration.
// Additional banner atop the application UI to show our banner slides.

public class FunDueDemo extends Application implements EventHandler<KeyEvent> {
    private static final Logger LOGGER = Logger
            .getLogger(FunDueDemo.class.getName());
    private static final String SLIDE_PATH = "file:resources/demo/Slide%d.JPG";
    private static final int SLIDE_START = 1;
    private static final int SLIDE_END = 9;

    private final UIInterface mUI = FXUI.getInstance();
    private final LogicInterface mLogic = Logic.getInstance();
    private final StorageInterface mStorage = Storage.getInstance();
    private final ImageView mImageView = new ImageView();
    private final Parent mFunDue = mUI.getUI();

    private int mSlide = SLIDE_START;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mLogic.registerCommandHandler(new AddHandler());
        mLogic.registerCommandHandler(new EditHandler());
        mLogic.registerCommandHandler(new DeleteHandler());
        mLogic.registerCommandHandler(new MarkImportantHandler());
        mLogic.registerCommandHandler(new MarkCompletedHandler());
        mLogic.registerCommandHandler(new UndoHandler());
        mLogic.registerCommandHandler(new RedoHandler());
        mLogic.registerCommandHandler(new FilterHandler());
        mLogic.registerCommandHandler(new PopHandler());
        mLogic.registerCommandHandler(new HelpHandler());
        mLogic.registerCommandHandler(new LoadHandler());
        mLogic.registerCommandHandler(new SearchHandler());

        mImageView.setPreserveRatio(true);

        VBox container = new VBox(mImageView, mFunDue);
        VBox.setVgrow(mFunDue, Priority.ALWAYS);

        Scene scene = new Scene(container, 800, 800);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this);
        mImageView.fitWidthProperty().bind(scene.widthProperty());

        loadSlide();

        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(800);
        primaryStage.getIcons().add(new Image("file:FunDUE Logo.png"));
        primaryStage.setTitle("FunDUE Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        mUI.injectDependency(mLogic);
        mLogic.injectDependencies(mStorage, mUI);
        mLogic.readTasks();
        mLogic.display();
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
          case UP :
            LOGGER.log(Level.INFO, "UP " + mSlide);
            if (mSlide > SLIDE_START) {
                mSlide--;
                loadSlide();
            }
            break;
          case DOWN :
            LOGGER.log(Level.INFO, "DOWN" + mSlide);
            if (mSlide < SLIDE_END) {
                mSlide++;
                loadSlide();
            }
            break;
          default :
            // Do nothing
        }
    }

    private void loadSlide() {
        if (mSlide >= SLIDE_START && mSlide <= SLIDE_END) {
            String slidePath = String.format(SLIDE_PATH, mSlide);
            LOGGER.log(Level.INFO, "Loading slide: " + slidePath);
            mImageView.setImage(new Image(slidePath));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```
###### src\sg\edu\cs2103aug2015_w13_2j\ui\TextPane.java
``` java
// Reason: Original text-based UI sub-component

/**
 * This class extends the JTextPane swing component with methods specific to
 * displaying and formatting Task objects and other views such as a help page
 * 
 * @author Zhu Chunqi
 * @see JTextPane
 */
public class TextPane extends JTextPane {
    private static final long serialVersionUID = -2906035118426407209L;

    private static final int WIDTH_ID = 4;
    private static final int WIDTH_NAME = 47;
    private static final int WIDTH_DATE = 16;
    private static final int WIDTH_TOTAL = WIDTH_ID + 1 + WIDTH_NAME + 1
            + WIDTH_DATE + 1 + WIDTH_DATE;

    public static final String NEWLINE = System.getProperty("line.separator");
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

    private static final Color COLOR_NORMAL = Color.BLACK;
    private static final Color COLOR_HIGHLIGHT = Color.RED;

    private static final AttributeSet STYLE_DEFAULT = getStyle(COLOR_NORMAL);
    private static final AttributeSet STYLE_HIGHLIGHT = getStyle(
            COLOR_HIGHLIGHT);

    /**
     * SimpleDateFormatter object which formats date objects using
     * <code>dd/MM/yy'T'HH:mm</code> format
     */
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(
            "dd/MM/yy'T'HH:mm");

    public TextPane() {
        super();
    }

    /**
     * Displays the list of Task objects in the document with the appropriate
     * styling
     * 
     * @param tasks
     *            The list of Task objects to be displayed
     */
    public void display(ArrayList<Task> tasks) {
        clear();
        writeSeparator();
        writeHeader();
        writeSeparator();
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                try {
                    if (tasks.get(i).isValid()) {
                        writeTask(tasks.get(i), i);
                    }
                } catch (InvalidTaskException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            print(SEPARATOR_VERTICAL);
            writeCentered(HEADER_NO_TASKS, WIDTH_TOTAL);
            print(SEPARATOR_VERTICAL);
            print(NEWLINE);
        }
        writeSeparator();
    }

    /**
     * Writes the headers of the table with vertical separators between columns
     * as shown compacted below<br>
     * <code>| ID | TASK NAME | START | END |</code>
     */
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

    /**
     * Writes a separator row with crosses at each column end point to the
     * document as shown compacted below<br>
     * <code>+----+-----------+-------+-----+</code>
     */
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
        print(SEPARATOR_VERTICAL);
        writeID(id);
        print(SEPARATOR_VERTICAL);
        print(SEPARATOR_BLANK);
        writeStatus(t);
        print(SEPARATOR_BLANK);
        writeTruncate(t.getName(), WIDTH_NAME - 5);
        print(SEPARATOR_VERTICAL);
        writeDates(t);
        print(SEPARATOR_VERTICAL);
        print(NEWLINE);
    }

    private void writeStatus(Task t) {
        if (t.isCompleted()) {
            print("[D]");
        } else if (t.isImportant()) {
            print("[!]", STYLE_HIGHLIGHT);
        } else {
            print("---");
        }
    }

    /**
     * Writes the provided string in a centered position within the provided
     * width, with the rest of the space padded by blanks
     * 
     * @param s
     *            The string to be written
     * @param width
     *            The width in which to center the string
     */
    private void writeCentered(String s, int width) {
        assert (s.length() < width);
        int start = (width - s.length()) / 2;
        writeRepeat(SEPARATOR_BLANK, start);
        print(s);
        writeRepeat(SEPARATOR_BLANK, width - start - s.length());
    }

    /**
     * Writes the index of the Task object. Internally calls writeCentered with
     * the string value of the index and a width of 4
     * 
     * @param id
     *            The index of the Task object
     */
    private void writeID(int id) {
        assert (id >= 0 && id <= 9999);
        writeCentered(String.valueOf(id), WIDTH_ID);
    }

    /**
     * Writes the string provided with provisions for truncating excess over
     * width and appending with ellipsis
     * 
     * @param s
     *            The string to be written
     * @param width
     *            The width in which to constrain the string
     */
    private void writeTruncate(String s, int width) {
        assert (s != null && s.length() > 0);
        assert (width > 3);
        if (s.length() < width) {
            print(s);
            writeRepeat(SEPARATOR_BLANK, width - s.length());
        } else {
            print(s.substring(0, width - 3) + "...");
        }
    }

    /**
     * Writes the date provided using the {@link TextPane#FORMAT_DATE} constant
     * or a centered <code>---</code> if the date is null
     * 
     * @param date
     *            The date to be written
     */
    private void writeDates(Task t) {
        Date start = t.getStart();
        Date end = t.getEnd();
        if (start == null) {
            writeCentered("---", WIDTH_DATE);
        } else {
            writeCentered(FORMAT_DATE.format(start), WIDTH_DATE);
        }
        print(SEPARATOR_VERTICAL);
        if (end == null) {
            writeCentered("---", WIDTH_DATE);
        } else {
            print(SEPARATOR_BLANK);
            if (t.isOverdue()) {
                print(FORMAT_DATE.format(end), STYLE_HIGHLIGHT);
            } else {
                print(FORMAT_DATE.format(end));
            }
            print(SEPARATOR_BLANK);
        }
    }

    /**
     * Utility method to repeatedly write the provided string for the specified
     * number of times
     * 
     * @param s
     *            The string to be repeated
     * @param n
     *            The number of times to repeat
     */
    private void writeRepeat(String s, int n) {
        for (int i = 0; i < n; i++) {
            print(s);
        }
    }

    public void print(String s) {
        print(s, STYLE_DEFAULT);
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
    private void print(String s, AttributeSet a) {
        StyledDocument document = getStyledDocument();
        try {
            document.insertString(document.getLength(), s, a);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the text pane by removing all content within the document
     */
    public void clear() {
        StyledDocument document = getStyledDocument();
        try {
            document.remove(0, document.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private static AttributeSet getStyle(Color c) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        return styleContext.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Background, c);
    }
}
```
###### src\sg\edu\cs2103aug2015_w13_2j\ui\TextUI.java
``` java
// Reason: Original text-based UI

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
          case KeyEvent.VK_ENTER :
            mLogic.executeCommand(mTextField.getText());
            mTextField.setText(null);
            break;
          case KeyEvent.VK_UP :
            // TODO: Pass to parser
            break;
          case KeyEvent.VK_DOWN :
            // TODO: Pass to parser
            break;
          case KeyEvent.VK_ESCAPE :
            System.exit(0);
            break;
          default :
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
    public boolean showChangeDataFilePathDialog() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean showHelpPage() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getFeedbackMessageString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void focusCommandBar() {
        // TODO Auto-generated method stub

    }
}
```
