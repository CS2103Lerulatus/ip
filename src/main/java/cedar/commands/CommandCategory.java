package cedar.commands;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CommandCategory {
    public static String[] aliases;
    public static String cmdDesc;

    public CommandCategory(String[] aliases, String cmdDesc) {
        this.aliases = aliases;
        this.cmdDesc = cmdDesc;
    }

    public abstract void execute(String[] args);

    public static String aliasesListString() {
        return Stream.of(aliases).collect(Collectors.joining(", "));
    }

    public static boolean aliasMatch(String arg) {
        return Arrays.stream(aliases).anyMatch(arg::equals);
    }

    public static CommandCategory use(CommandCategoryList category) {
        switch (category) {
        case HELP:
            return new HelpCommand();
        case EXIT:
            return new ExitCommand();
        case LIST:
            return new ListCommand();
        case MARK:
            return new MarkCommand();
        case UNMARK:
            return new UnmarkCommand();
        case DELETE:
            return new DeleteCommand();
        case NUKE:
            return new NukeCommand();
        case TODO:
            return new TodoCommand();
        case DEADLINE:
            return new DeadlineCommand();
        case EVENT:
            return new EventCommand();
        case SEARCH_DATE:
            return new SearchTaskByDateCommand();
        case SEARCH_PHRASE:
            return new SearchTaskByPhraseCommand();
        default:
            return new UnrecognizedCommand();
        }
    }
}
