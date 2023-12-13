package com.example.adventofcode.year2022.day13;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DistressSignalTest {
    private static Stream<Arguments> filePathsAndExpectedSumOfOrderedPairs() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day13/example_input",
                        13
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day13/input",
                        5393
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedSumOfOrderedPairs")
    void testObtainLengthOfShortestPathFromStart(final String filename,
                                                 final long expectedSumOfOrderedPairs) throws IOException {
        assertEquals(expectedSumOfOrderedPairs, DistressSignal.obtainSumOfOrderedPairs(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedMultipliedPositions() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day13/example_input",
                        140
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day13/input",
                        26712
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedMultipliedPositions")
    void testObtainLengthOfShortestPathFromBottom(final String filename,
                                                  final long expectedMultipliedPositions) throws IOException {
        assertEquals(expectedMultipliedPositions, DistressSignal.obtainExpectedMultipliedPositions(filename));
    }
}