package com.example.adventofcode.year2023.day12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HotSpringsTest {

    private static Stream<Arguments> filepathsAndExpectedSpringArrangements() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day12/example_input", 21),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day12/input", 7251)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSpringArrangements")
    void sumOfSpringArrangements(final String filename,
                                 final int expectedSpringArrangements) throws IOException {
        assertEquals(expectedSpringArrangements, HotSprings.obtainSumOfSpringArrangements(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedFoldedSpringArrangements() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day12/example_input", 525152),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day12/input", 2128386729962L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFoldedSpringArrangements")
    void sumOfFoldedSpringArrangements(final String filename,
                                       final long expectedSpringArrangements) throws IOException {
        assertEquals(expectedSpringArrangements, HotSprings.obtainSumOfFoldedSpringArrangements(filename));
    }
}