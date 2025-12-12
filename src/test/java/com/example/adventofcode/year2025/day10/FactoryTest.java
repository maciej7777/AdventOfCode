package com.example.adventofcode.year2025.day10;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FactoryTest {

    private static Stream<Arguments> filepathsAndExpectedMinButtonPressesForIndicatorLights() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day10/example_input", 7),
                Arguments.of("AdventOfCodeData/2025/day10/input", 507)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMinButtonPressesForIndicatorLights")
    void countMinButtonPressesForIndicatorLights(final String filename,
                                                 final long expectedMinButtonPresses) throws IOException {
        assertEquals(expectedMinButtonPresses, Factory.countMinButtonPressesForIndicatorLights(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedMinButtonPressesForJoltageLevelCounters() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day10/example_input", 33),
                Arguments.of("AdventOfCodeData/2025/day10/input", 18981)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMinButtonPressesForJoltageLevelCounters")
    void countMinButtonPressesForJoltageLevelCounters(final String filename,
                                                      final long expectedMinButtonPresses) throws IOException {
        assertEquals(expectedMinButtonPresses, Factory.countMinButtonPressesForJoltageLevelCounters(filename));
    }
}