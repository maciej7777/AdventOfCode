package com.example.adventofcode.year2024.day05;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintQueueTest {

    private static Stream<Arguments> filepathsAndExpectedMiddlePageSumForCorrectlyOrdered() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day05/example_input", 143),
                Arguments.of("AdventOfCodeData/2024/day05/input", 5329)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMiddlePageSumForCorrectlyOrdered")
    void calculateMiddlePageSumForCorrectlyOrdered(final String filename,
                                                   final int expectedMiddlePageSum) throws IOException {
        assertEquals(expectedMiddlePageSum, PrintQueue.calculateMiddlePageSumForCorrectlyOrdered(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedMiddlePageSumForIncorrectlyOrdered() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day05/example_input", 123),
                Arguments.of("AdventOfCodeData/2024/day05/input", 5833)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMiddlePageSumForIncorrectlyOrdered")
    void calculateMiddlePageSumForIncorrectlyOrdered(final String filename,
                                                      final int expectedMiddlePageSum) throws IOException {
        assertEquals(expectedMiddlePageSum, PrintQueue.calculateMiddlePageSumForIncorrectlyOrdered(filename));
    }
}