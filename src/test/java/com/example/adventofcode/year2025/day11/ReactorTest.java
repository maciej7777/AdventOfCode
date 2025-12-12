package com.example.adventofcode.year2025.day11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReactorTest {

    private static Stream<Arguments> filepathsAndExpectedWaysOut() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day11/example_input", 5),
                Arguments.of("AdventOfCodeData/2025/day11/input", 615)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedWaysOut")
    void countWaysOut(final String filename,
                      final long expectedWaysOut) throws IOException {
        assertEquals(expectedWaysOut, Reactor.countWaysOut(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedWaysOutThroughNodesSubset() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day11/example_input2", 2),
                Arguments.of("AdventOfCodeData/2025/day11/input", 303012373210128L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedWaysOutThroughNodesSubset")
    void countWaysOutThroughNodesSubset(final String filename,
                                        final long expectedWaysOut) throws IOException {
        assertEquals(expectedWaysOut, Reactor.countWaysOutThroughNodesSubset(filename));
    }
}