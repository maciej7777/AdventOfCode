package com.example.adventofcode.year2023.day06;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WaitForIt {
    private static final String FILENAME = "src/main/java/com/example/adventofcode/year2023/day06/input";
    private static final String EXAMPLE_FILENAME = "src/main/java/com/example/adventofcode/year2023/day06/example_input";


    public static void main(String[] args) throws IOException {
        System.out.println(calculateProductOfWinningCombinations(EXAMPLE_FILENAME));
        System.out.println(calculateProductOfWinningCombinations(FILENAME));
        System.out.println(calculateWinningCombinations(EXAMPLE_FILENAME));
        System.out.println(calculateWinningCombinations(FILENAME));
        System.out.println(calculateWinningCombinationsBruteForce(EXAMPLE_FILENAME));
        System.out.println(calculateWinningCombinationsBruteForce(FILENAME));
    }

    private record Race(long time, long distance) {
    }

    public static long calculateProductOfWinningCombinations(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        List<Race> races = mapRaces(lines);

        long wins = 1L;
        for (Race race : races) {
            wins *= countWaysToWin(race);
        }
        return wins;
    }

    public static long calculateWinningCombinations(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Race race = mapRace(lines);

        return countWaysToWinFast(race);
    }

    public static Long calculateWinningCombinationsBruteForce(final String filename) throws IOException {
        List<String> lines = readLines(filename);
        Race race = mapRace(lines);

        return countWaysToWin(race);
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

    private static List<Race> mapRaces(List<String> lines) {
        List<Race> races = new ArrayList<>();

        String[] times = new String[0];
        String[] distances = new String[0];
        for (String line : lines) {
            if (line.startsWith("Time:")) {
                line = line.substring(5);
                times = StringUtils.normalizeSpace(line).split(" ");
            } else {
                line = line.substring(9);
                distances = StringUtils.normalizeSpace(line).split(" ");
            }
        }

        for (int i = 0; i < times.length; i++) {
            races.add(new Race(Integer.parseInt(times[i]), Integer.parseInt(distances[i])));
        }
        return races;
    }

    private static Race mapRace(List<String> lines) {
        String time = null;
        String distance = null;
        for (String line : lines) {
            if (line.startsWith("Time:")) {
                time = line.substring(5).replace(" ", "");
            } else {
                distance = line.substring(9).replace(" ", "");
            }
        }

        return new Race(Long.parseLong(time), Long.parseLong(distance));
    }

    private static long countWaysToWin(Race race) {
        long waysToWin = 0;
        for (int t = 1; t < race.time; t++) {
            long remainingTime = race.time - t;
            long distanceTraveled = remainingTime * t;

            if (distanceTraveled > race.distance) {
                waysToWin++;
            }
        }
        return waysToWin;
    }

    private static long countWaysToWinFast(Race race) {
        long firstWins = 0;
        long lastWins = Long.MAX_VALUE;

        for (long t = 1L; t < race.time; t++) {
            long remainingTime = race.time - t;
            long distanceTraveled = remainingTime * t;

            if (distanceTraveled > race.distance) {
                firstWins = t;
                break;
            }
        }

        for (long t = race.time; t > 0; t--) {
            long remainingTime = race.time - t;
            long distanceTraveled = remainingTime * t;

            if (distanceTraveled > race.distance) {
                lastWins = t;
                break;
            }
        }

        return lastWins - firstWins + 1;
    }
}
