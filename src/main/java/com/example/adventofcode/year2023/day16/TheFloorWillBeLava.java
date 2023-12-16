package com.example.adventofcode.year2023.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TheFloorWillBeLava {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2023/day16/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2023/day16/example_input";

    public static void main(String[] args) throws IOException {

        System.out.println(countEnergizedPoints(EXAMPLE_FILENAME));
        //46
        System.out.println(countEnergizedPoints(FILENAME));
        //7927

        System.out.println(countMaximumEnergizedPoints(EXAMPLE_FILENAME));
        //51
        System.out.println(countMaximumEnergizedPoints(FILENAME));
        //8246

    }

    enum Direction {
        NORTH, WEST, SOUTH, EAST
    }

    record Point(int x, int y) {
    }

    record Step(Point position, Direction direction) {
    }

    public static long countEnergizedPoints(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> caveMap = buildCaveMap(lines);

        return calculateEnergized(caveMap, new Point(0, 0), Direction.EAST).size();
    }

    public static long countMaximumEnergizedPoints(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> caveMap = buildCaveMap(lines);

        return findMaxEnergized(caveMap);
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

    private static List<List<Character>> buildCaveMap(List<String> lines) {
        List<List<Character>> caveMap = new ArrayList<>();
        for (String line : lines) {
            List<Character> tmpList = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                tmpList.add(line.charAt(j));
            }
            caveMap.add(tmpList);
        }
        return caveMap;
    }

    private static int findMaxEnergized(List<List<Character>> caveMap) {
        int maxEnergized = 0;
        for (int i = 0; i < caveMap.getFirst().size(); i++) {
            Set<Point> energized = calculateEnergized(caveMap, new Point(0, i), Direction.EAST);
            if (energized.size() > maxEnergized) {
                maxEnergized = energized.size();
            }
            Set<Point> energized2 = calculateEnergized(caveMap, new Point(caveMap.getFirst().size() - 1, i), Direction.WEST);
            if (energized2.size() > maxEnergized) {
                maxEnergized = energized2.size();
            }
        }

        for (int j = 0; j < caveMap.size(); j++) {
            Set<Point> energized = calculateEnergized(caveMap, new Point(j, 0), Direction.SOUTH);
            if (energized.size() > maxEnergized) {
                maxEnergized = energized.size();
            }
            Set<Point> energized2 = calculateEnergized(caveMap, new Point(j, caveMap.size() - 1), Direction.NORTH);
            if (energized2.size() > maxEnergized) {
                maxEnergized = energized2.size();
            }
        }
        return maxEnergized;
    }

    private static Set<Point> calculateEnergized(List<List<Character>> caveMap, Point startPoint, Direction direction) {
        Set<Point> energized = new HashSet<>();
        Set<Step> stepsDone = new HashSet<>();

        Queue<Step> steps = new ArrayDeque<>();
        steps.add(new Step(startPoint, direction));

        while (!steps.isEmpty()) {
            Step current = steps.poll();

            if (current.position.x < 0 || current.position.x >= caveMap.getFirst().size()
                    || current.position.y < 0 || current.position.y >= caveMap.size()) {
                continue;
            }
            if (stepsDone.contains(current)) {
                continue;
            }
            stepsDone.add(current);
            energized.add(current.position);

            char currentFloor = caveMap.get(current.position.y).get(current.position.x);
            switch (current.direction) {
                case NORTH -> {
                    if (currentFloor == '.' || currentFloor == '|') {
                        steps.add(new Step(new Point(current.position.x, current.position.y - 1), Direction.NORTH));
                    } else if (currentFloor == '-') {
                        steps.add(new Step(new Point(current.position.x + 1, current.position.y), Direction.EAST));
                        steps.add(new Step(new Point(current.position.x - 1, current.position.y), Direction.WEST));
                    } else if (currentFloor == '/') {
                        steps.add(new Step(new Point(current.position.x + 1, current.position.y), Direction.EAST));
                    } else /*'\\'*/ {
                        steps.add(new Step(new Point(current.position.x - 1, current.position.y), Direction.WEST));
                    }
                }
                case SOUTH -> {
                    if (currentFloor == '.' || currentFloor == '|') {
                        steps.add(new Step(new Point(current.position.x, current.position.y + 1), Direction.SOUTH));
                    } else if (currentFloor == '-') {
                        steps.add(new Step(new Point(current.position.x + 1, current.position.y), Direction.EAST));
                        steps.add(new Step(new Point(current.position.x - 1, current.position.y), Direction.WEST));
                    } else if (currentFloor == '/') {
                        steps.add(new Step(new Point(current.position.x - 1, current.position.y), Direction.WEST));
                    } else /*'\\'*/ {
                        steps.add(new Step(new Point(current.position.x + 1, current.position.y), Direction.EAST));
                    }
                }
                case WEST -> {
                    if (currentFloor == '.' || currentFloor == '-') {
                        steps.add(new Step(new Point(current.position.x - 1, current.position.y), Direction.WEST));
                    } else if (currentFloor == '|') {
                        steps.add(new Step(new Point(current.position.x, current.position.y - 1), Direction.NORTH));
                        steps.add(new Step(new Point(current.position.x, current.position.y + 1), Direction.SOUTH));
                    } else if (currentFloor == '/') {
                        steps.add(new Step(new Point(current.position.x, current.position.y + 1), Direction.SOUTH));
                    } else /*'\\'*/ {
                        steps.add(new Step(new Point(current.position.x, current.position.y - 1), Direction.NORTH));
                    }
                }
                case EAST -> {
                    if (currentFloor == '.' || currentFloor == '-') {
                        steps.add(new Step(new Point(current.position.x + 1, current.position.y), Direction.EAST));
                    } else if (currentFloor == '|') {
                        steps.add(new Step(new Point(current.position.x, current.position.y - 1), Direction.NORTH));
                        steps.add(new Step(new Point(current.position.x, current.position.y + 1), Direction.SOUTH));
                    } else if (currentFloor == '/') {
                        steps.add(new Step(new Point(current.position.x, current.position.y - 1), Direction.NORTH));

                    } else /*'\\'*/ {
                        steps.add(new Step(new Point(current.position.x, current.position.y + 1), Direction.SOUTH));

                    }
                }
            }
        }
        return energized;
    }
}
