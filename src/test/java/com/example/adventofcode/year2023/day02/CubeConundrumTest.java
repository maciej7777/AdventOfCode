package com.example.adventofcode.year2023.day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CubeConundrumTest {

    private static Stream<Arguments> filepathsAndExpectedPossibleGamesIdsSum() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day02/example_input", 8),
                Arguments.of("AdventOfCodeData/2023/day02/input", 2685)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPossibleGamesIdsSum")
    void sumPossibleGamesIds(final String filename,
                             final int expectedPossibleGamesIdsSum) throws IOException {
        assertEquals(expectedPossibleGamesIdsSum, CubeConundrum.sumPossibleGamesIds(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedPowerOfSetsOfCubesSum() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2023/day02/example_input", 2286),
                Arguments.of("AdventOfCodeData/2023/day02/input", 83707)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPowerOfSetsOfCubesSum")
    void sumPowerOfSetsOfCubes(final String filename,
                               final int expectedPowerOfSetsOfCubesSum) throws IOException {
        assertEquals(expectedPowerOfSetsOfCubesSum, CubeConundrum.sumPowerOfSetsOfCubes(filename));

    }
}