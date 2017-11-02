package uk.co.dancetrix.util;

import java.util.ArrayList;

public class CsvParser {

    protected static String[] getRows(String csv) {
        String cleanCsv = cleanCsv(csv);

        String[] rawRows = cleanCsv.split("\n");

        ArrayList<String> rows = new ArrayList<>();

        for (int i = 1; i < rawRows.length; i++) {
            String row = rawRows[i];
            if (row != null && row.trim().length() > 0) {
                rows.add(row);
            }
        }

        return rows.toArray(new String[rows.size()]);
    }

    protected static String[] getColumns(String csvRow) {
        return csvRow.split(",");
    }

    protected static String cleanCsv(String csv) {
        return csv.replace("\r\n", "\n") // CR+LF -> LF
                  .replace("\n\r", "\n") // LF+CR -> LF
                  .replace("\r", "\n");  // CR    -> LF
    }

}
