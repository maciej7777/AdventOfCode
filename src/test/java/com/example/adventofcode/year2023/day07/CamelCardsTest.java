package com.example.adventofcode.year2023.day07;

import com.example.adventofcode.year2023.day07.CamelCards;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CamelCardsTest {

    private static Stream<Arguments> filepathsAndExpectedTotalWinnings() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day07/example_input", 6440),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day07/input", 248179786)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalWinnings")
    void testSimulateTotalWinnings(final String filename,
                                   final int expectedPairs) throws IOException {
        assertEquals(expectedPairs, CamelCards.simulateTotalWinnings(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedTotalJokerWinnings() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day07/example_input", 5905),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day07/input", 247885995)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalJokerWinnings")
    void testSimulateTotalJokerWinnings(final String filename,
                                        final int expectedPairs) throws IOException {
        assertEquals(expectedPairs, CamelCards.simulateTotalJokerWinnings(filename));
    }
}