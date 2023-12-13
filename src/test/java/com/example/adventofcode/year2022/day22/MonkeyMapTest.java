package com.example.adventofcode.year2022.day22;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonkeyMapTest {
    private static Stream<Arguments> filePathsAndExpectedPassword() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day22/example_input",
                        6032
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day22/input",
                        64256
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedPassword")
    void testCalculatePassword(final String filename,
                               final int expectedRoot) throws IOException {
        assertEquals(expectedRoot, MonkeyMap.calculatePassword(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedCubePassword() {
        return Stream.of(
                /*Arguments.of("src/main/java/com/example/adventofcode/year2022/day22/example_input",
                        5031
                ),*/
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day22/input",
                        109224
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedCubePassword")
    void testCalculateCubePassword(final String filename,
                                   final int expectedCubePassword) throws IOException {
        assertEquals(expectedCubePassword, MonkeyMapCube.calculateCubePassword(filename));
    }
}