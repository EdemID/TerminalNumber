import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class TerminalNumberTest {

    private static final Logger logger = LoggerFactory.getLogger(TerminalNumberTest.class);

    @ParameterizedTest(name = "{displayName} [{arguments}]")
    @DisplayName("Проверка поиска нужных элементов")
    @MethodSource("util.TestRunData#findAllElementsOfArray")
    void findAllElementsOfArray(String stringWithIds, String[] arrayOfStringsWitIds, List<String> expectedIds) {
        List<String> foundElements = TerminalNumber.findAllElementsOfArray(stringWithIds, arrayOfStringsWitIds);
        Assertions.assertTrue(foundElements.containsAll(expectedIds), "Expected id not found in array element. Actual " + foundElements + ", but expected" + expectedIds);
    }

    @ParameterizedTest(name = "{displayName} [{arguments}]")
    @DisplayName("Проверка записи элементов в csv файл и его содержимого")
    @MethodSource("util.TestRunData#writeToCsv")
    public void writeToCsv(String stringWithIds, String[] arrayOfStringsWitIds, String[] expectedIds) {
        TerminalNumber.findAllElementsOfArray(stringWithIds, arrayOfStringsWitIds);

        int i = 0;
        String filename = "build" + File.separator + "taskId.csv";
        try(Stream<String> stream = Files.lines(Paths.get(filename), Charset.defaultCharset())) {
            for (String line : (Iterable<String>) stream::iterator) {
                Assertions.assertEquals(line, expectedIds[i], "Incorrect id entry in csv");
                ++i;
            }
        } catch (IOException e) {
            logger.info("No such file: " + e.getMessage());
            Assertions.fail("No such file: " + e.getMessage());
        }
    }
}