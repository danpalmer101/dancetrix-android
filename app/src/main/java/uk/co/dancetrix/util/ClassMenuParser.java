package uk.co.dancetrix.util;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.ClassMenu;

public class ClassMenuParser extends CsvParser {

    public static ClassMenu parse(String csvString) {
        String[] csvRows = getRows(csvString);

        List<ClassMenu> classMenus = new ArrayList<>();

        for (String csvRow : csvRows) {
            String[] csvColumns = getColumns(csvRow);

            String format = csvColumns[0];

            switch (format) {
                case "V1":
                    ClassMenu classMenu = parseFormat1(csvColumns);
                    if (classMenu != null) {
                        classMenus.add(classMenu);
                    }
                default:
                    Log.w("CSV", "Unrecognised ClassMenu CSV format: " + format);
            }
        }

        return new ClassMenu("Classes", merge(classMenus));
    }

    /*
     * Merge all the single branch ClassMenus into a tree with common parents
     */
    private static List<ClassMenu> merge(List<ClassMenu> classMenus) {
        Map<String, ClassMenu> mergedClassMenus = new LinkedHashMap<>();

        for (ClassMenu classMenu : classMenus) {
            ClassMenu existingClassMenu = mergedClassMenus.get(classMenu.getName());

            if (existingClassMenu == null || classMenu.getClassDetails() != null) {
                // Unique root node (so far) or a leaf node, add it to the map
                mergedClassMenus.put(classMenu.getName(), classMenu);
            } else {
                // Common root node found, merge the children and add to the existing menu item in the map
                List<ClassMenu> children = new ArrayList<>();
                if (existingClassMenu.getChildren() != null) {
                    children.addAll(existingClassMenu.getChildren());
                }
                if (classMenu.getChildren() != null) {
                    children.addAll(classMenu.getChildren());
                }
                mergedClassMenus.put(existingClassMenu.getName(),
                                     new ClassMenu(existingClassMenu.getName(), merge(children)));
            }
        }

        List<ClassMenu> menus = new ArrayList<>(mergedClassMenus.values());

        Collections.sort(menus, new Comparator<ClassMenu>() {
            @Override
            public int compare(ClassMenu a, ClassMenu b) {
                return a.getName().compareTo(b.getName());
            }
        });

        return menus;
    }

    /*
     * Create a single branch ClassMenu tree for the CSV row
     */
    private static ClassMenu parseFormat1(String[] csvColumns) {
        // Parse each CSV field
        String name = csvColumns[1].trim();
        String[] menuPath = csvColumns[2].split("\\|");
        for (int i = 0; i < menuPath.length; i++) {
            menuPath[i] = menuPath[i].trim();
        }
        String datesLocation = csvColumns[3].trim();
        String descriptionLocation = csvColumns[4].trim();

        boolean allowIndividualBookings = Arrays.asList("yes", "true", "1").contains(csvColumns[5].trim().toLowerCase());

        // Create the class
        ClassDetails classDetails = new ClassDetails(
                TextUtils.join(",", csvColumns),
                TextUtils.join(" > ", menuPath),
                name,
                datesLocation,
                descriptionLocation,
                allowIndividualBookings);

        ClassMenu classMenu = new ClassMenu(name, classDetails);

        // Keep embedding class level in a parent for each element of the menu path
        for (int i = menuPath.length - 1; i >= 0; i--) {
            classMenu = new ClassMenu(menuPath[i], Collections.singletonList(classMenu));
        }

        return classMenu;
    }

}
