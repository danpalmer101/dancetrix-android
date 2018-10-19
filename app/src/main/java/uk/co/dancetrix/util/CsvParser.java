package uk.co.dancetrix.util;

import java.util.ArrayList;

class CsvParser {

    static String[] getRows(String csv) {
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

    static String[] getColumns(String csvRow) {
        return csvRow.split(",");
    }

    private static String cleanCsv(String csv) {
        return csv.replace("\r\n", "\n") // CR+LF -> LF
                  .replace("\n\r", "\n") // LF+CR -> LF
                  .replace("\r", "\n");  // CR    -> LF
    }

}
