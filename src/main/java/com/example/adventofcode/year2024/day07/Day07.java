package com.example.adventofcode.year2024.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO refactor this class
public class Day07 {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day07/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day07/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSolution1(EXAMPLE_FILENAME));
        System.out.println(calculateSolution1(FILENAME));
        System.out.println(calculateSolution2(EXAMPLE_FILENAME));
        System.out.println(calculateSolution2(FILENAME));
    }

    public static long calculateSolution1(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        long sum = 0;

        for (String line: lines) {
            String[] splitted = line.split(": ");
            long calibrationResult = Long.parseLong(splitted[0]);
            if (canGetCalibrationResult(calibrationResult, splitted[1])) {
                sum+=calibrationResult;
            }
        }


        return sum;
    }

    public static long calculateSolution2(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        long sum = 0;

        for (String line: lines) {
            String[] splitted = line.split(": ");
            long calibrationResult = Long.parseLong(splitted[0]);
            if (canGetCalibrationResult2(calibrationResult, splitted[1])) {
                sum+=calibrationResult;
            }
        }


        return sum;
    }

    private enum Operation {ADD, MULTIPLY};
    private enum Operation2 {ADD, MULTIPLY, CONCATENATION};

    private static boolean canGetCalibrationResult(long calibrationResult, String numbers) {
        String[] nb = numbers.split(" ");
        List<Long> tmpResults = new ArrayList<>();
        tmpResults.add(Long.parseLong(nb[0]));

        for (int position = 1; position < nb.length; position++) {
            List<Long> newResults = new ArrayList<>();
            for (Long result: tmpResults) {
                for (Operation o : Operation.values()) {
                    switch (o) {
                        case ADD -> newResults.add(result + Long.parseLong(nb[position]));
                        case MULTIPLY -> newResults.add(result * Long.parseLong(nb[position]));
                    }
                }
            }
            tmpResults = newResults;
        }

        return tmpResults.contains(calibrationResult);
    }

    private static boolean canGetCalibrationResult2(long calibrationResult, String numbers) {
        String[] nb = numbers.split(" ");
        List<Long> tmpResults = new ArrayList<>();
        tmpResults.add(Long.parseLong(nb[0]));

        for (int position = 1; position < nb.length; position++) {
            List<Long> newResults = new ArrayList<>();
            for (Long result: tmpResults) {
                for (Operation2 o : Operation2.values()) {
                    switch (o) {
                        case ADD -> newResults.add(result + Long.parseLong(nb[position]));
                        case MULTIPLY -> newResults.add(result * Long.parseLong(nb[position]));
                        case CONCATENATION -> newResults.add(Long.parseLong(result.toString() + nb[position]));
                    }
                }
            }
            tmpResults = newResults;
        }

        return tmpResults.contains(calibrationResult);
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
}
