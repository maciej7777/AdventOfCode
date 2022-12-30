package com.example.adventofcode.day22;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonkeyMapTest {
    private static Stream<Arguments> filePathsAndExpectedRoot() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day22/example_input",
                        6032
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day22/input",
                        64256
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedRoot")
    void testCalculateRoot(final String filename,
                           final int expectedRoot) throws IOException {
        assertEquals(expectedRoot, MonkeyMap.calculatePositions(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedHumn() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day22/example_input",
                        5031
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day22/input",
                        109224
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedHumn")
    void testCalculateHumn(final String filename,
                           final int expectedHumn) throws IOException {
        assertEquals(expectedHumn, MonkeyMap2.calculatePositions(filename));
    }
}