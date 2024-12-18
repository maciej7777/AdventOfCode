package com.example.adventofcode.year2024.day18;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RAMRunTest {
    private static Stream<Arguments> filepathsAndFallenBytesAndMemorySizeAndExpectedMinimumSteps() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day18/example_input", 12, 6, 22),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day18/input", 1224, 70, 304)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndFallenBytesAndMemorySizeAndExpectedMinimumSteps")
    void calculateMinimumStepsToExit(final String filename,
                                     final int fallenBytes,
                                     final int memorySize,
                                     final int expectedMinimumSteps) throws IOException {
        assertEquals(expectedMinimumSteps, RAMRun.calculateMinimumStepsToExit(filename, fallenBytes, memorySize));
    }

    private static Stream<Arguments> filepathsAndMemorySizeAndExpectedCoordinates() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day18/example_input", 6, "6,1"),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day18/input", 70, "50,28")
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndMemorySizeAndExpectedCoordinates")
    void calculateCoordinatesOfFirstBlockingByte(final String filename,
                                                 final int memorySize,
                                                 final String expectedCoordinates) throws IOException {
        assertEquals(expectedCoordinates, RAMRun.calculateCoordinatesOfFirstBlockingByte(filename, memorySize));
    }
}