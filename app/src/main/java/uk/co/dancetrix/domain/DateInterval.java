package uk.co.dancetrix.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateInterval implements Serializable {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("EEEE, MMM d, yyyy", Locale.getDefault());
    private static final SimpleDateFormat TIME_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.getDefault());

    private final Date start;
    private final Date end;

    public DateInterval(Date start, long durationMillis) {
        this.start = start;
        this.end = new Date(start.getTime() + durationMillis);
    }

    public Date getStart() {
        return this.start;
    }

    public Date getEnd() {
        return this.end;
    }

    public String asText() {
        return String.format("%s (%s)", dateText(this.start), asTimeOnlyText());
    }

    public String asTimeOnlyText() {
        return String.format("%s to %s", timeText(this.start), timeText(this.end));
    }

    private String dateText(Date date) {
        return DATE_FORMAT.format(date);
    }

    private String timeText(Date date) {
        return TIME_FORMAT.format(date);
    }

    @Override
    public String toString() {
        return asText();
    }

}
