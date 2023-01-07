package com.example.adventofcode.day01;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalorieCountingTest {

    private static Stream<Arguments> filepathsAndExpectedMaxCalories() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day01/example_input", 24001),
                Arguments.of("src/main/java/com/example/adventofcode/day01/input", 68467)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMaxCalories")
    void testCountMaxCalories(final String filename,
                              final int expectedPairs) throws IOException {
        assertEquals(expectedPairs, CalorieCounting.countMaxCalories(filename));
    }

    private static Stream<Arguments> filepathsAndExpected3MaxCalories() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day01/example_input", 45000),
                Arguments.of("src/main/java/com/example/adventofcode/day01/input", 203420)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpected3MaxCalories")
    void testCount3MaxCalories(final String filename,
                               final int expectedPairs) throws IOException {
        assertEquals(expectedPairs, CalorieCounting.count3MaxCalories(filename));
    }
}
