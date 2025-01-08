package com.example.adventofcode.year2024.day10;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HoofItTest {
    private static Stream<Arguments> filepathsAndExpectedTrailheadsScores() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day10/example_input", 1),
                Arguments.of("AdventOfCodeData/2024/day10/example_input2", 36),
                Arguments.of("AdventOfCodeData/2024/day10/input", 737)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTrailheadsScores")
    void calculateSumOfTrailheadsScores(final String filename,
                                        final int expectedTrailheadsScores) throws IOException {
        assertEquals(expectedTrailheadsScores, HoofIt.calculateSumOfTrailheadsScores(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedTrailheadsRaitings() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day10/example_input", 16),
                Arguments.of("AdventOfCodeData/2024/day10/example_input2", 81),
                Arguments.of("AdventOfCodeData/2024/day10/input", 1619)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTrailheadsRaitings")
    void calculateSumOfTrailheadsRatings(final String filename,
                                         final int expectedTrailheadsRatings) throws IOException {
        assertEquals(expectedTrailheadsRatings, HoofIt.calculateSumOfTrailheadsRatings(filename));
    }
}