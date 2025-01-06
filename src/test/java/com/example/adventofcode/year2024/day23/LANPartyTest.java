package com.example.adventofcode.year2024.day23;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LANPartyTest {
    private static Stream<Arguments> filepathsAndExpectedInterConnectedComputersWithTElement() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day23/example_input", 7),
                Arguments.of("AdventOfCodeData/2024/day23/input", 1098)
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedInterConnectedComputersWithTElement")
    void calculateInterConnectedComputersWithTElement(final String filename,
                                                      final long expectedInterConnectedComputersWithTElement) throws IOException {
        assertEquals(expectedInterConnectedComputersWithTElement, LANParty.calculateInterConnectedComputersWithTElement(filename));
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedInterConnectedComputersWithTElement")
    void calculateInterConnectedComputersWithTElementBronKerbosch(final String filename,
                                                                  final long expectedInterConnectedComputersWithTElement) throws IOException {
        assertEquals(expectedInterConnectedComputersWithTElement, LANParty.calculateInterConnectedComputersWithTElementBronKerbosch(filename));
    }

    private static Stream<Arguments> filepathsAndExpectedPassword() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2024/day23/example_input", "co,de,ka,ta"),
                Arguments.of("AdventOfCodeData/2024/day23/input", "ar,ep,ih,ju,jx,le,ol,pk,pm,pp,xf,yu,zg")
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedPassword")
    void calculatePassword(final String filename,
                           final String expectedPassword) throws IOException {
        assertEquals(expectedPassword, LANParty.calculatePassword(filename));
    }
}