package com.example.adventofcode.year2025.day02;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftShopTest {

    private static Stream<Arguments> filepathsAndExpectedSumOfInvalidIds() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day02/example_input", 1227775554L),
                Arguments.of("AdventOfCodeData/2025/day02/input", 22062284697L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSumOfInvalidIds")
    void sumInvalidIds(final String filename,
                       final long expectedSumOfInvalidIds) throws IOException {
        assertEquals(expectedSumOfInvalidIds, GiftShop.sumInvalidIds(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedSumOfInvalidIdsForNewRules() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2025/day02/example_input", 4174379265L),
                Arguments.of("AdventOfCodeData/2025/day02/input", 46666175279L)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedSumOfInvalidIdsForNewRules")
    void sumInvalidIdsByNewRules(final String filename,
                                 final long expectedSumOfInvalidIds) throws IOException {
        assertEquals(expectedSumOfInvalidIds, GiftShop.sumInvalidIdsByNewRules(filename));
    }
}