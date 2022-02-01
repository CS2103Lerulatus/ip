package cedar.commands;

import cedar.Cedar;
import cedar.tasks.TodoTask;

import java.util.Arrays;

public class TodoCommand extends CommandCategory {
    public static final String[] ALIASES = {"todo"};

    public TodoCommand() {
        super(ALIASES, "Add a todo task");
    }

    @Override
    public void execute(String[] command) {
        String content = String.join(" ", Arrays.copyOfRange(command, 1, command.length));
        Cedar.getInternalTaskList().add(new TodoTask(content));
        System.out.println("Added a new todo task: " + content);
        System.out.format("You currently have %d task(s)\n", Cedar.getInternalTaskList().size());
        Cedar.writeInternalListToJsonHandler();
    }
}
