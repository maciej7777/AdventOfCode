package com.example.adventofcode.year2024.day06;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuardGallivantTest {

    private static Stream<Arguments> filepathsAndExpectedGuardsPositions() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day06/example_input", 41),
                Arguments.of("AdventOfCodeData/2024/day06/input", 5177),
                Arguments.of("AdventOfCodeData/2024/day06/input2", 4711)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedGuardsPositions")
    void countGuardsPositions(final String filename,
                              final int expectedGuardsPositions) throws IOException {
        assertEquals(expectedGuardsPositions, GuardGallivant.countGuardsPositions(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedLoopPositions() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day06/example_input", 6),
                Arguments.of("AdventOfCodeData/2024/day06/input", 1686),
                Arguments.of("AdventOfCodeData/2024/day06/input2", 1562)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLoopPositions")
    void countLoopPositions(final String filename,
                            final int expectedLoopPositions) throws IOException {
        assertEquals(expectedLoopPositions, GuardGallivant.countLoopPositions(filename));
    }
}