package com.example.adventofcode.year2025.day06;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TrashCompactorTest {

    private static Stream<Arguments> filepathsAndExpectedMathHomeworkResult() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day06/example_input", 4277556),
                Arguments.of("AdventOfCodeData/2025/day06/input", 6503327062445L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMathHomeworkResult")
    void solveMathHomework(final String filename,
                           final long expectedMathHomeworkResult) throws IOException {
        assertEquals(expectedMathHomeworkResult, TrashCompactor.solveMathHomework(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedMathHomeworkResultInCephalopodWay() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day06/example_input", 3263827),
                Arguments.of("AdventOfCodeData/2025/day06/input", 9640641878593L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMathHomeworkResultInCephalopodWay")
    void solveMathHomeworkInCephalopodWay(final String filename,
                                          final long expectedMathHomeworkResult) throws IOException {
        assertEquals(expectedMathHomeworkResult, TrashCompactor.solveMathHomeworkInCephalopodWay(filename));
    }
}