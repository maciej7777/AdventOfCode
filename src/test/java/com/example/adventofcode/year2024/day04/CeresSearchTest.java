package com.example.adventofcode.year2024.day04;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CeresSearchTest {

    private static Stream<Arguments> filepathsAndExpectedXMASWords() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/example_input", 18),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/input", 2464)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedXMASWords")
    void countXMASWords(final String filename,
                        final int expectedXMASWords) throws IOException {
        assertEquals(expectedXMASWords, CeresSearch.countXMASWords(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedXShapedMASWords() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/example_input", 9),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day04/input", 1982)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedXShapedMASWords")
    void countXShapedMASWords(final String filename,
                              final int expectedXShapedMASWords) throws IOException {
        assertEquals(expectedXShapedMASWords, CeresSearch.countXShapedMASWords(filename));
    }
}