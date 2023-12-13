package com.example.adventofcode.year2022.day16;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProboscideaVolcaniumTest {
    private static Stream<Arguments> filePathsAndRowsAndExpectedMaxPressureReduction() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day16/example_input",
                        1651
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day16/input",
                        2056
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndRowsAndExpectedMaxPressureReduction")
    void testObtainMaxPressureReduction(final String filename,
                                  final long expectedMaxPressureReduction) throws IOException {
        assertEquals(expectedMaxPressureReduction, ProboscideaVolcanium.obtainMaxPressureReduction(filename));
    }

    private static Stream<Arguments> filePathsAndRowsAndExpectedMaxPressureReductionWithElephants() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day16/example_input",
                        1707
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day16/input",
                        2513
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndRowsAndExpectedMaxPressureReductionWithElephants")
    void testObtainMaxPressureReductionWithElephants(final String filename,
                                   final int expectedMaxPressureReductionWithElephants) throws IOException {
        assertEquals(expectedMaxPressureReductionWithElephants, ProboscideaVolcanium.obtainMaxPressureReductionWithElephant(filename));
    }
}