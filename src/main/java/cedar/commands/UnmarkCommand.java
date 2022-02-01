package cedar.commands;

import cedar.Cedar;
import cedar.CliParse;

public class UnmarkCommand extends CommandCategory {
    public static final String[] ALIASES = {"uncheck", "unmark"};

    public UnmarkCommand() {
        super(ALIASES, "Unmark task as done");
    }

    @Override
    public void execute(String[] command) {
        if (!CliParse.isInt(command[1])) {
            // if not int
            System.out.println("⚠ Invalid.");
        } else if (Integer.parseInt(command[1]) > Cedar.getInternalTaskList().size()) {
            // if int outside tasks range
            System.out.println("⚠ Invalid range.");
        } else {
            Cedar.getInternalTaskList().get(Integer.parseInt(command[1]) - 1).setState(false);
            System.out.format("OK, I've marked this task as yet undone: %s\n",
                    Cedar.getInternalTaskList().get(Integer.parseInt(command[1]) - 1));
        }
    }
}
