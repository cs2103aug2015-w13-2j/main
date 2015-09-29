package sg.edu.cs2103aug2015_w13_2j;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTest {
	// Use the same Parser object across all test cases
	private Parser parser;
	
	
	@Test
	public void createParserTest() {
		String sampleCommandLine = "add -s 23/09 -r 1week 'Task name'";
		parser = new Parser(sampleCommandLine);
		
		assertEquals(sampleCommandLine, parser.getCommandLine());
	}

}
