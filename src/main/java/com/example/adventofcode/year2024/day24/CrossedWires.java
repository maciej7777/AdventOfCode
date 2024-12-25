package com.example.adventofcode.year2024.day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CrossedWires {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day24/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day24/example_input";
    private static final String EXAMPLE_FILENAME2 = "src/main/java/com/example/adventofcode/year2024/day24/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateZ(EXAMPLE_FILENAME));
        System.out.println(calculateZ(EXAMPLE_FILENAME2));
        System.out.println(calculateZ(FILENAME));
    }

    record Gate(String left, String right, String operator, String result) {
    }

    private record Input(Map<String, Boolean> inputValues, List<Gate> gates, Set<String> zGates) {
    }

    public static long calculateZ(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);

        calculateResult(input.gates(), input.inputValues());
        return calculateBinaryNumber(input.zGates(), input.inputValues());
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

    private static Input parseInput(List<String> lines) {
        Map<String, Boolean> inputValues = new HashMap<>();
        List<Gate> gates = new ArrayList<>();
        Set<String> zGates = new HashSet<>();
        boolean readingVariables = true;
        for (String line: lines) {
            if (line.isEmpty()) {
                readingVariables = false;
            } else if (readingVariables) {
                String[] splitted = line.split(": ");
                inputValues.put(splitted[0], splitted[1].equals("1"));
            } else {
                String[] firstSplit = line.split(" -> ");
                String result = firstSplit[1];

                String[] secondSplit = firstSplit[0].split(" ");
                gates.add(new Gate(secondSplit[0], secondSplit[2], secondSplit[1], result));

                if (secondSplit[0].startsWith("z")) {
                    zGates.add(secondSplit[0]);
                }
                if (secondSplit[2].startsWith("z")) {
                    zGates.add(secondSplit[2]);
                }
                if (result.startsWith("z")) {
                    zGates.add(result);
                }
            }
        }
        return new Input(inputValues, gates, zGates);
    }

    private static void calculateResult(List<Gate> gates, Map<String, Boolean> inputValues) {
        while (!gates.isEmpty()) {
            List<Gate> newIterationGates = new ArrayList<>();
            for (Gate gate: gates) {
                if (!inputValues.containsKey(gate.left) || !inputValues.containsKey(gate.right)) {
                    newIterationGates.add(gate);
                } else {
                    boolean result = switch (gate.operator) {
                        case "OR" -> inputValues.get(gate.left) || inputValues.get(gate.right);
                        case "AND" -> inputValues.get(gate.left) && inputValues.get(gate.right);
                        case "XOR" -> inputValues.get(gate.left) ^ inputValues.get(gate.right);
                        default -> throw new InputMismatchException();
                    };
                    inputValues.put(gate.result, result);
                }
            }
            gates = newIterationGates;
        }
    }

    private static long calculateBinaryNumber(Set<String> zGates, Map<String, Boolean> inputValues) {
        long result = 0;
        long divider = 1;
        List<String> zElements = zGates.stream().toList().stream().sorted().toList();
        for (String el: zElements) {
            int myInt = Boolean.TRUE.equals(inputValues.get(el)) ? 1 : 0;

            result += myInt  * divider;
            divider *= 2;
        }
        return result;
    }
}
