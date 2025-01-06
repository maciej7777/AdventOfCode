package com.example.adventofcode.year2024.day25;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeChronicleTest {
    private static Stream<Arguments> filepathsAndExpectedFittingKeys() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day25/example_input", 4),
                Arguments.of("AdventOfCodeData/2024/day25/input", 2835)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFittingKeys")
    void calculateFittingKeys(final String filename,
                              final long expectedFittingKeys) throws IOException {
        assertEquals(expectedFittingKeys, CodeChronicle.calculateFittingKeys(filename));
    }
}