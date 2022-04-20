import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class TerminalNumber {

    private static final Logger logger = LoggerFactory.getLogger(TerminalNumber.class);
    private static String filename = "build" + File.separator + "taskId.csv";
    private static String regex = "T\\d{4}";

    /**
     * Find which elements of the array satisfy the condition: all id from the string
     * string are contained in an array element.
     *
     * @param    string    strings with ids
     * @param    array     array of strings with ids
     * @return            found elements
     */
    public static List<String> findAllElementsOfArray(String string, String[] array) {
        List<String> idsFromString = extractIdFromString(string);
        List<String> foundElements = new ArrayList<>();

        for (String stringWithIdsFromArray : array) {
            List<String> idsFromArray = extractIdFromString(stringWithIdsFromArray);
            if (idsFromArray.containsAll(idsFromString)) {
                foundElements.add(stringWithIdsFromArray);
            }
        }
        writeToCsv(foundElements);
        return foundElements;
    }

    /**
     * Extract id from String
     *
     * @param    string  string with ids
     * @return           list of ids
     */
    private static List<String> extractIdFromString(String string) {
        return Arrays.stream(string.split(","))
                .map(TerminalNumber::modElementByCondition)
                .filter(s -> s.matches(regex))
                .collect(Collectors.toList());
    }

    /**
     * Condition: replace Cyrillic "T" with Latin "T" and remove space on sides
     */
    private static String modElementByCondition(String s) {
        return s.replaceAll("Т", "T").trim();
    }

    private static void writeToCsv(List<String> foundElements) {
        StringBuilder sb = new StringBuilder();
        sb.append("id");
        sb.append(',');
        sb.append("taskId");
        sb.append('\n');

        int count = 0;
        try (PrintWriter pw = new PrintWriter(new File(filename))) {
            for (String foundElement : foundElements) {
                sb.append(++count);
                sb.append(',');
                sb.append(foundElement);
                sb.append('\n');
                pw.write(sb.toString());
                sb.setLength(0);
            }
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        }
    }
}
