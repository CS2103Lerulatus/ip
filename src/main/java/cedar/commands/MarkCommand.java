package cedar.commands;

import cedar.Cedar;
import cedar.CliParse;

public class MarkCommand extends CommandCategory {
    public static final String[] ALIASES = {"done", "check", "mark"};

    public MarkCommand() {
        super(ALIASES, "Mark task as done");
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
            Cedar.getInternalTaskList().get(Integer.parseInt(command[1]) - 1).setState(true);
            // wowie simulating human marking effort feels kind of cozy
            System.out.format("Nice. I've marked this task as done: %s\n",
                    Cedar.getInternalTaskList().get(Integer.parseInt(command[1]) - 1));
        }
    }
}
