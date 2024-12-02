package com.example.adventofcode.year2024.day02;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RedNosedReports {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day02/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day02/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countSafeReports(EXAMPLE_FILENAME));
        System.out.println(countSafeReports(FILENAME));
        System.out.println(countSafeReportsWithToleration(EXAMPLE_FILENAME));
        System.out.println(countSafeReportsWithToleration(FILENAME));

    }

    public static long countSafeReports(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int safe = 0;
        for (String line : lines) {
            if (isSafe(parseLevels(line))) safe++;
        }

        return safe;
    }

    public static long countSafeReportsWithToleration(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int safe = 0;
        for (String line : lines) {
            int[] levels = parseLevels(line);
            if (isSafe(levels)) {
                safe++;
            } else {
                int potentiallySafe = 0;
                for (int i = 0; i < levels.length; i++) {
                    int[] levelsCopy = Arrays.copyOf(levels, levels.length);
                    int[] levelsRemoved = ArrayUtils.remove(levelsCopy, i);
                    if (isSafe(levelsRemoved)) {
                        potentiallySafe++;
                    }
                }
                if (potentiallySafe > 0) {
                    safe++;
                }
            }
        }
        return safe;
    }

    private static List<String> readLines(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static int[] parseLevels(String line) {
        return Arrays.stream(line.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static boolean isSafe(int[] levels) {
        int order = 0;

        for (int i = 0; i < levels.length - 1; i++) {
            int difference = levels[i + 1] - levels[i];
            int absDiff = Math.abs(difference);
            if (absDiff < 1 || absDiff > 3) {
                return false;
            }
            if ((order < 0 && difference > 0) || (order > 0 && difference < 0)) {
                return false;
            }
            order = difference;
        }
        return true;
    }
}
