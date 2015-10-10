package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.ArrayList;

import java.util.Date;

import org.junit.Test;


/**Test cases for the Logic Component
 * 
 * @author Nguyen Tuong Van
 *
 */

public class LogicTest {
    FunDUE funDue = new FunDUE();
    private Logic logicComponent = funDue.getLogicInstance();
   
	@Test
	public void testAdd() {
        Task newTask = new Task("First"); 
        logicComponent.addTask(newTask);
        assertEquals("First", logicComponent.getTask(0).getName());
	}
	@Test
	public void testDelete(){
		System.out.println("TESTING DELETE BY INDEX");
		logicComponent.addTask(new Task("first test task"));
		logicComponent.addTask(new Task("second test task"));
		logicComponent.deleteTask(0);
		assertEquals(logicComponent.getAllTasks().get(0).getName(), "second test task");
	}
	
	@Test
	public void testDetermineType(){
		Task newTask = new Task("first test task");
		logicComponent.determineType(newTask);
		assertTrue(newTask.getEnd() == null);
		assertEquals(newTask.getType(), "FLOAT");
		newTask.setEnd(new Date());
		logicComponent.determineType(newTask);
		assertEquals(newTask.getType(), "DEADLINE");
		newTask.setStart(new Date());
		logicComponent.determineType(newTask);
		assertEquals(newTask.getType(), "EVENT");
	}
	
	@Test
	public void testEdit(){
		Task original = new Task("first test task");
		assertEquals(original.getCompleted(), "FALSE");
		assertEquals(original.getArchived(), "FALSE");
		assertEquals(original.getImportant(), "FALSE");
		logicComponent.addTask(original);
		assertEquals(original.getType(), "FLOAT");
		Task newTask = new Task("first test task");
		newTask.setEnd(new Date());
		logicComponent.determineType(newTask);
		logicComponent.editTask(original, newTask);
		assertEquals(original.getType(), "DEADLINE");
		newTask.setStart(new Date());
		logicComponent.determineType(newTask);
		logicComponent.editTask(original, newTask);
		assertEquals(original.getType(), "EVENT");
		newTask.setName("I have changed");
		logicComponent.editTask(original, newTask);
		assertEquals(original.getName(), newTask.getName());
	}
	
	@Test
	public void testStatus(){
		Task original = new Task("second test task");
		logicComponent.addTask(original);
		assertEquals(original.getCompleted(), "FALSE");
		original.setCompleted("TRUE");
		assertEquals(original.getCompleted(), "TRUE");
		ArrayList<Task> all = logicComponent.getAllTasks();
		assertTrue(all.size() == 1);
		//assertTrue(logicComponent.viewCompleted().size() == 1);
		logicComponent.deleteTask(all.indexOf(original));
		//assertEquals(original.getStatus(), "DELETED");
		assertTrue(all.isEmpty());
		//assertTrue(logicComponent.viewCompleted().isEmpty());
	}
	
	@Test
	public void testSortByDeadline(){
		Task one = new Task("ONE");
		logicComponent.addTask(one);
		Task two = new Task("TWO");
		logicComponent.addTask(two);
		Task seven = new Task("SEVEN");
		logicComponent.addTask(seven);
		System.out.println("Sorting all floats by name");
		ArrayList<Task> list = logicComponent.sortByDeadline();
		for(int i = 0; i< list.size(); i++){
			System.out.println(list.get(i).getName() + " " + list.get(i).getType());
		}
		System.out.println("Seven becomes deadline");
		seven.setEnd(new Date());
		assertTrue(seven.getStart() == null && seven.getEnd() != null);
		System.out.println("Seven's deadline = " + seven.getEnd());
		logicComponent.determineType(seven);
		//logicComponent.editTask("SEVEN", seven);
		//seven.setTypeDeadline();
		System.out.println("Seven's type = " + seven.getType());
		list = logicComponent.sortByDeadline();
		for(int i = 0; i< list.size(); i++){
			System.out.println(list.get(i).getName() +" " + list.get(i).getType());
		}
		
		System.out.println("Even more Seven ");
		Task newSeven = new Task("SEVEN SEVEN");
		newSeven.setEnd(new Date());
		newSeven.setStart(new Date());
		logicComponent.editTask(seven, newSeven);
		list = logicComponent.sortByDeadline();
		for(int i = 0; i< list.size(); i++){
			System.out.println(list.get(i).getName() +" " +  list.get(i).getType());
		}
		
		System.out.println("TWO's gonna get in front!");
		Task newTwo = new Task("I AM THE NEW TWO");
		newTwo.setEnd(new Date());
		newTwo.setStart(new Date());
		logicComponent.editTask(two, newTwo);
		
		Task five = new Task("FIVE");
		logicComponent.addTask(five);
		Task six = new Task("SIX");
		six.setEnd(new Date());
		logicComponent.addTask(six);
		
		list = logicComponent.sortByDeadline();
		for(int i = 0; i< list.size(); i++){
			System.out.println(list.get(i).getName() + " " +list.get(i).getType());
		}
	}
	
	@Test
	public void testOverdue(){
		Task one = new Task("ONE");
		logicComponent.addTask(one);
		Task two = new Task("TWO");
		logicComponent.addTask(two);
		one.setEnd(new Date(new Long("324567898")));
		ArrayList<Task> list = logicComponent.list();
		System.out.println("Testing list now");
		for(int i = 0; i< list.size(); i++){
			System.out.println(list.get(i).getName() + " " +list.get(i).getType() + " " + list.get(i).getCompleted() + " " + list.get(i).getArchived() + " " + list.get(i).getImportant());
		}
	}
}
