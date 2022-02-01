package cedar.commands;

import cedar.Cedar;
import cedar.CliParse;
import cedar.tasks.Task;

import java.util.ArrayList;

public class NukeCommand extends CommandCategory {
    public static final String[] ALIASES = {"nuke"};

    public NukeCommand() {
        super(ALIASES, "Remove all tasks");
    }

    @Override
    public void execute(String[] command) {
        Cedar.setInternalTaskList(new ArrayList<Task>());
        System.out.println("✨ Removed all tasks");
    }
}
