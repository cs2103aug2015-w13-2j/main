package sg.edu.cs2103aug2015_w13_2j;

import java.util.Date;

public interface TaskInterface {
    public void setName(String name);
    public String getName();
    
    public void setLabel(String label, String value);
    public String getLabel(String label);
    
    public Date getCreated();
    
    public void setDeadline(Date deadline);
    public Date getDeadline();
}
