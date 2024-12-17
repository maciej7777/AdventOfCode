package com.example.adventofcode.year2024.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReindeerMaze {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day16/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day16/example_input";
    private static final String EXAMPLE_FILENAME2 = "src/main/java/com/example/adventofcode/year2024/day16/example_input2";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateBestReindeerScore(EXAMPLE_FILENAME));
        System.out.println(calculateBestReindeerScore(EXAMPLE_FILENAME2));
        System.out.println(calculateBestReindeerScore(FILENAME));
        System.out.println(calculateTilesOnTheBestPaths(EXAMPLE_FILENAME));
        System.out.println(calculateTilesOnTheBestPaths(EXAMPLE_FILENAME2));
        System.out.println(calculateTilesOnTheBestPaths(FILENAME));
    }

    record Point(int x, int y) {
    }

    private record Input(Point start, Point end, Set<Point> walls) {
    }

    record PointDirection(Point point, Point direction) {
    }

    record MazeState(Point point, Point direction, int currentCost) {
    }

    record MazeState2(Point point, Point direction, Set<Point> path, int currentCost) {
    }

    private static final List<Point> DIRECTIONS = List.of(
            new Point(1, 0),
            new Point(-1, 0),
            new Point(0, 1),
            new Point(0, -1));
    
    public static int calculateBestReindeerScore(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = getInput(lines);

        Set<PointDirection> visited = new HashSet<>();
        PriorityQueue<MazeState> toVisit = new PriorityQueue<>(Comparator.comparingInt(state -> state.currentCost));
        toVisit.add(new MazeState(input.start(), DIRECTIONS.getFirst(), 0));

        int bestResult = -1;

        while(!toVisit.isEmpty()) {
            MazeState state = toVisit.poll();
            visited.add(new PointDirection(state.point, state.direction));
            for (Point direction: DIRECTIONS) {
                Point newPoint = new Point(state.point.x + direction.x, state.point.y + direction.y);
                if (!visited.contains(new PointDirection(newPoint, direction))
                        && !input.walls().contains(newPoint)) {
                    int cost = state.currentCost + 1;
                    if (direction != state.direction) {
                        cost += 1000;
                    }
                    if (newPoint.equals(input.end())) {
                        if (cost < bestResult || bestResult == -1) {
                            bestResult = cost;
                        }
                    } else if(cost < bestResult || bestResult == -1) {
                        toVisit.add(new MazeState(newPoint, direction, cost));
                    }
                }
            }
        }

        return bestResult;
    }

    public static int calculateTilesOnTheBestPaths(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = getInput(lines);

        Set<PointDirection> visited = new HashSet<>();
        PriorityQueue<MazeState2> toVisit = new PriorityQueue<>(Comparator.comparingInt(a -> a.currentCost));
        toVisit.add(new MazeState2(input.start(), DIRECTIONS.getFirst(), new HashSet<>(), 0));

        int bestResult = -1;
        Set<Point> bestPath = new HashSet<>();

        while(!toVisit.isEmpty()) {
            MazeState2 state = toVisit.poll();
            visited.add(new PointDirection(state.point, state.direction));
            state.path.add(state.point);
            for (Point direction: DIRECTIONS) {
                Point newPoint = new Point(state.point.x + direction.x, state.point.y + direction.y);
                if (!visited.contains(new PointDirection(newPoint, direction)) && !input.walls().contains(newPoint)) {
                    int cost = state.currentCost + 1;
                    if (direction != state.direction) {
                        cost += 1000;
                    }
                    if (newPoint.equals(input.end())) {
                        if (cost < bestResult || bestResult == -1) {
                            bestResult = cost;
                            bestPath = state.path;
                        } else if (cost == bestResult) {
                            bestPath.addAll(state.path);
                        }
                    } else if(cost < bestResult || bestResult == -1) {
                        toVisit.add(new MazeState2(newPoint, direction, new HashSet<>(state.path), cost));
                    }
                }
            }
        }

        return bestPath.size() + 1;
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

    private static Input getInput(List<String> lines) {

        Point start = new Point(0, 0);
        Point end = new Point(0, 0);
        Set<Point> walls = new HashSet<>();
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
}
