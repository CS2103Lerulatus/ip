package cedar.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task {
    protected LocalDate duedate;

    public DeadlineTask(String desc, LocalDate duedate) {
        super(desc);
        this.duedate = duedate;
    }

    public String prettyPrintDuedate() {
        // considering reading date format from an usr-defined .config file
        return duedate.format(DateTimeFormatter.ofPattern("(E) dd MMM yyyy"));
    }

    @Override
    public LocalDate getTemporalLabel() {
        return duedate;
    }

    @Override
    public String toString() {
        return "《D》 " + super.toString() + " By: [" + prettyPrintDuedate() + "]";
    }
}
