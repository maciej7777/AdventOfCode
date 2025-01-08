package com.example.adventofcode.year2024.day14;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestroomRedoubtTest {
    private static Stream<Arguments> filepathsAndMapWidthAndHeightAndExpectedSafetyFactor() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day14/example_input", 11, 7, 12),
                Arguments.of("AdventOfCodeData/2024/day14/input", 101, 103, 218619120)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndMapWidthAndHeightAndExpectedSafetyFactor")
    void calculateSafetyFactor(final String filename,
                               final int width,
                               final int height,
                               final int expectedSafetyFactor) throws IOException {
        assertEquals(expectedSafetyFactor, RestroomRedoubt.calculateSafetyFactor(filename, width, height));
    }

    private static Stream<Arguments> filepathsAndMapWidthAndHeightAndExpectedStepsToReachEasterEgg() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day14/input", 101, 103, 7055)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndMapWidthAndHeightAndExpectedStepsToReachEasterEgg")
    void calculateStepsToReachEasterEgg(final String filename,
                                        final int width,
                                        final int height,
                                        final int expectedStepsToReachEasterEgg) throws IOException {
        assertEquals(expectedStepsToReachEasterEgg, RestroomRedoubt.calculateStepsToReachEasterEgg(filename, width, height));
    }
}