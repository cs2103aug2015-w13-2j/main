package sg.edu.cs2103aug2015_w13_2j.Unused;

import java.util.ArrayList;

import sg.edu.cs2103aug2015_w13_2j.Task;

public class A0133387BUnused {
    
    //@@author A0133387B-unused
    /**
     * The codes below are unused because of refactoring of Logic subsequently
     * and registering each command as a CommandHandler
     * 
     * */
    
    /**
     * This method sorts a list of tasks according to their deadlines(if any)
     * The tasks with deadlines takes priority, followed by events sorted
     * according to start time and floats to be added last, sorted by their
     * names
     * 
     */
    /*
    private ArrayList<Task> search(String keyword) {
        ArrayList<Task> tasksWithKeyword = new ArrayList<Task>();
        for (int i = 0; i < mTasks.size(); i++) {
            if (mTasks.get(i).getName().toLowerCase()
                    .contains(keyword.toLowerCase())) {
            	tasksWithKeyword.add(mTasks.get(i));
            }
        }

        return tasksWithKeyword;
    }
    */
	/*
	   public void addTask(Task task){
	    	tasks.add(task);
	    	
	    	determineType(task);
	    	 
	    	   if(task.getType().equals("EVENT")){
	    		  events.add(task);
	    	   } else if (task.getType().equals("DUE")){
	    		  deadlines.add(task);
	    	   } else {
	    		  floats.add(task);
	    	   }
	    	
	    }
	   */
	    /**
	     * Determine the type of a task based on its start (if any) and end (if any) times
	     * @param task
	     *            the new task to be categorized
	     * 
	     */
	    /*
	    public void determineType(Task task){
	    	if(task.getDeadline() == null){// if deadLine == null, float
	    		task.setTypeFloat();
	    	} else {
	    		if (task.getStart() != null){//if deadLine != null and start != null, event
	    		    task.setTypeEvent();
	    	    } else {//if deadLine != null but start == null, then it's a task with deadline
	    		    task.setTypeDeadline();
	    	    }
	    	}
	    }
	    */
    /**
	 * This method merges the details of a new task and the original task by
	 * checking the fields of the new task Those that are blank are kept
	 * unchanged in the original task While the fields that are not null in the
	 * new task will be updated in the original task
	 * 
	 * @param original
	 *            the task that is being updated
	 * @param newTask
	 *            the task that is updating the original task
	 * @return the updated original task
	 */
/*
	public Task mergeDetails(Task original, Task newTask) {
		// possible fields to update: name, start, end
		if (newTask.getName() != null) {
			original.setName(newTask.getName());
		}
		if (newTask.getEnd() != null) {
			original.setEnd(newTask.getEnd());
		}
		if (newTask.getStart() != null) {
			original.setStart(newTask.getStart());
		}
		if (newTask.getCompleted().equals("TRUE")) {
			original.setCompleted("TRUE");
		}
		if (newTask.getArchived().equals("TRUE")) {
			original.setArchived("TRUE");
		}
		return original;
	}
*/
	/**
	 * This method edits a task according to the fields specified by the user It
	 * takes in a task formed by the name input by user, as well as the fields
	 * to changed It then updates the task identified by the name field in the
	 * method signature the updating is taken care of by the method mergeDetails
	 * Changes in the type of the task due to changes in the start and end time
	 * or status are also updated This information is updated manually by
	 * checking the changes (if any) in existence of start and deadline
	 * 
	 * @param the
	 *            name of the Task user wishes to edit
	 * @param the
	 *            new Task object formed by the requested changes
	 * @return the updated task
	 * 
	 */
    /*
	public Task editTask(Task original, Task edittingTask) {
		// potential edits to the type of task
		if (original.getStart() == null && original.getEnd() == null) {// originally float
			if (edittingTask.getStart() != null && edittingTask.getEnd() != null) {// edited to events														
				original.setType("EVENT");
			}
			if (edittingTask.getStart() == null && edittingTask.getEnd() != null) {// edited to deadline tasks
				original.setType("DEADLINE");
			}
		} else if (original.getStart() != null && original.getEnd() != null) {// originally event
			if (edittingTask.getEnd() == null) {// change to float
				original.setType("FLOAT");
			} else if (edittingTask.getStart() == null) {// change to task with deadline
				original.setType("DEADLINE");
			}
		} else if (original.getStart() == null && original.getEnd() != null) {// originally task with deadline
			if (edittingTask.getEnd() == null) {// change to float
				original.setType("FLOAT");
			} else if (edittingTask.getStart() != null && edittingTask.getEnd() != null) {// edited to event
				original.setType("EVENT");
			}
		}
		mergeDetails(original, edittingTask);
		mAppInstance.getFormatterInstance().format(original, FormatterInterface.Format.LIST); 
		try {
			mAppInstance.getStorageInstance().writeFile(tasks, "output.txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return original;
	}
    
    public ArrayList<Task> list() {
		// checkStatus();
		ArrayList<Task> userView = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getArchived().equals("FALSE")) {
				userView.add(tasks.get(i));
			}
		}
		mAppInstance.getFormatterInstance().format(userView, FormatterInterface.Format.LIST); 
		return userView;
	}

	/**
	 * This method sorts a list of tasks according to their deadlines(if any)
	 * The tasks with deadlines takes priority, followed by events sorted
	 * according to start time and floats to be added last, sorted by their
	 * names
	 * 
	 */
    /*
	public ArrayList<Task> sortByDeadline() {
		updateUserView();
		sort();
	}
	
	public ArrayList<Task> sort(){
	    Collections.sort(userView, new Comparator<Task>() {
			public int compare(Task task1, Task task2) {
				if (task1.getType().equals(task2.getType())) {
					if (task1.getType().equals("DEADLINE")) {
						return task1.getEnd().compareTo(task2.getEnd());
					} else if (task1.getType().equals("EVENT")) {
						return task1.getStart().compareTo(task2.getStart());
					} else {
						return task1.getName().compareTo(task2.getName());
					}
				} else {
					return task1.getType().compareTo(task2.getType());
				}
			}
		});
		mAppInstance.getFormatterInstance().format(userView, FormatterInterface.Format.LIST); 
		return userView;
	}
	
	public void updateUserView(){
	    if(userView.isEmpty()){
		    for (int i = 0; i < tasks.size(); i++) {
			    userView.add(tasks.get(i));
		    }
		}
    }
    */
	/**
	 * Below are the unused LogicTest JUnit codes
	 */
	/*
	 
	public class LogicTest {
	    
	    private Logic logicComponent;
	    
	    @Before
	    
	    public void init(){
	        logicComponent = new Logic();
	    }
	    
		@Test
		public void testAdd() {
	        Task newTask = new Task("First"); 
	        logicComponent.addTask(newTask);
	        assertEquals("First", logicComponent.getTask(0).getName());
		}
		@Test
		public void testDelete(){
			logicComponent.addTask(new Task("first test task"));
			logicComponent.addTask(new Task("second test task"));
			logicComponent.deleteTask(logicComponent.findTaskByName("first test task"));
			assertEquals(logicComponent.getAllTasks().get(0).getName(), "second test task");
		}
		
		@Test
		public void testDetermineType(){
			Task newTask = new Task("first test task");
			logicComponent.determineType(newTask);
			assertTrue(newTask.getDeadline() == null);
			assertEquals(newTask.getType(), "FLOAT");
			newTask.setDeadline(new Date());
			logicComponent.determineType(newTask);
			assertEquals(newTask.getType(), "DUE");
			newTask.setStart(new Date());
			logicComponent.determineType(newTask);
			assertEquals(newTask.getType(), "EVENT");
		}
		
		@Test
		public void testEdit(){
			Task original = new Task("first test task");
			assertEquals(original.getStatus(), "ONGOING");
			logicComponent.addTask(original);
			assertEquals(original.getType(), "FLOAT");
			Task newTask = new Task("first test task");
			newTask.setDeadline(new Date());
			logicComponent.determineType(newTask);
			logicComponent.editTask("first test task", newTask);
			assertEquals(original.getType(), "DUE");
			assertTrue(logicComponent.getDeadlines().contains(original));
			assertEquals(logicComponent.getFloats().contains(original), false);
			newTask.setStart(new Date());
			logicComponent.determineType(newTask);
			logicComponent.editTask("first test task", newTask);
			assertEquals(original.getType(), "EVENT");
			newTask.setName("I have changed");
			logicComponent.editTask("first test task", newTask);
			assertEquals(original.getName(), newTask.getName());
		}
		
		@Test
		public void testStatus(){
			Task original = new Task("second test task");
			logicComponent.addTask(original);
			assertEquals(original.getStatus(), "ONGOING");
			original.markCompleted();
			assertEquals(original.getStatus(), "COMPLETED");
			assertTrue(logicComponent.getAllTasks().size() == 1);
			assertTrue(logicComponent.viewCompleted().size() == 1);
			assertTrue(logicComponent.getFloats().size() == 1);
			assertTrue(logicComponent.getEvents().isEmpty());
			assertTrue(logicComponent.getDeadlines().isEmpty());
			logicComponent.deleteTask(original);
			assertEquals(original.getStatus(), "DELETED");
			assertTrue(logicComponent.getAllTasks().isEmpty());
			assertTrue(logicComponent.viewCompleted().isEmpty());
			assertTrue(logicComponent.getFloats().isEmpty());
			assertTrue(logicComponent.getEvents().isEmpty());
			assertTrue(logicComponent.getDeadlines().isEmpty());
			
		}
	}
	*/
}
