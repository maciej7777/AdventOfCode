package com.example.adventofcode.year2025.day12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChristmasTreeFarmTest {

    private static Stream<Arguments> filepathsAndExpectedRegionsFittingAllPresents() {
        return Stream.of(
                //Arguments.of("AdventOfCodeData/2025/day12/example_input", 2),
                Arguments.of("AdventOfCodeData/2025/day12/input", 474)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedRegionsFittingAllPresents")
    void countRegionsFittingAllPresents(final String filename,
                                        final long expectedRegionsFittingAllPresents) throws IOException {
        assertEquals(expectedRegionsFittingAllPresents, ChristmasTreeFarm.countRegionsFittingAllPresents(filename));
    }
}