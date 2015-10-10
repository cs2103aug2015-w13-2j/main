package sg.edu.cs2103aug2015_w13_2j;

import java.util.List;

//@@author A0124007X

/**
* Formatter class that processes output and messages before printing
* to the TextUI to provide feedback to the user
* 
* @author Lu Yang Kenneth
*/
public class Formatter implements FormatterInterface {
	private FunDUE mAppInstance;
	
    public Formatter(FunDUE appInstance) {
        mAppInstance = appInstance;
    }
    
    public void format(Task t, Format f) {
        mAppInstance.getTextUIInstance().println(t.getName());
    }
    
    public void format(List<Task> tasks, Format f) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).getName() + TextUI.NEWLINE);
        }
        mAppInstance.getTextUIInstance().print(sb.toString());
    }
    
    public void passThrough(String s) {
		mAppInstance.getTextUIInstance().print(s);
    }
}
