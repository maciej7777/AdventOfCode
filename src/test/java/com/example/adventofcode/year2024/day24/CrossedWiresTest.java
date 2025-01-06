package com.example.adventofcode.year2024.day24;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrossedWiresTest {
    private static Stream<Arguments> filepathsAndExpectedZ() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day24/example_input", 4),
                Arguments.of("AdventOfCodeData/2024/day24/example_input2", 2024),
                Arguments.of("AdventOfCodeData/2024/day24/input", 56278503604006L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedZ")
    void calculateZ(final String filename,
                    final long expectedZ) throws IOException {
        assertEquals(expectedZ, CrossedWires.calculateZ(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedSwitchedWires() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day24/input", "bhd,brk,dhg,dpd,nbf,z06,z23,z38")
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSwitchedWires")
    void findSwitchedWires(final String filename,
                           final String expectedSwitchedWires) throws IOException {
        assertEquals(expectedSwitchedWires, CrossedWires.findSwitchedWires(filename));
    }
}