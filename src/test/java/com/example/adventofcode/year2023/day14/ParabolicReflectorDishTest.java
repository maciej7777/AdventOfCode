package com.example.adventofcode.year2023.day14;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParabolicReflectorDishTest {

    private static Stream<Arguments> filepathsAndExpectedTotalLoad() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day14/example_input", 136),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day14/input", 109098)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalLoad")
    void calculateTotalLoad(final String filename,
                            final int expectedTotalLoad) throws IOException {
        assertEquals(expectedTotalLoad, ParabolicReflectorDish.calculateTotalLoad(filename));
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalLoad")
    void calculateTotalLoadAdvanced(final String filename,
                                    final int expectedTotalLoad) throws IOException {
        assertEquals(expectedTotalLoad, ParabolicReflectorDish.calculateTotalLoadAdvanced(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedTotalLoadWithSpinCycle() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day14/example_input", 64),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day14/input", 100064)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedTotalLoadWithSpinCycle")
    void calculateTotalLoadWithSpinCycle(final String filename,
                                         final int expectedTotalLoad) throws IOException {
        assertEquals(expectedTotalLoad, ParabolicReflectorDish.calculateTotalLoadWithSpinCycle(filename));
    }
}