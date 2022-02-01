package cedar.commands;

public class UnrecognizedCommand extends CommandCategory {
    public static final String[] ALIASES = {};

    public UnrecognizedCommand() {
        super(ALIASES, "");
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Unrecognized. Check for typo(s) in commands.");
        System.out.println("Type \"help\" for a brief list of available commands.");
        System.out.println("Type \"help -d\" for a detailed list of available commands.");
    }
}
