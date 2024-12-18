package com.example.adventofcode.year2024.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RAMRun {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day18/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day18/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateMinimumStepsToExit(EXAMPLE_FILENAME, 12, 6));
        System.out.println(calculateMinimumStepsToExit(FILENAME, 1224, 70));
        System.out.println(calculateCoordinatesOfFirstBlockingByte(EXAMPLE_FILENAME, 6));
        System.out.println(calculateCoordinatesOfFirstBlockingByte(FILENAME, 70));
    }

    public record Point(int x, int y) {
    }

    private static final List<Point> DIRECTIONS = List.of(
            new Point(1, 0),
            new Point(-1, 0),
            new Point(0, 1),
            new Point(0, -1));

    record MazeState(Point point, int currentCost) {
    }


    public static long calculateMinimumStepsToExit(final String filename, int fallenBytes, int size) throws IOException {
        List<String> lines = readLines(filename);
        List<Point> corruptedSegments = parseCorruptedSegments(lines);

        Point start = new Point(0, 0);
        Point exit = new Point(size, size);

        return calculateShortestPath(size, start, corruptedSegments.subList(0, fallenBytes), exit);
    }

    public static Point calculateCoordinatesOfFirstBlockingByte(final String filename, int size) throws IOException {
        List<String> lines = readLines(filename);
        List<Point> corruptedSegments = parseCorruptedSegments(lines);

        Point start = new Point(0, 0);
        Point exit = new Point(size, size);

        int minCorruptedBlock = findFirstBlockingByteIndex(size, corruptedSegments, start, exit);

        return corruptedSegments.get(minCorruptedBlock-1);
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

    private static List<Point> parseCorruptedSegments(List<String> lines) {
        List<Point> corruptedSegments = new ArrayList<>();
        for (String line: lines) {
            String[] splitted = line.split(",");
            corruptedSegments.add(new Point(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
        }
        return corruptedSegments;
    }

    private static int calculateShortestPath(int size, Point start, List<Point> corruptedSegments, Point exit) {
        PriorityQueue<MazeState> toVisit = new PriorityQueue<>(Comparator.comparingInt(state -> state.currentCost));
        Set<Point> visited = new HashSet<>();

        int bestResult = -1;
        toVisit.add(new MazeState(start, 0));

        while (!toVisit.isEmpty()) {
            MazeState state = toVisit.poll();
            if (visited.contains(state.point)) {
                continue;
            }
            visited.add(state.point);
            for (Point direction : DIRECTIONS) {
                Point newPoint = new Point(state.point.x + direction.x, state.point.y + direction.y);
                if (!visited.contains(newPoint) && isValidPoint(newPoint.x, newPoint.y, size) && !corruptedSegments.contains(newPoint)) {
                    int cost = state.currentCost + 1;
                    if (newPoint.equals(exit)) {
                        return cost;
                    } else {
                        toVisit.add(new MazeState(newPoint, cost));
                    }
                }
            }
        }

        return bestResult;
    }

    private static boolean isValidPoint(int newX1, int newY1, int size) {
        return newX1 >= 0 && newX1 <= size && newY1 >= 0 && newY1 <= size;
    }

    private static int findFirstBlockingByteIndex(int size, List<Point> corruptedSegments, Point start, Point exit) {
        int minCorruptedBlock = 0;
        int maxCorruptedBlock = corruptedSegments.size() - 1;

        while (minCorruptedBlock < maxCorruptedBlock) {
            int middleCorruptedBlock = (minCorruptedBlock+maxCorruptedBlock)/2;
            List<Point> newCorruptedSegments = corruptedSegments.subList(0, middleCorruptedBlock);
            if (calculateShortestPath(size, start, newCorruptedSegments, exit) == -1) {
                maxCorruptedBlock = middleCorruptedBlock;
            } else {
                minCorruptedBlock = middleCorruptedBlock + 1;
            }
        }
        return minCorruptedBlock;
    }
}
