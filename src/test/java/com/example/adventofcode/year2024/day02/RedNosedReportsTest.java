package com.example.adventofcode.year2024.day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedNosedReportsTest {
    private static Stream<Arguments> filepathsAndExpectedSafeReports() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day02/example_input", 2),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day02/input", 279)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSafeReports")
    void countSafeReports(final String filename,
                          final int expectedLocationIds) throws IOException {
        assertEquals(expectedLocationIds, RedNosedReports.countSafeReports(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedSafeReportsWithToleration() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day02/example_input", 4),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day02/input", 343)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSafeReportsWithToleration")
    void countSafeReportsWithToleration(final String filename,
                                        final int expectedLocationIds) throws IOException {
        assertEquals(expectedLocationIds, RedNosedReports.countReportsSafeWithToleration(filename));
    }
}