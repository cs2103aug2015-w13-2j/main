package sg.edu.cs2103aug2015_w13_2j;

//@@author A0121410H
public class Formatter implements FormatterInterface {
    private TextUI mTextUI;
    
    public Formatter(TextUI textUI) {
        mTextUI = textUI;
    }
    
    public void format(Task t, Format f) {
        mTextUI.println(t.getName());
    }
    
    public void format(Task[] t, Format f) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < t.length; i++) {
            sb.append(t[i].getName() + TextUI.NEWLINE);
        }
        mTextUI.print(sb.toString());
    }
}
