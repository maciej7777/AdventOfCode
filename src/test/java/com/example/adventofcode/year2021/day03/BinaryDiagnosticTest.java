package com.example.adventofcode.year2021.day03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryDiagnosticTest {

    private static Stream<Arguments> filepathsAndExpectedPowerConsumption() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2021/day03/example_input", 198),
                Arguments.of("AdventOfCodeData/2021/day03/input", 4138664)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPowerConsumption")
    void testCalculatePowerConsumption(final String filename,
                                       final int expectedPowerConsumption) throws IOException {
        assertEquals(expectedPowerConsumption, BinaryDiagnostic.calculatePowerConsumption(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedLifeSupportRating() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2021/day03/example_input", 230),
                Arguments.of("AdventOfCodeData/2021/day03/input", 4273224)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLifeSupportRating")
    void testCalculateLifeSupportRating(final String filename,
                                        final int expectedLifeSupportRating) throws IOException {
        assertEquals(expectedLifeSupportRating, BinaryDiagnostic.calculateLifeSupportRating(filename));
    }
}