package com.example.adventofcode.day01_2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trebuchet {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day01_2023/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day01_2023/example_input";

    private static final String EXAMPLE_FILENAME2 = "src/main/java/com/example/adventofcode/day01_2023/example_input2";

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
        System.out.println(sumCalibrationNumbers(EXAMPLE_FILENAME));
        System.out.println(sumCalibrationNumbers(FILENAME));

        System.out.println(sumCalibrationNumbersWithText(EXAMPLE_FILENAME2));
        System.out.println(sumCalibrationNumbersWithText(FILENAME));
    }

    public static int sumCalibrationNumbers(final String filename) throws IOException {
        List<Integer> sums = readCalibrationNumbers(filename);

        return sums.stream().mapToInt(i -> i).sum();
    }

    public static int sumCalibrationNumbersWithText(final String filename) throws IOException {
        List<Integer> sums = readCalibrationNumbersWithText(filename);

        return sums.stream().mapToInt(i -> i).sum();
    }


    private static List<Integer> readCalibrationNumbers(final String filename) throws IOException {
        List<Integer> sums;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            sums = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String currentNumber = "";

                for (int i = 0; i < line.length(); i++) {
                    if (Character.isDigit(line.charAt(i))) {
                        currentNumber += line.charAt(i);
                    }
                }
                if (currentNumber.length() == 2) {
                    sums.add(Integer.parseInt(currentNumber));
                } else if (currentNumber.length() == 1) {
                    sums.add(Integer.parseInt(currentNumber + currentNumber));
                } else {
                    sums.add(Integer.parseInt(String.valueOf(currentNumber.charAt(0)) + currentNumber.charAt(currentNumber.length() - 1)));
                }
            }

        }
        return sums;
    }

    private static List<Integer> readCalibrationNumbersWithText(final String filename) throws IOException {
        List<Integer> sums;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            sums = new ArrayList<>();
            while ((line = br.readLine()) != null) {
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
                if (currentNumber.length() == 2) {
                    sums.add(Integer.parseInt(currentNumber));
                } else if (currentNumber.length() == 1) {
                    sums.add(Integer.parseInt(currentNumber + currentNumber));
                } else {
                    sums.add(Integer.parseInt(String.valueOf(currentNumber.charAt(0)) + currentNumber.charAt(currentNumber.length() - 1)));
                }
            }

        }

        return sums;
    }
}
