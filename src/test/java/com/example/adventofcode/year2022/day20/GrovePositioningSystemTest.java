package com.example.adventofcode.year2022.day20;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrovePositioningSystemTest {
    private static Stream<Arguments> filePathsAndExpectedSumOfGroveCoordinates() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day20/example_input",
                        3
                ),
                Arguments.of("AdventOfCodeData/2022/day20/input",
                        7584
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedSumOfGroveCoordinates")
    void testCalculateSumOfGroveCoordinates(final String filename,
                                            final long expectedSumOfGroveCoordinates) throws IOException {
        assertEquals(expectedSumOfGroveCoordinates, GrovePositioningSystem.calculateSumOfGroveCoordinates(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedSumOfEncryptedGroveCoordinates() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day20/example_input",
                        1623178306L
                ),
                Arguments.of("AdventOfCodeData/2022/day20/input",
                        4907679608191L
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedSumOfEncryptedGroveCoordinates")
    void testCalculateSumOfEncryptedGroveCoordinates(final String filename,
                                                     final long expectedSumOfEncryptedGroveCoordinates) throws IOException {
        assertEquals(expectedSumOfEncryptedGroveCoordinates, GrovePositioningSystem.calculateSumOfEncryptedGroveCoordinates(filename));
    }
}