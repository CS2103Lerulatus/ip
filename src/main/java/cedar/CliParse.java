package cedar;

import cedar.commands.CommandCategory;
import cedar.commands.CommandCategoryList;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static cedar.commands.CommandCategoryList.UNRECOGNIZED;

public class CliParse {

    protected static void parse(String[] s) {
        String[] command = Stream.of(s).map(String::toLowerCase).toArray(String[]::new);

        boolean validCmdFlag = false;
        for (CommandCategoryList commandType: CommandCategoryList.values()) {
            if (CommandCategory.use(commandType).aliasMatch(command[0])) {
                CommandCategory.use(commandType).execute(command);
                validCmdFlag = true;
                break;
            }
        }
        if (!validCmdFlag) {
            CommandCategory.use(UNRECOGNIZED).execute(command);
        }

        // todo: consider abstracting all commands and their actions into their own CommandCategory classes.
        // the exact schema for this design require some thought and planning [maybe even uml]
        // A factory method pattern + ENUMS for all Command-type objects could prove to be very elegant.
    }

    public static LocalDate naturalLangDateParseHandler(String languageDate) {
        List<Date> dates = new PrettyTimeParser().parse(languageDate);
        if (dates.size() == 0) {
            // couldn't find appropriate date for input request will return null.
            return null;
        }
        LocalDate date = dates.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
