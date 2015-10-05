package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import java.util.ArrayList;

import java.util.Date;

import org.junit.Test;

import sg.edu.cs2103aug2015_w13_2j.TaskInterface.Label;


/**Test cases for the Logic Component
 * 
 * @author Nguyen Tuong Van
 *
 */

public class LogicTest {
    
    private Logic logicComponent;
	@Test
	public void testAdd() {
		logicComponent = new Logic();
        Task newTask = new Task("First"); 
        logicComponent.addTask(newTask);
        assertEquals("First", logicComponent.getTask(0).getName());
	}
	@Test
	public void testDelete(){
		logicComponent = new Logic();
		logicComponent.addTask(new Task("first test task"));
		logicComponent.addTask(new Task("second test task"));
		logicComponent.deleteTask("first test task");
		assertEquals(logicComponent.getAllTasks().get(0).getName(), "second test task");
	}
	
	@Test
	public void testDetermineType(){
		logicComponent = new Logic();
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
		logicComponent = new Logic();
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
		logicComponent = new Logic();
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
		logicComponent.deleteTask(original.getName());
		assertEquals(original.getStatus(), "DELETED");
		assertTrue(logicComponent.getAllTasks().isEmpty());
		assertTrue(logicComponent.viewCompleted().isEmpty());
		assertTrue(logicComponent.getFloats().isEmpty());
		assertTrue(logicComponent.getEvents().isEmpty());
		assertTrue(logicComponent.getDeadlines().isEmpty());
		
	}
	
	@Test
	public void testSortByDeadline(){
		logicComponent = new Logic();
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
		seven.setDeadline(new Date());
		assertTrue(seven.getStart() == null && seven.getDeadline() != null);
		System.out.println("Seven's deadline = " + seven.getDeadline());
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
		newSeven.setDeadline(new Date());
		newSeven.setStart(new Date());
		logicComponent.editTask("SEVEN", newSeven);
		list = logicComponent.sortByDeadline();
		for(int i = 0; i< list.size(); i++){
			System.out.println(list.get(i).getName() +" " +  list.get(i).getType());
		}
		
		System.out.println("TWO's gonna get in front!");
		Task newTwo = new Task("I AM THE NEW TWO");
		newTwo.setDeadline(new Date());
		newTwo.setStart(new Date());
		logicComponent.editTask("TWO", newTwo);
		
		Task five = new Task("FIVE");
		logicComponent.addTask(five);
		Task six = new Task("SIX");
		six.setDeadline(new Date());
		logicComponent.addTask(six);
		
		list = logicComponent.sortByDeadline();
		for(int i = 0; i< list.size(); i++){
			System.out.println(list.get(i).getName() + " " +list.get(i).getType());
		}
	}
}
