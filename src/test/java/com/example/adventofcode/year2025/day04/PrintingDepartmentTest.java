package com.example.adventofcode.year2025.day04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintingDepartmentTest {

    private static Stream<Arguments> filepathsAndExpectedAccessibleRollsOfPaper() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day04/example_input", 13),
                Arguments.of("AdventOfCodeData/2025/day04/input", 1428)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedAccessibleRollsOfPaper")
    void countAccessibleRollsOfPaper(final String filename,
                                     final long expectedAccessibleRollsOfPaper) throws IOException {
        assertEquals(expectedAccessibleRollsOfPaper, PrintingDepartment.countAccessibleRollsOfPaper(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedAccessibleRollsOfPaperRecursively() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day04/example_input", 43),
                Arguments.of("AdventOfCodeData/2025/day04/input", 8936)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedAccessibleRollsOfPaperRecursively")
    void countAccessibleRollsOfPaperRecursively(final String filename,
                                                final long expectedAccessibleRollsOfPaperRecursively) throws IOException {
        assertEquals(expectedAccessibleRollsOfPaperRecursively, PrintingDepartment.countAccessibleRollsOfPaperRecursively(filename));

    }
}