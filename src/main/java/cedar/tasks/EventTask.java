package cedar.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task {
    protected LocalDate timestamp;

    public EventTask(String desc, LocalDate timestamp) {
        super(desc);
        this.timestamp = timestamp;
    }

    public String prettyPrintTimestamp() {
        // considering reading date format from an usr-defined .config file
        return timestamp.format(DateTimeFormatter.ofPattern("(E) dd MMM yyyy"));
    }

    @Override
    public LocalDate getTemporalLabel() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "《E》 " + super.toString() + " At: [" + prettyPrintTimestamp() + "]";
    }
}
