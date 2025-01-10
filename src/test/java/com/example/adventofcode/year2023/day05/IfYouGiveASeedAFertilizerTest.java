package com.example.adventofcode.year2023.day05;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IfYouGiveASeedAFertilizerTest {

    private static Stream<Arguments> filepathsAndExpectedLowestLocationNumber() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day05/example_input", 35),
                Arguments.of("AdventOfCodeData/2023/day05/input", 650599855)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLowestLocationNumber")
    void calculateLowestLocationNumber(final String filename,
                                       final int expectedLowestLocationNumber) throws IOException {
        assertEquals(expectedLowestLocationNumber, IfYouGiveASeedAFertilizer.calculateLowestLocationNumber(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedLowestLocationNumberFromRanges() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day05/example_input", 46),
                Arguments.of("AdventOfCodeData/2023/day05/input", 1240035)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLowestLocationNumberFromRanges")
    void calculateLowestLocationNumberFromRanges(final String filename,
                                                 final int expectedLowestLocationNumber) throws IOException {
        assertEquals(expectedLowestLocationNumber, IfYouGiveASeedAFertilizer.calculateLowestLocationNumberFromRanges(filename));
    }
}