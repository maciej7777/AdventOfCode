package com.example.adventofcode.year2024.day21;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeypadConundrumTest {
    private static Stream<Arguments> filepathsAndNumberOfKeypadsAndExpectedSumOfComplexities() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day21/example_input", 2, 126384),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day21/input", 2, 136780),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day21/example_input", 25, 154115708116294L),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day21/input", 25, 167538833832712L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndNumberOfKeypadsAndExpectedSumOfComplexities")
    void calculateSumOfCodeComplexities(final String filename,
                                        final int numberOfKeypads,
                                        final long expectedSumOfComplexities) throws IOException {
        assertEquals(expectedSumOfComplexities, KeypadConundrum.calculateSumOfCodeComplexities(filename, numberOfKeypads));
    }
}