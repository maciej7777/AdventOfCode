package com.example.adventofcode.year2024.day20;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RaceConditionTest {
    private static Stream<Arguments> filepathsAndCheatLengthAndSavedTimeAndExpectedCheats() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day20/example_input", 2, 12, 8),
                Arguments.of("AdventOfCodeData/2024/day20/input", 2, 100, 1381),
                Arguments.of("AdventOfCodeData/2024/day20/example_input", 20, 72, 29),
                Arguments.of("AdventOfCodeData/2024/day20/example_input", 20, 76, 3),
                Arguments.of("AdventOfCodeData/2024/day20/input", 20, 100, 982124)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndCheatLengthAndSavedTimeAndExpectedCheats")
    void calculateCheats(final String filename,
                         final int cheatLength,
                         final int savedTime,
                         final int expectedCheats) throws IOException {
        assertEquals(expectedCheats, RaceCondition.calculateCheats(filename, cheatLength, savedTime));
    }
}