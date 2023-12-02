package com.example.adventofcode.year2022.day19;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotEnoughMineralsTest {
    private static Stream<Arguments> filePathsAndExpectedMaximumGeodesSum() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day19/example_input",
                        33
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day19/input",
                        1092
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedMaximumGeodesSum")
    void testObtainMaximumGeodesSum(final String filename,
                                    final long expectedMaximumGeodes) throws IOException {
        assertEquals(expectedMaximumGeodes, NotEnoughMinerals.obtainMaximumGeodesSum(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedMaximumGeodesMultiplication() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day19/example_input",
                        3472
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day19/input",
                        3542
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedMaximumGeodesMultiplication")
    void testObtainMaximumGeodesMultiplication(final String filename,
                                               final int expectedMaximumGeodes) throws IOException {
        assertEquals(expectedMaximumGeodes, NotEnoughMinerals.obtainMaximumGeodesMultiplication(filename));
    }
}