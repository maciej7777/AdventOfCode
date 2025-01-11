package com.example.adventofcode.year2022.day18;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoilingBouldersTest {
    private static Stream<Arguments> filePathsAndRowsAndExpectedSurfaceArea() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day18/example_input",
                        64
                ),
                Arguments.of("AdventOfCodeData/2022/day18/input",
                        4460
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndRowsAndExpectedSurfaceArea")
    void testObtainSurfaceArea(final String filename,
                               final long expectedSurfaceArea) throws IOException {
        assertEquals(expectedSurfaceArea, BoilingBoulders.obtainSurfaceArea(filename));
    }

    private static Stream<Arguments> filePathsAndRowsAndExpectedExteriorSurfaceArea() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day18/example_input",
                        58
                ),
                Arguments.of("AdventOfCodeData/2022/day18/input",
                        2498
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndRowsAndExpectedExteriorSurfaceArea")
    void testObtainExteriorSurfaceArea(final String filename,
                                                     final int expectedExteriorSurfaceArea) throws IOException {
        assertEquals(expectedExteriorSurfaceArea, BoilingBoulders.obtainExteriorSurfaceArea(filename));
    }
}