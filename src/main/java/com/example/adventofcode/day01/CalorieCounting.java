package com.example.adventofcode.day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalorieCounting {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day01/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day01/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countMaxCalories(FILENAME));
        System.out.println(count3MaxCalories(FILENAME));
    }

    public static int countMaxCalories(final String filename) throws IOException {
        List<Integer> sums = readSums(filename);

        return sums.stream().max(Integer::compareTo).orElse(0);
    }

    public static int count3MaxCalories(final String filename) throws IOException {
        List<Integer> sums = readSums(filename);

        return sums.stream()
                .sorted((f1, f2) -> f2 - f1)
                .limit(3)
                .reduce(0, Integer::sum);
    }

    private static List<Integer> readSums(final String filename) throws IOException {
        List<Integer> sums;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            sums = new ArrayList<>();
            int currentSum = 0;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    sums.add(currentSum);
                    currentSum = 0;
                } else {
                    currentSum += Integer.parseInt(line);
                }
            }
            sums.add(currentSum);
        }

        return sums;
    }
}
