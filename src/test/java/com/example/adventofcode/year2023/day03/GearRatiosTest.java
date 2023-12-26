package com.example.adventofcode.year2023.day03;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GearRatiosTest {

    private static Stream<Arguments> filepathsAndExpectedPartNumbersSum() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day03/example_input", 4361),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day03/input", 536576)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPartNumbersSum")
    void sumPartNumbers(final String filename,
                        final int expectedPartNumbersSum) throws IOException {
        assertEquals(expectedPartNumbersSum, GearRatios.sumPartNumbers(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedGearRatiosSum() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day03/example_input", 467835),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day03/input", 75741499)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedGearRatiosSum")
    void sumGearRatios(final String filename,
                       final int expectedGearRatiosSum) throws IOException {
        assertEquals(expectedGearRatiosSum, GearRatios.sumGearRatios(filename));
    }
}