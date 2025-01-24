package com.example.adventofcode.year2021.day03;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class BinaryDiagnostic {
    private static final String FILENAME = "AdventOfCodeData/2021/day03/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2021/day03/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculatePowerConsumption(EXAMPLE_FILENAME));
        System.out.println(calculatePowerConsumption(FILENAME));
        System.out.println(calculateLifeSupportRating(EXAMPLE_FILENAME));
        System.out.println(calculateLifeSupportRating(FILENAME));
    }

    public static long calculatePowerConsumption(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Integer> counters = countOnes(lines);

        int numberOfLines = lines.size();
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();
        for (int counter : counters) {
            if (counter > numberOfLines / 2) {
                gammaRate.append('1');
                epsilonRate.append('0');
            } else {
                gammaRate.append('0');
                epsilonRate.append('1');
            }
        }

        return calculateBinaryNumber(gammaRate.toString()) * calculateBinaryNumber(epsilonRate.toString());
    }

    public static long calculateLifeSupportRating(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        String oxygenGeneratorRating = calculateOxygenGeneratorRating(lines);
        String co2ScrubberRating = calculateCo2ScrubberRating(lines);

        return calculateBinaryNumber(oxygenGeneratorRating) * calculateBinaryNumber(co2ScrubberRating);
    }

    private static List<Integer> countOnes(List<String> lines) {
        List<Integer> counters = new ArrayList<>();
        for (int i = 0; i < lines.getFirst().length(); i++) {
            counters.add(0);
        }

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '1') {
                    counters.set(i, counters.get(i) + 1);
                }
            }
        }
        return counters;
    }

    private static String calculateOxygenGeneratorRating(List<String> lines) {
        List<String> numbersToConsider = new ArrayList<>(lines);

        for (int i = 0; i < lines.getFirst().length(); i++) {
            List<String> nextNumbers = new ArrayList<>();
            char expectedBit = countOnesOnPosition(numbersToConsider, i) * 2 >= numbersToConsider.size() ? '1' : '0';
            for (String number : numbersToConsider) {
                if (number.charAt(i) == expectedBit) {
                    nextNumbers.add(number);
                }
            }
            if (nextNumbers.size() == 1) {
                return nextNumbers.getFirst();
            }
            numbersToConsider = nextNumbers;
        }
        return "";
    }

    private static String calculateCo2ScrubberRating(List<String> lines) {
        List<String> numbersToConsider = new ArrayList<>(lines);

        for (int i = 0; i < lines.getFirst().length(); i++) {
            List<String> nextNumbers = new ArrayList<>();
            char expectedBit = countOnesOnPosition(numbersToConsider, i) * 2 >= numbersToConsider.size() ? '0' : '1';
            for (String number : numbersToConsider) {
                if (number.charAt(i) == expectedBit) {
                    nextNumbers.add(number);
                }
            }
            if (nextNumbers.size() == 1) {
                return nextNumbers.getFirst();
            }
            numbersToConsider = nextNumbers;
        }
        return "";
    }

    private static int countOnesOnPosition(List<String> lines, int position) {
        int counter = 0;

        for (String line : lines) {
            if (line.charAt(position) == '1') {
                counter++;
            }
        }
        return counter;
    }

    private static long calculateBinaryNumber(String number) {
        long result = 0;
        long divider = 1;
        for (int i = number.length() - 1; i >= 0; i--) {
            int myInt = number.charAt(i) == '1' ? 1 : 0;

            result += myInt * divider;
            divider *= 2;
        }
        return result;
    }
}
