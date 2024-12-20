package com.example.adventofcode.year2024.day13;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static com.example.adventofcode.year2024.day13.ClawContraption.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ClawContraptionTest {
    private static Stream<Arguments> filepathsAndprizePositionModifierAndExpectedTokens() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day13/example_input", new Point(0L, 0L), 480),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day13/input", new Point(0L, 0L), 31761),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day13/example_input", new Point(10000000000000L, 10000000000000L), 875318608908L),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day13/input", new Point(10000000000000L, 10000000000000L), 90798500745591L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndprizePositionModifierAndExpectedTokens")
    void calculateMinimalNumberOfTokens(final String filename,
                                        final Point prizePositionModifier,
                                        final int expectedTokens) throws IOException {
        assertEquals(expectedTokens, ClawContraption.calculateMinimalNumberOfTokens(filename, prizePositionModifier));
    }
}