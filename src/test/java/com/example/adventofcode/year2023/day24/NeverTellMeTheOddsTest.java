package com.example.adventofcode.year2023.day24;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NeverTellMeTheOddsTest {

    private static Stream<Arguments> filepathsAndExpectedIntersections() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day24/example_input",7L, 27L, 2),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day24/input", 200000000000000L, 400000000000000L, 18184)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedIntersections")
    void countXYLinesIntersectionsInArea(final String filename,
                                         final long areaBeginIndex,
                                         final long areaEndIndex,
                                         final int expectedLongestHike) throws IOException {
        assertEquals(expectedLongestHike, NeverTellMeTheOdds.countXYLinesIntersectionsInArea(filename, areaBeginIndex, areaEndIndex));
    }

    private static Stream<Arguments> filepathsAndExpectedCoordinatesSum() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day24/example_input", 47),
                Arguments.of("src/main/java/com/example/adventofcode/year2023/day24/input", 557789988450159L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedCoordinatesSum")
    void obtainThrowingPositionCoordinatesSum(final String filename,
                                              final long expectedCoordinatesSum) throws IOException {
        assertEquals(expectedCoordinatesSum, NeverTellMeTheOdds.obtainThrowingPositionCoordinatesSum(filename));
    }
}