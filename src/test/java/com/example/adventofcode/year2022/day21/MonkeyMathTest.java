package com.example.adventofcode.year2022.day21;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MonkeyMathTest {
    private static Stream<Arguments> filePathsAndExpectedRoot() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day21/example_input",
                        152L
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day21/input",
                        276156919469632L
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedRoot")
    void testCalculateRoot(final String filename,
                           final long expectedRoot) throws IOException {
        assertEquals(expectedRoot, MonkeyMath.calculateRoot(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedHumn() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day21/example_input",
                        301L
                ),
                Arguments.of("src/main/java/com/example/adventofcode/year2022/day21/input",
                        3441198826073L
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedHumn")
    void testCalculateHumn(final String filename,
                           final long expectedHumn) throws IOException {
        assertEquals(expectedHumn, MonkeyMath.calculateHumn(filename));
    }
}