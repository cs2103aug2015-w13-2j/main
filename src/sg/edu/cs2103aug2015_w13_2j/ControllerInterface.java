package sg.edu.cs2103aug2015_w13_2j;

import sg.edu.cs2103aug2015_w13_2j.Controller.Commands;

/**
 * Public interface of the Controller Class
 * 
 * @@author A0130894B
 */
public interface ControllerInterface {
	/**
	 * Retrieves the task object being assembled by this class. 
	 * 
	 * @return Task object assembled
	 */
	public Task getControllerTask();
	
	/**
	 * Executes command as specified by the user input. In the process, it
	 * invokes the corresponding methods from the Logic class.
	 * 
	 * This method is for execution when Parser passes a parser object 
	 * or the list of tokens representing the user input to this Controller 
	 * class.
	 */
	public void startCommandExecution();
}
