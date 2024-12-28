package com.example.adventofcode.year2024.day08;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ResonantCollinearity {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2024/day08/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2024/day08/example_input";

    public static void main(String[] args) throws IOException {
        System.out.println(countAntinodeLocations(EXAMPLE_FILENAME));
        System.out.println(countAntinodeLocations(FILENAME));
        System.out.println(countAntinodeLocationsWithResonantHarmonics(EXAMPLE_FILENAME));
        System.out.println(countAntinodeLocationsWithResonantHarmonics(FILENAME));
    }


    record Point(int x, int y) {
    }

    private record Input(HashMap<Character, List<Point>> mapRepresentation, int maxX, int maxY) {
    }

    public static int countAntinodeLocations(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);
        Set<Point> locations = calculateAntinodeLocations(input);

        return locations.size();
    }

    public static int countAntinodeLocationsWithResonantHarmonics(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Input input = parseInput(lines);

        Set<Point> locations = calculateAntinodeLocationsWithResonantHarmonics(input);

        return locations.size();
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

        return new Input(mapRepresentation, maxX, maxY - 1);
    }

    private static Set<Point> calculateAntinodeLocations(Input input) {
        Set<Point> locations = new HashSet<>();
        for (List<Point> positions : input.mapRepresentation().values()) {
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int diffX = positions.get(i).x - positions.get(j).x;
                    int diffY = positions.get(i).y - positions.get(j).y;

                    int newX1 = positions.get(i).x + diffX;
                    int newY1 = positions.get(i).y + diffY;

                    if (isValidPoint(newX1, newY1, input.maxX(), input.maxY())) {
                        locations.add(new Point(newX1, newY1));
                    }

                    int newX2 = positions.get(j).x - diffX;
                    int newY2 = positions.get(j).y - diffY;

                    if (isValidPoint(newX2, newY2, input.maxX(), input.maxY())) {
                        locations.add(new Point(newX2, newY2));
                    }
                }
            }
        }
        return locations;
    }

    private static boolean isValidPoint(int newX1, int newY1, int maxX, int maxY) {
        return newX1 >= 0 && newX1 <= maxX && newY1 >= 0 && newY1 <= maxY;
    }

    private static Set<Point> calculateAntinodeLocationsWithResonantHarmonics(Input input) {
        Set<Point> locations = new HashSet<>();
        for (List<Point> positions : input.mapRepresentation.values()) {
            for (int i = 0; i < positions.size(); i++) {
                for (int j = i + 1; j < positions.size(); j++) {
                    int diffX = positions.get(i).x - positions.get(j).x;
                    int diffY = positions.get(i).y - positions.get(j).y;

                    int newX1 = positions.get(i).x + diffX;
                    int newY1 = positions.get(i).y + diffY;
                    while (isValidPoint(newX1, newY1, input.maxX, input.maxY)) {
                        locations.add(new Point(newX1, newY1));
                        newX1 += diffX;
                        newY1 += diffY;
                    }

                    int newX2 = positions.get(j).x - diffX;
                    int newY2 = positions.get(j).y - diffY;
                    while (isValidPoint(newX2, newY2, input.maxX, input.maxY)) {
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
        return locations;
    }
}
