package com.example.adventofcode.day12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HillClimbingAlgorithmTest {
    private static Stream<Arguments> filepathsAndExpectedLengthFromStart() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day12/example_input",
                        31
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day12/input",
                        490
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLengthFromStart")
    void testObtainLengthOfShortestPathFromStart(final String filename,
                                                 final long expectedShortestPathLength) throws IOException {
        assertEquals(expectedShortestPathLength, HillClimbingAlgorithm.obtainLengthOfShortestPathFromStart(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedLengthFromBottom() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day12/example_input",
                        29
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day12/input",
                        488
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLengthFromBottom")
    void testObtainLengthOfShortestPathFromBottom(final String filename,
                                                  final long expectedShortestPathLength) throws IOException {
        assertEquals(expectedShortestPathLength, HillClimbingAlgorithm.obtainLengthOfShortestPathFromBottom(filename));
    }

}