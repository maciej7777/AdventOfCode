package com.example.adventofcode.year2023.day21;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class StepCounterBruteForce {
    private static final String FILENAME = "AdventOfCodeData/2023/day21/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day21/example_input";

    private static final List<Point> DIRECTIONS = List.of(
            new Point(0, 1),
            new Point(0, -1),
            new Point(1, 0),
            new Point(-1, 0)
    );

    public static void main(String[] args) throws IOException {
         System.out.println(countFinalGardenPlots(EXAMPLE_FILENAME, 6));
         System.out.println(countFinalGardenPlots(FILENAME, 64));
    }


    record Point(int x, int y) {
    }

    record State(Point point, int step) {
    }

    public static long countFinalGardenPlots(final String filename, final int steps) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = obtainScenarios(lines);
        Point startPoint = findStartPoint(map);

        return countGardenPlotsAfterSteps(steps, startPoint, map);
    }

    public static long countFinalGardenPlotsInInfiniteMap(final String filename, final int steps) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = obtainScenarios(lines);
        Point start = findStartPoint(map);

        return countGardenPlotsAfterStepsInInfiniteMap(steps, start, map);
    }

    private static List<List<Character>> obtainScenarios(List<String> inputLines) {
        List<List<Character>> map = new ArrayList<>();
        for (String line : inputLines) {
            char[] splitted = line.toCharArray();
            List<Character> lavaLine = new ArrayList<>();
            for (char c : splitted) {
                lavaLine.add(c);
            }
            map.add(lavaLine);
        }
        return map;
    }

    private static Point findStartPoint(List<List<Character>> map) {
        Point start = new Point(0, 0);
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.getFirst().size(); i++) {
                if (map.get(j).get(i).equals('S')) {
                    start = new Point(i, j);
                }
            }
        }
        return start;
    }

    private static int countGardenPlotsAfterSteps(int steps, Point start, List<List<Character>> map) {
        int gardenPlots = 0;
        Queue<State> queue = new ArrayDeque<>();
        Set<State> visited = new HashSet<>();
        queue.add(new State(start, 0));

        while (!queue.isEmpty()) {
            State poll = queue.poll();

            if (poll.step == steps) {
                gardenPlots++;
                continue;
            }

            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(poll.point.x + direction.x, poll.point.y + direction.y);

                if (isValid(newPoint, map) && map.get(newPoint.y).get(newPoint.x) != '#') {
                    State newState = new State(newPoint, poll.step + 1);

                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        queue.add(newState);
                    }
                }
            }
        }
        return gardenPlots;
    }

    private static boolean isValid(Point newPoint, List<List<Character>> map) {
        return newPoint.y >= 0 && newPoint.y < map.size()
                && newPoint.x >= 0 && newPoint.x < map.getFirst().size();
    }

    private static int countGardenPlotsAfterStepsInInfiniteMap(int steps, Point start, List<List<Character>> map) {
        int gardenPlots = 0;
        Queue<State> queue = new ArrayDeque<>();
        Set<State> visited = new HashSet<>();
        queue.add(new State(start, 0));

        while (!queue.isEmpty()) {
            State poll = queue.poll();

            if (poll.step == steps) {
                gardenPlots++;
                continue;
            }

            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(poll.point.x + direction.x, poll.point.y + direction.y);

                int yCoordinateToCheck = (newPoint.y % map.size() + map.size()) % map.size();
                int xCoordinateToCheck = (newPoint.x % map.getFirst().size() + map.getFirst().size()) % map.getFirst().size();

                if (map.get(yCoordinateToCheck).get(xCoordinateToCheck) != '#') {
                    State newState = new State(newPoint, poll.step + 1);

                    if (!visited.contains(newState)) {
                        visited.add(newState);
                        queue.add(newState);
                    }
                }
            }
        }
        return gardenPlots;
    }
}
