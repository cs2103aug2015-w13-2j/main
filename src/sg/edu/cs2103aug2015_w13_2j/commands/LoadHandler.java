package sg.edu.cs2103aug2015_w13_2j.commands;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;

public class LoadHandler extends CommandHandler {
    private static final String NAME = "Load Data File";
    private static final String SYNTAX = "";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = {};
    private static final String[] RESERVED = { "load" };

    public LoadHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        logic.showChangeDataFilePathDialog();
        logic.readTasks();
    }
}
