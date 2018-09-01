package uk.co.dancetrix.util;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.co.dancetrix.domain.UniformGroup;
import uk.co.dancetrix.domain.UniformItem;

public class UniformParser extends CsvParser {

    public static List<UniformGroup> parse(String csvString) {
        String[] csvRows = getRows(csvString);

        List<Pair<String, UniformItem>> uniformItems = new ArrayList<>();

        for (String csvRow : csvRows) {
            String[] csvColumns = getColumns(csvRow);

            String format = csvColumns[0];

            switch (format) {
                case "V1":
                    Pair<String, UniformItem> uniformItem = parseFormat1(csvColumns);
                    uniformItems.add(uniformItem);
                default:
                    Log.w("CSV", "Unrecognised uniform CSV format: " + format);
            }
        }

        return group(uniformItems);
    }

    /*
     * Merge all the items into groups
     */
    private static List<UniformGroup> group(List<Pair<String, UniformItem>> uniformItems) {
        Map<String, List<UniformItem>> uniformGroupMap = new LinkedHashMap<>();

        for (Pair<String, UniformItem> uniformItem : uniformItems) {
            List<UniformItem> items;
            if (uniformGroupMap.containsKey(uniformItem.first)) {
                items = uniformGroupMap.get(uniformItem.first);
            } else {
                items = new ArrayList<>();
                uniformGroupMap.put(uniformItem.first, items);
            }
            items.add(uniformItem.second);
        }

        List<UniformGroup> uniformGroups = new ArrayList<>();

        for (Map.Entry<String, List<UniformItem>> uniformGroup : uniformGroupMap.entrySet()) {
            uniformGroups.add(new UniformGroup(uniformGroup.getKey(), uniformGroup.getValue()));
        }

        return uniformGroups;
    }

    /**
     * Create a UniformItem for each row, alogn with the name of the group it should belong to
     */
    private static Pair<String, UniformItem> parseFormat1(String[] csvColumns) {
        // Parse each CSV field
        String section = csvColumns[1].trim();
        String id = csvColumns[2].trim();
        String name = csvColumns[3].trim();
        String[] sizes;
        if (csvColumns.length > 4 && !csvColumns[4].trim().isEmpty()) {
            sizes = csvColumns[4].split("\\|");
            for (int i = 0; i < sizes.length; i++) {
                sizes[i] = sizes[i].trim();
            }
        } else {
            sizes = new String[0];
        }

        return new Pair<>(section, new UniformItem(id, name, Arrays.asList(sizes)));
    }

}
