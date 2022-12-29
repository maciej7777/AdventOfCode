package com.example.adventofcode.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ProboscideaVolcanium {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day16/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day16/example_input";

    public static void main(String[] args) throws IOException {
        Map<String, Valve> exampleInput = readInput(FILENAME);
        System.out.println(calculatePositionsBetterWithElephants(exampleInput));
    }

    public static int obtainMaxPressureReduction(String filename) throws IOException {
        Map<String, Valve> exampleInput = readInput(filename);
        return calculatePositionsBetter(exampleInput);
    }

    public static int obtainMaxPressureReductionWithElephant(String filename) throws IOException {
        Map<String, Valve> exampleInput = readInput(filename);
        return calculatePositionsBetterWithElephants(exampleInput);
    }

    public record Valve(String name, int flowRate, List<String> connectedValves) {
    }

    public static Map<String, Valve> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Map<String, Valve> map = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.replace("Valve ", "");
                line = line.replace("has flow rate=", "");
                line = line.replace("; tunnels lead to valves", "");
                line = line.replace("; tunnel leads to valve", "");
                line = line.replace(",", "");

                String[] elements = line.split(" ");

                List<String> connectedValveNames = new ArrayList<>(Arrays.asList(elements).subList(2, elements.length));

                map.put(elements[0], new Valve(elements[0], Integer.parseInt(elements[1]), connectedValveNames));
            }
            return map;
        }
    }

    public static Map<Valve, Map<Valve, Integer>> reduceInput(Map<String, Valve> moves) {

        Map<Valve, Map<Valve, Integer>> nonZeroValveDistances = new HashMap<>();
        Set<Valve> nonZeroValves = new HashSet<>();
        for (Map.Entry<String, Valve> entry : moves.entrySet()) {
            Valve current = entry.getValue();
            if (current.flowRate > 0) {
                nonZeroValves.add(current);
            }
        }

        for (Map.Entry<String, Valve> entry : moves.entrySet()) {
            Valve from = entry.getValue();
            for (Valve to : nonZeroValves) {
                if (from != to) {
                    Map<Valve, Integer> distancesFrom = nonZeroValveDistances.getOrDefault(from, new HashMap<>());
                    distancesFrom.put(to, findDistanceBetweenNodes(from, to, moves));
                    nonZeroValveDistances.put(from, distancesFrom);
                }
            }
        }
        return nonZeroValveDistances;
    }

    record ValveToVisit(Valve valve, int distance) {
    }

    private static int findDistanceBetweenNodes(Valve from, Valve to, Map<String, Valve> moves) {
        Set<Valve> visited = new HashSet<>();
        Deque<ValveToVisit> queue = new ArrayDeque<>();
        visited.add(from);
        queue.addLast(new ValveToVisit(from, 0));

        while (!queue.isEmpty()) {
            ValveToVisit current = queue.pollFirst();

            if (current.valve == to) {
                return current.distance;
            }

            for (String nextValveName : current.valve.connectedValves) {
                Valve toVisit = moves.get(nextValveName);
                if (!visited.contains(toVisit)) {
                    queue.addLast(new ValveToVisit(toVisit, current.distance + 1));
                    visited.add(toVisit);
                }
            }
        }
        return 50;
    }

    public static int calculatePositionsBetter(Map<String, Valve> moves) {

        Map<Valve, Map<Valve, Integer>> newInput = reduceInput(moves);
        Map<Valve, Long> binaryPositions = generateBinaryMapFor(newInput.keySet().stream().filter(valve -> valve.flowRate > 0).collect(Collectors.toSet()));

        Valve aa = moves.get("AA");

        return calculateMaxPressureReduction(newInput, binaryPositions, moves, aa, 30, 0, 0).values().stream().max(Integer::compareTo).orElse(0);
    }

    public static int calculatePositionsBetterWithElephants(Map<String, Valve> moves) {

        Map<Valve, Map<Valve, Integer>> newInput = reduceInput(moves);
        Map<Valve, Long> binaryPositions = generateBinaryMapFor(newInput.keySet().stream().filter(valve -> valve.flowRate > 0).collect(Collectors.toSet()));

        Valve aa = moves.get("AA");

        Map<Long, Integer> allResults = calculateMaxPressureReduction(newInput, binaryPositions, moves, aa, 26, 0, 0);

        int maxResult = 0;
        for (Map.Entry<Long, Integer> result: allResults.entrySet()) {
            for (Map.Entry<Long, Integer> otherResult: allResults.entrySet()) {
                int tmpResult = result.getValue() + otherResult.getValue();
                if ((result.getKey() & otherResult.getKey()) == 0 && tmpResult > maxResult) {
                    maxResult = tmpResult;
                }
            }
        }
        return maxResult;
    }

    private static Map<Valve, Long> generateBinaryMapFor(final Set<Valve> valves) {
        Map<Valve, Long> binaryRepresentation = new HashMap<>();
        int i = 0;
        for (Valve valve: valves) {
            binaryRepresentation.put(valve, 1L<<i);
            i++;
        }
        return binaryRepresentation;
    }

    private static Map<Long, Integer> calculateMaxPressureReduction(Map<Valve, Map<Valve, Integer>> newInput, Map<Valve, Long> binaryPositions, Map<String, Valve> moves, Valve move, int timeLeft, long open, int currentReduction) {
        if (move.flowRate > 0 && timeLeft > 1 && ((binaryPositions.get(move)&open) == 0)) {
            timeLeft--;
            currentReduction += timeLeft * move.flowRate;
            if (!move.name.equals("AA")) open = (open | binaryPositions.get(move));
        }

        if (timeLeft == 0) {
            return Map.of(open, currentReduction);
        }

        Map<Long, Integer> costReductionMap = new HashMap<>();
        for (Map.Entry<Valve, Integer> nextNode : newInput.get(move).entrySet()) {
            if (timeLeft - nextNode.getValue() > 0) {
                Map<Long, Integer> returnedCostReductionMap = calculateMaxPressureReduction(newInput, binaryPositions, moves, nextNode.getKey(), timeLeft - nextNode.getValue(), open, currentReduction);
                for (Map.Entry<Long, Integer> reduction: returnedCostReductionMap.entrySet()) {
                    if (reduction.getValue() > costReductionMap.getOrDefault(reduction.getKey(), 0)) {
                        costReductionMap.put(reduction.getKey(), reduction.getValue());
                    }
                }
            }
        }
        if (currentReduction > costReductionMap.getOrDefault(open, 0)) {
            costReductionMap.put(open, currentReduction);
        }

        return costReductionMap;
    }
}
