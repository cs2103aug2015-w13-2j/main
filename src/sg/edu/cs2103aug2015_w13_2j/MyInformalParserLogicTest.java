package sg.edu.cs2103aug2015_w13_2j;

/**
 * Informal temporary test class for convenience
 * 
 * @author Natasha
 */
public class MyInformalParserLogicTest {

	private Parser parser;
	private Logic logic;
	
	public MyInformalParserLogicTest() {
		new FunDUE();
		
		FunDUE.sTextUI.print("Hello world!");
		
		this.parser = FunDUE.sParser;
		this.logic = FunDUE.sLogic;
	}
	
	public static void main(String[] args) {
		MyInformalParserLogicTest informalTest = new MyInformalParserLogicTest();
		
		printTestLabel(1);
		String userCmd1 = "add -e 23/09 'Do CS2103T Prototype'";
		informalTest.printTaskDetails(userCmd1, 1);
		
		printTestLabel(2);
		String userCmd2 = "add -s 10/03 'Do Something else'";
		informalTest.printTaskDetails(userCmd2, 2);
		
		printTestLabel(3);
		// Basically edit the second task to 
		// 1.) Change start time
		// 2.) Add a deadline
		String userCmd3 = "edit -s 11/03 -e 11/11 'Do Something else'";
		informalTest.printTaskDetails(userCmd3, 2);
		
		printTestLabel(4);
		String userCmd4 = "delete 'Do CS2103T Prototype'";
		informalTest.printTaskDetails(userCmd4, 1);
	}
	
	private static void printTestLabel(int index) {
		System.out.println("\n\n");
		System.out.println("***************************************");
		System.out.println("TEST " + index);
		System.out.println("***************************************");
	}

	private void printTaskDetails(String userCmd, int numOfTasks) {
		System.out.println("---------------PARSED TOKENS----------------");
		parser.parseCommand(userCmd);
		parser.executeCommand();
		
		for (int i = 0; i < numOfTasks; i++) {
			Task currentTask = logic.getTask(i);
			
			System.out.println("_________________________________________________________________");
			System.out.println("For task " + i + ".....");
			System.out.println("--------------TEST GENERAL TASK ATTR------------");
			System.out.println(parser.getListOfTokens());
		
			// Test if getters are correct
			System.out.println("Task name: " + currentTask.getName());
			System.out.println("Task status: " + currentTask.getStatus());
			System.out.println("Task type: " + currentTask.getType());
			System.out.println("Task created: " + currentTask.getCreated());
			System.out.println("Task start: " + currentTask.getStart());
			System.out.println("Task deadline: " + currentTask.getDeadline());
			System.out.println("Task toString for Storage (On a new line, without dotted'...'): \n...\n"
					+ currentTask.toString() + "...");
			// Test for labels
			TaskInterface.Label created = TaskInterface.Label.CREATED;
			TaskInterface.Label start = TaskInterface.Label.START;
			TaskInterface.Label deadline = TaskInterface.Label.DEADLINE;
			TaskInterface.Label name = TaskInterface.Label.NAME;
			TaskInterface.Label type = TaskInterface.Label.TYPE;
			TaskInterface.Label status = TaskInterface.Label.STATUS;
			System.out.println("---------------TEST TASK LABELS---------------");
			System.out.println("Created: " + currentTask.getLabel(created));
			System.out.println("Start: " + currentTask.getLabel(start));
			System.out.println("Deadline: " + currentTask.getLabel(deadline));
			System.out.println("Name: " + currentTask.getLabel(name));
			System.out.println("Type: " + currentTask.getLabel(type));
			System.out.println("Status: " + currentTask.getLabel(status));
		}
		System.out.println("****-------****------END TEST-------****------****");
	}

}
