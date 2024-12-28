package com.example.adventofcode.year2024.day07;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BridgeRepairTest {

    private static Stream<Arguments> filepathsAndExpectedCalibrationNumber() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day07/example_input", 3749),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day07/input", 8401132154762L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCalibrationNumber")
    void calculateCalibrationNumber(final String filename,
                                    final long expectedCalibrationNumber) throws IOException {
        assertEquals(expectedCalibrationNumber, BridgeRepair.calculateCalibrationNumber(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedCalibrationNumberWithConcatenation() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day07/example_input", 11387),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day07/input", 95297119227552L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCalibrationNumberWithConcatenation")
    void calculateCalibrationNumberWithConcatenation(final String filename,
                                                     final long expectedCalibrationNumber) throws IOException {
        assertEquals(expectedCalibrationNumber, BridgeRepair.calculateCalibrationNumberWithConcatenation(filename));
    }
}