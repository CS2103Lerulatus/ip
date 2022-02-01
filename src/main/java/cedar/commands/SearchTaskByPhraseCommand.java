package cedar.commands;

import cedar.Cedar;
import cedar.tasks.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SearchTaskByPhraseCommand extends CommandCategory {
    public static final String[] ALIASES = {"searchtasks",
            "searchtask",
            "searchkeyword",
            "findtask",
            "findtasks",
            "search",
            "find"};

    public SearchTaskByPhraseCommand() {
        super(ALIASES, "Search by phrase");
    }

    @Override
    public void execute(String[] command) {
        String searchPhrase = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
        ArrayList<Task> matchingPhraseList = Cedar.getInternalTaskList().stream()
                .filter(tasks -> tasks.getDesc().toLowerCase().contains(searchPhrase.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
        if (matchingPhraseList.size() == 0) {
            System.out.format("🔎 Search returned 0 hits for \"%s\".\n", searchPhrase);
        } else {
            System.out.format("🔎 Search returned %d hit(s).\n", matchingPhraseList.size());
            //System.out.format("🔎 Search returned %d hit(s) for \"%s\".\n", matchingPhraseList.size(), searchPhrase);
            System.out.println("Here are your results:");
            int index = 1;
            for (Task task : matchingPhraseList) {
                System.out.format("%d: " + task.toString() + "\n", index++);
            }
        }
    }
}
