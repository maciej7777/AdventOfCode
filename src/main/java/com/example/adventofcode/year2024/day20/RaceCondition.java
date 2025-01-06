package com.example.adventofcode.year2024.day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RaceCondition {
    private static final String FILENAME = "AdventOfCodeData/2024/day20/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2024/day20/example_input";

    private record Input(Point start, Point end, List<Point> walls) {
    }

    private static final List<Point> DIRECTIONS = List.of(
            new Point(1, 0),
            new Point(-1, 0),
            new Point(0, 1),
            new Point(0, -1));
    
    public static void main(String[] args) throws IOException {
        System.out.println(calculateCheats(EXAMPLE_FILENAME, 2, 12));
        System.out.println(calculateCheats(FILENAME, 2, 100));
        System.out.println(calculateCheats(EXAMPLE_FILENAME, 20, 72));
        System.out.println(calculateCheats(EXAMPLE_FILENAME, 20, 76));
        System.out.println(calculateCheats(FILENAME, 20, 100));
    }


    record Point(int x, int y) {
    }

    record MazeState(Point point, int currentCost, List<Point> path) {
    }

    public static long calculateCheats(final String filename, int cheatLength, int savedTime) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);

        List<Point> shortestPath = calculateShortestPath(input.start, input.walls, input.end);
        int count = 0;

        for (int i = 0; i < shortestPath.size(); i++) {
            for (int j = i + savedTime + 2; j < shortestPath.size(); j++) {
                int distance = calculateDistance(shortestPath.get(i), shortestPath.get(j));
                if (distance <= cheatLength && j - i - distance >= savedTime) {
                    count++;
                }
            }
        }

        return count;
    }

    private static int calculateDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
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
        Point start = new Point(0, 0);
        Point end = new Point(0, 0);

        List<Point> walls = new ArrayList<>();
        int j = 0;
        for (String line: lines) {
            for (int i = 0; i < line.length(); i++) {
                char tmp = line.charAt(i);
                if (tmp == 'S') {
                    start = new Point(i, j);
                } else if (tmp == 'E') {
                    end = new Point(i, j);
                } else if (tmp == '#') {
                    walls.add(new Point(i, j));
                }
            }
            j++;
        }

        return new Input(start, end, walls);
    }

    private static List<Point> calculateShortestPath(Point start, List<Point> corruptedSegments, Point exit) {
        PriorityQueue<MazeState> toVisit = new PriorityQueue<>(Comparator.comparingInt(state -> state.currentCost));
        Set<Point> visited = new HashSet<>();

        List<Point> startPath = new ArrayList<>();
        startPath.add(start);
        toVisit.add(new MazeState(start, 0, new ArrayList<>(startPath)));

        while (!toVisit.isEmpty()) {
            MazeState state = toVisit.poll();
            if (visited.contains(state.point)) {
                continue;
            }
            visited.add(state.point);
            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(state.point.x + direction.x, state.point.y + direction.y);
                if (!visited.contains(newPoint) && !corruptedSegments.contains(newPoint)) {
                    int cost = state.currentCost + 1;
                    List<Point> path = new ArrayList<>(state.path);
                    path.add(newPoint);
                    if (newPoint.equals(exit)) {
                        return path;
                    } else {
                        toVisit.add(new MazeState(newPoint, cost, path));
                    }
                }
            }
        }

        return List.of();
    }
}
