package com.example.adventofcode.year2023.day22;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SandSlabsTest {

    private static Stream<Arguments> filepathsAndExpectedSafeToDisintegrateBricksCount() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day22/example_input", 5),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day22/input", 471)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSafeToDisintegrateBricksCount")
    void countSafeToDisintegrateBricks(final String filename,
                                       final int expectedSafeToDisintegrateBricksCount) throws IOException {
        assertEquals(expectedSafeToDisintegrateBricksCount, SandSlabs.countSafeToDisintegrateBricks(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedFallingBricksCount() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day22/example_input", 7),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day22/input", 68525)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFallingBricksCount")
    void countFallingBricks(final String filename,
                            final int expectedFallingBricksCount) throws IOException {
        assertEquals(expectedFallingBricksCount, SandSlabs.countFallingBricks(filename));
    }
}