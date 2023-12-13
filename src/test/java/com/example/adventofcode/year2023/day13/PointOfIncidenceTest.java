package com.example.adventofcode.year2023.day13;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointOfIncidenceTest {
    private static Stream<Arguments> filepathsAndSmudgesAndExpectedSummarizedNotes() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day13/example_input", 0, 405),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day13/input", 0, 33122),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day13/example_input", 1, 400),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day13/input", 1, 32312)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndSmudgesAndExpectedSummarizedNotes")
    void testSummarizePatternNotes(final String filename,
                                   final int smudges,
                                   final int expectedSummarizedNotes) throws IOException {
        assertEquals(expectedSummarizedNotes, PointOfIncidence.summarizePatternNotes(filename, smudges));
    }
}