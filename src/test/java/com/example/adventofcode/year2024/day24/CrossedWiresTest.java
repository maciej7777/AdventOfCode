package com.example.adventofcode.year2024.day24;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrossedWiresTest {
    private static Stream<Arguments> filepathsAndExpectedZ() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day24/example_input", 4),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day24/example_input2", 2024),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day24/input", 56278503604006L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedZ")
    void calculateZ(final String filename,
                    final long expectedZ) throws IOException {
        assertEquals(expectedZ, CrossedWires.calculateZ(filename));
    }
}