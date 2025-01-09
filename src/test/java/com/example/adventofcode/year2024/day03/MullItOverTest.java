package com.example.adventofcode.year2024.day03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MullItOverTest {
    private static Stream<Arguments> filepathsAndExpectedInstructionsResult() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day03/example_input", 161),
                Arguments.of("AdventOfCodeData/2024/day03/input", 169021493)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedInstructionsResult")
    void calculateInstructionsResult(final String filename,
                                     final int expectedInstructionsResult) throws IOException {
        assertEquals(expectedInstructionsResult, MullItOver.calculateInstructionsResult(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedInstructionsResultWithIgnoringConditions() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day03/example_input2", 48),
                Arguments.of("AdventOfCodeData/2024/day03/input", 111762583)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedInstructionsResultWithIgnoringConditions")
    void calculateInstructionsResultWithIgnoringConditions(final String filename,
                                                           final int expectedInstructionsResult) throws IOException {
        assertEquals(expectedInstructionsResult, MullItOver.calculateInstructionsResultWithIgnoringConditions(filename));
    }
}