package cedar;

import cedar.Cedar;

import java.util.Arrays;
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
                        System.out.format("%d: "+task.toString()+"\n", index++);
                    }
                }
                break;
            case "add":
                // parse omit first command token
                String[] content = Arrays.copyOfRange(s, 1, s.length);
                Cedar.internalTaskList.add(new Task(String.join(" ", content)));
                System.out.println("Added: " + Arrays.toString(content));
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
                    Cedar.internalTaskList.get(Integer.parseInt(command[1])-1).setState(true);
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
                    Cedar.internalTaskList.get(Integer.parseInt(command[1])-1).setState(false);
                }
                break;
            default:
                System.out.println("Unrecognized.");
                System.out.println("Type \"help\" for a list of available commands.");
        }
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
