package com.example.adventofcode.year2024.day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//TODO refactor this class
public class Day08 {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day08/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day08/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(calculateSolution1(EXAMPLE_FILENAME));
        System.out.println(calculateSolution1(FILENAME));
        System.out.println(calculateSolution2(EXAMPLE_FILENAME));
        System.out.println(calculateSolution2(FILENAME));
    }


    record Point(int x, int y) {
    }

    public static long calculateSolution1(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        HashMap<Character, List<Point>> mapRepresentation = new HashMap<>();
        int maxX = 0;
        int maxY = 0;
        for (String line : lines) {
            maxX = line.length() - 1;
            for (int i = 0; i < line.length(); i++) {
                char currentElement = line.charAt(i);
                if (currentElement != '.') {
                    List<Point> currentPoints = mapRepresentation.getOrDefault(currentElement, new ArrayList<>());
                    currentPoints.add(new Point(i, maxY));
                    mapRepresentation.put(currentElement, currentPoints);
                }
            }
            maxY++;
        }
        maxY--;


        Set<Point> locations = new HashSet<>();
        for (List<Point> positions : mapRepresentation.values()) {
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int diffX = positions.get(i).x - positions.get(j).x;
                    int diffY = positions.get(i).y - positions.get(j).y;

                    int newX1 = positions.get(i).x + diffX;
                    int newY1 = positions.get(i).y + diffY;

                    if (isValidPoint(newX1, newY1, maxX, maxY)) {
                        locations.add(new Point(newX1, newY1));
                    }

                    int newX2 = positions.get(j).x - diffX;
                    int newY2 = positions.get(j).y - diffY;

                    if (isValidPoint(newX2, newY2, maxX, maxY)) {
                        locations.add(new Point(newX2, newY2));
                    }
                }
            }
        }

        return locations.size();
    }

    private static boolean isValidPoint(int newX1, int newY1, int maxX, int maxY) {
        return newX1 >= 0 && newX1 <= maxX && newY1 >= 0 && newY1 <= maxY;
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

    public static long calculateSolution2(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        HashMap<Character, List<Point>> mapRepresentation = new HashMap<>();
        int maxX = 0;
        int maxY = 0;
        for (String line : lines) {
            maxX = line.length() - 1;
            for (int i = 0; i < line.length(); i++) {
                char currentElement = line.charAt(i);
                if (currentElement != '.') {
                    List<Point> currentPoints = mapRepresentation.getOrDefault(currentElement, new ArrayList<>());
                    currentPoints.add(new Point(i, maxY));
                    mapRepresentation.put(currentElement, currentPoints);
                }
            }
            maxY++;
        }
        maxY--;


        Set<Point> locations = new HashSet<>();
        for (List<Point> positions : mapRepresentation.values()) {
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int diffX = positions.get(i).x - positions.get(j).x;
                    int diffY = positions.get(i).y - positions.get(j).y;

                    int newX1 = positions.get(i).x + diffX;
                    int newY1 = positions.get(i).y + diffY;
                    while (isValidPoint(newX1, newY1, maxX, maxY)) {
                        locations.add(new Point(newX1, newY1));
                        newX1 += diffX;
                        newY1 += diffY;
                    }

                    int newX2 = positions.get(j).x - diffX;
                    int newY2 = positions.get(j).y - diffY;

                    while (isValidPoint(newX2, newY2, maxX, maxY)) {
                        locations.add(new Point(newX2, newY2));
                        newX2 -= diffX;
                        newY2 -= diffY;
                    }
                }
            }

            if (positions.size() > 1) {
                locations.addAll(positions);
            }
        }

        return locations.size();
    }
}
