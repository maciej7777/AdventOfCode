package com.example.adventofcode.year2025.day07;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LaboratoriesTest {

    private static Stream<Arguments> filepathsAndExpectedTimesBeamSplits() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day07/example_input", 21),
                Arguments.of("AdventOfCodeData/2025/day07/input", 1698)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTimesBeamSplits")
    void calculateTimesBeamSplits(final String filename,
                                  final long expectedTimesBeamSplits) throws IOException {
        assertEquals(expectedTimesBeamSplits, Laboratories.calculateTimesBeamSplits(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedTachyonParticleTimelines() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day07/example_input", 40),
                Arguments.of("AdventOfCodeData/2025/day07/input", 95408386769474L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTachyonParticleTimelines")
    void calculateTachyonParticleTimelines(final String filename,
                                           final long expectedTachyonParticleTimelines) throws IOException {
        assertEquals(expectedTachyonParticleTimelines, Laboratories.calculateTachyonParticleTimelines(filename));
    }
}