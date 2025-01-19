package com.example.adventofcode.year2024.day10;

import java.io.IOException;
import java.util.*;

import static com.example.adventofcode.utils.FileUtils.readLines;

public class HoofIt {
    private static final String FILENAME = "AdventOfCodeData/2024/day10/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day10/example_input";
    private static final String EXAMPLE_FILENAME2 = "AdventOfCodeData/2024/day10/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSumOfTrailheadsScores(EXAMPLE_FILENAME));
        System.out.println(calculateSumOfTrailheadsScores(EXAMPLE_FILENAME2));
        System.out.println(calculateSumOfTrailheadsScores(FILENAME));
        System.out.println(calculateSumOfTrailheadsRatings(EXAMPLE_FILENAME));
        System.out.println(calculateSumOfTrailheadsRatings(EXAMPLE_FILENAME2));
        System.out.println(calculateSumOfTrailheadsRatings(FILENAME));
    }


    record Point(int x, int y) {
    }

    record Trail(Point point, Set<Point> visited) {

    }

    private static final List<Point> DIRECTIONS = List.of(
            new Point(-1, 0),
            new Point(1, 0),
            new Point(0, 1),
            new Point(0, -1)
    );

    public static long calculateSumOfTrailheadsScores(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Integer>> map = readMap(lines);

        int sum = 0;
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.get(j).size(); i++) {
                if (map.get(j).get(i) == 0) {
                    sum += calculateReachableNines(new Point(i, j), map);
                }
            }
        }


        return sum;
    }

    public static long calculateSumOfTrailheadsRatings(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Integer>> map = readMap(lines);

        int sum = 0;
        for (int j = 0; j < map.size(); j++) {
            for (int i = 0; i < map.get(j).size(); i++) {
                if (map.get(j).get(i) == 0) {
                    sum += calculateAvailableTrails(new Point(i, j), map);
                }
            }
        }

        return sum;
    }

    private static List<List<Integer>> readMap(List<String> lines) {
        List<List<Integer>> map = new ArrayList<>();
        for (String line : lines) {
            List<Integer> elements = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                elements.add(line.charAt(i) - '0');
            }
            map.add(elements);
        }
        return map;
    }

    private static int calculateReachableNines(Point point, List<List<Integer>> map) {
        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.add(point);

        Set<Point> visited = new HashSet<>();
        int count = 0;
        while (!queue.isEmpty()) {
            Point top = queue.pop();
            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(top.x + direction.x, top.y + direction.y);
                if (isValid(newPoint, map) && isIncreasingByOne(map, top, newPoint) && !visited.contains(newPoint)) {
                    if (map.get(newPoint.y).get(newPoint.x) != 9) {
                        queue.add(newPoint);
                    } else {
                        count++;
                    }
                    visited.add(newPoint);

                }
            }
        }
        return count;
    }

    private static int calculateAvailableTrails(Point point, List<List<Integer>> map) {
        ArrayDeque<Trail> queue = new ArrayDeque<>();
        Set<Point> visited = new HashSet<>();
        queue.add(new Trail(point, visited));


        int count = 0;
        while (!queue.isEmpty()) {
            Trail topTrail = queue.pop();
            Point top = topTrail.point;
            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(top.x + direction.x, top.y + direction.y);
                if (isValid(newPoint, map)
                        && isIncreasingByOne(map, top, newPoint)
                        && !visited.contains(newPoint)) {
                    if (map.get(newPoint.y).get(newPoint.x) != 9) {
                        Set<Point> newVisited = new HashSet<>(visited);
                        newVisited.add(newPoint);
                        queue.add(new Trail(newPoint, newVisited));
                    } else {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private static boolean isValid(Point newPoint, List<List<Integer>> elements) {
        if (newPoint.x < 0 || newPoint.y < 0) {
            return false;
        }
        if (newPoint.x >= elements.size()) {
            return false;
        }
        if (newPoint.y >= elements.get(newPoint.x).size()) {
            return false;
        }

        return true;
    }

    private static boolean isIncreasingByOne(List<List<Integer>> map, Point previousPoint, Point newPoint) {
        return map.get(previousPoint.y).get(previousPoint.x) + 1 == map.get(newPoint.y).get(newPoint.x);
    }
}
