package com.example.adventofcode.year2025.day05;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CafeteriaTest {

    private static Stream<Arguments> filepathsAndExpectedFreshIngredientsAvailable() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day05/example_input", 3),
                Arguments.of("AdventOfCodeData/2025/day05/input", 885)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFreshIngredientsAvailable")
    void countFreshIngredientsAvailable(final String filename,
                                        final long expectedFreshIngredientsAvailable) throws IOException {
        assertEquals(expectedFreshIngredientsAvailable, Cafeteria.countFreshIngredientsAvailable(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedFreshIngredients() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day05/example_input", 14),
                Arguments.of("AdventOfCodeData/2025/day05/input", 348115621205535L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFreshIngredients")
    void countFreshIngredients(final String filename,
                               final long expectedFreshIngredients) throws IOException {
        assertEquals(expectedFreshIngredients, Cafeteria.countFreshIngredients(filename));
    }
}