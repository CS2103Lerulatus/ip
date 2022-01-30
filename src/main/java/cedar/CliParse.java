package cedar;

import cedar.tasks.*;
import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CliParse {

    protected static void parse(String[] s) {
        String[] command = Stream.of(s).map(String::toLowerCase).toArray(String[]::new);
        switch (command[0]) {
        case "help":
            // consider storing this internally as an array or something as future commands expands
            System.out.println("Available commands: help, exit, bye, list, add, mark, unmark");
            Config.printDivider();
            break;
        case "exit":
        case "bye":
            Cedar.setActivityState(false);
            System.out.println("You have left the forest.");
            break;
        case "ls":
        case "list":
            if (Cedar.internalTaskList.size() == 0) {
                System.out.println("TodoList is empty");
            } else {
                int index = 1;
                for (Task task : Cedar.internalTaskList) {
                    //System.out.println((index++)+"%d: "+task);
                    System.out.format("%d: " + task.toString() + "\n", index++);
                }
            }
            break;
        /*case "add":
            // deprecated? lmao
            String[] content = Arrays.copyOfRange(command, 1, command.length);
            // ^^ bugfix above
            Cedar.internalTaskList.add(new Task(String.join(" ", content)));
            System.out.println("Added: " + String.join(" ", content));
            System.out.format("You currently have %d task(s)\n", Cedar.internalTaskList.size());
            break;*/
        case "todo":
        case "event":
        case "deadline":
            addTaskTypeHandler(command);
            try {
                writeInternalListToJsonHandler();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error saving tasklist to disk. Check Logs.");
            }
            break;
        case "done":
        case "check":
        case "mark":
            // I'm sure there are better/faster cli parsers out there than this clumsy block
            if (!isInt(command[1])) {
                // if not int
                System.out.println("Invalid.");
            } else if (Integer.parseInt(command[1]) > Cedar.internalTaskList.size()) {
                // if int outside tasks range
                System.out.println("Invalid range.");
            } else {
                Cedar.internalTaskList.get(Integer.parseInt(command[1]) - 1).setState(true);
                // wowie simulating human marking effort feels kind of cozy
                System.out.format("Nice. I've marked this task as done: %s\n",
                        Cedar.internalTaskList.get(Integer.parseInt(command[1]) - 1));
            }
            break;
        case "uncheck":
        case "unmark":
            if (!isInt(command[1])) {
                // if not int
                System.out.println("Invalid.");
            } else if (Integer.parseInt(command[1]) > Cedar.internalTaskList.size()) {
                // if int outside tasks range
                System.out.println("Invalid range.");
            } else {
                Cedar.internalTaskList.get(Integer.parseInt(command[1]) - 1).setState(false);
                System.out.format("OK, I've marked this task as yet undone: %s\n",
                        Cedar.internalTaskList.get(Integer.parseInt(command[1]) - 1));
            }
            break;
        case "delete":
        case "rm":
            if (!isInt(command[1])) {
                // if not int
                System.out.println("Invalid.");
            } else if (Integer.parseInt(command[1]) > Cedar.internalTaskList.size()) {
                // if int outside tasks range
                System.out.println("Invalid range.");
            } else {
                System.out.format("Removed this task from records: %s\n",
                        Cedar.internalTaskList.get(Integer.parseInt(command[1]) - 1));
                Cedar.internalTaskList.remove(Integer.parseInt(command[1]) - 1);
            }
            break;
        case "nuke":
            Cedar.internalTaskList = new ArrayList<>();
            break;
        case "searchdate":
        case "search-date":
        case "finddate":
        case "find-date":
            String languageDate = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
            LocalDate searchDate = naturalLangDateParseHandler(languageDate);
            ArrayList<Task> matchingDatesList = Cedar.internalTaskList.stream()
                    .filter(tasks -> searchDate.equals(tasks.getTemporalLabel()))
                    .collect(Collectors.toCollection(ArrayList::new));
            if (matchingDatesList.size() == 0) {
                System.out.println("🔎 Search returned 0 hits.");
            } else {
                System.out.format("🔎 Search returned %d hit(s).\n", matchingDatesList.size());
                System.out.println("Here are your results:");
                int index = 1;
                for (Task task : matchingDatesList) {
                    //System.out.println((index++)+"%d: "+task);
                    System.out.format("%d: " + task.toString() + "\n", index++);
                }
            }
            break;
        default:
            System.out.println("Unrecognized. Check for typo(s) in commands.");
            System.out.println("Type \"help\" for a list of available commands.");
        }
    }

    private static LocalDate naturalLangDateParseHandler(String languageDate) {
        List<Date> dates = new PrettyTimeParser().parse(languageDate);
        LocalDate date = dates.get(0).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return date;
    }

    private static void writeInternalListToJsonHandler() throws IOException {
        // consider fixing this filepath to a CONSTANT cuz it will nvr_change4ever
        Writer writeUserdata = new FileWriter(Cedar.SAVED_USERDATA);
        Gson dateJsonSerialize = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateSerializer()).create();
        dateJsonSerialize.toJson(Cedar.internalTaskList, writeUserdata);
        writeUserdata.flush();
        writeUserdata.close();
    }

    private static void addTaskTypeHandler(String[] command) {
        switch (command[0]) {
        case "todo": {
            String content = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
            Cedar.internalTaskList.add(new TodoTask(content));
            System.out.println("Added a new todo task: " + content);
            break;
        }
        case "event": {
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
            LocalDate eventDate = naturalLangDateParseHandler(content[1]);
            EventTask freshEvent = new EventTask(content[0], eventDate);
            Cedar.internalTaskList.add(freshEvent);
            System.out.format("Added a new Event Task! Attend event [%s] at: %s [%s]\n", content[0], content[1],
                    freshEvent.prettyPrintTimestamp());
            break;
        }
        case "deadline": {
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
            LocalDate deadlineDate = naturalLangDateParseHandler(content[1]);
            DeadlineTask freshDeadlineTask = new DeadlineTask(content[0], deadlineDate);
            Cedar.internalTaskList.add(freshDeadlineTask);
            System.out.format("Added a new Deadline Task! Finish [%s] before: %s [%s]\n", content[0], content[1],
                    freshDeadlineTask.prettyPrintDuedate());
            break;
        }
        default:
            System.out.println("⚠ Error: Nonexistent task type");
            return;
        }
        System.out.format("You currently have %d task(s)\n", Cedar.internalTaskList.size());
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
