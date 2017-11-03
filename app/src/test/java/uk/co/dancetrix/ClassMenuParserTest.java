package uk.co.dancetrix;

import org.junit.Test;

import java.util.Arrays;

import uk.co.dancetrix.domain.ClassDetails;
import uk.co.dancetrix.domain.ClassMenu;
import uk.co.dancetrix.util.ClassMenuParser;

import static org.junit.Assert.*;

public class ClassMenuParserTest {

    private static final String CSV =
            "Format,Name,Menu Structure,Dates File,Description File,Individual Date Booking\n" +
            "V1,Class 1, Category A|Type 1,a1.csv,b1.txt,true\n" +
            "V1,Class 2, Category A|Type 2,a2.csv,b2.txt,false\n" +
            "V1,Class 3, Category B|Type 1,a3.csv,b3.txt,true\n" +
            "V1,Class 4, Category B|Type 2,a4.csv,b4.txt,false\n";

    @Test
    public void testParse() throws Exception {
        ClassMenu menu = ClassMenuParser.parse(CSV);

        assertEquals("Classes", menu.getName());

        ClassMenu catA = menu.getChildren().get(0);
        ClassMenu catAType1 = catA.getChildren().get(0);
        ClassMenu catAType2 = catA.getChildren().get(1);

        ClassMenu catB = menu.getChildren().get(1);
        ClassMenu catBType1 = catB.getChildren().get(0);
        ClassMenu catBType2 = catB.getChildren().get(1);

        ClassMenu class1 = catAType1.getChildren().get(0);
        ClassMenu class2 = catAType2.getChildren().get(0);
        ClassMenu class3 = catBType1.getChildren().get(0);
        ClassMenu class4 = catBType2.getChildren().get(0);

        assertEquals("Category A", catA.getName());
        assertEquals("Type 1", catAType1.getName());
        assertEquals("Class 1", class1.getName());
        assertEquals("Class 1", class1.getClassDetails().getName());
        assertEquals("Type 2", catAType2.getName());
        assertEquals("Class 2", class2.getName());
        assertEquals("Class 2", class2.getClassDetails().getName());

        assertEquals("Category B", catB.getName());
        assertEquals("Type 1", catBType1.getName());
        assertEquals("Class 3", class3.getName());
        assertEquals("Class 3", class3.getClassDetails().getName());
        assertEquals("Type 2", catBType2.getName());
        assertEquals("Class 4", class4.getName());
        assertEquals("Class 4", class4.getClassDetails().getName());
    }
}
