package cedar.commands;

import cedar.Cedar;
import cedar.CliParse;
import cedar.tasks.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SearchTaskByDateCommand extends CommandCategory {
    public static final String[] ALIASES = {"searchdate", "search-date", "finddate", "find-date"};

    public SearchTaskByDateCommand() {
        super(ALIASES, "Search tasks by date");
    }

    @Override
    public void execute(String[] command) {
        String languageDate = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
        LocalDate searchDate = CliParse.naturalLangDateParseHandler(languageDate);
        if (searchDate != null) {
            ArrayList<Task> matchingDatesList = Cedar.getInternalTaskList().stream()
                    .filter(tasks -> searchDate.equals(tasks.getTemporalLabel()))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (matchingDatesList.size() == 0) {
                System.out.println("🔎 Search returned 0 hits.");
            } else {
                System.out.format("🔎 Search returned %d hit(s) for \"%s\".\n", matchingDatesList.size(), languageDate);
                System.out.println("Here are your results:");
                int index = 1;
                for (Task task : matchingDatesList) {
                    //System.out.println((index++)+"%d: "+task);
                    System.out.format("%d: " + task.toString() + "\n", index++);
                }
            }
        } else {
            System.out.println("⚠ Invalid date provided. (Check spelling?)");
        }
    }
}
