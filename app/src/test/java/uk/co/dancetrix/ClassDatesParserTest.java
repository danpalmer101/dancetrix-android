package uk.co.dancetrix;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.domain.DateInterval;
import uk.co.dancetrix.util.ClassDatesParser;
import uk.co.dancetrix.util.ClassMenuParser;

import static org.junit.Assert.assertEquals;

public class ClassDatesParserTest {

    private static final String CSV =
                    "Format,Date,Start Time,Duration\n" +
                    "V1,02/10/2017,20:00,60\n" +
                    "V1,09/10/2017,19:00,30\n";

    @Test
    public void testParse() throws Exception {
        List<DateInterval> dates = ClassDatesParser.parse(CSV);

        Calendar start0 = Calendar.getInstance();
        start0.set(2017, Calendar.OCTOBER, 2, 20, 0, 0);
        start0.set(Calendar.MILLISECOND, 0);
        Calendar end0 = Calendar.getInstance();
        end0.set(2017, Calendar.OCTOBER, 2, 21, 0, 0);
        end0.set(Calendar.MILLISECOND, 0);
        Calendar start1 = Calendar.getInstance();
        start1.set(2017, Calendar.OCTOBER, 9, 19, 0, 0);
        start1.set(Calendar.MILLISECOND, 0);
        Calendar end1 = Calendar.getInstance();
        end1.set(2017, Calendar.OCTOBER, 9, 19, 30, 0);
        end1.set(Calendar.MILLISECOND, 0);

        assertEquals(start0.getTime(), dates.get(0).getStart());
        assertEquals(end0.getTime(), dates.get(0).getEnd());
        assertEquals(start1.getTime(), dates.get(1).getStart());
        assertEquals(end1.getTime(), dates.get(1).getEnd());
    }
}
