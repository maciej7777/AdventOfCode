package com.example.adventofcode.year2023.day12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HotSpringsBruteForceTest {
    private static Stream<Arguments> filepathsAndExpectedSpringArrangements() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day12/example_input", 21),
                Arguments.of("AdventOfCodeData/2023/day12/input", 7251)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSpringArrangements")
    void sumOfSpringArrangements(final String filename,
                                 final int expectedSpringArrangements) throws IOException {
        assertEquals(expectedSpringArrangements, HotSpringsBruteForce.obtainSumOfSpringArrangements(filename));
    }
}