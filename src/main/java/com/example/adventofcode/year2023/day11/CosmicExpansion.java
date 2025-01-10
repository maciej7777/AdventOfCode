package com.example.adventofcode.year2023.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CosmicExpansion {
    private static final String FILENAME = "AdventOfCodeData/2023/day11/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day11/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfDistances(EXAMPLE_FILENAME));
        System.out.println(calculateSumOfDistancesWithExpansion(EXAMPLE_FILENAME, 2L));

        System.out.println(calculateSumOfDistances(FILENAME));
        System.out.println(calculateSumOfDistancesWithExpansion(FILENAME, 2L));

        System.out.println(calculateSumOfDistancesWithExpansion(EXAMPLE_FILENAME, 10L));
        System.out.println(calculateSumOfDistancesWithExpansion(EXAMPLE_FILENAME, 100L));
        System.out.println(calculateSumOfDistancesWithExpansion(EXAMPLE_FILENAME, 1000000L));
        System.out.println(calculateSumOfDistancesWithExpansion(FILENAME, 1000000L));
    }

    record Point(int x, int y) {
    }

    public static long calculateSumOfDistances(String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<List<Character>> spaceMap = obtainSpaceMap(inputLines);
        List<Integer> rowsToDuplicate = obtainRowsToDuplicate(spaceMap);
        List<Integer> columnsToDuplicate = obtainColumnsToDuplicate(spaceMap);

        addRows(rowsToDuplicate, spaceMap);
        addColumns(columnsToDuplicate, spaceMap);
        List<Point> galaxies = locateGalaxies(spaceMap);

        return calculateDistancesBetweenGalaxies(galaxies);
    }

    public static long calculateSumOfDistancesWithExpansion(String filename, long expansion) throws IOException {
        List<String> inputLines = readLines(filename);
        List<List<Character>> spaceMap = obtainSpaceMap(inputLines);
        List<Integer> rowsToDuplicate = obtainRowsToDuplicate(spaceMap);
        List<Integer> columnsToDuplicate = obtainColumnsToDuplicate(spaceMap);

        List<Point> galaxies = locateGalaxies(spaceMap);

        return calculateDistancesBetweenGalaxiesWithExpansion(galaxies, rowsToDuplicate, columnsToDuplicate, expansion);
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

    private static List<List<Character>> obtainSpaceMap(List<String> inputLines) {
        List<List<Character>> spaceMap = new ArrayList<>();

        for (String line : inputLines) {
            char[] splitted = line.toCharArray();
            List<Character> spaceLine = new ArrayList<>();
            for (char c : splitted) {
                spaceLine.add(c);
            }
            spaceMap.add(spaceLine);
        }

        return spaceMap;
    }

    private static List<Integer> obtainRowsToDuplicate(List<List<Character>> spaceMap) {
        List<Integer> rowsToDuplicate = new ArrayList<>();
        for (int i = 0; i < spaceMap.size(); i++) {
            int countRows = 0;
            for (int j = 0; j < spaceMap.getFirst().size(); j++) {
                if (spaceMap.get(i).get(j) == '#') {
                    countRows++;
                }
            }
            if (countRows == 0) {
                rowsToDuplicate.add(i);
            }
        }
        return rowsToDuplicate;
    }

    private static List<Integer> obtainColumnsToDuplicate(List<List<Character>> spaceMap) {
        List<Integer> columnsToDuplicate = new ArrayList<>();
        for (int i = 0; i < spaceMap.getFirst().size(); i++) {
            int countColumns = 0;
            for (List<Character> characters : spaceMap) {
                if (characters.get(i) == '#') {
                    countColumns++;
                }
            }
            if (countColumns == 0) {
                columnsToDuplicate.add(i);
            }
        }
        return columnsToDuplicate;
    }

    private static void addRows(List<Integer> rowsToDuplicate, List<List<Character>> spaceMap) {
        int rowsAdded = 0;
        for (int row : rowsToDuplicate) {
            List<Character> duplicatedRow = new ArrayList<>();
            for (int i = 0; i < spaceMap.getFirst().size(); i++) {
                duplicatedRow.add('.');
            }
            spaceMap.add(row + rowsAdded, duplicatedRow);
            rowsAdded++;
        }
    }

    private static void addColumns(List<Integer> columnsToDuplicate, List<List<Character>> spaceMap) {
        int columnsAdded = 0;
        for (int column : columnsToDuplicate) {
            for (List<Character> characters : spaceMap) {
                characters.add(column + columnsAdded, '.');
            }
            columnsAdded++;
        }
    }

    private static List<Point> locateGalaxies(List<List<Character>> spaceMap) {
        List<Point> galaxies = new ArrayList<>();
        for (int i = 0; i < spaceMap.size(); i++) {
            for (int j = 0; j < spaceMap.getFirst().size(); j++) {
                if (spaceMap.get(i).get(j) == '#') {
                    galaxies.add(new Point(i, j));
                }
            }
        }
        return galaxies;
    }

    private static long calculateDistancesBetweenGalaxies(List<Point> galaxies) {
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                long distance = Math.abs(galaxies.get(i).x - galaxies.get(j).x) + Math.abs(galaxies.get(i).y - galaxies.get(j).y);
                sum += distance;
            }
        }
        return sum;
    }

    private static long calculateDistancesBetweenGalaxiesWithExpansion(List<Point> galaxies,
                                                                       List<Integer> rowsToDuplicate,
                                                                       List<Integer> columnsToDuplicate,
                                                                       long expansion) {
        expansion--;
        long sum = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                long distance = Math.abs(galaxies.get(i).x - galaxies.get(j).x) + Math.abs(galaxies.get(i).y - galaxies.get(j).y);
                for (Integer row : rowsToDuplicate) {
                    if ((row > galaxies.get(i).x && row < galaxies.get(j).x)
                            || (row > galaxies.get(j).x && row < galaxies.get(i).x)) {
                        distance += expansion;
                    }
                }
                for (Integer column : columnsToDuplicate) {
                    if ((column > galaxies.get(i).y && column < galaxies.get(j).y)
                            || (column > galaxies.get(j).y && column < galaxies.get(i).y)) {
                        distance += expansion;
                    }
                }
                sum+=distance;
            }
        }
        return sum;
    }
}
