package com.example.adventofcode.year2023.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ClumsyCrucible {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2023/day17/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2023/day17/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateHeatLoss(EXAMPLE_FILENAME));
        //102
        System.out.println(calculateHeatLoss(FILENAME));
        //817
        System.out.println(calculateUltraCrucibleHeatLoss(EXAMPLE_FILENAME));
        //94
        System.out.println(calculateUltraCrucibleHeatLoss(FILENAME));
        //925
    }


    record Point(int x, int y) {
    }

    private static final List<Point> DIRECTIONS = List.of(new Point(-1, 0), new Point(0, 1), new Point(1, 0), new Point(0, -1));

    static class HeatState implements Comparable<HeatState> {

        Point currentPoint;
        Integer currentHeat;
        int direction;
        int stepsInDirection;

        public HeatState(Point currentPoint, Integer currentHeat, int direction, int stepsInDirection) {
            this.currentPoint = currentPoint;
            this.currentHeat = currentHeat;
            this.direction = direction;
            this.stepsInDirection = stepsInDirection;
        }

        @Override
        public int compareTo(HeatState other) {
            return this.currentHeat - other.currentHeat;
        }
    }

    record Path(Point currentPoint, int direction, int lineLength) {
    }

    public static long calculateHeatLoss(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Integer>> lavaMap = buildLavaMap(lines);
        Map<Path, Integer> heatMap = buildHeatMap(lavaMap, false);

        Point endPoint = new Point(lavaMap.size() - 1, lavaMap.getFirst().size() - 1);
        return calculateMinimumHeatAtPoint(endPoint, heatMap);
    }

    public static long calculateUltraCrucibleHeatLoss(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<List<Integer>> lavaMap = buildLavaMap(lines);
        Map<Path, Integer> heatMap = buildHeatMap(lavaMap, true);

        Point endPoint = new Point(lavaMap.size() - 1, lavaMap.getFirst().size() - 1);
        return calculateMinimumHeatAtPoint(endPoint, heatMap);
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

    private static List<List<Integer>> buildLavaMap(List<String> lines) {
        List<List<Integer>> caveMap = new ArrayList<>();
        for (String line : lines) {
            List<Integer> tmpList = new ArrayList<>();
            for (int j = 0; j < line.length(); j++) {
                tmpList.add(Integer.parseInt(String.valueOf(line.charAt(j))));
            }
            caveMap.add(tmpList);
        }
        return caveMap;
    }

    private static Map<Path, Integer> buildHeatMap(List<List<Integer>> lavaMap, boolean ultraCrucible) {
        Map<Path, Integer> heatMap = new HashMap<>();
        Queue<HeatState> queue = new PriorityQueue<>();
        queue.add(new HeatState(new Point(0, 0), 0, -1, -1));

        while (!queue.isEmpty()) {
            HeatState top = queue.poll();
            Path currentPath = new Path(top.currentPoint, top.direction, top.stepsInDirection);
            if (heatMap.containsKey(currentPath) && heatMap.get(currentPath) <= top.currentHeat) {
                continue;
            }
            heatMap.put(currentPath, top.currentHeat);

            Point current = top.currentPoint;
            for (int i = 0; i < 4; i++) {
                Point nextPoint = new Point(current.x + DIRECTIONS.get(i).x, current.y + DIRECTIONS.get(i).y);

                int stepsInDirection = 1;
                if (i == top.direction) {
                    stepsInDirection += top.stepsInDirection;
                }

                if (isInRange(lavaMap, nextPoint) || isReverse(i, top)) {
                    continue;
                }
                if ((!ultraCrucible && isValidLavaPath(stepsInDirection))
                                || (ultraCrucible && isValidUltraCruciblePath(stepsInDirection, i, top))){
                    queue.add(new HeatState(nextPoint, top.currentHeat + lavaMap.get(nextPoint.y).get(nextPoint.x), i, stepsInDirection));
                }
            }
        }
        return heatMap;
    }

    private static boolean isInRange(List<List<Integer>> lavaMap, Point nextPoint) {
        return nextPoint.y < 0 || nextPoint.y >= lavaMap.size() || nextPoint.x < 0 || nextPoint.x >= lavaMap.getFirst().size();
    }

    private static boolean isValidLavaPath(int stepsInDirection) {
        return stepsInDirection <= 3;
    }

    private static boolean isValidUltraCruciblePath(int stepsInDirection, int i, HeatState top) {
        return stepsInDirection <= 10 && (i == top.direction || top.stepsInDirection >= 4 || top.stepsInDirection == -1);
    }

    private static boolean isReverse(int i, HeatState top) {
        return ((i + 2) % 4) == top.direction;
    }

    private static int calculateMinimumHeatAtPoint(Point endPoint, Map<Path, Integer> heatMap) {
        int result = Integer.MAX_VALUE;
        for (Map.Entry<Path, Integer> heatMapEntry : heatMap.entrySet()) {
            if (heatMapEntry.getKey().currentPoint.equals(endPoint)) {
                int totalHeat = heatMapEntry.getValue();
                if (totalHeat < result) {
                    result = totalHeat;
                }
            }
        }
        return result;
    }
}
