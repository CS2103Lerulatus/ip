package cedar.tasks;

public class EventTask extends Task {
    protected String timestamp;

    public EventTask(String desc, String timestamp) {
        super(desc);
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "《E》 " + super.toString() + " @(" + timestamp + ")";
    }
}
