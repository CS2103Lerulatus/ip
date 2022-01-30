package cedar.tasks;

import java.time.LocalDate;

public class TodoTask extends Task {

    public TodoTask(String desc) {
        super(desc);
    }

    @Override
    public LocalDate getTemporalLabel() {
        return null;
    }

    @Override
    public String toString() {
        return "《T》 " + super.toString();
    }
}
