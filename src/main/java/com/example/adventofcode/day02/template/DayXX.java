package com.example.adventofcode.day02.template;

import java.io.IOException;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class DayXX {
    private static final String FILENAME = "AdventOfCodeData/2025/dayXX/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/dayXX/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSolution1(EXAMPLE_FILENAME));
        System.out.println(calculateSolution1(FILENAME));
        /*System.out.println(calculateSolution2(EXAMPLE_FILENAME));
        System.out.println(calculateSolution2(FILENAME));*/
    }


    record Point(int x, int y) {
    }

    public static long calculateSolution1(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        for (String line: lines) {
            System.out.println(line);
        }


        return 0;
    }
}
