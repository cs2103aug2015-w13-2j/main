package sg.edu.cs2103aug2015_w13_2j.ui;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.LogicInterface;
import sg.edu.cs2103aug2015_w13_2j.Task;
import sg.edu.cs2103aug2015_w13_2j.TaskInterface.TaskNotFoundException;
import sg.edu.cs2103aug2015_w13_2j.commands.AddHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.DeleteHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.EditHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.FilterHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.HelpHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.LoadHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkCompletedHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.MarkImportantHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.PopHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.RedoHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.SearchHandler;
import sg.edu.cs2103aug2015_w13_2j.commands.UndoHandler;
import sg.edu.cs2103aug2015_w13_2j.filters.Filter;
import sg.edu.cs2103aug2015_w13_2j.storage.Storage;

// @@author A0133387B

public class FXUITest extends Application implements UIInterface{
	private FXUI mUI = FXUI.getInstance();  //<--here
	private ArrayList<Task> mTasks;
    private FeedbackMessage mFeedback;
    private Logic mLogic = (Logic) Logic.getInstance();
    private Storage mStorage = Storage.getInstance();
    
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
        mUI.createUI(primaryStage);
        mUI.injectDependency(mLogic);
        mLogic.injectDependencies(mStorage, this);
        mLogic.readTasks();
        mLogic.display();
    }

  
    public ArrayList<Task> getTasksForDisplay(){
    	return mTasks;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void injectDependency(LogicInterface logic) {
        mLogic = (Logic) logic;
    }

	@Override
	public void display(ArrayList<Task> tasks) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Task getTask(int index) throws TaskNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void feedback(FeedbackMessage f) {
		mFeedback = f;
	}
	
	public String getFeedBackMessage() {
		return mFeedback.getMessage();
	}

    
	public void pushFilter(Filter filter) {
	
		
	}

	@Override
	public Filter popFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateFilters(ArrayList<Task> tasks) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void showChangeDataFilePathDialog() {
		// TODO Auto-generated method stub
		
	}
  
}
