package cedar.commands;

import cedar.Cedar;
import cedar.CliParse;
import cedar.tasks.EventTask;

import java.time.LocalDate;
import java.util.Arrays;

public class EventCommand extends CommandCategory {
    public static final String[] ALIASES = {"event"};

    public EventCommand() {
        super(ALIASES, "Add an event task");
    }

    @Override
    public void execute(String[] command) {
        if (Arrays.stream(command).noneMatch("/at"::equals)) {
            System.out.println("⚠ Error adding event: timestamp /at [date] not found.");
            return;
        }
        String[] content = String.join(" ", Arrays.copyOfRange(command, 1, command.length))
                .split("\\s*/at\\s*", 2);
        if (content[0].isBlank()) {
            System.out.println("⚠ Error: task not found");
            return;
        } else if (content[1].isBlank()) {
            System.out.println("⚠ Error: date not found");
            return;
        }
        // considering reading date format from an usr-defined .config file
        LocalDate eventDate = CliParse.naturalLangDateParseHandler(content[1]);
        if (eventDate == null) {
            System.out.println("⚠ Error: malformed event date or entered incorrectly. (Check spelling?)");
            return;
        }
        EventTask freshEvent = new EventTask(content[0], eventDate);
        Cedar.getInternalTaskList().add(freshEvent);
        System.out.format("Added a new Event Task! Attend event [%s] at: %s [%s]\n", content[0], content[1],
                freshEvent.prettyPrintTimestamp());
        System.out.format("You currently have %d task(s)\n", Cedar.getInternalTaskList().size());
        Cedar.writeInternalListToJsonHandler();
    }
}
