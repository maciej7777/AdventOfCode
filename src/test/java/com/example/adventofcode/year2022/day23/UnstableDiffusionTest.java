package com.example.adventofcode.year2022.day23;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnstableDiffusionTest {
    private static Stream<Arguments> filePathsAndExpectedElfMoves() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day23/example_input",
                        true,
                        110
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day23/input",
                        true,
                        4025
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day23/example_input",
                        false,
                        20
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day23/input",
                        false,
                        935
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedElfMoves")
    void testCalculateRoot(final String filename,
                           final boolean part1,
                           final int expectedElfMoves) throws IOException {
        assertEquals(expectedElfMoves, UnstableDiffusion.calculateElfMoves(filename, part1));
    }
}