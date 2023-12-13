package com.example.adventofcode.year2023.day01;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrebuchetTest {
    private static Stream<Arguments> filepathsAndExpectedCalibrationNumbers() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day01/example_input", 142),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day01/input", 54634)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCalibrationNumbers")
    void sumCalibrationNumbers(final String filename,
                               final int expectedCalibrationNumbers) throws IOException  {
        assertEquals(expectedCalibrationNumbers, Trebuchet.sumCalibrationNumbers(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedCalibrationNumbersWithText() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day01/example_input2", 281),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day01/input", 53855)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCalibrationNumbersWithText")
    void sumCalibrationNumbersWithText(final String filename,
                                       final int expectedCalibrationNumbers) throws IOException {
        assertEquals(expectedCalibrationNumbers, Trebuchet.sumCalibrationNumbersWithText(filename));
    }
}