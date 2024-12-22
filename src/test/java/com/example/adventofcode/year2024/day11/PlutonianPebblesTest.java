package com.example.adventofcode.year2024.day11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlutonianPebblesTest {
    private static Stream<Arguments> filepathsAndTimesBlinkingAndExpectedStonesBruteForce() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day11/example_input", 25, 55312),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day11/input", 25, 220722)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndTimesBlinkingAndExpectedStonesBruteForce")
    void countStonesAfterBlinkingBruteForce(final String filename,
                                            final int timesBlinking,
                                            final int expectedStones) throws IOException {
        assertEquals(expectedStones, PlutonianPebbles.countStonesAfterBlinkingBruteForce(filename, timesBlinking));
    }

    private static Stream<Arguments> filepathsAndTimesBlinkingAndExpectedStones() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day11/example_input", 25, 55312),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day11/input", 25, 220722),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day11/example_input", 75, 65601038650482L),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day11/input", 75, 261952051690787L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndTimesBlinkingAndExpectedStones")
    void countStonesAfterBlinking(final String filename,
                                  final int timesBlinking,
                                  final long expectedStones) throws IOException {
        assertEquals(expectedStones, PlutonianPebbles.countStonesAfterBlinking(filename, timesBlinking));
    }
}