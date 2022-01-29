package cedar.tasks;

public class DeadlineTask extends Task {
    protected String duedate;

    public DeadlineTask(String desc, String duedate) {
        super(desc);
        this.duedate = duedate;
    }

    @Override
    public String toString() {
        return "《D》 " + super.toString() + " (By: " + duedate + ")";
    }
}
