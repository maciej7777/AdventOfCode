package com.example.adventofcode.year2024.day22;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonkeyMarketTest {
    private static Stream<Arguments> filepathsAndNumberOfSecretNumbersAndExpectedSumOfSecretNumbers() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day22/example_input", 2000, 37327623L),
                Arguments.of("AdventOfCodeData/2024/day22/input", 2000, 13753970725L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndNumberOfSecretNumbersAndExpectedSumOfSecretNumbers")
    void calculateSumOfSecretNumbers(final String filename,
                                     final int numberOfSecretNumbers,
                                     final long expectedSumOfSecretNumbers) throws IOException {
        assertEquals(expectedSumOfSecretNumbers, MonkeyMarket.calculateSumOfSecretNumbers(filename, numberOfSecretNumbers));
    }

    private static Stream<Arguments> filepathsAndNumberOfSecretNumbersAndExpectedMaxBananasNumber() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day22/example_input", 2000, 24L),
                Arguments.of("AdventOfCodeData/2024/day22/example_input2", 2000, 23L),
                Arguments.of("AdventOfCodeData/2024/day22/input", 2000, 1570L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndNumberOfSecretNumbersAndExpectedMaxBananasNumber")
    void calculateMaxBananasNumber(final String filename,
                                   final int numberOfSecretNumbers,
                                   final long expectedMaxBananasNumber) throws IOException {
        assertEquals(expectedMaxBananasNumber, MonkeyMarket.calculateMaxBananasNumber(filename, numberOfSecretNumbers));
    }
}