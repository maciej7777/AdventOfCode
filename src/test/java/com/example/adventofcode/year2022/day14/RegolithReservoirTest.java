package com.example.adventofcode.year2022.day14;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegolithReservoirTest {
    private static Stream<Arguments> filePathsAndExpectedNumberOfUnitsOfSandToStartFlowing() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day14/example_input",
                        24
                ),
                Arguments.of("AdventOfCodeData/2022/day14/input",
                        808
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedNumberOfUnitsOfSandToStartFlowing")
    void testObtainNumberOfUnitsOfSandToStartFlowing(final String filename,
                                                     final long expectedNumberOfUnitsOfSandToStartFlowing) throws IOException {
        assertEquals(expectedNumberOfUnitsOfSandToStartFlowing, RegolithReservoir.obtainNumberOfUnitsOfSandToStartFlowing(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedNumberOfUnitsOfSandToBlockSource() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day14/example_input",
                        93
                ),
                Arguments.of("AdventOfCodeData/2022/day14/input",
                        26625
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedNumberOfUnitsOfSandToBlockSource")
    void testObtainNumberOfUnitsOfSandToBlockSource(final String filename,
                                                    final long expectedNumberOfUnitsOfSandToBlockSource) throws IOException {
        assertEquals(expectedNumberOfUnitsOfSandToBlockSource, RegolithReservoir.obtainNumberOfUnitsOfSandToBlockSource(filename));
    }
}