package cedar.commands;

import cedar.Cedar;
import cedar.tasks.Task;

public class ListCommand extends CommandCategory {
    public static final String[] ALIASES = {"ls", "list"};

    public ListCommand() {
        super(ALIASES, "List");
    }

    @Override
    public void execute(String[] args) {
        if (Cedar.getInternalTaskList().size() == 0) {
            System.out.println("✨ TodoList is empty");
        } else {
            int index = 1;
            for (Task task : Cedar.getInternalTaskList()) {
                //System.out.println((index++)+"%d: "+task);
                System.out.format("%d: " + task.toString() + "\n", index++);
            }
        }
    }
}
