package com.example.adventofcode.year2023.day19;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AplentyTest {

    private static Stream<Arguments> filepathsAndExpectedRatingsSum() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day19/example_input", 19114),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day19/input", 319062)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedRatingsSum")
    void calculateAcceptedPartsRatingsSum(final String filename,
                                          final int expectedRatingsSum) throws IOException {
        assertEquals(expectedRatingsSum, Aplenty.calculateAcceptedPartsRatingsSum(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedCombinationsCount() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day19/example_input", 167409079868000L),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day19/input", 118638369682135L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCombinationsCount")
    void calculateAcceptedCombinations(final String filename,
                                       final long expectedCombinationsCount) throws IOException {
        assertEquals(expectedCombinationsCount, Aplenty.calculateAcceptedCombinationsCount(filename));
    }
}