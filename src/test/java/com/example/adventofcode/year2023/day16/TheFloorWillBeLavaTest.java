package com.example.adventofcode.year2023.day16;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TheFloorWillBeLavaTest {

    private static Stream<Arguments> filepathsAndExpectedEnergizedTitles() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day16/example_input", 46),
                Arguments.of("AdventOfCodeData/2023/day16/input", 7927)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedEnergizedTitles")
    void calculateSolution1(final String filename,
                            final int expectedEnergizedTitles) throws IOException {
        assertEquals(expectedEnergizedTitles, TheFloorWillBeLava.countEnergizedPoints(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedBestTitles() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day16/example_input", 51),
                Arguments.of("AdventOfCodeData/2023/day16/input", 8246)
        );
    }
    @ParameterizedTest
    @MethodSource("filepathsAndExpectedBestTitles")
    void calculateSolution2(final String filename,
                            final int expectedEnergizedTitles) throws IOException {
        assertEquals(expectedEnergizedTitles, TheFloorWillBeLava.countMaximumEnergizedPoints(filename));
    }
}