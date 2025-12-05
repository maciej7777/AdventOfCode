package com.example.adventofcode.year2025.day01;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecretEntranceTest {

    private static Stream<Arguments> filepathsAndExpectedPassword() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day01/example_input", 3),
                Arguments.of("AdventOfCodeData/2025/day01/input", 1086)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPassword")
    void restorePasswordSimulation(final String filename,
                                   final int expectedPassword) throws IOException {
        assertEquals(expectedPassword, SecretEntrance.restorePasswordSimulation(filename));
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPassword")
    void restorePasswordImproved(final String filename,
                                 final int expectedPassword) throws IOException {
        assertEquals(expectedPassword, SecretEntrance.restorePasswordImproved(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedPasswordByClickMethod() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day01/example_input", 6),
                Arguments.of("AdventOfCodeData/2025/day01/input", 6268)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPasswordByClickMethod")
    void restorePasswordClickMethod(final String filename,
                                    final int expectedPassword) throws IOException {
        assertEquals(expectedPassword, SecretEntrance.restorePasswordClickMethod(filename));
    }
}