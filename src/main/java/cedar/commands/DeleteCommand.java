package cedar.commands;

import cedar.Cedar;
import cedar.CliParse;

public class DeleteCommand extends CommandCategory {
    public static final String[] ALIASES = {"rm", "delete"};

    public DeleteCommand() {
        super(ALIASES, "Delete task item");
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
            System.out.format("Removed this task from records: %s\n",
                    Cedar.getInternalTaskList().get(Integer.parseInt(command[1]) - 1));
            Cedar.getInternalTaskList().remove(Integer.parseInt(command[1]) - 1);
        }
    }
}
