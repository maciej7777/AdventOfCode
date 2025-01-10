package com.example.adventofcode.year2023.day21;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StepCounterHardcodedTest {

    private static Stream<Arguments> filepathsAndExpectedFinalGardenPlotsCount() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day21/example_input", 6, 16),
                Arguments.of("AdventOfCodeData/2023/day21/input", 64, 3617)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFinalGardenPlotsCount")
    void countFinalGardenPlots(final String filename,
                               final int steps,
                               final int expectedFinalGardenPlotsCount) throws IOException {
        assertEquals(expectedFinalGardenPlotsCount, StepCounterHardcoded.countFinalGardenPlots(filename, steps));
    }

    private static Stream<Arguments> filepathsAndExpectedFinalGardenPlotsCountInInfiniteMap() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day21/example_input", 6, 16),
                Arguments.of("AdventOfCodeData/2023/day21/example_input", 10, 50),
                Arguments.of("AdventOfCodeData/2023/day21/example_input", 50, 1594),
                Arguments.of("AdventOfCodeData/2023/day21/example_input", 100, 6536),
                Arguments.of("AdventOfCodeData/2023/day21/example_input", 500, 167004),
                Arguments.of("AdventOfCodeData/2023/day21/example_input", 1000, 668697)
                //Arguments.of("AdventOfCodeData/2023/day21/example_input", 5000, 16733044)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFinalGardenPlotsCountInInfiniteMap")
    void countFinalGardenPlotsInInfiniteMap(final String filename,
                                            final int steps,
                                            final long expectedFinalGardenPlotsCount) throws IOException {
        assertEquals(expectedFinalGardenPlotsCount, StepCounterHardcoded.countFinalGardenPlotsInInfiniteMap(filename, steps));
    }

    private static Stream<Arguments> filepathsAndExpectedFinalGardenPlotsCountInInfiniteMapExtended() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day21/input", 26501365, 596857397104703L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFinalGardenPlotsCountInInfiniteMapExtended")
    void countFinalGardenPlotsInInfiniteMapExtended(final String filename,
                                            final int steps,
                                            final long expectedFinalGardenPlotsCount) throws IOException {
        assertEquals(expectedFinalGardenPlotsCount, StepCounterHardcoded.countFinalGardenPlotsInInfiniteMapExtended(filename, steps));
    }
}