package com.example.adventofcode.year2024.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//TODO refactor this class
public class Day06 {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day06/input";
    private static final String FILENAME2 = "src/main/java/com/example/adventofcode/year2024/day06/input2";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day06/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSolution1(EXAMPLE_FILENAME));
        System.out.println(calculateSolution1(FILENAME));
        System.out.println(calculateSolution2(EXAMPLE_FILENAME));
        System.out.println(calculateSolution2(FILENAME));
        System.out.println(calculateSolution2(FILENAME2));
    }


    record Point(int x, int y) {
    }

    static List<Point> directions = List.of(new Point(0, -1),
            new Point(1, 0),
            new Point(0, 1),
            new Point(-1, 0));

    public static long calculateSolution1(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int j = 0;
        Point startPosition = new Point(0, 0);
        Set<Point> obstacles = new HashSet<>();
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

        Set<Point> positions = calculateGuardPath(startPosition, lines, obstacles);

        return positions.size();
    }

    public static long calculateSolution2(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        int j = 0;
        Point startPosition = new Point(0, 0);
        Set<Point> obstacles = new HashSet<>();
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

        Set<Point> positions = calculateGuardPath(startPosition, lines, obstacles);

        int cyclesCount = 0;
        for (Point position : positions) {
            if (!position.equals(startPosition) && (isGuardPathACycle(startPosition, lines, obstacles, position))) {
                cyclesCount++;
            }
        }

        return cyclesCount;
    }

    private static Set<Point> calculateGuardPath(Point startPosition, List<String> lines, Set<Point> obstacles) {
        Set<Point> positions = new HashSet<>();
        int direction = 0;
        Point currentPoint = startPosition;
        while (isInRange(currentPoint, lines.getFirst().length() - 1, lines.size() - 1)) {
            positions.add(currentPoint);

            Point newPoint = new Point(currentPoint.x + directions.get(direction).x, currentPoint.y + directions.get(direction).y);
            if (obstacles.contains(newPoint)) {
                direction = (direction + 1) % 4;
                newPoint = new Point(currentPoint.x + directions.get(direction).x, currentPoint.y + directions.get(direction).y);
            }
            currentPoint = newPoint;
        }
        return positions;
    }


    record PointDirection(Point point, int direction) {

    }

    private static boolean isGuardPathACycle(Point startPosition, List<String> lines, Set<Point> obstacles, Point newObstacle) {
        Set<PointDirection> pointDirections = new HashSet<>();
        int direction = 0;
        Point currentPoint = startPosition;

        while (isInRange(currentPoint, lines.getFirst().length() - 1, lines.size() - 1)) {
            Point newPoint = new Point(currentPoint.x + directions.get(direction).x, currentPoint.y + directions.get(direction).y);
            if (obstacles.contains(newPoint) || newObstacle.equals(newPoint)) {
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

    private static boolean isInRange(Point position, int maxX, int maxY) {
        return position.x >= 0 && position.x <= maxX && position.y >= 0 && position.y <= maxY;

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
}
