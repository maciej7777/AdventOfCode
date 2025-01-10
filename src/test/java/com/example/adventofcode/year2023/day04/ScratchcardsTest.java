package com.example.adventofcode.year2023.day04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScratchcardsTest {

    private static Stream<Arguments> filepathsAndExpectedWinningPoints() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day04/example_input", 13),
                Arguments.of("AdventOfCodeData/2023/day04/input", 24542)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedWinningPoints")
    void calculateWinningPoints(final String filename,
                                final int expectedWinningPoints) throws IOException {
        assertEquals(expectedWinningPoints, Scratchcards.calculateWinningPoints(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedTotalScratchcards() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day04/example_input", 30),
                Arguments.of("AdventOfCodeData/2023/day04/input", 8736438)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalScratchcards")
    void calculateTotalScratchcards(final String filename,
                                    final int expectedTotalScratchcards) throws IOException {
        assertEquals(expectedTotalScratchcards, Scratchcards.calculateTotalScratchcards(filename));
    }
}