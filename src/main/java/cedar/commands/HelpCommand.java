package cedar.commands;

import cedar.Config;

import static cedar.commands.CommandCategoryList.*;

public class HelpCommand extends CommandCategory {
    public static final String[] ALIASES = {"help"};

    public HelpCommand() {
        super(ALIASES, "Help");
    }

    @Override
    public void execute(String[] args) {
        System.out.println("Available commands:");
        for (CommandCategoryList commands: CommandCategoryList.values()) {
            if (commands != UNRECOGNIZED) {
                System.out.format("%s — %s\n", CommandCategory.use(commands).cmdDesc,
                        CommandCategory.use(commands).aliasesListString());
            }
        }
        Config.printDivider();
    }

}
