package com.example.adventofcode.day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HillClimbingAlgorithm {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day12/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day12/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(obtainLengthOfShortestPathFromStart(FILENAME));
    }

    public static int obtainLengthOfShortestPathFromStart(String fileName) throws IOException {
        List<List<Character>> input = readInput(fileName);
        return calculateLengthOfShortestPath(input, false);
    }

    public static int obtainLengthOfShortestPathFromBottom(String fileName) throws IOException {
        List<List<Character>> input = readInput(fileName);
        return calculateLengthOfShortestPath(input, true);
    }

    private record Point(int x, int y) {
    }

    public static List<List<Character>> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            List<List<Character>> allElements = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                char[] elements = line.toCharArray();
                List<Character> tmpElements = new ArrayList<>();
                for (char el : elements) {
                    tmpElements.add(el);
                }
                allElements.add(tmpElements);
            }
            return allElements;
        }
    }

    record Visiting(Point point, int steps) {
    }

    private static int calculateLengthOfShortestPath(List<List<Character>> elements, boolean includeAllStartElements) {
        Point startPosition = null;
        Point endPosition = null;
        List<Point> startingPositions = new ArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < elements.get(i).size(); j++) {
                if (elements.get(i).get(j).equals('S')) {
                    startPosition = new Point(i, j);
                    startingPositions.add(new Point(i, j));
                } else if (elements.get(i).get(j).equals('E')) {
                    endPosition = new Point(i, j);
                } else if (elements.get(i).get(j).equals('a')) {
                    startingPositions.add(new Point(i, j));
                }
            }
        }
        elements.get(startPosition.x).set(startPosition.y, 'a');
        elements.get(endPosition.x).set(endPosition.y, 'z');

        if (includeAllStartElements) {
            return bfs(elements, startingPositions, endPosition);
        }
        return bfs(elements, List.of(startPosition), endPosition);
    }

    public static int bfs(List<List<Character>> elements, List<Point> startPosition, Point endPosition) {
        Map<Point, Integer> smallestDistance = new HashMap<>();
        Deque<Visiting> toVisit = new ArrayDeque<>();

        for (Point sp : startPosition) {
            toVisit.add(new Visiting(sp, 0));
        }


        while (!toVisit.isEmpty()) {
            Visiting current = toVisit.getLast();
            toVisit.removeLast();

            if (smallestDistance.getOrDefault(current.point, Integer.MAX_VALUE) > current.steps) {
                smallestDistance.put(current.point, current.steps);
            }

            Point[] pointsToEvaluate = {
                    new Point(current.point.x - 1, current.point.y),
                    new Point(current.point.x, current.point.y - 1),
                    new Point(current.point.x + 1, current.point.y),
                    new Point(current.point.x, current.point.y + 1)
            };

            for (Point pointToEvaluate : pointsToEvaluate) {
                if (pointToEvaluate.x >= 0 && pointToEvaluate.x < elements.size()
                        && pointToEvaluate.y >= 0 && pointToEvaluate.y < elements.get(0).size()
                        && elements.get(current.point.x).get(current.point.y) + 1 >= elements.get(pointToEvaluate.x).get(pointToEvaluate.y)
                        && smallestDistance.getOrDefault(pointToEvaluate, Integer.MAX_VALUE) > current.steps + 1) {
                    toVisit.addLast(new Visiting(pointToEvaluate, current.steps + 1));
                }
            }
        }

        return smallestDistance.get(endPosition);
    }
}
