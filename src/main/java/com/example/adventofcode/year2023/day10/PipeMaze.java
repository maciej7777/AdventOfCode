package com.example.adventofcode.year2023.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PipeMaze {
    private static final String FILENAME = "AdventOfCodeData/2023/day10/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day10/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2023/day10/example_input2";
    private static final String EXAMPLE_FILENAME3 = "AdventOfCodeData/2023/day10/example_input3";
    private static final String EXAMPLE_FILENAME4 = "AdventOfCodeData/2023/day10/example_input4";

    private static final List<Point> DIRECTIONS = List.of(
            new Point(-1, 0),
            new Point(1, 0),
            new Point(0, -1),
            new Point(0, 1)
    );

    public static void main(String[] args) throws IOException {
        System.out.println(calculateHalfOfLoopCircuit(EXAMPLE_FILENAME));
        //8
        System.out.println(calculateHalfOfLoopCircuit(FILENAME));
        //6838
        System.out.println(calculateLoopArea(EXAMPLE_FILENAME2));
        // 4
        System.out.println(calculateLoopArea(EXAMPLE_FILENAME3));
        // 8
        System.out.println(calculateLoopArea(EXAMPLE_FILENAME4));
        // 10
        System.out.println(calculateLoopArea(FILENAME));
        // 451
    }


    record Point(int x, int y) {
    }

    public static int calculateHalfOfLoopCircuit(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<List<Character>> map = buildMap(inputLines);
        Point start = findStartPosition(map);

        int cycleLength = 0;
        for (Point direction : DIRECTIONS) {
            Point newPoint = new Point(start.x + direction.x, start.y + direction.y);
            if (isPointValid(newPoint, map)) {
                int length = countCycleLength(start, newPoint, map);
                if (length > cycleLength) {
                    cycleLength = length;
                }
            }
        }
        return cycleLength / 2;
    }

    public static int calculateLoopArea(final String filename) throws IOException {
        List<String> inputLines = readLines(filename);
        List<List<Character>> map = buildMap(inputLines);
        Point start = findStartPosition(map);
        Set<Point> others = findOtherPositions(map);

        List<Point> cyclePoints = findCyclePoints(start, map);

        Set<Point> left = findInternalPoints(cyclePoints, true);
        cyclePoints.forEach(left::remove);
        Set<Point> right = findInternalPoints(cyclePoints, false);
        cyclePoints.forEach(right::remove);
        cyclePoints.forEach(others::remove);
        Set<Point> internalDots = revealInternal(cyclePoints, left, right, map);

        int result = 0;
        for (Point other : others) {
            if (!isDotExternal(cyclePoints, other, map, internalDots)) {
                result++;
            }
        }
        return result;
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

    private static List<List<Character>> buildMap(List<String> inputLines) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : inputLines) {
            List<Character> tmp = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                char currentElement = line.charAt(i);
                tmp.add(currentElement);
            }
            map.add(tmp);
        }
        return map;
    }

    private static Point findStartPosition(List<List<Character>> map) {
        Point start = null;
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.get(j).size(); i++) {
                char currentElement = map.get(j).get(i);
                if (currentElement == 'S') {
                    start = new Point(i, j);
                }
            }
        }
        return start;
    }

    private static Set<Point> findOtherPositions(List<List<Character>> map) {
        Set<Point> otherPositions = new HashSet<>();
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.get(j).size(); i++) {
                char currentElement = map.get(j).get(i);
                if (currentElement != 'S') {
                    otherPositions.add(new Point(i, j));
                }
            }
        }
        return otherPositions;
    }

    private static boolean isPointValid(Point validatedPoint, List<List<Character>> map) {
        return validatedPoint.x >= 0 && validatedPoint.x < map.getFirst().size()
                && validatedPoint.y >= 0 && validatedPoint.y < map.size();
    }

    private static int countCycleLength(Point startPosition, Point nextPosition, List<List<Character>> map) {
        int steps = 1;
        Point currentPosition = startPosition;
        while (nextPosition.x != startPosition.x || nextPosition.y != startPosition.y) {
            int deltaI = nextPosition.x - currentPosition.x;
            int deltaJ = nextPosition.y - currentPosition.y;
            currentPosition = nextPosition;

            Character next = map.get(nextPosition.y).get(nextPosition.x);

            if (next == '.') {
                return -1;
            }
            if (deltaI == -1) {
                if (!Set.of('-', 'F', 'L').contains(next)) {
                    return -1;
                }
                if (next == '-') {
                    nextPosition = new Point(currentPosition.x - 1, currentPosition.y);
                }
                if (next == 'F') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y + 1);
                }
                if (next == 'L') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y - 1);
                }
            }
            if (deltaI == 1) {
                if (!Set.of('-', '7', 'J').contains(next)) {
                    return -1;
                }
                if (next == '-') {
                    nextPosition = new Point(currentPosition.x + 1, currentPosition.y);
                }
                if (next == '7') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y + 1);
                }
                if (next == 'J') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y - 1);
                }
            }
            if (deltaJ == -1) {
                if (!Set.of('|', '7', 'F').contains(next)) {
                    return -1;
                }
                if (next == '|') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y - 1);
                }
                if (next == '7') {
                    nextPosition = new Point(currentPosition.x - 1, currentPosition.y);
                }
                if (next == 'F') {
                    nextPosition = new Point(currentPosition.x + 1, currentPosition.y);
                }
            }
            if (deltaJ == 1) {
                if (!Set.of('|', 'L', 'J').contains(next)) {
                    return -1;
                }
                if (next == '|') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y + 1);
                }
                if (next == 'L') {
                    nextPosition = new Point(currentPosition.x + 1, currentPosition.y);
                }
                if (next == 'J') {
                    nextPosition = new Point(currentPosition.x - 1, currentPosition.y);
                }
            }

            if (!isPointValid(nextPosition, map)) {
                return -1;
            }
            steps++;
        }
        return steps;
    }

    private static List<Point> findCyclePoints(Point start, List<List<Character>> map) {
        List<Point> points = new ArrayList<>();

        for (Point direction : DIRECTIONS) {
            Point newPoint = new Point(start.x + direction.x, start.y + direction.y);
            if (isPointValid(newPoint, map)) {
                List<Point> cyclePoints = findCycle(start, newPoint, map);

                if (cyclePoints != null) {
                    points = cyclePoints;
                    break;
                }
            }
        }
        return points;
    }

    private static Set<Point> revealInternal(List<Point> points, Set<Point> left, Set<Point> right, List<List<Character>> map) {
        for (Point l : left) {
            if (isDotExternal(points, l, map, Collections.emptySet())) {
                return right;
            }
        }
        return left;
    }

    private static void tryToAddToTheQueue(Point point, Set<Point> visited, List<Point> points, ArrayDeque<Point> toVisit) {
        if (!points.contains(point) && !visited.contains(point)) {
            toVisit.add(point);
            visited.add(point);
        }
    }

    private static boolean isDotExternal(List<Point> points, Point dot, List<List<Character>> map, Set<Point> internalPoints) {
        ArrayDeque<Point> toVisit = new ArrayDeque<>();
        Set<Point> visited = new HashSet<>();

        tryToAddToTheQueue(new Point(dot.x - 1, dot.y), visited, points, toVisit);
        tryToAddToTheQueue(new Point(dot.x + 1, dot.y), visited, points, toVisit);
        tryToAddToTheQueue(new Point(dot.x, dot.y - 1), visited, points, toVisit);
        tryToAddToTheQueue(new Point(dot.x, dot.y + 1), visited, points, toVisit);

        if (internalPoints.contains(dot)) {
            return false;
        }
        while (!toVisit.isEmpty()) {
            Point p = toVisit.poll();

            if (p.x == -1 || p.x == map.getFirst().size()
                    || p.y == -1 || p.y == map.size()) {
                return true;
            }
            if (internalPoints.contains(p)) {
                return false;
            }

            tryToAddToTheQueue(new Point(p.x - 1, p.y), visited, points, toVisit);
            tryToAddToTheQueue(new Point(p.x + 1, p.y), visited, points, toVisit);
            tryToAddToTheQueue(new Point(p.x, p.y - 1), visited, points, toVisit);
            tryToAddToTheQueue(new Point(p.x, p.y + 1), visited, points, toVisit);

        }
        return !internalPoints.isEmpty();
    }

    private static Set<Point> findInternalPoints(List<Point> points, boolean useLeft) {
        Set<Point> left = new HashSet<>();
        Set<Point> right = new HashSet<>();
        for (int i = 1; i <= points.size(); i++) {
            int newPointId = i % points.size();
            int deltaX = points.get(newPointId).x - points.get(i - 1).x;
            int deltaY = points.get(newPointId).y - points.get(i - 1).y;

            if (deltaX == 1) {
                //we go right
                left.add(new Point(points.get(newPointId).x, points.get(newPointId).y - 1));
                right.add(new Point(points.get(newPointId).x, points.get(newPointId).y + 1));
                left.add(new Point(points.get(i - 1).x, points.get(i - 1).y - 1));
                right.add(new Point(points.get(i - 1).x, points.get(i - 1).y + 1));

            } else if (deltaX == -1) {
                // we go left
                left.add(new Point(points.get(newPointId).x, points.get(newPointId).y + 1));
                right.add(new Point(points.get(newPointId).x, points.get(newPointId).y - 1));
                left.add(new Point(points.get(i - 1).x, points.get(i - 1).y + 1));
                right.add(new Point(points.get(i - 1).x, points.get(i - 1).y - 1));

            } else if (deltaY == 1) {
                // we go bottom
                left.add(new Point(points.get(newPointId).x + 1, points.get(newPointId).y));
                right.add(new Point(points.get(newPointId).x - 1, points.get(newPointId).y));
                left.add(new Point(points.get(i - 1).x + 1, points.get(i - 1).y));
                right.add(new Point(points.get(i - 1).x - 1, points.get(i - 1).y));

            } else {
                // we go top
                left.add(new Point(points.get(newPointId).x - 1, points.get(newPointId).y));
                right.add(new Point(points.get(newPointId).x + 1, points.get(newPointId).y));
                left.add(new Point(points.get(i - 1).x - 1, points.get(i - 1).y));
                right.add(new Point(points.get(i - 1).x + 1, points.get(i - 1).y));

            }
        }
        return useLeft ? left : right;
    }


    private static List<Point> findCycle(Point startPosition, Point nextPosition, List<List<Character>> map) {
        Point currentPosition = startPosition;
        List<Point> points = new ArrayList<>();
        points.add(startPosition);
        points.add(nextPosition);

        while (nextPosition.x != startPosition.x || nextPosition.y != startPosition.y) {
            int deltaI = nextPosition.x - currentPosition.x;
            int deltaJ = nextPosition.y - currentPosition.y;
            currentPosition = nextPosition;

            Character next = map.get(nextPosition.y).get(nextPosition.x);

            if (next == '.') {
                return null;
            }
            if (deltaI == -1) {
                if (!Set.of('-', 'F', 'L').contains(next)) {
                    return null;
                }
                if (next == '-') {
                    nextPosition = new Point(currentPosition.x - 1, currentPosition.y);
                }
                if (next == 'F') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y + 1);
                }
                if (next == 'L') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y - 1);
                }
            }
            if (deltaI == 1) {
                if (!Set.of('-', '7', 'J').contains(next)) {
                    return null;
                }
                if (next == '-') {
                    nextPosition = new Point(currentPosition.x + 1, currentPosition.y);
                }
                if (next == '7') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y + 1);
                }
                if (next == 'J') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y - 1);
                }
            }
            if (deltaJ == -1) {
                if (!Set.of('|', '7', 'F').contains(next)) {
                    return null;
                }
                if (next == '|') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y - 1);
                }
                if (next == '7') {
                    nextPosition = new Point(currentPosition.x - 1, currentPosition.y);
                }
                if (next == 'F') {
                    nextPosition = new Point(currentPosition.x + 1, currentPosition.y);
                }
            }
            if (deltaJ == 1) {
                if (!Set.of('|', 'L', 'J').contains(next)) {
                    return null;
                }
                if (next == '|') {
                    nextPosition = new Point(currentPosition.x, currentPosition.y + 1);
                }
                if (next == 'L') {
                    nextPosition = new Point(currentPosition.x + 1, currentPosition.y);
                }
                if (next == 'J') {
                    nextPosition = new Point(currentPosition.x - 1, currentPosition.y);
                }
            }

            if (!isPointValid(nextPosition, map)) {
                return null;
            }
            points.add(nextPosition);
        }
        return points;
    }
}