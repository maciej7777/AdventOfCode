package com.example.adventofcode.year2022.day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BeaconExclusionZone {
    private static final String FILENAME = "AdventOfCodeData/2022day15/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2022day15/example_input";
    public static final int SEARCHING_AREA = 1000000;

    public static void main(String[] args) throws IOException {
        Map<Point, Point> input = readInput(FILENAME);
        Map<Point, Point> exampleInput = readInput(EXAMPLE_FILENAME);

        System.out.println("Example Result: " + countEmptyPositions(exampleInput, 10));
        System.out.println("Result: " + countEmptyPositions(input, 2000000));

        System.out.println("Example Result2: " + calculateTuningFrequency(exampleInput, 20));
        System.out.println("Result2: " + calculateTuningFrequency(input, 4000000));
    }

    record Point(int x, int y) {
    }

    public static Map<Point, Point> readInput(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Map<Point, Point> sensorBeaconMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.replace("Sensor at x=", "");
                line = line.replace("y=", "");
                line = line.replace(": closest beacon is at x=", ",");
                line = line.replace(", ", ",");

                String[] elements = line.split(",");

                Point sensor = new Point(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]));
                Point beacon = new Point(Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));

                sensorBeaconMap.put(sensor, beacon);
            }
            return sensorBeaconMap;
        }
    }

    public static int obtainEmptyPositions(String fileName, int row) throws IOException {
        Map<Point, Point> input = readInput(fileName);
        return countEmptyPositions(input, row);
    }

    public static long obtainTuningFrequency(String fileName, int range) throws IOException {
        Map<Point, Point> input = readInput(fileName);
        return calculateTuningFrequency(input, range);
    }

    private static int countEmptyPositions(Map<Point, Point> sensorBeaconMap, int row) {

        int maxX = -Integer.MAX_VALUE;
        int minX = Integer.MAX_VALUE;

        Set<Point> beacons = new HashSet<>();
        Set<Point> sensors = new HashSet<>();
        for (Map.Entry<Point, Point> entry : sensorBeaconMap.entrySet()) {
            if (entry.getKey().x > maxX) {
                maxX = entry.getKey().x;
            }

            if (entry.getKey().x < minX) {
                minX = entry.getKey().x;
            }

            sensors.add(entry.getKey());
            beacons.add(entry.getValue());

            if (entry.getValue().x > maxX) {
                maxX = entry.getValue().x;
            }

            if (entry.getValue().x < minX) {
                minX = entry.getValue().x;
            }
        }

        int count = 0;
        for (int i = minX - SEARCHING_AREA; i <= maxX + SEARCHING_AREA; i++) {
            Point pointToValidate = new Point(i, row);
            if (!sensors.contains(pointToValidate)
                    && !beacons.contains(pointToValidate)
                    && isPointCloserToAnySensor(sensorBeaconMap, pointToValidate)) {
                count++;
            }
        }

        return count;
    }

    private static long calculateTuningFrequency(Map<Point, Point> sensorBeconMap, int biggestX) {
        Set<Point> becons = new HashSet<>();
        Set<Point> sensors = new HashSet<>();
        Set<Point> candidates = new HashSet<>();
        for (Map.Entry<Point, Point> entry : sensorBeconMap.entrySet()) {
            sensors.add(entry.getKey());
            becons.add(entry.getValue());

            int manhatan = countManhattanDistance(entry.getKey(), entry.getValue());
            manhatan++;

            for (int i = 0; i < manhatan; i++) {
                candidates.add(new Point(entry.getKey().x + i, entry.getKey().y + manhatan - i));
                candidates.add(new Point(entry.getKey().x - i, entry.getKey().y - manhatan + i));
                candidates.add(new Point(entry.getKey().x + manhatan - i, entry.getKey().y + i));
                candidates.add(new Point(entry.getKey().x - manhatan + i, entry.getKey().y - i));
            }
        }

        long tuningFrequency = 0L;
        for (Point candidate : candidates) {
            if (!sensors.contains(candidate)
                    && !becons.contains(candidate)
                    && !pointIsOutOfBox(biggestX, candidate)
                    && !isPointCloserToAnySensor(sensorBeconMap, candidate)) {
                tuningFrequency = 4000000L * candidate.x + candidate.y;
            }
        }

        return tuningFrequency;
    }

    private static boolean pointIsOutOfBox(int biggestX, Point candidate) {
        return candidate.x < 0 || candidate.x > biggestX || candidate.y < 0 || candidate.y > biggestX;
    }


    private static boolean isPointCloserToAnySensor(Map<Point, Point> sensorBeconMap, Point pointToValidate) {
        for (Map.Entry<Point, Point> entry : sensorBeconMap.entrySet()) {
            if (isPointCloserToTheSensor(pointToValidate, entry.getKey(), entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isPointCloserToTheSensor(Point newPoint, Point sensor, Point closestBecon) {
        return countManhattanDistance(newPoint, sensor) <= countManhattanDistance(closestBecon, sensor);
    }

    private static int countManhattanDistance(Point point1, Point point2) {
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }
}
