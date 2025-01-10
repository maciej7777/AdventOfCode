package com.example.adventofcode.year2023.day09;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MirageMaintenanceTest {

    private static Stream<Arguments> filepathsAndExpectedExtrapolatedValues() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day09/example_input", 114),
                Arguments.of("AdventOfCodeData/2023/day09/input", 1789635132)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedExtrapolatedValues")
    void calculateSumOfExtrapolatedValues(final String filename,
                                          final int expectedExtrapolatedValues) throws IOException {
        assertEquals(expectedExtrapolatedValues, MirageMaintenance.calculateSumOfExtrapolatedValues(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedExtrapolatedValuesOnTheBeginning() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day09/example_input", 2),
                Arguments.of("AdventOfCodeData/2023/day09/input", 913)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedExtrapolatedValuesOnTheBeginning")
    void calculateSumOfExtrapolatedValuesOnTheBeginning(final String filename,
                                                        final int expectedExtrapolatedValues) throws IOException {
        assertEquals(expectedExtrapolatedValues, MirageMaintenance.calculateSumOfExtrapolatedValuesOnTheBeginning(filename));
    }
}