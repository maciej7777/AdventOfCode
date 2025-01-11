package com.example.adventofcode.year2022.day07;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoSpaceLeftOnDeviceTest {
    private static Stream<Arguments> filePathsAndExpectedSmallFilesSizeSum() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day07/example_input",
                        95437
                ),
                Arguments.of("AdventOfCodeData/2022/day07/input",
                        1444896
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedSmallFilesSizeSum")
    void testObtainSmallFilesSizeSum(final String filename,
                                     final int expectedSmallFilesSizeSum) throws IOException {
        assertEquals(expectedSmallFilesSizeSum, NoSpaceLeftOnDevice.obtainSmallFilesSizeSum(filename));
    }

    private static Stream<Arguments> filePathsAndExpectedSumOfEncryptedGroveCoordinates() {
        return Stream.of(
                Arguments.of("AdventOfCodeData/2022/day07/example_input",
                        24933642
                ),
                Arguments.of("AdventOfCodeData/2022/day07/input",
                        404395
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filePathsAndExpectedSumOfEncryptedGroveCoordinates")
    void testObtainFileToRemove(final String filename,
                                final int expectedFileToRemove) throws IOException {
        assertEquals(expectedFileToRemove, NoSpaceLeftOnDevice.obtainFileToRemove(filename));
    }
}