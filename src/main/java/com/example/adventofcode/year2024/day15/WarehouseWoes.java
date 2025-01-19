package com.example.adventofcode.year2024.day15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class WarehouseWoes {
    private static final String FILENAME = "AdventOfCodeData/2024/day15/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day15/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2024/day15/example_input2";
    private static final String EXAMPLE_FILENAME3 = "AdventOfCodeData/2024/day15/example_input3";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateBoxesGPSCoordinates(EXAMPLE_FILENAME));
        System.out.println(calculateBoxesGPSCoordinates(EXAMPLE_FILENAME2));
        System.out.println(calculateBoxesGPSCoordinates(EXAMPLE_FILENAME3));
        System.out.println(calculateBoxesGPSCoordinates(FILENAME));
        System.out.println(calculateBoxesGPSCoordinatesForScaledWarehouse(EXAMPLE_FILENAME));
        System.out.println(calculateBoxesGPSCoordinatesForScaledWarehouse(EXAMPLE_FILENAME2));
        System.out.println(calculateBoxesGPSCoordinatesForScaledWarehouse(EXAMPLE_FILENAME3));
        System.out.println(calculateBoxesGPSCoordinatesForScaledWarehouse(FILENAME));
    }


    record Point(int x, int y) {
    }

    record Input(List<List<Character>> map, List<Point> movesDirections, Point robotPosition) {
    }

    public static long calculateBoxesGPSCoordinates(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);

        Point robotPosition = input.robotPosition;
        for (Point move : input.movesDirections) {
            Point proposedPosition = new Point(robotPosition.x + move.x, robotPosition.y + move.y);
            if (tryToMove(robotPosition, move, input.map)) {
                robotPosition = proposedPosition;
            }
        }

        return calculateGPSSum(input.map, 'O');
    }

    public static long calculateBoxesGPSCoordinatesForScaledWarehouse(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInputForExpandedWarehouse(lines);

        Point robotPosition = input.robotPosition;
        for (Point move : input.movesDirections) {
            Point proposedPosition = new Point(robotPosition.x + move.x, robotPosition.y + move.y);
            if (canMoveExtended(robotPosition, move, input.map)) {
                moveExtended(robotPosition, move, input.map);
                robotPosition = proposedPosition;
            }
        }

        return calculateGPSSum(input.map, '[');
    }

    private static Input parseInput(List<String> inputLines) {
        List<List<Character>> map = new ArrayList<>();
        List<Point> movesDirections = new ArrayList<>();
        Point robotPosition = new Point(0, 0);

        for (String line : inputLines) {
            if (line.contains("#")) {
                List<Character> elements = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '@') {
                        robotPosition = new Point(map.size(), i);
                    }
                    elements.add(line.charAt(i));
                }
                map.add(elements);
            } else if (line.contains(">") || line.contains("<") || line.contains("^") || line.contains("v")) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '>' -> movesDirections.add(new Point(0, 1));
                        case '<' -> movesDirections.add(new Point(0, -1));
                        case '^' -> movesDirections.add(new Point(-1, 0));
                        case 'v' -> movesDirections.add(new Point(1, 0));
                    }
                }
            }
        }

        return new Input(map, movesDirections, robotPosition);
    }

    private static Input parseInputForExpandedWarehouse(List<String> inputLines) {
        List<List<Character>> map = new ArrayList<>();
        List<Point> movesDirections = new ArrayList<>();
        Point robotPosition = new Point(0, 0);

        for (String line : inputLines) {
            if (line.contains("#")) {
                List<Character> elements = new ArrayList<>();
                for (int i = 0; i < line.length(); i++) {
                    switch (line.charAt(i)) {
                        case '@' -> {
                            robotPosition = new Point(map.size(), 2 * i);
                            elements.add('@');
                            elements.add('.');
                        }
                        case '.' -> {
                            elements.add('.');
                            elements.add('.');
                        }
                        case '#' -> {
                            elements.add('#');
                            elements.add('#');
                        }
                        case 'O' -> {
                            elements.add('[');
                            elements.add(']');
                        }
                    }
                }
                map.add(elements);
            } else if (line.contains(">") || line.contains("<") || line.contains("^") || line.contains("v")) {
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '>' -> movesDirections.add(new Point(0, 1));
                        case '<' -> movesDirections.add(new Point(0, -1));
                        case '^' -> movesDirections.add(new Point(-1, 0));
                        case 'v' -> movesDirections.add(new Point(1, 0));
                    }
                }
            }
        }

        return new Input(map, movesDirections, robotPosition);
    }

    private static boolean tryToMove(Point currentPosition, Point move, List<List<Character>> map) {
        Point proposedPosition = new Point(currentPosition.x + move.x, currentPosition.y + move.y);
        if (map.get(proposedPosition.x).get(proposedPosition.y) == '#') {
            return false;
        } else if (map.get(proposedPosition.x).get(proposedPosition.y) == '.') {
            map.get(proposedPosition.x).set(proposedPosition.y, map.get(currentPosition.x).get(currentPosition.y));
            map.get(currentPosition.x).set(currentPosition.y, '.');
            return true;
        } else /*if (map.get(proposedPosition.x).get(proposedPosition.y) == 'O')*/ {
            if (tryToMove(proposedPosition, move, map)) {
                map.get(proposedPosition.x).set(proposedPosition.y, map.get(currentPosition.x).get(currentPosition.y));
                map.get(currentPosition.x).set(currentPosition.y, '.');
                return true;
            }
            return false;
        }
    }

    private static boolean canMoveExtended(Point currentPosition, Point move, List<List<Character>> map) {
        Point proposedPosition = new Point(currentPosition.x + move.x, currentPosition.y + move.y);

        if (move.equals(new Point(0, 1)) || move.equals(new Point(0, -1))) {
            if (map.get(proposedPosition.x).get(proposedPosition.y) == '#') {
                return false;
            } else if (map.get(proposedPosition.x).get(proposedPosition.y) == '.') {
                return true;
            } else /*if (map.get(proposedPosition.x).get(proposedPosition.y) == '[]')*/ {
                return canMoveExtended(proposedPosition, move, map);
            }
        } else {
            if (map.get(proposedPosition.x).get(proposedPosition.y) == '#') {
                return false;
            } else if (map.get(proposedPosition.x).get(proposedPosition.y) == '.') {
                return true;
            } else if (map.get(proposedPosition.x).get(proposedPosition.y) == '[') {
                Point left = proposedPosition;
                Point right = new Point(proposedPosition.x, proposedPosition.y + 1);

                return canMoveExtended(left, move, map) && canMoveExtended(right, move, map);
            } else {
                Point right = proposedPosition;
                Point left = new Point(proposedPosition.x, proposedPosition.y - 1);

                return canMoveExtended(left, move, map) && canMoveExtended(right, move, map);
            }
        }
    }

    private static void moveExtended(Point currentPosition, Point move, List<List<Character>> map) {
        Point proposedPosition = new Point(currentPosition.x + move.x, currentPosition.y + move.y);

        if (move.equals(new Point(0, 1)) || move.equals(new Point(0, -1))) {
            if (map.get(proposedPosition.x).get(proposedPosition.y) != '.') {
                moveExtended(proposedPosition, move, map);
            }
            map.get(proposedPosition.x).set(proposedPosition.y, map.get(currentPosition.x).get(currentPosition.y));
            map.get(currentPosition.x).set(currentPosition.y, '.');
        } else {
            if (map.get(proposedPosition.x).get(proposedPosition.y) == '.') {
                map.get(proposedPosition.x).set(proposedPosition.y, map.get(currentPosition.x).get(currentPosition.y));
                map.get(currentPosition.x).set(currentPosition.y, '.');

            } else if (map.get(proposedPosition.x).get(proposedPosition.y) == '[') {
                Point left = proposedPosition;
                Point right = new Point(proposedPosition.x, proposedPosition.y + 1);
                moveExtended(left, move, map);
                moveExtended(right, move, map);

                map.get(proposedPosition.x).set(proposedPosition.y, map.get(currentPosition.x).get(currentPosition.y));
                map.get(currentPosition.x).set(currentPosition.y, '.');
            } else {
                Point right = proposedPosition;
                Point left = new Point(proposedPosition.x, proposedPosition.y - 1);

                moveExtended(left, move, map);
                moveExtended(right, move, map);
                map.get(proposedPosition.x).set(proposedPosition.y, map.get(currentPosition.x).get(currentPosition.y));
                map.get(currentPosition.x).set(currentPosition.y, '.');

            }
        }
    }

    private static int calculateGPSSum(List<List<Character>> map, char box) {
        int sum = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j) == box) {
                    sum += i * 100 + j;
                }
            }
        }
        return sum;
    }
}
