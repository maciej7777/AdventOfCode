package com.example.adventofcode.year2024.day12;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GardenGroupsTest {
    private static Stream<Arguments> filepathsAndExpectedFencingPrice() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/example_input", 140),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/example_input2", 772),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/example_input3", 1930),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/input", 1473620)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFencingPrice")
    void calculateFencingPrice(final String filename,
                               final int expectedFencingPrice) throws IOException {
        assertEquals(expectedFencingPrice, GardenGroups.calculateFencingPrice(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedFencingPriceDiscounted() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/example_input", 80),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/example_input2", 436),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/example_input3", 1206),
                Arguments.of("src/main/java/com/example/adventofcode/year2024/day12/input", 902620)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedFencingPriceDiscounted")
    void calculateSumOfTrailheadsRatings(final String filename,
                                         final int expectedFencingPriceDiscounted) throws IOException {
        assertEquals(expectedFencingPriceDiscounted, GardenGroups.calculateFencingPriceDiscounted(filename));
    }
}