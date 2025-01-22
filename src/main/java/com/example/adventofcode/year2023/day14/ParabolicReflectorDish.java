package com.example.adventofcode.year2023.day14;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class ParabolicReflectorDish {
    private static final String FILENAME = "AdventOfCodeData/2023/day14/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day14/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateTotalLoad(EXAMPLE_FILENAME));
        System.out.println(calculateTotalLoadAdvanced(EXAMPLE_FILENAME));
        //136
        System.out.println(calculateTotalLoad(FILENAME));
        System.out.println(calculateTotalLoadAdvanced(FILENAME));
        //109098
        System.out.println(calculateTotalLoadWithSpinCycle(EXAMPLE_FILENAME));
        //64
        System.out.println(calculateTotalLoadWithSpinCycle(FILENAME));
        //100064
    }


    record Point(int x, int y) {
    }

    enum Direction {
        NORTH, WEST, SOUTH, EAST
    }

    public static Long calculateTotalLoad(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        List<List<Character>> map = readMap(lines);
        List<Point> points = readPoints(map);
        movePointsTop(points, map);

        return calculateTotalLoadSum(map);
    }

    public static Long calculateTotalLoadAdvanced(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = readMap(lines);
        moveRocks(map, Direction.NORTH);

        return calculateTotalLoadSum(map);
    }

    public static Long calculateTotalLoadWithSpinCycle(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = readMap(lines);
        performCycles(map);

        return calculateTotalLoadSum(map);
    }

    private static List<List<Character>> readMap(List<String> lines) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : lines) {
            List<Character> elements = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                elements.add(line.charAt(i));
            }
            map.add(elements);
        }
        return map;
    }

    private static List<Point> readPoints(List<List<Character>> map) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j) == 'O') {
                    points.add(new Point(i, j));
                }
            }
        }
        return points;
    }

    private static void movePointsTop(List<Point> points, List<List<Character>> map) {
        for (Point p : points) {
            int availablePosition = p.x;
            for (int i = p.x - 1; i >= 0; i--) {
                if (map.get(i).get(p.y) == '.') {
                    availablePosition = i;
                } else {
                    break;
                }
            }
            if (availablePosition != p.x) {
                map.get(p.x).set(p.y, '.');
                map.get(availablePosition).set(p.y, 'O');
            }
        }
    }

    private static long calculateTotalLoadSum(List<List<Character>> map) {
        long sum = 0;
        for (int x = 0; x < map.size(); x++) {
            for (int y = 0; y < map.get(0).size(); y++) {
                if (map.get(x).get(y) == 'O') {
                    sum += map.size() - x;
                }
            }
        }
        return sum;
    }

    private static void performCycles(List<List<Character>> map) {
        long cycleId = 0;
        long totalCycles = 1000000000;
        long cycleLength = 0;
        Map<String, Long> cycleMemory = new HashMap<>();
        while (cycleId < totalCycles) {
            String cycleRepresentation = calculateCycleRepresentation(map);

            if (cycleMemory.containsKey(cycleRepresentation)) {
                cycleLength = cycleId - cycleMemory.get(cycleRepresentation);
                break;
            }
            cycleMemory.put(cycleRepresentation, cycleId);

            moveRocks(map, Direction.NORTH);
            moveRocks(map, Direction.WEST);
            moveRocks(map, Direction.SOUTH);
            moveRocks(map, Direction.EAST);
            cycleId++;
        }
        long remainingCycles = (totalCycles - cycleId) % cycleLength;

        for (int i = 0; i < remainingCycles; i++) {
            moveRocks(map, Direction.NORTH);
            moveRocks(map, Direction.WEST);
            moveRocks(map, Direction.SOUTH);
            moveRocks(map, Direction.EAST);
        }
    }

    private static String calculateCycleRepresentation(List<List<Character>> map) {
        String cycleRepresentation = "";
        for (List<Character> row : map) {
            cycleRepresentation += row.toString();
        }
        return cycleRepresentation;
    }

    private static void moveRocks(List<List<Character>> map, Direction direction) {
        switch (direction) {
            case NORTH -> {
                for (int x = 0; x < map.getFirst().size(); x++) {
                    int firstIndexAvailable = 0;
                    for (int y = 0; y < map.size(); y++) {
                        if (map.get(y).get(x) == 'O') {
                            if (y != firstIndexAvailable) {
                                map.get(firstIndexAvailable).set(x, 'O');
                                map.get(y).set(x, '.');
                            }
                            firstIndexAvailable++;
                        } else if (map.get(y).get(x) == '#') {
                            firstIndexAvailable = y + 1;
                        }
                    }
                }
            }
            case WEST -> {
                for (int y = 0; y < map.size(); y++) {
                    int firstIndexAvailable = 0;
                    for (int x = 0; x < map.getFirst().size(); x++) {
                        if (map.get(y).get(x) == 'O') {
                            if (x != firstIndexAvailable) {
                                map.get(y).set(firstIndexAvailable, 'O');
                                map.get(y).set(x, '.');
                            }
                            firstIndexAvailable++;
                        } else if (map.get(y).get(x) == '#') {
                            firstIndexAvailable = x + 1;
                        }
                    }
                }
            }
            case SOUTH -> {
                for (int x = 0; x < map.getFirst().size(); x++) {
                    int firstIndexAvailable = map.size() - 1;
                    for (int y = map.size() - 1; y >= 0; y--) {
                        if (map.get(y).get(x) == 'O') {
                            if (y != firstIndexAvailable) {
                                map.get(firstIndexAvailable).set(x, 'O');
                                map.get(y).set(x, '.');
                            }
                            firstIndexAvailable--;
                        } else if (map.get(y).get(x) == '#') {
                            firstIndexAvailable = y - 1;
                        }
                    }
                }
            }
            case EAST -> {
                for (int y = 0; y < map.size(); y++) {
                    int firstIndexAvailable = map.getFirst().size() - 1;
                    for (int x = map.getFirst().size() - 1; x >= 0; x--) {
                        if (map.get(y).get(x) == 'O') {
                            if (x != firstIndexAvailable) {
                                map.get(y).set(firstIndexAvailable, 'O');
                                map.get(y).set(x, '.');
                            }
                            firstIndexAvailable--;
                        } else if (map.get(y).get(x) == '#') {
                            firstIndexAvailable = x - 1;
                        }
                    }
                }
            }
        }
    }
}
