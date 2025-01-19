package com.example.adventofcode.year2024.day24;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class CrossedWires {
    private static final String FILENAME = "AdventOfCodeData/2024/day24/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day24/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2024/day24/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateZ(EXAMPLE_FILENAME));
        System.out.println(calculateZ(EXAMPLE_FILENAME2));
        System.out.println(calculateZ(FILENAME));
        System.out.println(findSwitchedWires(FILENAME));
    }

    record Gate(String left, String right, String operator, String result) {
    }

    private record Input(Map<String, Boolean> inputValues, List<Gate> gates, Set<String> zGates, String highestZ) {
    }

    public static long calculateZ(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);

        Map<String, Boolean> wireValues = calculateWireValues(input.gates(), input.inputValues());
        return calculateBinaryNumber(input.zGates(), wireValues);
    }

    public static String findSwitchedWires(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);

        Set<String> switchedWires = findSwitchedWires(input);
        return joinAndSort(switchedWires);
    }

    private static Set<String> findSwitchedWires(Input input) {
        Set<String> switchedWires = new HashSet<>();
        Set<Character> xyz = Set.of('x', 'y', 'z');
        for (Gate gate: input.gates()) {
            if (gate.result.startsWith("z") && !gate.operator.equals("XOR") && !gate.result.equals(input.highestZ)) {
                switchedWires.add(gate.result);
            }
            if (gate.operator.equals("XOR")
                    && !xyz.contains(gate.result.charAt(0))
                    && !xyz.contains(gate.left.charAt(0))
                    && !xyz.contains(gate.right.charAt(0))) {
                switchedWires.add(gate.result);
            }
            if (gate.operator.equals("AND") && !isGateInput(gate, "x00")) {
                for (Gate nextGate: input.gates()) {
                    if (isGateInput(nextGate, gate.result) && !nextGate.operator.equals("OR")) {
                        switchedWires.add(gate.result);
                    }
                }
            }
            if (gate.operator.equals("XOR")) {
                for (Gate nextGate: input.gates()) {
                    if (isGateInput(nextGate, gate.result) && nextGate.operator.equals("OR")) {
                        switchedWires.add(gate.result);
                    }
                }
            }
        }
        return switchedWires;
    }

    private static boolean isGateInput(Gate gate, String input) {
        return input.equals(gate.left) || input.equals(gate.right);
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

        return new Input(inputValues, gates, zGates, findHighestZ(zGates));
    }

    private static String findHighestZ(Set<String> zGates) {
        int topZ = 0;
        String topZValue = "z00";
        for (String z: zGates) {
            int zNumber = Integer.parseInt(z.substring(1));
            if (zNumber > topZ) {
                topZ = zNumber;
                topZValue = z;
            }
        }
        return topZValue;
    }

    private static Map<String, Boolean> calculateWireValues(List<Gate> gates, Map<String, Boolean> inputValues) {
        Map<String, Boolean> wireValues = new HashMap<>(inputValues);
        while (!gates.isEmpty()) {
            List<Gate> newIterationGates = new ArrayList<>();
            for (Gate gate: gates) {
                if (!wireValues.containsKey(gate.left) || !wireValues.containsKey(gate.right)) {
                    newIterationGates.add(gate);
                } else {
                    boolean result = switch (gate.operator) {
                        case "OR" -> wireValues.get(gate.left) || wireValues.get(gate.right);
                        case "AND" -> wireValues.get(gate.left) && wireValues.get(gate.right);
                        case "XOR" -> wireValues.get(gate.left) ^ wireValues.get(gate.right);
                        default -> throw new InputMismatchException();
                    };
                    wireValues.put(gate.result, result);
                }
            }
            gates = newIterationGates;
        }

        return wireValues;
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

    private static String joinAndSort(Set<String> wires) {
        return wires.stream().sorted().collect(Collectors.joining(","));
    }
}
