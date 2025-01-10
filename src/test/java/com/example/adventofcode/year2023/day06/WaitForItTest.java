package com.example.adventofcode.year2023.day06;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WaitForItTest {

    private static Stream<Arguments> filepathsAndExpectedProductOfWinningCombinations() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day06/example_input", 288),
                Arguments.of("AdventOfCodeData/2023/day06/input", 840336)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedProductOfWinningCombinations")
    void calculateProductOfWinningCombinations(final String filename,
                                               final int expectedProductOfWinningCombinations) throws IOException {
        assertEquals(expectedProductOfWinningCombinations, WaitForIt.calculateProductOfWinningCombinations(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedWinningCombinations() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day06/example_input", 71503),
                Arguments.of("AdventOfCodeData/2023/day06/input", 41382569)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedWinningCombinations")
    void calculateWinningCombinations(final String filename,
                                      final int expectedWinningCombinations) throws IOException {
        assertEquals(expectedWinningCombinations, WaitForIt.calculateWinningCombinations(filename));
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedWinningCombinations")
    void calculateWinningCombinations2(final String filename,
                                       final int expectedWinningCombinations) throws IOException {
        assertEquals(expectedWinningCombinations, WaitForIt.calculateWinningCombinationsBruteForce(filename));

    }
}