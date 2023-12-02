package com.example.adventofcode.year2022.day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FullOfHotAir {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day25/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day25/example_input";

    public static void main(String[] args) throws IOException {
        List<String> exampleInput = readInput(EXAMPLE_FILENAME);
        List<String> input = readInput(FILENAME);

        System.out.println(calculatePositions(exampleInput));
        System.out.println(calculatePositions(input));

    }

    public static String calculateFuelNeeded(final String fileName) throws IOException {
        List<String> input = readInput(fileName);
        return calculatePositions(input);
    }

    private static List<String> readInput(final String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<String> input = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                input.add(line);
            }

            return input;
        }
    }

    private static String calculatePositions(final List<String> inputLines) {

        long total = 0L;
        for (String line : inputLines) {
            long sum = convertSnafuToDecimal(line);
            total += sum;
        }

        return convertDecimalToSnafu(total);
    }

    private static long convertSnafuToDecimal(final String line) {
        String[] elements = line.split("");
        long sum = 0;
        for (int i = 0; i < line.length(); i++) {
            double pow = Math.pow(5, line.length() - i - 1);
            if (elements[i].equals("=")) {
                sum += -2 * pow;
            } else if (elements[i].equals("-")) {
                sum += -1 * pow;
            } else {
                sum += Integer.parseInt(elements[i]) * pow;
            }
        }
        return sum;
    }

    private static String convertDecimalToSnafu(final long decimal) {
        long currentNumber = decimal;
        String snafu = "";

        while (currentNumber != 0L) {
            int base = (int) (currentNumber % 5);
            switch (base) {
                case (0):
                    snafu = "0" + snafu;
                    break;
                case (1):
                    snafu = "1" + snafu;
                    currentNumber--;
                    break;
                case (2):
                    snafu = "2" + snafu;
                    currentNumber -= 2;
                    break;
                case (3):
                    snafu = "=" + snafu;
                    currentNumber += 2;
                    break;
                case (4):
                    snafu = "-" + snafu;
                    currentNumber++;
                    break;
            }

            currentNumber = currentNumber / 5;
        }
        return snafu;
    }
}
