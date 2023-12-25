package com.example.adventofcode.year2023.day23;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ALongWalkBruteForceTest {

    private static Stream<Arguments> filepathsAndExpectedLongestHike() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day23/example_input", 94),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day23/input", 2170)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLongestHike")
    void calculateLongestHike(final String filename,
                              final int expectedLongestHike) throws IOException {
        assertEquals(expectedLongestHike, ALongWalkBruteForce.calculateLongestHike(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedLongestHikeWithoutSlopes() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day23/example_input", 154)
                //Arguments.of("src/main/java/com/example/adventofcode/year2023/day23/input", 6502)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLongestHikeWithoutSlopes")
    void calculateLongestHikeWithoutSlopes(final String filename,
                                           final int expectedLongestHike) throws IOException {
        assertEquals(expectedLongestHike, ALongWalkBruteForce.calculateLongestHikeWithoutSlopes(filename));
    }
}