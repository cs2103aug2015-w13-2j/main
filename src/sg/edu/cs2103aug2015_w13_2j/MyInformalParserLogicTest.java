package sg.edu.cs2103aug2015_w13_2j;

public class MyInformalParserLogicTest {

	public static void main(String[] args) {
		String userCmd1 = "add -s 23/09 -e 24/09 'Do CS2103T Prototype'";
		
		new FunDUE();
		FunDUE.sTextUI.print("Hello world!");
		Parser parser = FunDUE.sParser;
		Logic logic = FunDUE.sLogic;
		
		System.out.println("---------------PARSED TOKENS----------------");
		parser.parseCommand(userCmd1);
		parser.executeCommand();
		
		System.out.println("--------------TEST GENERAL TASK ATTR------------");
		System.out.println(parser.getListOfTokens());
		
		// Test if getters are correct
		System.out.println("Task name: " + logic.getTask(0).getName());
		System.out.println("Task status: " + logic.getTask(0).getStatus());
		System.out.println("Task type: " + logic.getTask(0).getType());
		System.out.println("Task created: " + logic.getTask(0).getCreated());
		System.out.println("Task start: " + logic.getTask(0).getStart());
		System.out.println("Task deadline: " + logic.getTask(0).getDeadline());
		System.out.println("Task toString for Storage (On a new line, without dotted'...'): \n...\n" 
				   + logic.getTask(0).toString() + "...");

		// Test for labels
		TaskInterface.Label created = TaskInterface.Label.CREATED;
		TaskInterface.Label start = TaskInterface.Label.START;
		TaskInterface.Label deadline = TaskInterface.Label.DEADLINE;
		TaskInterface.Label name = TaskInterface.Label.NAME;
		TaskInterface.Label type = TaskInterface.Label.TYPE;
		TaskInterface.Label status = TaskInterface.Label.STATUS;
		
		System.out.println("---------------TEST TASK LABELS---------------");
		System.out.println("Created: " + logic.getTask(0).getLabel(created));
		System.out.println("Start: " + logic.getTask(0).getLabel(start));
		System.out.println("Deadline: " + logic.getTask(0).getLabel(deadline));
		System.out.println("Name: " + logic.getTask(0).getLabel(name));
		System.out.println("Type: " + logic.getTask(0).getLabel(type));
		System.out.println("Status: " + logic.getTask(0).getLabel(status));
		
	}

}
