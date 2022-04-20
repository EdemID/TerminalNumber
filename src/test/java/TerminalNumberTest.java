import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TerminalNumberTest {

    @Test
    public void findAllElementsOfArray() {
        String stringWithIds = "T1004, , TEWAD, T1WER, T43234, T234F, T1005, T2342Q, T1999,T1213T1232"; // in T1999 Latin "T"
        String[] arrayOfStringsWitIds = new String[]{
                "T1005, ,T1004,Т1999",          // in T1999 Cyrillic "T"
                " T1008, ,Т1004, ,T1005,T1999", // in T1004 Cyrillic "T"
                "T1009, ,T1005,T1004,",
                "T1004, ,T1006, T1007,",
                "T1006, ,T1001, ,",
                " T1999, T1004,T1005 "          // in T1999 Latin "T"
        };
        List<String> foundElements = TerminalNumber.findAllElementsOfArray(stringWithIds, arrayOfStringsWitIds);

        List<String> expectedIds = new ArrayList<>();
        expectedIds.add("T1005, ,T1004,Т1999");          // in T1999 Cyrillic "T"
        expectedIds.add(" T1008, ,Т1004, ,T1005,T1999"); // in T1004 Cyrillic "T"
        expectedIds.add(" T1999, T1004,T1005 ");         // in T1999 Latin "T"

        Assert.assertTrue("Expected id not found in array element", foundElements.containsAll(expectedIds));
    }

    @Test
    public void writeToCsv() {
        String stringWithIds = "T1004, , TEWAD, T1WER, T43234, T234F, T1005";
        String[] arrayOfStringsWitIds = new String[]{
                "T1005, ,T1004,",
                " T1008, ,T1004, ,T1005,",
                "T1009, ,T1005,"
        };
        TerminalNumber.findAllElementsOfArray(stringWithIds, arrayOfStringsWitIds);

        int i = 0;
        String[] expectedIds = new String[]{
                "id,taskId",
                "1,T1005, ,T1004,",
                "2, T1008, ,T1004, ,T1005,"
        };
        String filename = "build" + File.separator + "taskId.csv";
        try(Stream<String> stream = Files.lines(Paths.get(filename))) {
            for (String line : (Iterable<String>) stream::iterator) {
                Assert.assertEquals("Incorrect id entry in csv", line, expectedIds[i]);
                ++i;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}