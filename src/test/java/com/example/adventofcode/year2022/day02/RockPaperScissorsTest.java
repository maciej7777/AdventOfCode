package com.example.adventofcode.year2022.day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RockPaperScissorsTest {
    private static Stream<Arguments> filePathsAndExpectedStrategyScoreForMoves() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day02/example_input", 15),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day02/input", 11475)
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedStrategyScoreForMoves")
    void testObtainStrategyScoreForMoves(final String filename,
                                         final int expectedStrategyScore) throws IOException {
        assertEquals(expectedStrategyScore, RockPaperScissors.obtainStrategyScoreForMoves(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedStrategyScoreForResults() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day02/example_input", 12),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day02/input", 16862)
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedStrategyScoreForResults")
    void testObtainStrategyScoreForResults(final String filename,
                                           final int expectedStrategyScore) throws IOException {
        assertEquals(expectedStrategyScore, RockPaperScissors.obtainStrategyScoreForResults(filename));
    }
}