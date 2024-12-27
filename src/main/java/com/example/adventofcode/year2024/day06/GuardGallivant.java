package com.example.adventofcode.year2024.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GuardGallivant {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day06/input";
    private static final String FILENAME2 = "src/main/java/com/example/adventofcode/year2024/day06/input2";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day06/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countGuardsPositions(EXAMPLE_FILENAME));
        System.out.println(countGuardsPositions(FILENAME));
        System.out.println(countLoopPositions(EXAMPLE_FILENAME));
        System.out.println(countLoopPositions(FILENAME));
        System.out.println(countLoopPositions(FILENAME2));
    }

    private record Input(Point startPosition, Set<Point> obstacles, int maxX, int maxY) {
    }

    record Point(int x, int y) {
    }

    record PointDirection(Point point, int direction) {
    }

    static List<Point> directions = List.of(new Point(0, -1),
            new Point(1, 0),
            new Point(0, 1),
            new Point(-1, 0));

    public static long countGuardsPositions(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);
        Set<Point> positions = calculateGuardPath(input);

        return positions.size();
    }

    public static long countLoopPositions(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);
        Set<Point> positions = calculateGuardPath(input);

        return countLoopPositions(positions, input);
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
        Point startPosition = new Point(0, 0);
        Set<Point> obstacles = new HashSet<>();
        int j = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '^') {
                    startPosition = new Point(i, j);
                } else if (line.charAt(i) == '#') {
                    obstacles.add(new Point(i, j));
                }
            }
            j++;
        }
        return new Input(startPosition, obstacles, lines.getFirst().length() - 1, lines.size() - 1);
    }

    private static Set<Point> calculateGuardPath(final Input input) {
        Set<Point> positions = new HashSet<>();
        int direction = 0;
        Point currentPoint = input.startPosition;
        while (isInRange(currentPoint, input.maxX, input.maxY)) {
            positions.add(currentPoint);

            Point newPoint = new Point(currentPoint.x + directions.get(direction).x, currentPoint.y + directions.get(direction).y);
            if (input.obstacles.contains(newPoint)) {
                direction = (direction + 1) % 4;
                newPoint = currentPoint;
            }
            currentPoint = newPoint;
        }
        return positions;
    }

    private static boolean isInRange(Point position, int maxX, int maxY) {
        return position.x >= 0 && position.x <= maxX && position.y >= 0 && position.y <= maxY;
    }

    private static int countLoopPositions(Set<Point> positions, Input input) {
        int cyclesCount = 0;
        for (Point position : positions) {
            if (!position.equals(input.startPosition) && (isGuardPathACycle(input, position))) {
                cyclesCount++;
            }
        }

        return cyclesCount;
    }

    private static boolean isGuardPathACycle(Input input, Point newObstacle) {
        Set<PointDirection> pointDirections = new HashSet<>();
        int direction = 0;
        Point currentPoint = input.startPosition;

        while (isInRange(currentPoint, input.maxX, input.maxY)) {
            Point newPoint = new Point(
                    currentPoint.x + directions.get(direction).x,
                    currentPoint.y + directions.get(direction).y
            );
            if (input.obstacles.contains(newPoint) || newObstacle.equals(newPoint)) {
                direction = (direction + 1) % 4;
                PointDirection newPointDirection = new PointDirection(newPoint, direction);
                if (pointDirections.contains(newPointDirection)) {
                    return true;
                }
                pointDirections.add(newPointDirection);

                newPoint = new Point(currentPoint.x, currentPoint.y);
            }
            currentPoint = newPoint;
        }
        return false;
    }
}
