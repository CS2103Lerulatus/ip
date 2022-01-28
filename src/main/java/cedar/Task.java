package cedar;

public class Task {
    protected String desc;
    protected boolean doneState;

    public Task(String desc) {
        this.desc = desc;
        this.doneState = false;
    }

    public String getStatusIcon() {
        // wow unicode works
        return (doneState ? "✓" : "☐"); // mark done task with X
    }

    public void setState(boolean state) {
        this.doneState = state;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getStatusIcon(), desc);
    }
}