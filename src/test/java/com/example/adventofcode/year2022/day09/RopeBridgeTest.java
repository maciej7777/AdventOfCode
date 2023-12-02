package com.example.adventofcode.year2022.day09;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RopeBridgeTest {
    private static Stream<Arguments> filePathsRopeLengthAndExpectedPositionsTouchedByRopeTail() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day09/example_input",
                        2,
                        13
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day09/input",
                        2,
                        6357
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day09/example_input",
                        10,
                        1
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day09/example_input_larger",
                        10,
                        36
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day09/input",
                        10,
                        2627
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsRopeLengthAndExpectedPositionsTouchedByRopeTail")
    void testCalculatePositionsTouchedByRopeTail(final String fileName,
                                                 final int ropeLength,
                                                 final int expectedPositionsTouchedByRopeTail) throws IOException {
        assertEquals(expectedPositionsTouchedByRopeTail, RopeBridge.calculatePositionsTouchedByRopeTail(fileName, ropeLength));
    }
}