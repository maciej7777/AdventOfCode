package com.example.adventofcode.year2023.day18;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LavaductLagoonTest {

    private static Stream<Arguments> filepathsAndExpectedLavaHold() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day18/example_input", 62),
                Arguments.of("AdventOfCodeData/2023/day18/input", 46334)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedLavaHold")
    void calculateLavaHold(final String filename,
                            final int expectedLavaHold) throws IOException  {
        assertEquals(expectedLavaHold, LavaductLagoon.calculateLavaHold(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedCorrectedLavaHold() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day18/example_input", 952408144115L),
                Arguments.of("AdventOfCodeData/2023/day18/input", 102000662718092L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCorrectedLavaHold")
    void calculateCorrectedLavaHold(final String filename,
                            final long expectedLavaHold) throws IOException {
        assertEquals(expectedLavaHold, LavaductLagoon.calculateCorrectedLavaHold(filename));
    }
}