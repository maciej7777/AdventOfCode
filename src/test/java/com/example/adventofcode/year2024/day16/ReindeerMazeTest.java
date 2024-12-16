package com.example.adventofcode.year2024.day16;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReindeerMazeTest {
    private static Stream<Arguments> filepathsAndExpectedBestReindeerScore() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day16/example_input", 7036),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day16/example_input2", 11048),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day16/input", 135536)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedBestReindeerScore")
    void calculateBoxesGPSCoordinates(final String filename,
                                      final int expectedBestReindeerScore) throws IOException {
        assertEquals(expectedBestReindeerScore, ReindeerMaze.calculateBestReindeerScore(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedTilesOnTheBestPaths() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day16/example_input", 45),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day16/example_input2", 64),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day16/input", 583)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTilesOnTheBestPaths")
    void calculateTilesOnTheBestPaths(final String filename,
                                      final int expectedTilesOnTheBestPaths) throws IOException {
        assertEquals(expectedTilesOnTheBestPaths, ReindeerMaze.calculateTilesOnTheBestPaths(filename));
    }
}