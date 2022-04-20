package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class TestRunData {
    public static Stream<Arguments> findAllElementsOfArray() {
        List<String> expectedIds = new ArrayList<>();
        expectedIds.add("T1005, ,T1004,");
        expectedIds.add(" T1008, ,T1004, ,T1005,");

        List<String> expectedIds2 = new ArrayList<>();
        expectedIds2.add("T1005, ,Т1004,");                 // in T1004 Cyrillic "T"
        expectedIds2.add(" T1008, ,T1004, ,T1005,");

        List<String> expectedIds3 = new ArrayList<>();
        expectedIds3.add("Т1004, ,T1006, T1007,");          // in T1004 Cyrillic "T"
        expectedIds3.add(" T1006, ,T1001, ,");

        return Stream.of(
                Arguments.of(
                        "T1004, , T1005,",
                        new String[]{
                        "T1005, ,T1004,",
                        " T1008, ,T1004, ,T1005,",
                        "T1009, ,T1005,"
                        },
                        expectedIds
                ),
                Arguments.of(
                        "T1004, , T1005,",
                        new String[]{
                                "T1005, ,Т1004,",           // in T1004 Cyrillic "T"
                                " T1008, ,T1004, ,T1005,",
                                "T1009, ,T1005,"
                        },
                        expectedIds2
                ),
                Arguments.of(
                        ",Т1006,",                          // in T1006 Cyrillic "Т"
                        new String[]{
                                "Т1004, ,T1006, T1007,",    // in T1004 Cyrillic "T"
                                " T1006, ,T1001, ,",
                                "T1009, ,T1005,",
                        },
                        expectedIds3
                )
        );
    }

    public static Stream<Arguments> writeToCsv() {
        return Stream.of(
                Arguments.of(
                        "Т1004, , T1005,",                  // in T1004 Cyrillic "T"
                        new String[]{
                                "T1005, ,T1004,",
                                " T1008, ,T1004, ,T1005,",
                                "T1009, ,T1005,"
                        },
                        new String[]{
                                "id,taskId",
                                "1,T1005, ,T1004,",
                                "2, T1008, ,T1004, ,T1005,"
                        }
                ),
                Arguments.of(
                        ",Т1006,",                          // in T1006 Cyrillic "T"
                        new String[]{
                                "T1004, ,Т1006, T1007,",    // in T1006 Cyrillic "T"
                                " T1006, ,T1001, ,",
                                "T1009, ,T1005,",
                        },
                        new String[]{
                                "id,taskId",
                                "1,T1004, ,Т1006, T1007,",
                                "2, T1006, ,T1001, ,"
                        }
                )
        );
    }
}
