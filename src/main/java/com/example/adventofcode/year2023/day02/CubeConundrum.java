package com.example.adventofcode.year2023.day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CubeConundrum {
    private static final String FILENAME = "AdventOfCodeData/2023/day02/input";
    private static final String EXAMPLE_FILENAME = "AdventOfCodeData/2023/day02/example_input";


    private static final Map<String, Integer> limits = Map.of(
            "red", 12,
            "green", 13,
            "blue", 14
    );

    public static void main(String[] args) throws IOException {
        System.out.println(sumPossibleGamesIds(EXAMPLE_FILENAME));
        System.out.println(sumPossibleGamesIds(FILENAME));

        System.out.println(sumPowerOfSetsOfCubes(EXAMPLE_FILENAME));
        System.out.println(sumPowerOfSetsOfCubes(FILENAME));
    }

    public static int sumPossibleGamesIds(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        int sum = 0;
        int gameId = 1;

        for (String line : lines) {
            String[] colours = getColours(line);
            if (isValidGame(colours)) {
                sum += gameId;
            }
            gameId++;
        }

        return sum;
    }

    public static int sumPowerOfSetsOfCubes(final String filename) throws IOException {
        List<String> lines = readLines(filename);

        int sum = 0;
        for (String line : lines) {
            String[] colours = getColours(line);

            Map<String, Integer> max = buildMaxColourOccurrences(colours);
            int gamePower = max.getOrDefault("red", 0) *
                    max.getOrDefault("green", 0) *
                    max.getOrDefault("blue", 0);

            sum += gamePower;
        }

        return sum;
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

    private static String[] getColours(String line) {
        String[] entry = line.split(": ");
        String entry2 = entry[1].replace(",", "").replace(";", "");

        return entry2.split(" ");
    }

    private static boolean isValidGame(String[] colours) {
        for (int i = 0; i < colours.length; i += 2) {
            String colour = colours[i + 1];
            Integer count = Integer.parseInt(colours[i]);
            if (limits.getOrDefault(colour, 200) < count) {
                return false;
            }
        }
        return true;
    }

    private static Map<String, Integer> buildMaxColourOccurrences(String[] colours) {
        Map<String, Integer> max = new HashMap<>();
        for (int i = 0; i < colours.length; i += 2) {
            String colour = colours[i + 1];
            Integer count = Integer.parseInt(colours[i]);
            if (max.getOrDefault(colour, 0) < count) {
                max.put(colour, count);
            }
        }
        return max;
    }
}
