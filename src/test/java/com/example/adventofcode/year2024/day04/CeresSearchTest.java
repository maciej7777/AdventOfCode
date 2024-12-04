package com.example.adventofcode.year2024.day04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CeresSearchTest {

    private static Stream<Arguments> filepathsAndExpectedSafeReports() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/example_input", 18),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/input", 2464)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSafeReports")
    void calculateInstructionsResult(final String filename,
                                     final int expectedInstructionsResult) throws IOException {
        assertEquals(expectedInstructionsResult, CeresSearch.countXMASWords(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedSafeReportsWithToleration() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/example_input", 9),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/input", 1982)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSafeReportsWithToleration")
    void calculateInstructionsResultWithIgnoringConditions(final String filename,
                                                           final int expectedInstructionsResult) throws IOException {
        assertEquals(expectedInstructionsResult, CeresSearch.countXShapedMASWords(filename));
    }
}