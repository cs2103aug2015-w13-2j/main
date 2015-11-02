package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import org.ocpsoft.prettytime.PrettyTime;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.InvalidTaskException;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.filters.FilterChain;

// @@author A0121410H

public class GUI implements UIInterface {
    public static final PrettyTime PRETTY_TIME = new PrettyTime();
    private WebView browser;
    private WebEngine webEngine;
    
    private static GUI sInstance;
    
    private final FilterChain mFilterChain;
    
    private ArrayList<Task> mTasks;
    private LogicInterface mLogic;
    
    /**
     * Private constructor
     */
    private GUI() {
        mTasks = new ArrayList<Task>();
        mFilterChain = new FilterChain();
    }
    
    /**
     * Retrieves the singleton instance of this {@link UIInterface} component
     * 
     * @return {@link UIInterface} component singleton instance
     */
    public static GUI getInstance() {
        if (sInstance == null) {
            sInstance = new GUI();
        }
        return sInstance;
    }
    
    @Override
    public void injectDependency(LogicInterface logic) {
        mLogic = logic;
    }
    
    // @@author A0124007X
    
    @Override
    public void display(ArrayList<Task> tasks) {
        // TODO
        // This is super primitive at the moment
        
        mTasks = tasks;
        String s = "";
        
        if (tasks.size() > 0) {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                
                try {
                    if(task.isValid()){
                        s += (i+1) + " " + task.getName() + "<br>";
                    }
                } catch (InvalidTaskException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            display(s);
        } else {
            display("No tasks!");
        }
    }
    
    @Override
    public void display(String s) {
        // TODO *BUG* something wrong here
        //setContent("view", s);
        System.out.println(s);
    }
    
    // @@author A0121410H
    
    @Override
    public Task getTask(int index) throws TaskNotFoundException {
        try {
            // NOTE: list is zero indexed whereas display is 1 indexed
            return mTasks.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new TaskNotFoundException();
        }
    }
    
    @Override
    public void feedback(FeedbackMessage f) {
        setContent("feedbackContainer", f.getMessage());
    }
    
    @Override
    public void pushFilter(Filter filter) {
        mFilterChain.pushFilter(filter);
    }
    
    @Override
    public Filter popFilter() {
        return mFilterChain.popFilter();
    }
    
    @Override
    public void updateFilters(ArrayList<Task> tasks) {
        mFilterChain.updateFilters(tasks);
    }
    
    // @@author A0124007X
    
    /**
     * The only Java method that will be upcalled from Javascript
     */
    public void executeCommand(String command) {
        //mLogic.executeCommand(command);

        System.out.println("before command: " + command);
        // TODO: *BUG* Javascript commands don't work within this method
        /*Platform.runLater(new Runnable(){
            @Override
            public void run() {
                setContent("view", command);
            }
        });*/
        setContent("view", command);
        System.out.println("after command");
    }

    /**
     * Convenience method to run Javascript commands
     */
    private Object js(String command) {
        return webEngine.executeScript(command);
    }
    
    /**
     * Convenience method to set the attribute value of an HTML element
     */
    private void setAttribute(String id, String attribute, String value) {
        js("document.getElementById('" + id + "').setAttribute('" + attribute + "', '"
                + value + "');");
    }
    
    /**
     * Convenience method to write the contents of an HTML element
     */
    private void setContent(String id, String value) {
        js("document.getElementById('" + id + "').innerHTML = '" + value + "';");
    }
    
    /**
     * Allows Java upcalls from Javascript
     */
    private void initializeGUI() {
        JSObject window = (JSObject) js("window");
        window.setMember("GUI", new GUI());
        setAttribute("commandForm", "onsubmit", "GUI.executeCommand(this.command.value)");
    }
    
    public void createUI(Stage stage) {
        browser = new WebView();
        webEngine = browser.getEngine();
        webEngine.load(getClass().getResource("GUI.html").toExternalForm());
        
        webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>(){
                @Override
                public void changed(ObservableValue ov, State oldState,
                        State newState) {
                    if (newState == State.SUCCEEDED) {
                        initializeGUI();
                    }
                }
            }
        );
        
        // TODO: app icon and title in taskbar
        browser.setContextMenuEnabled(false);
        Scene scene = new Scene(browser);
        stage.setTitle("FunDUE");
        stage.getIcons().add(new Image("file:FunDUE Logo.png"));
        stage.setHeight(800);
        stage.setWidth(700);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
