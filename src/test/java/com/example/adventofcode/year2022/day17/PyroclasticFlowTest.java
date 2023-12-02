package com.example.adventofcode.year2022.day17;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PyroclasticFlowTest {
    private static Stream<Arguments> filePathsAndIterationsAndExpectedTowerHeight() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day17/example_input",
                        2022,
                        3068
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day17/input",
                        2022,
                        3124
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day17/example_input",
                        1000000000000L,
                        1514285714288L
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day17/input",
                        1000000000000L,
                        1561176470569L
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndIterationsAndExpectedTowerHeight")
    void testObtainRocksHeight(final String filename,
                               final long iterations,
                               final long expectedTowerHeight) throws IOException {
        assertEquals(expectedTowerHeight, PyroclasticFlow.obtainTowerHeight(filename, iterations));
    }
}