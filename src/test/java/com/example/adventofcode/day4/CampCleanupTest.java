package com.example.adventofcode.day4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

class CampCleanupTest {
    private static final String FILENAME = "src/main/java/com.example.adventofcode.day4/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com.example.adventofcode.day4/example_input";

    private static Stream<Arguments> filepathsAndExpectedNumberOfFullyInclusiveIntervals() {
        return Stream.of(
                Arguments.of("src/main/java/com.example.adventofcode.day4/example_input", 2),
                Arguments.of("src/main/java/com.example.adventofcode.day4/input", 571)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedNumberOfFullyInclusiveIntervals")
    public void testCalculateFullyInclusiveIntervals(final String filename,
                                                     final int expectedPairs) throws IOException {
        Assertions.assertEquals(expectedPairs, CampCleanup.countFullyInclusiveIntervals(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedNumberOfIntervalIntersections() {
        return Stream.of(
                Arguments.of("src/main/java/com.example.adventofcode.day4/example_input", 4),
                Arguments.of("src/main/java/com.example.adventofcode.day4/input", 917)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedNumberOfIntervalIntersections")
    public void countIntervalsIntersections(final String filename,
                                                     final int expectedPairs) throws IOException {
        Assertions.assertEquals(expectedPairs, CampCleanup.countIntervalsIntersections(filename));
    }
}