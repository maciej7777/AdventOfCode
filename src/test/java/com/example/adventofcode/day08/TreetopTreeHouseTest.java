package com.example.adventofcode.day08;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TreetopTreeHouseTest {
    private static Stream<Arguments> filePathsAndExpectedTreesVisibleFromOutside() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day08/example_input",
                        21
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day08/input",
                        1684
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedTreesVisibleFromOutside")
    void testCalculateTreesVisibleFromOutside(final String fileName,
                                              final int expectedTreesVisibleFromOutside) throws IOException {
        assertEquals(expectedTreesVisibleFromOutside, TreetopTreeHouse.calculateTreesVisibleFromOutside(fileName));
    }

    private static Stream<Arguments> filePathsAndExpectedHighestScenicScore() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day08/example_input",
                        8
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day08/input",
                        486540
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedHighestScenicScore")
    void testCalculateHighestScenicScore(final String fileName,
                                         final int expectedHighestScenicScore) throws IOException {
        assertEquals(expectedHighestScenicScore, TreetopTreeHouse.calculateHighestScenicScore(fileName));
    }
}