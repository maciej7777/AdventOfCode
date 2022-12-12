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
        Set<Point> visited = new HashSet<>();

        for (Point sp : startPosition) {
            toVisit.add(new Visiting(sp, 0));
            visited.add(sp);
        }


        while (!toVisit.isEmpty()) {

            int steps = 0;
            if (steps == 100) {
                System.out.println("100");
            }
            Visiting current = toVisit.getLast();
            toVisit.removeLast();

            if (smallestDistance.getOrDefault(current.point, Integer.MAX_VALUE) > current.steps) {
                smallestDistance.put(current.point, current.steps);
            }

            if (current.point == endPosition) {
                return smallestDistance.get(endPosition);
            }

            if (current.point != endPosition) {
                if (current.point.x > 0
                        && elements.get(current.point.x).get(current.point.y) >
                        elements.get(current.point.x - 1).get(current.point.y) - 2
                        && smallestDistance.getOrDefault(new Point(current.point.x - 1, current.point.y), Integer.MAX_VALUE) > current.steps + 1
                ) {
                    toVisit.addLast(new Visiting(new Point(current.point.x - 1, current.point.y), current.steps + 1));
                }
                if (current.point.y > 0
                        && elements.get(current.point.x).get(current.point.y) >
                        elements.get(current.point.x).get(current.point.y - 1) - 2
                        && smallestDistance.getOrDefault(new Point(current.point.x, current.point.y - 1), Integer.MAX_VALUE) > current.steps + 1
                ) {
                    toVisit.addLast(new Visiting(new Point(current.point.x, current.point.y - 1), current.steps + 1));
                }
                if (current.point.x + 1 < elements.size()
                        && elements.get(current.point.x).get(current.point.y) >
                        elements.get(current.point.x + 1).get(current.point.y) - 2
                        && smallestDistance.getOrDefault(new Point(current.point.x + 1, current.point.y), Integer.MAX_VALUE) > current.steps + 1
                ) {
                    toVisit.addLast(new Visiting(new Point(current.point.x + 1, current.point.y), current.steps + 1));
                }

                Point evaluated = new Point(current.point.x, current.point.y + 1);
                if (current.point.y + 1 < elements.get(0).size() &&
                        elements.get(current.point.x).get(current.point.y) >
                                elements.get(evaluated.x).get(evaluated.y) - 2
                        && smallestDistance.getOrDefault(evaluated, Integer.MAX_VALUE) > current.steps + 1
                ) {
                    toVisit.addLast(new Visiting(evaluated, current.steps + 1));
                }
            }
        }

        return smallestDistance.get(endPosition);
    }
}
