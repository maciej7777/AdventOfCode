package com.example.adventofcode.year2021.day01;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class SonarSweep {
    private static final String FILENAME = "AdventOfCodeData/2021/day01/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2021/day01/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateDepthIncreases(EXAMPLE_FILENAME));
        System.out.println(calculateDepthIncreases(FILENAME));
        System.out.println(calculateDepthIncreasesWithSlidingWindow(EXAMPLE_FILENAME, 3));
        System.out.println(calculateDepthIncreasesWithSlidingWindow(FILENAME, 3));
    }

    public static long calculateDepthIncreases(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Integer> measurements = parseMeasurements(lines);

        int level = measurements.getFirst();
        int levelIncreases = 0;
        for (int i = 1; i < measurements.size(); i++) {
            if (level < measurements.get(i)) {
                levelIncreases++;
            }
            level = measurements.get(i);
        }

        return levelIncreases;
    }

    public static long calculateDepthIncreasesWithSlidingWindow(final String filename, final int windowSize) throws IOException {
        List<String> lines = readLines(filename);
        List<Integer> measurements = parseMeasurements(lines);

        int level = 0;
        for (int i = 0; i < windowSize; i++) {
            level += measurements.get(i);
        }

        int levelIncreases = 0;
        for (int i = windowSize; i < measurements.size(); i++) {
            int nextLevel = level + measurements.get(i) - measurements.get(i - windowSize);
            if (level < nextLevel) {
                levelIncreases++;
            }
            level = nextLevel;
        }

        return levelIncreases;
    }

    private static List<Integer> parseMeasurements(List<String> lines) {
        return lines.stream()
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();
    }
}
