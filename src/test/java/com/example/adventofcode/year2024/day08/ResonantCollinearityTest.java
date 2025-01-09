package com.example.adventofcode.year2024.day08;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResonantCollinearityTest {

    private static Stream<Arguments> filepathsAndExpectedAntinodeLocations() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day08/example_input", 14),
                Arguments.of("AdventOfCodeData/2024/day08/input", 259)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedAntinodeLocations")
    void countAntinodeLocations(final String filename,
                                final int expectedAntinodeLocations) throws IOException {
        assertEquals(expectedAntinodeLocations, ResonantCollinearity.countAntinodeLocations(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedAntinodeLocationsWithResonantHarmonics() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day08/example_input", 34),
                Arguments.of("AdventOfCodeData/2024/day08/input", 927)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedAntinodeLocationsWithResonantHarmonics")
    void countAntinodeLocationsWithResonantHarmonics(final String filename,
                                                     final int expectedCalibrationNumber) throws IOException {
        assertEquals(expectedCalibrationNumber, ResonantCollinearity.countAntinodeLocationsWithResonantHarmonics(filename));
    }
}