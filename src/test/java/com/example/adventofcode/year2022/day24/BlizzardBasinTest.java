package com.example.adventofcode.year2022.day24;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BlizzardBasinTest {
    private static Stream<Arguments> filePathsAndExpectedTimeToLeaveTheValley() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day24/example_input",
                        18
                ),
                Arguments.of("AdventOfCodeData/2022/day24/input",
                        279
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedTimeToLeaveTheValley")
    void testCalculateTimeToLeaveTheValley(final String filename,
                           final int expectedTimeToLeaveTheValley) throws IOException {
        assertEquals(expectedTimeToLeaveTheValley, BlizzardBasin.calculateTimeToLeaveTheValley(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedTimeToReachAllThePoints() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day24/example_input",
                        54
                ),
                Arguments.of("AdventOfCodeData/2022/day24/input",
                        762
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedTimeToReachAllThePoints")
    void testCalculateTimeToReachAllThePoints(final String filename,
                                              final int expectedTimeToReachAllThePoints) throws IOException {
        assertEquals(expectedTimeToReachAllThePoints, BlizzardBasin.calculateTimeToReachAllThePoints(filename));
    }
}