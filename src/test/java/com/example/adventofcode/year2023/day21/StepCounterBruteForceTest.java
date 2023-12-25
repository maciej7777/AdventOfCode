package com.example.adventofcode.year2023.day21;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StepCounterBruteForceTest {

    private static Stream<Arguments> filepathsAndExpectedFinalGardenPlotsCount() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day21/example_input", 6, 16),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day21/input", 64, 3617)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFinalGardenPlotsCount")
    void countFinalGardenPlots(final String filename,
                               final int steps,
                               final int expectedFinalGardenPlotsCount) throws IOException {
        assertEquals(expectedFinalGardenPlotsCount, StepCounterBruteForce.countFinalGardenPlots(filename, steps));
    }

    private static Stream<Arguments> filepathsAndExpectedFinalGardenPlotsCountInInfiniteMap() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day21/example_input", 6, 16),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day21/example_input", 10, 50),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day21/example_input", 50, 1594),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day21/example_input", 100, 6536)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFinalGardenPlotsCountInInfiniteMap")
    void countFinalGardenPlotsInInfiniteMap(final String filename,
                                            final int steps,
                                            final long expectedFinalGardenPlotsCount) throws IOException {
        assertEquals(expectedFinalGardenPlotsCount, StepCounterBruteForce.countFinalGardenPlotsInInfiniteMap(filename, steps));
    }
}