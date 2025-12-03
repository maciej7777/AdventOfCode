package com.example.adventofcode.year2025.day03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class Lobby {
    private static final String FILENAME = "AdventOfCodeData/2025/day03/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2025/day03/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateTotalOutputJoltage(EXAMPLE_FILENAME));
        System.out.println(calculateTotalOutputJoltage(FILENAME));
        System.out.println(calculateTotalOutputJoltage(EXAMPLE_FILENAME, 2));
        System.out.println(calculateTotalOutputJoltage(FILENAME, 2));
        System.out.println(calculateTotalOutputJoltage(EXAMPLE_FILENAME, 12));
        System.out.println(calculateTotalOutputJoltage(FILENAME, 12));
    }

    public static long calculateTotalOutputJoltage(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int sum = 0;
        for (String line: lines) {
            String[] elements = line.split("");

            int max = 0;
            int maxPosition = 0;
            for (int i = 0; i < elements.length-1; i++) {
                if (Integer.parseInt(elements[i]) > max) {
                    max = Integer.parseInt(elements[i]);
                    maxPosition = i;
                }
            }

            int secondMax = 0;
            for (int i = maxPosition+1; i < elements.length; i++) {
                if (Integer.parseInt(elements[i]) > secondMax) {
                    secondMax = Integer.parseInt(elements[i]);
                }
            }

            sum += max*10+secondMax;
        }

        return sum;
    }

    public static long calculateTotalOutputJoltage(final String filename, final int joltageLength) throws IOException {
        List<String> lines = readLines(filename);
        long sum = 0;

        for (String line: lines) {
            String[] elements = line.split("");

            List<Integer> maxValues = prepareArray(joltageLength, 0);
            List<Integer> maxPositions = prepareArray(joltageLength + 1, -1);

            for (int i = 0; i < joltageLength; i++) {
                for (int j = maxPositions.get(i)+1; j < elements.length - joltageLength + i + 1; j++) {
                    if (Integer.parseInt(elements[j]) > maxValues.get(i)) {
                        maxValues.set(i, Integer.parseInt(elements[j]));
                        maxPositions.set(i + 1, j);
                    }
                }
            }

            sum += parseMaxJoltageNumber(joltageLength, maxValues);
        }

        return sum;
    }

    private static List<Integer> prepareArray(final int length, final int elements) {
        List<Integer> max = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            max.add(elements);
        }
        return max;
    }

    private static long parseMaxJoltageNumber(final int length, final List<Integer> maxValues) {
        long sum = 0;
        for (int i = 0; i < length; i++) {
            sum += (long) maxValues.get(i) * pow(10, length - i - 1L);
        }
        return sum;
    }

    private static long pow(final long a, final long b) {
        if (b == 0) return 1;
        if (b == 1) return a;
        if (((b & 1) == 0)) return pow(a * a, b / 2);
        else return a * pow(a * a, b / 2);
    }
}
