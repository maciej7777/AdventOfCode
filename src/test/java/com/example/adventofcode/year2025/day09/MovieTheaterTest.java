package com.example.adventofcode.year2025.day09;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTheaterTest {
    
    private static Stream<Arguments> filepathsAndConnectionsAndExpectedLargestArea() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day09/example_input", 50),
                Arguments.of("AdventOfCodeData/2025/day09/input", 4769758290L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndConnectionsAndExpectedLargestArea")
    void calculateLargestAreaBruteForce(final String filename,
                                        final long expectedLargestArea) throws IOException {
        assertEquals(expectedLargestArea, MovieTheater.calculateLargestAreaBruteForce(filename));
    }

    @ParameterizedTest
    @MethodSource("filepathsAndConnectionsAndExpectedLargestArea")
    void calculateLargestArea(final String filename,
                              final long expectedLargestArea) throws IOException {
        assertEquals(expectedLargestArea, MovieTheater.calculateLargestArea(filename));
    }

    private static Stream<Arguments> filepathsAndConnectionsAndExpectedLargestAreaInsideOfPolygon() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day09/example_input", 24),
                Arguments.of("AdventOfCodeData/2025/day09/input", 1588990708L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndConnectionsAndExpectedLargestAreaInsideOfPolygon")
    void calculateLargestAreaInsideOfPolygon(final String filename,
                                             final long expectedLargestArea) throws IOException {
        assertEquals(expectedLargestArea, MovieTheater.calculateLargestAreaInsideOfPolygon(filename));
    }
}