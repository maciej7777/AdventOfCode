package com.example.adventofcode.day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BlizzardBasin {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/day24/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/day24/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateTimeToLeaveTheValley(EXAMPLE_FILENAME));
        System.out.println(calculateTimeToLeaveTheValley(FILENAME));

        System.out.println(calculateTimeToReachAllThePoints(EXAMPLE_FILENAME));
        System.out.println(calculateTimeToReachAllThePoints(FILENAME));

    }

    public static int calculateTimeToLeaveTheValley(String fileName) throws IOException {
        Input input = readInput(fileName);

        Point exit = new Point(input.maxWallX - 1, input.maxWallY);

        Deque<Point> goals = new ArrayDeque<>();
        goals.add(exit);

        return calculateTimeToReachGoals(input, goals);
    }

    public static int calculateTimeToReachAllThePoints(String fileName) throws IOException {
        Input input = readInput(fileName);

        Point start = new Point(1, 0);
        Point exit = new Point(input.maxWallX - 1, input.maxWallY);

        Deque<Point> goals = new ArrayDeque<>();
        goals.add(exit);
        goals.add(start);
        goals.add(exit);

        return calculateTimeToReachGoals(input, goals);
    }

    private record Point(int x, int y) {
    }

    private enum Direction {
        N, E, S, W
    }

    private record Blizzard(Point point, Direction direction){}
    
    private record Input(Set<Blizzard> blizzards, Set<Point> walls, int maxWallX, int maxWallY) {
    }

    private static Input readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Set<Blizzard> blizzards = new HashSet<>();
            Set<Point> walls = new HashSet<>();
            int lineNumber = 0;
            int maxWallX = 0;
            int maxWallY = 0;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split("");
                for (int i = 0; i < line.length(); i++) {
                    switch (elements[i]) {
                        case "<" -> blizzards.add(new Blizzard(new Point(i, lineNumber), Direction.W));
                        case ">" -> blizzards.add(new Blizzard(new Point(i, lineNumber), Direction.E));
                        case "^" -> blizzards.add(new Blizzard(new Point(i, lineNumber), Direction.N));
                        case "v" -> blizzards.add(new Blizzard(new Point(i, lineNumber), Direction.S));
                        case "#" -> {
                            if (maxWallX < i) {
                                maxWallX = i;
                            }
                            if (maxWallY < lineNumber) {
                                maxWallY = lineNumber;
                            }
                            walls.add(new Point(i, lineNumber));
                        }
                    }
                }
                lineNumber++;
            }

            walls.add(new Point(1, -1));
            walls.add(new Point(maxWallX - 1, maxWallY + 1));
            return new Input(blizzards, walls, maxWallX, maxWallY);
        }
    }

    private static final Point[] moves = {
            new Point(0, 0),
            new Point(0, -1),
            new Point(0, 1),
            new Point(-1, 0),
            new Point(1, 0)
    };

    private static int calculateTimeToReachGoals(Input input, Deque<Point> goals) {

        Point start = new Point(1, 0);
        Set<Blizzard> blizzards = input.blizzards;
        Set<Point> currentPositions = new HashSet<>();
        currentPositions.add(start);

        int time = 0;
        while (!goals.isEmpty()) {
            time++;
            blizzards = calculateNewBlizzardPositions(input, blizzards);
            currentPositions = calculateNewPositions(input.walls, blizzards, currentPositions);

            for (Point currentPosition: currentPositions) {
                if (currentPosition.equals(goals.getFirst())) {
                    currentPositions = Set.of(goals.getFirst());
                    goals.pop();
                    break;
                }
            }

        }

        return time;
    }

    private static Set<Point> calculateNewPositions(Set<Point> walls, Set<Blizzard> blizzards, Set<Point> currentPositions) {
        Set<Point> tmpPositions = new HashSet<>();
        Set<Point> blizzardPoints = blizzards.stream().map(blizzard -> blizzard.point).collect(Collectors.toSet());
        for (Point position : currentPositions) {
            for (Point move : moves) {
                Point newPoint = new Point(position.x + move.x, position.y + move.y);

                if (!walls.contains(position) && !blizzardPoints.contains(newPoint)) {
                    tmpPositions.add(newPoint);
                }
            }
        }
        currentPositions = tmpPositions;
        return currentPositions;
    }

    private static Set<Blizzard> calculateNewBlizzardPositions(Input input, Set<Blizzard> blizzards) {
        Set<Blizzard> newBlizzards = new HashSet<>();
        for (Blizzard blizzard : blizzards) {
            switch (blizzard.direction) {
                case N -> {
                    int y = blizzard.point.y - 1;
                    if (y == 0) {
                        y = input.maxWallY - 1;
                    }
                    newBlizzards.add(new Blizzard(new Point(blizzard.point().x, y), blizzard.direction));
                }
                case E -> {
                    int x = blizzard.point.x + 1;
                    if (x == input.maxWallX) {
                        x = 1;
                    }
                    newBlizzards.add(new Blizzard(new Point(x, blizzard.point.y), blizzard.direction));
                }
                case W -> {
                    int x = blizzard.point.x - 1;
                    if (x == 0) {
                        x = input.maxWallX - 1;
                    }
                    newBlizzards.add(new Blizzard(new Point(x, blizzard.point.y), blizzard.direction));
                }
                case S -> {
                    int y = blizzard.point.y + 1;
                    if (y == input.maxWallY) {
                        y = 1;
                    }
                    newBlizzards.add(new Blizzard(new Point(blizzard.point.x, y), blizzard.direction));
                }
            }
        }
        return newBlizzards;
    }
}
