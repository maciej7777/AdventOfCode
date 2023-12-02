package com.example.adventofcode.year2022.day05;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupplyStacksTest {
    private static Stream<Arguments> filePathsAndExpectedStacksTopForMovingSeparated() {
        return Stream.of(
                Arguments.of(
                        "src/main/java/com/example/adventofcode/day05/example_input",
                        "CMZ"
                ),
                Arguments.of(
                        "src/main/java/com/example/adventofcode/day05/input",
                        "JDTMRWCQJ"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedStacksTopForMovingSeparated")
    void testSimulateStacksMovingSeparated(final String fileName,
                                           final String expectedStacksTop) throws IOException {
        assertEquals(expectedStacksTop, SupplyStacks.simulateStacksMovingSeparated(fileName));
    }

    private static Stream<Arguments> filePathsAndExpectedStacksTopForMovingTogether() {
        return Stream.of(
                Arguments.of(
                        "src/main/java/com/example/adventofcode/day05/example_input",
                        "MCD"
                ),
                Arguments.of(
                        "src/main/java/com/example/adventofcode/day05/input",
                        "VHJDDCWRD"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedStacksTopForMovingTogether")
    void testSimulateStacksMovingTogether(final String fileName,
                                          final String expectedStacksTop) throws IOException {
        assertEquals(expectedStacksTop, SupplyStacks.simulateStacksMovingTogether(fileName));
    }
}