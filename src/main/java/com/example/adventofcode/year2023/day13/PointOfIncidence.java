package com.example.adventofcode.year2023.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PointOfIncidence {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2023/day13/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2023/day13/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(summarizePatternNotes(EXAMPLE_FILENAME, 0));
        System.out.println(summarizePatternNotes(FILENAME, 0));
        System.out.println(summarizePatternNotes(EXAMPLE_FILENAME, 1));
        System.out.println(summarizePatternNotes(FILENAME, 1));
    }

    public static long summarizePatternNotes(String filename, int smudges) throws IOException {
        List<String> inputLines = readLines(filename);
        List<List<List<Character>>> scenarios = obtainScenarios(inputLines);
        return summarizeNotes(smudges, scenarios);
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

    private static List<List<List<Character>>> obtainScenarios(List<String> inputLines) {
        List<List<List<Character>>> scenarios = new ArrayList<>();
        List<List<Character>> map = new ArrayList<>();
        for (String line : inputLines) {
            if (line.isEmpty()) {
                scenarios.add(map);
                map = new ArrayList<>();
            } else {
                char[] splitted = line.toCharArray();
                List<Character> lavaLine = new ArrayList<>();
                for (char c : splitted) {
                    lavaLine.add(c);
                }
                map.add(lavaLine);
            }
        }
        scenarios.add(map);
        return scenarios;
    }

    private static long summarizeNotes(int smudges, List<List<List<Character>>> scenarios) {
        long sum = 0;
        for (List<List<Character>> lavaMap : scenarios) {
            for (int i = 0; i < lavaMap.size() - 1; i++) {
                if (checkReflectionRow(i, lavaMap, smudges)) {
                    sum += 100L * (i + 1);
                }
            }

            for (int i = 0; i < lavaMap.getFirst().size() - 1; i++) {
                if (checkReflectionColumn(i, lavaMap, smudges)) {
                    sum += i + 1;
                }
            }
        }
        return sum;
    }

    private static boolean checkReflectionRow(int topRow, List<List<Character>> map, int expectedSmudges) {
        int i = topRow;
        int j = topRow + 1;
        int smudges = 0;
        while (i >= 0 && j < map.size()) {
            for (int k = 0; k < map.get(i).size(); k++) {
                if (!map.get(i).get(k).equals(map.get(j).get(k))) {
                    if (smudges < expectedSmudges) {
                        smudges++;
                    } else {
                        return false;
                    }
                }
            }
            i--;
            j++;
        }

        return smudges == expectedSmudges;
    }

    private static boolean checkReflectionColumn(int leftColumn, List<List<Character>> map, int expectedMistakes) {
        int i = leftColumn;
        int j = leftColumn + 1;
        int mistakes = 0;

        while (i >= 0 && j < map.get(0).size()) {
            for (int k = 0; k < map.size(); k++) {
                if (!map.get(k).get(i).equals(map.get(k).get(j))) {
                    if (mistakes < expectedMistakes) {
                        mistakes++;
                    } else {
                        return false;
                    }
                }
            }
            i--;
            j++;
        }

        return mistakes == expectedMistakes;

    }
}
