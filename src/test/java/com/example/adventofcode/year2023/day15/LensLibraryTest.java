package com.example.adventofcode.year2023.day15;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LensLibraryTest {

    private static Stream<Arguments> filepathsAndExpectedSumOfHashes() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day15/example_input", 1320),
                Arguments.of("AdventOfCodeData/2023/day15/input", 503487)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSumOfHashes")
    void calculateSumOfHashes(final String filename,
                              final int expectedSumOfHashes) throws IOException {
        assertEquals(expectedSumOfHashes, LensLibrary.calculateSumOfHashes(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedFocusingPower() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day15/example_input", 145),
                Arguments.of("AdventOfCodeData/2023/day15/input", 261505)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFocusingPower")
    void calculateSolution2(final String filename,
                            final int expectedFocusingPower) throws IOException {
        assertEquals(expectedFocusingPower, LensLibrary.calculateFocusingPower(filename));
    }
}