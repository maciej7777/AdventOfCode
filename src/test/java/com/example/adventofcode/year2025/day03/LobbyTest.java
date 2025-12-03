package com.example.adventofcode.year2025.day03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LobbyTest {

    private static Stream<Arguments> filepathsAndExpectedTotalOutputJoltage() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day03/example_input", 357),
                Arguments.of("AdventOfCodeData/2025/day03/input", 17359)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalOutputJoltage")
    void testCalculateTotalOutputJoltage(final String filename,
                                         final long expectedTotalOutputJoltage) throws IOException {
        assertEquals(expectedTotalOutputJoltage, Lobby.calculateTotalOutputJoltage(filename));
    }

    private static Stream<Arguments> filepathsAndJoltageLengthAndExpectedTotalOutputJoltage() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day03/example_input", 2, 357),
                Arguments.of("AdventOfCodeData/2025/day03/input", 2, 17359),
                Arguments.of("AdventOfCodeData/2025/day03/example_input",12, 3121910778619L),
                Arguments.of("AdventOfCodeData/2025/day03/input", 12, 172787336861064L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndJoltageLengthAndExpectedTotalOutputJoltage")
    void testCalculateTotalOutputJoltageExtended(final String filename,
                                                 final int joltageLength,
                                                 final long expectedTotalOutputJoltage) throws IOException {
        assertEquals(expectedTotalOutputJoltage, Lobby.calculateTotalOutputJoltage(filename, joltageLength));

    }
}