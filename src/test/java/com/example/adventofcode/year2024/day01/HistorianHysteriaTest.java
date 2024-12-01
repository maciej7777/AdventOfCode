package com.example.adventofcode.year2024.day01;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistorianHysteriaTest {
    private static Stream<Arguments> filepathsAndExpectedTotalDistance() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day01/example_input", 11),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day01/input", 1882714)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalDistance")
    void calculateTotalDistance(final String filename,
                                final int expectedLocationIds) throws IOException {
        assertEquals(expectedLocationIds, HistorianHysteria.calculateTotalDistance(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedCalibrationNumbersWithText() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day01/example_input", 31),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day01/input", 19437052L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCalibrationNumbersWithText")
    void calculateSimilarityScore(final String filename,
                                  final long expectedLocationIds) throws IOException {
        assertEquals(expectedLocationIds, HistorianHysteria.calculateSimilarityScore(filename));
    }
}