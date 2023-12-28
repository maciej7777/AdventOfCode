package com.example.adventofcode.year2023.day08;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HauntedWastelandTest {

    private static Stream<Arguments> filepathsAndExpectedSteps() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day08/example_input", 2),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day08/example_input2", 6),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day08/input", 21389)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSteps")
    void calculateStepsToReachZZZ(final String filename,
                                  final int expectedSteps) throws IOException {
        assertEquals(expectedSteps, HauntedWasteland.calculateStepsToReachZZZ(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedStepsByGhosts() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day08/example_input3", 6),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day08/input", 21083806112641L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedStepsByGhosts")
    void calculateStepsToReachZNodesByGhosts(final String filename,
                                             final long expectedSteps) throws IOException {
        assertEquals(expectedSteps, HauntedWasteland.calculateStepsToReachZNodesByGhosts(filename));
    }
}