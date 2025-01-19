package com.example.adventofcode.year2024.day22;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class MonkeyMarket {
    private static final String FILENAME = "AdventOfCodeData/2024/day22/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day22/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2024/day22/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfSecretNumbers(EXAMPLE_FILENAME, 2000));
        System.out.println(calculateSumOfSecretNumbers(FILENAME, 2000));
        System.out.println(calculateMaxBananasNumber(EXAMPLE_FILENAME2, 2000));
        System.out.println(calculateMaxBananasNumber(FILENAME, 2000));
    }

    private record Difference(int difference, int bananas) {
    }

    private record Pattern(int element1, int element2, int element3, int element4) {
    }

    public static long calculateSumOfSecretNumbers(final String filename, final int numberOfSecretNumbers) throws IOException {
        List<String> lines = readLines(filename);

        long result = 0L;
        for (String line : lines) {
            long secretNumber = Long.parseLong(line);
            for (int i = 0; i < numberOfSecretNumbers; i++) {
                secretNumber = calculateNewSecret(secretNumber);
            }

            result += secretNumber;
        }

        return result;
    }

    public static long calculateMaxBananasNumber(final String filename, final int numberOfSecretNumbers) throws IOException {
        List<String> lines = readLines(filename);

        Map<Pattern, Integer> patterns = new HashMap<>();
        for (String line : lines) {
            List<Difference> differences = calculateLastDigitDifferences(numberOfSecretNumbers, line);

            Set<Pattern> seen = new HashSet<>();
            for (int i = 3; i < differences.size(); i++) {
                Pattern newPattern = new Pattern(
                        differences.get(i - 3).difference,
                        differences.get(i - 2).difference,
                        differences.get(i - 1).difference,
                        differences.get(i).difference
                );
                if (!seen.contains(newPattern)) {
                    seen.add(newPattern);
                    patterns.put(newPattern, patterns.getOrDefault(newPattern, 0) + differences.get(i).bananas);
                }
            }
        }

        int max = 0;
        for (Map.Entry<Pattern, Integer> pattern: patterns.entrySet()) {
            if (pattern.getValue() > max) {
                max = pattern.getValue();
            }
        }

        return max;
    }

    private static Long calculateNewSecret(Long secretNumber) {
        long newSecretNumber = secretNumber * 64;
        newSecretNumber = secretNumber ^ newSecretNumber;
        secretNumber = newSecretNumber % 16777216;

        newSecretNumber = secretNumber / 32;
        newSecretNumber = secretNumber ^ newSecretNumber;
        secretNumber = newSecretNumber % 16777216;

        newSecretNumber = secretNumber * 2048;
        newSecretNumber = secretNumber ^ newSecretNumber;
        return newSecretNumber % 16777216;
    }

    private static List<Difference> calculateLastDigitDifferences(int numberOfSecretNumbers, String line) {
        long secretNumber = Long.parseLong(line);
        List<Difference> differences = new ArrayList<>();
        for (int i = 0; i < numberOfSecretNumbers; i++) {
            long newSecretNumber = calculateNewSecret(secretNumber);
            int newDifference = (int) (newSecretNumber % 10);
            int diff = newDifference - (int) (secretNumber % 10);
            differences.add(new Difference(diff, newDifference));

            secretNumber = newSecretNumber;
        }
        return differences;
    }
}
