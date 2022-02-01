package cedar.commands;

import cedar.Cedar;
import cedar.CliParse;
import cedar.tasks.DeadlineTask;

import java.time.LocalDate;
import java.util.Arrays;

public class DeadlineCommand extends CommandCategory {
    public static final String[] ALIASES = {"deadline"};

    public DeadlineCommand() {
        super(ALIASES, "Add a task with an associated deadline");
    }

    @Override
    public void execute(String[] command) {
        if (Arrays.stream(command).noneMatch("/by"::equals)) {
            System.out.println("⚠ Error adding deadline: do /by [date] not found.");
            return;
        }
        String[] content = String.join(" ", Arrays.copyOfRange(command, 1, command.length))
                .split("\\s*/by\\s*", 2);
        if (content[0].isBlank()) {
            System.out.println("⚠ Error: task not found");
            return;
        } else if (content[1].isBlank()) {
            System.out.println("⚠ Error: date not found");
            return;
        }
        LocalDate deadlineDate = CliParse.naturalLangDateParseHandler(content[1]);
        if (deadlineDate == null) {
            System.out.println("⚠ Error: malformed date or entered incorrectly. (Check spelling?)");
            return;
        }
        DeadlineTask freshDeadlineTask = new DeadlineTask(content[0], deadlineDate);
        Cedar.getInternalTaskList().add(freshDeadlineTask);
        System.out.format("Added a new Deadline Task! Finish [%s] before: %s [%s]\n", content[0], content[1],
                freshDeadlineTask.prettyPrintDuedate());
        System.out.format("You currently have %d task(s)\n", Cedar.getInternalTaskList().size());
        Cedar.writeInternalListToJsonHandler();
    }
}
