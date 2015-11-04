package sg.edu.cs2103aug2015_w13_2j.commands;

import java.util.HashMap;
import java.util.HashSet;

import sg.edu.cs2103aug2015_w13_2j.Logic;
import sg.edu.cs2103aug2015_w13_2j.parser.Command;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage;
import sg.edu.cs2103aug2015_w13_2j.ui.FeedbackMessage.FeedbackType;
import sg.edu.cs2103aug2015_w13_2j.ui.TextPane;

// @@author NOAUTHOR

public class HelpHandler extends CommandHandler {
    private static final String NAME = "Help";
    private static final String SYNTAX = "<COMMAND_NAME>";
    private static final String[] FLAGS = {};
    private static final String[] OPTIONS = { OPTION_COMMAND_NAME };
    private static final String[] RESERVED = { "h", "help" };
    private static final String HELP_SUCCESS = "Press enter to return.";

    public HelpHandler() {
        super(NAME, SYNTAX, FLAGS, OPTIONS, RESERVED);
    }

    @Override
    public void execute(Logic logic, Command command) {
        HashMap<String, CommandHandler> map = logic.getCommandHandlers();
        HashSet<CommandHandler> handlers = new HashSet<CommandHandler>(
                map.values());
        StringBuilder sb = new StringBuilder();
        for (CommandHandler handler : handlers) {
            sb.append(handler.getName() + TextPane.NEWLINE);
            sb.append("Usage: " + handler.getReservedKeywords() + " ");
            sb.append(handler.getSyntax() + TextPane.NEWLINE);
            sb.append("Flags: " + TextPane.NEWLINE);
            for (String flag : handler.getFlags()) {
                sb.append("\t" + flag + TextPane.NEWLINE);
            }
            sb.append("Options: " + TextPane.NEWLINE);
            for (String option : handler.getOptions()) {
                sb.append("\t" + option + TextPane.NEWLINE);
            }
            sb.append(TextPane.NEWLINE);
        }
        logic.display(sb.toString());
        logic.feedback(new FeedbackMessage(HELP_SUCCESS, FeedbackType.INFO));
    }

    @Override
    public boolean shouldDisplay() {
        return false;
    }
}
