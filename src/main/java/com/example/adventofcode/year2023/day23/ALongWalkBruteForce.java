package com.example.adventofcode.year2023.day23;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class ALongWalkBruteForce {
    private static final String FILENAME = "AdventOfCodeData/2023/day23/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day23/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateLongestHike(EXAMPLE_FILENAME));
        System.out.println(calculateLongestHike(FILENAME));
        System.out.println(calculateLongestHikeWithoutSlopes(EXAMPLE_FILENAME));
    }

    record Point(int x, int y) {
    }

    record State(Point point, List<Point> history) {
    }

    private static final List<Point> DIRECTIONS = List.of(
            new Point(0, 1),
            new Point(0, -1),
            new Point(1, 0),
            new Point(-1, 0)
    );

    public static int calculateLongestHike(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = obtainMap(lines);

        Point start = new Point(1, 0);
        Point end = new Point(map.getLast().size() - 2, map.size() - 1);
        return calculateLongestDistanceBetween(start, map, end);
    }

    public static long calculateLongestHikeWithoutSlopes(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Character>> map = obtainMap(lines);

        Point start = new Point(1, 0);
        Point end = new Point(map.getLast().size() - 2, map.size() - 1);
        return calculateLongestDistanceWithoutSlopesBetween(start, map, end);
    }

    private static List<List<Character>> obtainMap(List<String> inputLines) {
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

    private static int calculateLongestDistanceBetween(Point start, List<List<Character>> map, Point end) {
        int maxPath = 0;
        Queue<State> queue = new ArrayDeque<>();
        List<Point> history = new ArrayList<>();
        history.add(start);
        queue.add(new State(start, history));

        while (!queue.isEmpty()) {
            State poll = queue.poll();

            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(poll.point.x + direction.x, poll.point.y + direction.y);
                if (isValid(newPoint, map) && !poll.history.contains(newPoint)) {
                    if (map.get(newPoint.y).get(newPoint.x).equals('#')
                            || isStepInWrongDirection(direction, poll, map)) {
                        continue;
                    }

                    if (newPoint.equals(end)) {
                        if (poll.history.size() + 1 > maxPath) {
                            maxPath = poll.history.size() + 1;
                        }
                    } else {
                        List<Point> newHistory = new ArrayList<>(poll.history);
                        newHistory.add(newPoint);
                        queue.add(new State(newPoint, newHistory));
                    }
                }
            }
        }

        return maxPath - 1;
    }

    private static boolean isValid(Point newPoint, List<List<Character>> map) {
        return newPoint.y >= 0 && newPoint.y < map.size()
                && newPoint.x >= 0 && newPoint.x < map.getFirst().size();
    }

    private static boolean isStepInWrongDirection(Point direction, State poll, List<List<Character>> map) {
        if (!poll.history.isEmpty() && (map.get(poll.history.getLast().y).get(poll.history.getLast().x).equals('>')
                && !direction.equals(new Point(1, 0)))) {
            return true;
        }
        if (!poll.history.isEmpty() && (map.get(poll.history.getLast().y).get(poll.history.getLast().x).equals('<')
                && !direction.equals(new Point(-1, 0)))) {
            return true;
        }
        if (!poll.history.isEmpty() && (map.get(poll.history.getLast().y).get(poll.history.getLast().x).equals('v')
                && !direction.equals(new Point(0, 1)))) {
            return true;
        }
        if (!poll.history.isEmpty() && (map.get(poll.history.getLast().y).get(poll.history.getLast().x).equals('^')
                && !direction.equals(new Point(0, -1)))) {
            return true;
        }

        return false;
    }

    private static int calculateLongestDistanceWithoutSlopesBetween(Point start, List<List<Character>> map, Point end) {
        int maxPath = 0;
        Queue<State> queue = new ArrayDeque<>();
        List<Point> history = new ArrayList<>();
        history.add(start);
        queue.add(new State(start, history));

        while (!queue.isEmpty()) {
            State poll = queue.poll();

            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(poll.point.x + direction.x, poll.point.y + direction.y);
                if (isValid(newPoint, map) && !poll.history.contains(newPoint)) {
                    if (map.get(newPoint.y).get(newPoint.x).equals('#')) {
                        continue;
                    }

                    if (newPoint.equals(end)) {
                        if (poll.history.size() + 1 > maxPath) {
                            maxPath = poll.history.size() + 1;
                        }
                    } else {
                        List<Point> newHistory = new ArrayList<>(poll.history);
                        newHistory.add(newPoint);
                        queue.add(new State(newPoint, newHistory));
                    }
                }
            }
        }

        return maxPath - 1;
    }
}