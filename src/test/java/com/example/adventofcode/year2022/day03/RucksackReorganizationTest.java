package com.example.adventofcode.year2022.day03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RucksackReorganizationTest {
    private static Stream<Arguments> filePathsAndExpectedSumOfPriorities() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day03/example_input", 157),
                Arguments.of("AdventOfCodeData/2022/day03/input", 7428)
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedSumOfPriorities")
    void testCalculateSumOfPrioritiesForFile(final String filename,
                                             final int expectedSumOfPriorities) throws IOException {
        assertEquals(expectedSumOfPriorities, RucksackReorganization.calculateSumOfPriorities(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedSumOfPrioritiesForBatches() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day03/example_input", 70),
                Arguments.of("AdventOfCodeData/2022/day03/input", 2650)
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedSumOfPrioritiesForBatches")
    void calculateSumOfPrioritiesForBatches(final String filename,
                                            final int expectedSumOfPriorities) throws IOException {
        assertEquals(expectedSumOfPriorities, RucksackReorganization.calculateSumOfPrioritiesForBatches(filename));
    }
}