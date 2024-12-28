package com.example.adventofcode.year2024.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BridgeRepair {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day07/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day07/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateCalibrationNumber(EXAMPLE_FILENAME));
        System.out.println(calculateCalibrationNumber(FILENAME));
        System.out.println(calculateCalibrationNumberWithConcatenation(EXAMPLE_FILENAME));
        System.out.println(calculateCalibrationNumberWithConcatenation(FILENAME));
    }

    private enum Operation {ADD, MULTIPLY}
    private enum OperationWithConcatenation {ADD, MULTIPLY, CONCATENATION}

    private record Input(long calibrationResult, List<Long> numbers) {
    }

    public static long calculateCalibrationNumber(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Input> inputs = parseInput(lines);
        long sum = 0;

        for (Input input: inputs) {
            if (canGetCalibrationResult(input)) {
                sum+=input.calibrationResult;
            }
        }

        return sum;
    }

    public static long calculateCalibrationNumberWithConcatenation(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Input> inputs = parseInput(lines);
        long sum = 0;

        for (Input input: inputs) {
            if (canGetCalibrationResultWithConcatenation(input)) {
                sum+=input.calibrationResult;
            }
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

    private static List<Input> parseInput(List<String> lines) {
        List<Input> inputs = new ArrayList<>();
        for (String line: lines) {
            String[] splitted = line.split(": ");
            long calibrationResult = Long.parseLong(splitted[0]);

            String[] numbersSplitted = splitted[1].split(" ");
            List<Long> numbers = Stream.of(numbersSplitted)
                    .map(Long::valueOf)
                    .toList();

            inputs.add(new Input(calibrationResult, numbers));
        }

        return inputs;
    }

    private static boolean canGetCalibrationResult(Input input) {
        List<Long> tmpResults = new ArrayList<>();
        tmpResults.add(input.numbers.getFirst());

        for (int position = 1; position < input.numbers.size(); position++) {
            List<Long> newResults = new ArrayList<>();
            for (Long result: tmpResults) {
                for (Operation operation : Operation.values()) {
                    if (operation.equals(Operation.ADD)) {
                        newResults.add(result + input.numbers.get(position));
                    } else {
                        newResults.add(result * input.numbers.get(position));
                    }
                }
            }
            tmpResults = newResults;
        }

        return tmpResults.contains(input.calibrationResult);
    }

    private static boolean canGetCalibrationResultWithConcatenation(Input input) {
        List<Long> tmpResults = new ArrayList<>();
        tmpResults.add(input.numbers.getFirst());

        for (int position = 1; position < input.numbers.size(); position++) {
            List<Long> newResults = new ArrayList<>();
            for (Long result: tmpResults) {
                for (OperationWithConcatenation o : OperationWithConcatenation.values()) {
                    switch (o) {
                        case ADD -> newResults.add(result + input.numbers.get(position));
                        case MULTIPLY -> newResults.add(result * input.numbers.get(position));
                        case CONCATENATION -> newResults.add(Long.parseLong(result.toString() + input.numbers.get(position)));
                    }
                }
            }
            tmpResults = newResults;
        }

        return tmpResults.contains(input.calibrationResult);
    }
}
