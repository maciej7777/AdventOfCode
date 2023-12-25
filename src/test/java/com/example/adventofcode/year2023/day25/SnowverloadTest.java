package com.example.adventofcode.year2023.day25;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnowverloadTest {
    private static Stream<Arguments> filepathsAndExpectedMinCutsSizesMultiplication() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day25/example_input", 54),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day25/input", 551196)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedMinCutsSizesMultiplication")
    void calculateMinWiresCutSets(final String filename,
                                  final int expectedMinCutsSizesMultiplication) throws IOException {
        assertEquals(expectedMinCutsSizesMultiplication, Snowverload.calculateMinWiresCutSets(filename));
    }
}