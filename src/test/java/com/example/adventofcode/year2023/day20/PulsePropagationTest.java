package com.example.adventofcode.year2023.day20;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PulsePropagationTest {

    private static Stream<Arguments> filepathsAndExpectedLowAndHighPulsesProduct() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day20/example_input", 32000000),
                Arguments.of("AdventOfCodeData/2023/day20/example_input2", 11687500),
                Arguments.of("AdventOfCodeData/2023/day20/input", 1020211150)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLowAndHighPulsesProduct")
    void calculateLowAndHighPulsesProduct(final String filename,
                                          final int expectedLowAndHighPulsesProduct) throws IOException {
        assertEquals(expectedLowAndHighPulsesProduct, PulsePropagation.calculateLowAndHighPulsesProduct(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedMinButtonPushes() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day20/input", 238815727638557L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMinButtonPushes")
    void calculateMinButtonPushes(final String filename,
                                  final long expectedMinButtonPushes) throws IOException {
        assertEquals(expectedMinButtonPushes, PulsePropagation.calculateMinButtonPushesForRx(filename));
    }
}