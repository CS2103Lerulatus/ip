package cedar;

import cedar.Cedar;

import java.util.Arrays;
import java.util.stream.Stream;

public class CliParse {

    protected static void parse(String[] s) {
        String[] command = Stream.of(s).map(String::toLowerCase).toArray(String[]::new);
        switch (command[0]) {
            case "exit":
            case "bye":
                Cedar.setActivityState(false);
                System.out.println("You have left the forest.");
                break;
            case "list":
                if (Cedar.internalTaskList.size() == 0) {
                    System.out.println("TodoList == empty");
                } else {
                    int index = 1;
                    for (String task : Cedar.internalTaskList) {
                        //System.out.println((index++)+"%d: "+task);
                        System.out.format("%d: "+task+"\n", index++);
                    }
                }
                break;
            case "add":
                // parse omit first command token
                String[] content = Arrays.copyOfRange(s, 1, s.length);
                Cedar.internalTaskList.add(String.join(" ", content));
                System.out.println("Added: " + Arrays.toString(content));
                break;
            default:
                Cedar.internalTaskList.add(String.join(" ", s));
                System.out.println("Added: " + Arrays.toString(s));
        }
    }
}
