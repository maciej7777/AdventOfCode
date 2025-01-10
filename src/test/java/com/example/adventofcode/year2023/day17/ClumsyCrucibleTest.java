package com.example.adventofcode.year2023.day17;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClumsyCrucibleTest {

    private static Stream<Arguments> filepathsAndExpectedHeatLoss() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day17/example_input", 102),
                Arguments.of("AdventOfCodeData/2023/day17/input", 817)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedHeatLoss")
    void calculateHeatLoss(final String filename,
                            final int expectedEnergizedTitles) throws IOException {
        assertEquals(expectedEnergizedTitles, ClumsyCrucible.calculateHeatLoss(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedHeatLossForUltraCrucible() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day17/example_input", 94),
                Arguments.of("AdventOfCodeData/2023/day17/input", 925)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedHeatLossForUltraCrucible")
    void calculateUltraCrucibleHeatLoss(final String filename,
                            final int expectedEnergizedTitles) throws IOException {
        assertEquals(expectedEnergizedTitles, ClumsyCrucible.calculateUltraCrucibleHeatLoss(filename));
    }
}