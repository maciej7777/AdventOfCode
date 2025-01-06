package com.example.adventofcode.year2024.day17;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronospatialComputerTest {
    private static Stream<Arguments> filepathsAndExpectedProgramOutput() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day17/example_input",
                        List.of(4, 6, 3, 5, 6, 3, 5, 2, 1, 0)),
                Arguments.of("AdventOfCodeData/2024/day17/example_input2",
                        List.of(0, 3, 5, 4, 3, 0)),
                Arguments.of("AdventOfCodeData/2024/day17/input",
                        List.of(4, 3, 7, 1, 5, 3, 0, 5, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedProgramOutput")
    void calculateProgramOutput(final String filename,
                                final List<Integer> expectedProgramOutput) throws IOException {
        assertEquals(expectedProgramOutput, ChronospatialComputer.calculateProgramOutput(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedMinimalInitialValue() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day17/example_input2", 117440L),
                Arguments.of("AdventOfCodeData/2024/day17/input", 190384615275535L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMinimalInitialValue")
    void calculateMinimalInitialValueToCopyProgram(final String filename,
                                                   final long expectedMinimalInitialValue) throws IOException {
        assertEquals(expectedMinimalInitialValue, ChronospatialComputer.calculateMinimalInitialValueToCopyProgram(filename));
    }
}