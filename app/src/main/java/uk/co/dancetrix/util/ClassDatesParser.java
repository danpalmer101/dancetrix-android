package uk.co.dancetrix.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.dancetrix.domain.DateInterval;

public class ClassDatesParser extends CsvParser {

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public static List<DateInterval> parse(String csvString) {
        String[] csvRows = getRows(csvString);

        List<DateInterval> dateIntervals = new ArrayList<>();

        for (String csvRow : csvRows) {
            String[] csvColumns = getColumns(csvRow);

            String format = csvColumns[0];

            switch (format) {
                case "V1":
                    DateInterval dateInterval = parseFormat1(csvColumns);
                    if (dateInterval != null) {
                        dateIntervals.add(dateInterval);
                    }
                    break;
                default:
                    Log.w("CSV", "Unrecognised Class Date CSV format: " + format);
            }
        }

        return dateIntervals;
    }

    private static DateInterval parseFormat1(String[] csvColumns) {
        String date = csvColumns[1].trim();
        String time = csvColumns[2].trim();
        Long duration = Long.parseLong(csvColumns[3].trim());

        try {
            return new DateInterval(DATE_FORMAT.parse(date + " " + time), duration * 60 * 1000);
        } catch (ParseException e) {
            Log.w("CSV", "Invalid date format: " + date + " " + time);
            return null;
        }
    }

}
