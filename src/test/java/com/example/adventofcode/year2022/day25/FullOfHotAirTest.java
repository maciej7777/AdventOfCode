package com.example.adventofcode.year2022.day25;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FullOfHotAirTest {
    private static Stream<Arguments> filePathsAndExpectedFuelNeeded() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day25/example_input",
                        "2=-1=0"
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day25/input",
                        "2-0-020-1==1021=--01"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedFuelNeeded")
    void testCalculateFuelNeeded(final String filename,
                                 final String expectedFuelNeeded) throws IOException {
        assertEquals(expectedFuelNeeded, FullOfHotAir.calculateFuelNeeded(filename));
    }
}