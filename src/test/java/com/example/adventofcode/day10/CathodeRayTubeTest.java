package com.example.adventofcode.day10;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CathodeRayTubeTest {

    private static Stream<Arguments> filepathsAndExpectedResults() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day10/example_input",
                        13140,
                        "##..##..##..##..##..##..##..##..##..##.." +
                        "###...###...###...###...###...###...###." +
                        "####....####....####....####....####...." +
                        "#####.....#####.....#####.....#####....." +
                        "######......######......######......####" +
                        "#######.......#######.......#######....."
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day10/input",
                        11820,
                        "####.###....##.###..###..#..#..##..#..#." +
                        "#....#..#....#.#..#.#..#.#.#..#..#.#..#." +
                        "###..#..#....#.###..#..#.##...#..#.####." +
                        "#....###.....#.#..#.###..#.#..####.#..#." +
                        "#....#....#..#.#..#.#.#..#.#..#..#.#..#." +
                        "####.#.....##..###..#..#.#..#.#..#.#..#."
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedResults")
    void testObtainSignalStrengthSumAndDrawings(final String filename,
                                              final int expectedSignalStrengthSum,
                                              final String expectedDrawing) throws IOException {
        assertEquals(expectedSignalStrengthSum, CathodeRayTube.obtainSignalStrengthSumAndDrawings(filename).signalStrengthSum());
        assertEquals(expectedDrawing, CathodeRayTube.obtainSignalStrengthSumAndDrawings(filename).drawing());
    }
}