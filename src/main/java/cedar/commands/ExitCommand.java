package cedar.commands;

import cedar.Cedar;

public class ExitCommand extends CommandCategory {
    public static final String[] ALIASES = {"exit", "quit", "bye"};

    public ExitCommand() {
        super(ALIASES, "Exit");
    }

    @Override
    public void execute(String[] args) {
        Cedar.setActivityState(false);
        System.out.println("You have left the forest.");
    }
}
