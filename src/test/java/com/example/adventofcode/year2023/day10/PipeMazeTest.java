package com.example.adventofcode.year2023.day10;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PipeMazeTest {
    private static Stream<Arguments> filepathsAndExpectedHalfOfCircuit() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day10/example_input", 8),
                Arguments.of("AdventOfCodeData/2023/day10/input", 6838)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedHalfOfCircuit")
    void calculateHalfOfLoopCircuit(final String filename,
                                    final int expectedHalfOfCircuit) throws IOException {
        assertEquals(expectedHalfOfCircuit, PipeMaze.calculateHalfOfLoopCircuit(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedLoopArea() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day10/example_input2", 4),
                Arguments.of("AdventOfCodeData/2023/day10/example_input3", 8),
                Arguments.of("AdventOfCodeData/2023/day10/example_input4", 10),
                Arguments.of("AdventOfCodeData/2023/day10/input", 451)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLoopArea")
    void calculateLoopArea(final String filename,
                           final int expectedLoopArea) throws IOException {
        assertEquals(expectedLoopArea, PipeMaze.calculateLoopArea(filename));
    }
}