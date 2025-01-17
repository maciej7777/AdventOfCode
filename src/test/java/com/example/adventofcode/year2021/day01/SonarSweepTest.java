package com.example.adventofcode.year2021.day01;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SonarSweepTest {

    private static Stream<Arguments> filepathsAndExpectedDepthIncreases() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2021/day01/example_input", 7),
                Arguments.of("AdventOfCodeData/2021/day01/input", 1832)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedDepthIncreases")
    void testCalculateDepthIncreases(final String filename,
                                     final int expectedDepthIncreases) throws IOException {
        assertEquals(expectedDepthIncreases, SonarSweep.calculateDepthIncreases(filename));
    }

    private static Stream<Arguments> filepathsSlidingWindowSizeAndExpectedDepthIncreases() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2021/day01/example_input", 1, 7),
                Arguments.of("AdventOfCodeData/2021/day01/example_input", 3, 5),
                Arguments.of("AdventOfCodeData/2021/day01/input", 1, 1832),
                Arguments.of("AdventOfCodeData/2021/day01/input", 3, 1858)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsSlidingWindowSizeAndExpectedDepthIncreases")
    void testCalculateDepthIncreasesWithSlidingWindow(final String filename,
                                                      final int slidingWindowSize,
                                                      final int expectedDepthIncreases) throws IOException {
        assertEquals(expectedDepthIncreases, SonarSweep.calculateDepthIncreasesWithSlidingWindow(filename, slidingWindowSize));
    }
}