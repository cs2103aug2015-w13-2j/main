package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H
public class Formatter implements FormatterInterface {
    public Formatter() {
        // Empty constructor
    }
    
    public void format(Task t, Format f) {
        FunDUE.sTextUI.println(t.getName());
    }
    
    public void format(Task[] t, Format f) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < t.length; i++) {
            sb.append(t[i].getName() + TextUI.NEWLINE);
        }
        FunDUE.sTextUI.print(sb.toString());
    }
}
