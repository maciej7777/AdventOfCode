package com.example.adventofcode.year2022.day04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CampCleanupTest {
    private static Stream<Arguments> filepathsAndExpectedNumberOfFullyInclusiveIntervals() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day04/example_input", 2),
                Arguments.of("AdventOfCodeData/2022/day04/input", 571)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedNumberOfFullyInclusiveIntervals")
    void testCalculateFullyInclusiveIntervals(final String filename,
                                              final int expectedPairs) throws IOException {
        assertEquals(expectedPairs, CampCleanup.countFullyInclusiveIntervals(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedNumberOfIntervalIntersections() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day04/example_input", 4),
                Arguments.of("AdventOfCodeData/2022/day04/input", 917)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedNumberOfIntervalIntersections")
    void testCountIntervalsIntersections(final String filename,
                                         final int expectedPairs) throws IOException {
        assertEquals(expectedPairs, CampCleanup.countIntervalsIntersections(filename));
    }
}