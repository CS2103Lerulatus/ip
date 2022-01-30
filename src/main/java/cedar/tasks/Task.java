package cedar.tasks;

public abstract class Task {
    protected String desc;
    protected boolean doneState;

    protected String taskType;

    public Task(String desc) {
        this.desc = desc;
        this.doneState = false;
        // works for all subclasses - tested: code coverage 100%
        this.taskType = this.getClass().getSimpleName();
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