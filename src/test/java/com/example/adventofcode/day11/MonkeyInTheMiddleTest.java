package com.example.adventofcode.day11;

import com.example.adventofcode.day10.CathodeRayTube;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MonkeyInTheMiddleTest {
    private static Stream<Arguments> filepathsAndExpectedResults() {
        return Stream.of(
                Arguments.of("src/main/java/com/example/adventofcode/day11/example_input",
                        3,
                        20,
                        10605
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day11/example_input",
                        1,
                        10000,
                        2713310158L
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day11/input",
                        3,
                        20,
                        54752
                ),
                Arguments.of("src/main/java/com/example/adventofcode/day11/input",
                        1,
                        10000,
                        13606755504L
                )
        );
    }

    @ParameterizedTest
    @MethodSource("filepathsAndExpectedResults")
    void testCalculateFullyInclusiveIntervals(final String filename,
                                              final int divideBy,
                                              final int rounds,
                                              final long expectedMonkeyBusiness) throws IOException {
        assertEquals(expectedMonkeyBusiness, MonkeyInTheMiddle.obtainMonkeyBusiness(filename, divideBy, rounds));
    }
}