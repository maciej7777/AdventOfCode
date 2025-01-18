package com.example.adventofcode.year2021.day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiveTest {

    private static Stream<Arguments> filepathsAndExpectedPositionProduct() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2021/day02/example_input", 150),
                Arguments.of("AdventOfCodeData/2021/day02/input", 1714680)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPositionProduct")
    void testCalculateDepthIncreases(final String filename,
                                     final int expectedPositionProduct) throws IOException {
        assertEquals(expectedPositionProduct, Dive.calculatePositionProduct(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedPositionProductWithAim() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2021/day02/example_input", 900),
                Arguments.of("AdventOfCodeData/2021/day02/input", 1963088820)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPositionProductWithAim")
    void testCalculatePositionProductWithAim(final String filename,
                                             final int expectedPositionProductWithAim) throws IOException {
        assertEquals(expectedPositionProductWithAim, Dive.calculatePositionProductWithAim(filename));
    }
}