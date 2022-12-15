package com.example.adventofcode.day15;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BeaconExclusionZoneTest {

    private static Stream<Arguments> filePathsAndRowsAndExpectedEmptyPositions() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day15/example_input",
                        10,
                        26
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day15/input",
                        2000000,
                        4919281
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndRowsAndExpectedEmptyPositions")
    void testObtainEmptyPositions(final String filename,
                                  final int row,
                                  final long expectedEmptyPositions) throws IOException {
        assertEquals(expectedEmptyPositions, BeaconExclusionZone.obtainEmptyPositions(filename, row));
    }

    private static Stream<Arguments> filePathsAndRangesAndExpectedTuningFrequencies() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day15/example_input",
                        20,
                        56000011
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day15/input",
                        4000000,
                        12630143363767L
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndRangesAndExpectedTuningFrequencies")
    void testObtainTuningFrequency(final String filename,
                                   final int range,
                                   final long expectedTuningFrequency) throws IOException {
        assertEquals(expectedTuningFrequency, BeaconExclusionZone.obtainTuningFrequency(filename, range));
    }
}