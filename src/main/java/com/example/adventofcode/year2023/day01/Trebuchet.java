package com.example.adventofcode.year2023.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trebuchet {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2023/day01/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2023/day01/example_input";

    private static final String EXAMPLE_FILENAME2 = "src/main/java/com/example/adventofcode/year2023/day01/example_input2";

    private static final Map<String, String> digitNames = Map.of(
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9"
    );

    public static void main(String[] args) throws IOException {
        System.out.println(calculateCalibrationNumbersSum(EXAMPLE_FILENAME));
        System.out.println(calculateCalibrationNumbersSum(FILENAME));

        System.out.println(sumCalibrationNumbersWithText(EXAMPLE_FILENAME2));
        System.out.println(sumCalibrationNumbersWithText(FILENAME));
    }

    public static int calculateCalibrationNumbersSum(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int sum = 0;
        for (String line : lines) {
            sum += obtainCalibrationNumber(obtainDigitsFromLine(line));
        }

        return sum;
    }

    public static int sumCalibrationNumbersWithText(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int sum = 0;
        for (String line : lines) {
            sum += obtainCalibrationNumber(obtainNumbersFromLine(line));
        }

        return sum;
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

    private static int obtainCalibrationNumber(String numbersFromTheLine) {
        if (numbersFromTheLine.length() == 2) {
            return Integer.parseInt(numbersFromTheLine);
        } else if (numbersFromTheLine.length() == 1) {
            return Integer.parseInt(numbersFromTheLine + numbersFromTheLine);
        } else {
            return Integer.parseInt(String.valueOf(numbersFromTheLine.charAt(0)) + numbersFromTheLine.charAt(numbersFromTheLine.length() - 1));
        }
    }

    private static String obtainDigitsFromLine(String line) {
        String currentNumber = "";
        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                currentNumber += line.charAt(i);
            }
        }
        return currentNumber;
    }

    private static String obtainNumbersFromLine(String line) {
        String currentNumber = "";

        for (int i = 0; i < line.length(); i++) {
            if (Character.isDigit(line.charAt(i))) {
                currentNumber += line.charAt(i);
            } else {
                for (Map.Entry<String, String> entry : digitNames.entrySet()) {
                    if (line.substring(0, i + 1).endsWith(entry.getKey())) {
                        currentNumber += entry.getValue();
                    }
                }
            }

        }
        return currentNumber;
    }
}
