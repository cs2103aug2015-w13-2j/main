package sg.edu.cs2103aug2015_w13_2j;

import java.util.List;

//@@author A0121410H
public class Formatter implements FormatterInterface {
    public Formatter() {
        // Empty constructor
    }
    
    public void format(Task t, Format f) {
        FunDUE.sTextUI.println(t.getName());
    }
    
    public void format(List<Task> tasks, Format f) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).getName() + TextUI.NEWLINE);
        }
        FunDUE.sTextUI.print(sb.toString());
    }
    
    public void passThrough(String s) {
        FunDUE.sTextUI.print(s);
    }
}
