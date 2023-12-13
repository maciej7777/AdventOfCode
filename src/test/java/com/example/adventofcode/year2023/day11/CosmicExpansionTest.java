package com.example.adventofcode.year2023.day11;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CosmicExpansionTest {
    private static Stream<Arguments> filepathsAndExpectedSumOfDistances() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/example_input", 374),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/input", 9648398)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSumOfDistances")
    void testCalculateSumOfDistances(final String filename,
                                     final long expectedSumOfDistances) throws IOException {
        assertEquals(expectedSumOfDistances, CosmicExpansion.calculateSumOfDistances(filename));
    }

    private static Stream<Arguments> filepathsAndExpansionAndExpectedSumOfDistances() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/example_input", 2, 374),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/input", 2, 9648398),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/example_input", 10, 1030),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/example_input", 100, 8410),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/example_input", 1000000, 82000210),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day11/input", 1000000, 618800410814L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpansionAndExpectedSumOfDistances")
    void testCalculateSumOfDistances(final String filename,
                                     final long expansion,
                                     final long expectedSumOfDistances) throws IOException {
        assertEquals(expectedSumOfDistances, CosmicExpansion.calculateSumOfDistancesWithExpansion(filename, expansion));
    }
}