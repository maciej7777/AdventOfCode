package com.example.adventofcode.year2024.day19;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinenLayoutTest {
    private static Stream<Arguments> filepathsAndExpectedPossibleDesigns() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day19/example_input", 6),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day19/input", 247)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPossibleDesigns")
    void calculatePossibleDesigns(final String filename,
                                  final int expectedPossibleDesigns) throws IOException {
        assertEquals(expectedPossibleDesigns, LinenLayout.calculatePossibleDesigns(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedPossibleDesignsOptions() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day19/example_input", 16L),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day19/input", 692596560138745L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPossibleDesignsOptions")
    void calculatePossibleDesignsOptions(final String filename,
                                         final long expectedPossibleDesignsOptions) throws IOException {
        assertEquals(expectedPossibleDesignsOptions, LinenLayout.calculatePossibleDesignsOptions(filename));
    }
}