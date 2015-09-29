package sg.edu.cs2103aug2015_w13_2j;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * Sample Add command format
 * add -s <date/time> -e <date/time> -r <frequency/> -d <date/time> "Task name"
 */
public class Parser implements ParserInterface {
	
	private String commandLine;
	
	public Parser(String commandLine) {
		this.commandLine = commandLine;
	}
	
	public String getCommandLine () {
		return this.commandLine;
	}
	
	
	
}
