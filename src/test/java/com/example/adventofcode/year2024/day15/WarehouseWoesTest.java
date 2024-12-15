package com.example.adventofcode.year2024.day15;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WarehouseWoesTest {
    private static Stream<Arguments> filepathsAndExpectedBoxesGPSCoordinates() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/example_input", 2028),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/example_input2", 10092),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/example_input3", 1921),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/input", 1430536)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedBoxesGPSCoordinates")
    void calculateBoxesGPSCoordinates(final String filename,
                                      final int expectedBoxesGPSCoordinates) throws IOException {
        assertEquals(expectedBoxesGPSCoordinates, WarehouseWoes.calculateBoxesGPSCoordinates(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedBoxesGPSCoordinatesForScaledWarehouse() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/example_input", 1751),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/example_input2", 9021),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/example_input3", 1233),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day15/input", 1452348)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedBoxesGPSCoordinatesForScaledWarehouse")
    void calculateBoxesGPSCoordinatesForScaledWarehouse(final String filename,
                                                        final int expectedBoxesGPSCoordinates) throws IOException {
        assertEquals(expectedBoxesGPSCoordinates, WarehouseWoes.calculateBoxesGPSCoordinatesForScaledWarehouse(filename));
    }
}